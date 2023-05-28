import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Compras {
    private int ID;
    private int IDCompraCliente;
    private String MatriculaCoche;

    public Compras() {
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getIDCompraCliente() {
        return IDCompraCliente;
    }

    public void setIDCompraCliente(int IDCompraCliente) {
        this.IDCompraCliente = IDCompraCliente;
    }

    public String getMatriculaCoche() {
        return MatriculaCoche;
    }

    public void setMatriculaCoche(String MatriculaCoche) {
        this.MatriculaCoche = MatriculaCoche;
    }

    public static Compras buscarCompraPorID(Connection connection, int idCompra) {
        Compras compra = null;
        String sql = "SELECT * FROM Compras WHERE IDCompraCliente = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCompra);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                compra = new Compras();
                compra.setID(rs.getInt("ID"));
                compra.setIDCompraCliente(rs.getInt("IDCompraCliente"));
                compra.setMatriculaCoche(rs.getString("MatriculaCoche"));
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la compra por ID: " + e.getMessage());
        }

        return compra;
    }

    public static Set<Compras> mostrarCompras(Connection connection) {
        Set<Compras> compras = new HashSet<>();
        String sql = "SELECT * FROM Compras";
        try {
            Statement sentencia = connection.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            while (rs.next()) {
                Compras compra = new Compras();
                compra.setID(rs.getInt("ID"));
                compra.setIDCompraCliente(rs.getInt("IDCompraCliente"));
                compra.setMatriculaCoche(rs.getString("MatriculaCoche"));

                compras.add(compra);
            }
        } catch (SQLException e) {
            System.out.println("Error al crear la lista de compras.");
        }
        return compras;
    }

   public void save(Connection connection) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    
    System.out.print("Introduzca el ID de la compra: ");
    int idCompra = scanner.nextInt();
    
    System.out.print("Introduzca el código interno del cliente: ");
    int codigoInternoCliente = scanner.nextInt();
    
    System.out.print("Introduzca la matrícula del coche: ");
    String matriculaCoche = scanner.next();
    
    String query = "INSERT INTO Compras (IDCompraCliente, CodigoInternoCliente, MatriculaCoche) VALUES (?, ?, ?)";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, idCompra);
        statement.setInt(2, codigoInternoCliente);
        statement.setString(3, matriculaCoche);

        statement.executeUpdate();
        System.out.println("Compra agregada correctamente.");
    }
}


    public void update(Connection connection) throws SQLException {
        String query = "UPDATE Compras SET MatriculaCoche = ? WHERE IDCompraCliente = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, MatriculaCoche);
            statement.setInt(2, IDCompraCliente);

            statement.executeUpdate();
        }
    }

    public void delete(Connection connection) throws SQLException {
        String query = "DELETE FROM Compras WHERE IDCompraCliente = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, IDCompraCliente);

            statement.executeUpdate();
        }
    }

    public void mostrarMenu(Connection connection) {
        Scanner scanner = new Scanner(System.in);

        int opcion;
        do {
            System.out.println("MENU");
            System.out.println("1. Mostrar compras");
            System.out.println("2. Buscar compra por ID");
            System.out.println("3. Agregar compra");
            System.out.println("4. Actualizar compra");
            System.out.println("5. Eliminar compra");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    mostrarTodasLasCompras(connection);
                    break;
                case 2:
                    buscarCompraPorId(connection);
                    break;
                case 3:
                    agregarCompra(connection);
                    break;
                case 4:
                    actualizarCompra(connection);
                    break;
                case 5:
                    eliminarCompra(connection);
                    break;
                case 0:
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private void mostrarTodasLasCompras(Connection connection) {
        Set<Compras> compras = mostrarCompras(connection);
        System.out.println("COMPRAS:");
        for (Compras compra : compras) {
            System.out.println(compra);
        }
    }

    private void buscarCompraPorId(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduzca el ID de la compra: ");
        int idCompra = scanner.nextInt();

        Compras compra = buscarCompraPorID(connection, idCompra);
        if (compra != null) {
            System.out.println("Compra encontrada: " + compra);
        } else {
            System.out.println("No se encontró ninguna compra con el ID especificado.");
        }
    }

    private void agregarCompra(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduzca el ID de la compra: ");
        int idCompra = scanner.nextInt();
        System.out.print("Introduzca la matrícula del coche: ");
        String matriculaCoche = scanner.next();

        Compras nuevaCompra = new Compras();
        nuevaCompra.setIDCompraCliente(idCompra);
        nuevaCompra.setMatriculaCoche(matriculaCoche);

        try {
            nuevaCompra.save(connection);
            System.out.println("Compra agregada correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar la compra: " + e.getMessage());
        }
    }

    private void actualizarCompra(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduzca el ID de la compra a actualizar: ");
        int idCompra = scanner.nextInt();

        Compras compra = buscarCompraPorID(connection, idCompra);
        if (compra != null) {
            System.out.println("Compra encontrada: " + compra);
            System.out.print("Introduzca la nueva matrícula del coche: ");
            String matriculaCoche = scanner.next();

            compra.setMatriculaCoche(matriculaCoche);

            try {
                compra.update(connection);
                System.out.println("Compra actualizada correctamente.");
            } catch (SQLException e) {
                System.out.println("Error al actualizar la compra: " + e.getMessage());
            }
        } else {
            System.out.println("No se encontró ninguna compra con el ID especificado.");
        }
    }

    private void eliminarCompra(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Introduzca el ID de la compra a eliminar: ");
        int idCompra = scanner.nextInt();

        Compras compra = buscarCompraPorID(connection, idCompra);
        if (compra != null) {
            System.out.println("Compra encontrada: " + compra);
            System.out.print("¿Está seguro de que desea eliminar esta compra? (S/N): ");
            String confirmacion = scanner.next();

            if (confirmacion.equalsIgnoreCase("S")) {
                try {
                    compra.delete(connection);
                    System.out.println("Compra eliminada correctamente.");
                } catch (SQLException e) {
                    System.out.println("Error al eliminar la compra: " + e.getMessage());
                }
            } else {
                System.out.println("Eliminación de la compra cancelada.");
            }
        } else {
            System.out.println("No se encontró ninguna compra con el ID especificado.");
        }
    }

    @Override
    public String toString() {
        return "Compras{" +
                "IDCompraCliente=" + getIDCompraCliente() +
                ", codigoInternoCliente=" + getIDCompraCliente()+
                ", matriculaCoche='" + getMatriculaCoche() + '\'' +
                '}';
    }
}
