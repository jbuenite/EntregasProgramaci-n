
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Juan Instituto
 */
public class Conexion {
    Connection conn1 = null;
    public void Conexion (){
        try {
            String url = "jdbc:mysql://localhost:3306/concesionariotesla";
            String user = "root";
            String password = "";
            conn1 = DriverManager.getConnection(url, user, password);
            System.out.println("conectado a base de datos");
        } catch (SQLException ex) {
            System.out.println("ERROR:La dirección no es válida o el usuario y clave");
            ex.printStackTrace();
        }
    }
    public void cerrarConexion (){
        try {
        conn1.close();
        } catch (SQLException ex) {
        System.out.println("ERROR:al cerrar la conexión");
        ex.printStackTrace();
        }
    }
}
