
package ConecctionDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConexionBD {
    
    // Estableceremos los metodos de la conexion a nuestra base de datos.

    private static String nameDataBases = "sistemaInventario";
    private static String url = "jdbc:mysql://localhost:3306/" + nameDataBases;
    private static String usuario = "root";
    private static String clave = "1234";
    Connection estadoConexion = null;

    public Connection establecerConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            estadoConexion = DriverManager.getConnection(url, usuario, clave);
            System.out.println("ConexiÃ³n exitosa con :");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al conectar: " + e.getMessage());
        }
        return estadoConexion;
    }

    public void cerrarConexion() {
        try {
            if (estadoConexion != null) {
                estadoConexion.close();
                System.out.println("Conexion cerrada exitosamente.");
            }
        } catch (Exception e) {
            System.err.println("Error al cerrar la conexiÃ³n: " + e.toString());
        }
    }
    
}
