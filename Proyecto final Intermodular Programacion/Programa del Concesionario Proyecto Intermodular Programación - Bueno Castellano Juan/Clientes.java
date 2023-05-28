import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Clientes {
    private int codigoInterno;
    private String NIF;
    private String nombre;
    private String direccion;
    private String ciudad;
    private String numeroTelefono;
    private static final Conexion con = new Conexion();

    public Clientes() {
    }

    public int getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(int codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public static Clientes buscarClientePorCodigoInterno(Connection connection, int codigoInterno) throws SQLException {
        String query = "SELECT * FROM Clientes WHERE codigointerno = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Clientes cliente = null;

        try {
            statement = connection.prepareStatement(query);
            statement.setInt(1, codigoInterno);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                cliente = new Clientes();
                cliente.setCodigoInterno(resultSet.getInt("codigointerno"));
                cliente.setNIF(resultSet.getString("NIF"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setDireccion(resultSet.getString("direccion"));
                cliente.setCiudad(resultSet.getString("ciudad"));
                cliente.setNumeroTelefono(resultSet.getString("numerotelefono"));
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el cliente por código interno: " + e.getMessage());
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }

        return cliente;
    }

    public static Set<Clientes> mostrarClientes(Connection connection) {
        Set<Clientes> clientes = new HashSet<>();
        String sql = "SELECT * FROM Clientes";
        try {
            con.Conexion();
            Statement sentencia = con.conn1.createStatement();
            ResultSet rs = sentencia.executeQuery(sql);
            while (rs.next()) {
                Clientes cliente = new Clientes();
                cliente.setCodigoInterno(rs.getInt("codigointerno"));
                cliente.setNIF(rs.getString("NIF"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setCiudad(rs.getString("ciudad"));
                cliente.setNumeroTelefono(rs.getString("numerotelefono"));

                clientes.add(cliente);
            }
            con.cerrarConexion();
        } catch (SQLException e) {
            System.out.println("Error al crear la lista de clientes.");
        }
        return clientes;
    }

    public void save(Connection connection) throws SQLException {
        String query = "INSERT INTO Clientes (NIF, nombre, direccion, ciudad, numerotelefono) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduce el NIF: ");
            String nifValue = scanner.nextLine();
            System.out.print("Introduce el nombre: ");
            String nombreValue = scanner.nextLine();
            System.out.print("Introduce la dirección: ");
            String direccionValue = scanner.nextLine();
            System.out.print("Introduce la ciudad: ");
            String ciudadValue = scanner.nextLine();
            System.out.print("Introduce el número de teléfono: ");
            String numeroTelefonoValue = scanner.nextLine();

            statement.setString(1, nifValue);
            statement.setString(2, nombreValue);
            statement.setString(3, direccionValue);
            statement.setString(4, ciudadValue);
            statement.setString(5, numeroTelefonoValue);

            statement.executeUpdate();
        }
    }

    public void update(Connection connection) throws SQLException {
        String query = "UPDATE Clientes SET NIF = ?, nombre = ?, direccion = ?, ciudad = ?, numerotelefono = ? WHERE codigointerno = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Introduce el NIF: ");
            String nifValue = scanner.nextLine();
            System.out.print("Introduce el nombre: ");
            String nombreValue = scanner.nextLine();
            System.out.print("Introduce la dirección: ");
            String direccionValue = scanner.nextLine();
            System.out.print("Introduce la ciudad: ");
            String ciudadValue = scanner.nextLine();
            System.out.print("Introduce el número de teléfono: ");
            String numeroTelefonoValue = scanner.nextLine();

            statement.setString(1, nifValue);
            statement.setString(2, nombreValue);
            statement.setString(3, direccionValue);
            statement.setString(4, ciudadValue);
            statement.setString(5, numeroTelefonoValue);
            statement.setInt(6, codigoInterno);

            statement.executeUpdate();
        }
    }

    public void delete(Connection connection) throws SQLException {
        String query = "DELETE FROM Clientes WHERE codigointerno = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, codigoInterno);

            statement.executeUpdate();
        }
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "codigoInterno=" + codigoInterno +
                ", NIF='" + NIF + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ciudad='" + ciudad + '\'' +
                ", numeroTelefono='" + numeroTelefono + '\'' +
                '}';
    }
}
