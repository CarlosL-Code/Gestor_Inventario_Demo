package Services;

import ConecctionDB.ConexionBD;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DetalleDao {

    ConexionBD conexion = new ConexionBD();

    public int registrarDetalle(Detalle dv) {
        String sql = "INSERT INTO detalle (codigo_Producto, cantidad, precio, idVentas) VALUES(?,?,?,?)";
        int resultado = 0;

        try {
            PreparedStatement lecturaDato = conexion.establecerConexion().prepareStatement(sql);
            lecturaDato.setString(1, dv.getCodigo());  // Ahora es VARCHAR en la BD
            lecturaDato.setInt(2, dv.getCantidad());
            lecturaDato.setDouble(3, dv.getPrecio());
            lecturaDato.setInt(4, dv.getId()); // Asegúrate de que idVenta es correcto
            lecturaDato.execute();

        } catch (SQLException e) {
            e.printStackTrace(); // Imprime detalles del error
        } finally {
            try {
                conexion.cerrarConexion();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        return resultado;
    }
}
