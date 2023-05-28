import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Coches {
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
    private double precioVenta;
    private static final Conexion con = new Conexion();

    public Coches() {
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public static Coches buscarCochePorMatricula(Connection connection, String matricula) throws SQLException {
        String query = "SELECT * FROM Coches WHERE Matricula = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Coches coche = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setString(1, matricula);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                coche = new Coches();
                coche.setMatricula(resultSet.getString("Matricula"));
                coche.setMarca(resultSet.getString("Marca"));
                coche.setModelo(resultSet.getString("Modelo"));
                coche.setColor(resultSet.getString("Color"));
                coche.setPrecioVenta(resultSet.getDouble("PrecioVenta"));
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el coche por matrícula: " + e.getMessage());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }

        return coche;
    }

    public static Set<Coches> mostrarCoches(Connection connection) {
        Set<Coches> coches = new HashSet<>();
        String sql = "SELECT * FROM Coches";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Coches coche = new Coches();
                coche.setMatricula(rs.getString("Matricula"));
                coche.setMarca(rs.getString("Marca"));
                coche.setModelo(rs.getString("Modelo"));
                coche.setColor(rs.getString("Color"));
                coche.setPrecioVenta(rs.getDouble("PrecioVenta"));

                coches.add(coche);
            }
        } catch (SQLException e) {
            System.out.println("Error al crear la lista de coches: " + e.getMessage());
        }
        return coches;
    }

   public void save(Connection connection) throws SQLException {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Introduce la matrícula del coche: ");
    String matriculaValue = scanner.nextLine();

    if (matriculaValue == null || matriculaValue.isEmpty()) {
        System.out.println("Error al añadir el coche: La matrícula no puede ser nula o vacía");
        return;
    }

    String query = "INSERT INTO Coches (Matricula, Marca, Modelo, Color, PrecioVenta) VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        System.out.print("Introduce la marca del coche: ");
        String marcaValue = scanner.nextLine();
        System.out.print("Introduce el modelo del coche: ");
        String modeloValue = scanner.nextLine();
        System.out.print("Introduce el color del coche: ");
        String colorValue = scanner.nextLine();
        System.out.print("Introduce el precio de venta del coche: ");
        double precioVentaValue = scanner.nextDouble();

        statement.setString(1, matriculaValue);
        statement.setString(2, marcaValue);
        statement.setString(3, modeloValue);
        statement.setString(4, colorValue);
        statement.setDouble(5, precioVentaValue);

        statement.executeUpdate();
    } catch (SQLException e) {
        System.out.println("Error al añadir el coche: " + e.getMessage());
    }
}


    public void update(Connection connection) throws SQLException {
        String query = "UPDATE Coches SET Marca = ?, Modelo = ?, Color = ?, PrecioVenta = ? WHERE Matricula = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduce la nueva marca del coche: ");
            String marcaValue = scanner.nextLine();
            System.out.print("Introduce el nuevo modelo del coche: ");
            String modeloValue = scanner.nextLine();
            System.out.print("Introduce el nuevo color del coche: ");
            String colorValue = scanner.nextLine();
            System.out.print("Introduce el nuevo precio de venta del coche: ");
            double precioVentaValue = scanner.nextDouble();
            scanner.nextLine(); // Consume the remaining newline character
            System.out.print("Introduce la matrícula del coche a actualizar: ");
            String matriculaValue = scanner.nextLine();

            statement.setString(1, marcaValue);
            statement.setString(2, modeloValue);
            statement.setString(3, colorValue);
            statement.setDouble(4, precioVentaValue);
            statement.setString(5, matriculaValue);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar el coche: " + e.getMessage());
        }
    }

    public void delete(Connection connection) throws SQLException {
        String query = "DELETE FROM Coches WHERE Matricula = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduce la matrícula del coche a eliminar: ");
            String matriculaValue = scanner.nextLine();

            statement.setString(1, matriculaValue);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar el coche: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Coche{" +
                "matricula='" + matricula + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", color='" + color + '\'' +
                ", precioVenta=" + precioVenta +
                '}';
    }
}
