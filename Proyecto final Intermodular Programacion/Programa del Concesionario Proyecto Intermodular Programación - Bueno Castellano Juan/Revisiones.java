import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Revisiones {

    private int IDRevision;
    private String matriculaCoche;
    private boolean cambioFiltro;
    private boolean cambioAceite;
    private boolean cambioFrenos;
    private String otros;
    private static final Scanner scanner = new Scanner(System.in);

    public Revisiones() {
    }

    public int getIDRevision() {
        return IDRevision;
    }

    public void setIDRevision(int IDRevision) {
        this.IDRevision = IDRevision;
    }

    public String getMatriculaCoche() {
        return matriculaCoche;
    }

    public void setMatriculaCoche(String matriculaCoche) {
        this.matriculaCoche = matriculaCoche;
    }

    public boolean isCambioFiltro() {
        return cambioFiltro;
    }

    public void setCambioFiltro(boolean cambioFiltro) {
        this.cambioFiltro = cambioFiltro;
    }

    public boolean isCambioAceite() {
        return cambioAceite;
    }

    public void setCambioAceite(boolean cambioAceite) {
        this.cambioAceite = cambioAceite;
    }

    public boolean isCambioFrenos() {
        return cambioFrenos;
    }

    public void setCambioFrenos(boolean cambioFrenos) {
        this.cambioFrenos = cambioFrenos;
    }

    public String getOtros() {
        return otros;
    }

    public void setOtros(String otros) {
        this.otros = otros;
    }

    public static Set<Revisiones> mostrarRevisiones(Connection connection) {
        Set<Revisiones> revisiones = new HashSet<>();
        String sql = "SELECT * FROM Revisiones";
        try {
            Statement sentencia = connection.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            while (rs.next()) {
                Revisiones revision = new Revisiones();
                revision.setIDRevision(rs.getInt("IDRevision"));
                revision.setMatriculaCoche(rs.getString("MatriculaCoche"));
                revision.setCambioFiltro(rs.getBoolean("CambioFiltro"));
                revision.setCambioAceite(rs.getBoolean("CambioAceite"));
                revision.setCambioFrenos(rs.getBoolean("CambioFrenos"));
                revision.setOtros(rs.getString("Otros"));

                revisiones.add(revision);
            }
        } catch (SQLException e) {
            System.out.println("Error al crear la lista de revisiones.");
        }
        return revisiones;
    }
    
    public static void mostrarRevision(Connection connection) {
    String sql = "SELECT * FROM Revisiones";
    try {
        Statement sentencia = connection.createStatement();
        ResultSet rs = sentencia.executeQuery(sql);
        while (rs.next()) {
            int IDRevision = rs.getInt("IDRevision");
            String MatriculaCoche = rs.getString("MatriculaCoche");
            boolean CambioFiltro = rs.getBoolean("CambioFiltro");
            boolean CambioAceite = rs.getBoolean("CambioAceite");
            boolean CambioFrenos = rs.getBoolean("CambioFrenos");
            String Otros = rs.getString("Otros");

            System.out.println("ID de revisión: " + IDRevision);
            System.out.println("Matrícula del coche: " + MatriculaCoche);
            System.out.println("Cambio de filtro: " + CambioFiltro);
            System.out.println("Cambio de aceite: " + CambioAceite);
            System.out.println("Cambio de frenos: " + CambioFrenos);
            System.out.println("Otros: " + Otros);
            System.out.println("--------------------");
        }
    } catch (SQLException e) {
        System.out.println("Error al mostrar las revisiones.");
    }
}


    public void anadirRevision(Connection connection) {
        Revisiones revision = new Revisiones();

        // Obtener los valores para cada columna
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el ID de la revisión: ");
        int idRevision = scanner.nextInt();
        revision.setIDRevision(idRevision);

        scanner.nextLine(); // Limpiar el buffer de entrada

        System.out.print("Ingrese la matrícula del coche: ");
        String MatriculaCoche = scanner.nextLine();
        revision.setMatriculaCoche(MatriculaCoche);

        System.out.print("¿Se realizó el cambio de filtro? (true/false): ");
        boolean CambioFiltro = scanner.nextBoolean();
        revision.setCambioFiltro(CambioFiltro);

        System.out.print("¿Se realizó el cambio de aceite? (true/false): ");
        boolean CambioAceite = scanner.nextBoolean();
        revision.setCambioAceite(CambioAceite);

        System.out.print("¿Se realizó el cambio de frenos? (true/false): ");
        boolean CambioFrenos = scanner.nextBoolean();
        revision.setCambioFrenos(CambioFrenos);

        scanner.nextLine(); // Limpiar el buffer de entrada

        System.out.print("Ingrese otros detalles de la revisión: ");
        String Otros = scanner.nextLine();
        revision.setOtros(Otros);

        try {
            revision.save(connection); // Guardar la revisión en la base de datos
            System.out.println("La revisión ha sido añadida exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al añadir la revisión.");
        } finally {
            scanner.close(); // Cerrar el scanner después de su uso
        }
    }

    public void editarRevision(Connection connection) {
        // Solicitar el ID de la revisión a editar
        System.out.print("Ingrese el ID de la revisión a editar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de entrada

        // Crear una nueva revisión con el ID proporcionado
        Revisiones revision = buscarRevisionPorID(connection, id);
        if (revision != null) {
            // Solicitar los nuevos datos de la revisión al usuario
            System.out.print("Ingrese la nueva matrícula del coche: ");
            String MatriculaCoche = scanner.nextLine();
            revision.setMatriculaCoche(MatriculaCoche);

            System.out.print("¿Se realizó el cambio de filtro? (true/false): ");
            boolean CambioFiltro = scanner.nextBoolean();
            revision.setCambioFiltro(CambioFiltro);

            System.out.print("¿Se realizó el cambio de aceite? (true/false): ");
            boolean CambioAceite = scanner.nextBoolean();
            revision.setCambioAceite(CambioAceite);

            System.out.print("¿Se realizó el cambio de frenos? (true/false): ");
            boolean CambioFrenos = scanner.nextBoolean();
            revision.setCambioFrenos(CambioFrenos);

            scanner.nextLine(); // Limpiar el buffer de entrada

            System.out.print("Ingrese los nuevos detalles de la revisión: ");
            String Otros = scanner.nextLine();
            revision.setOtros(Otros);

            try {
                revision.update(connection); // Actualizar la revisión en la base de datos
                System.out.println("La revisión ha sido actualizada exitosamente.");
            } catch (SQLException e) {
                System.out.println("Error al editar la revisión.");
            }
        } else {
            System.out.println("La revisión con ID " + id + " no existe en la base de datos.");
        }
    }

    public void borrarRevision(Connection connection) {
        // Solicitar el ID de la revisión a borrar
        System.out.print("Ingrese el ID de la revisión a borrar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de entrada

        // Comprobar si la revisión existe en la base de datos
        Revisiones revision = buscarRevisionPorID(connection, id);
        if (revision != null) {
            // La revisión existe, se puede borrar
            revision.setIDRevision(id);
            try {
                revision.delete(connection);
                System.out.println("La revisión ha sido borrada exitosamente.");
            } catch (SQLException e) {
                System.out.println("Error al borrar la revisión.");
            }
        } else {
            System.out.println("La revisión con ID " + id + " no existe en la base de datos.");
        }
    }

    public void save(Connection connection) throws SQLException {
        String query = "INSERT INTO Revisiones (IDRevision, MatriculaCoche, CambioFiltro, CambioAceite, CambioFrenos, Otros) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, IDRevision);
            statement.setString(2, matriculaCoche);
            statement.setBoolean(3, cambioFiltro);
            statement.setBoolean(4, cambioAceite);
            statement.setBoolean(5, cambioFrenos);
            statement.setString(6, otros);

            statement.executeUpdate();
        }
    }

    public void update(Connection connection) throws SQLException {
        String query = "UPDATE Revisiones SET MatriculaCoche = ?, CambioFiltro = ?, CambioAceite = ?, CambioFrenos = ?, Otros = ? WHERE IDRevision = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, matriculaCoche);
            statement.setBoolean(2, cambioFiltro);
            statement.setBoolean(3, cambioAceite);
            statement.setBoolean(4, cambioFrenos);
            statement.setString(5, otros);
            statement.setInt(6, IDRevision);

            statement.executeUpdate();
        }
    }

    public void delete(Connection connection) throws SQLException {
        String query = "DELETE FROM Revisiones WHERE IDRevision = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, IDRevision);

            statement.executeUpdate();
        }
    }

    public static Revisiones buscarRevisionPorID(Connection connection, int id) {
        Revisiones revision = null;
        String sql = "SELECT * FROM Revisiones WHERE IDRevision = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                revision = new Revisiones();
                revision.setIDRevision(rs.getInt("IDRevision"));
                revision.setMatriculaCoche(rs.getString("MatriculaCoche"));
                revision.setCambioFiltro(rs.getBoolean("CambioFiltro"));
                revision.setCambioAceite(rs.getBoolean("CambioAceite"));
                revision.setCambioFrenos(rs.getBoolean("CambioFrenos"));
                revision.setOtros(rs.getString("Otros"));
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la revisión por ID.");
        }

        return revision;
    }
}
