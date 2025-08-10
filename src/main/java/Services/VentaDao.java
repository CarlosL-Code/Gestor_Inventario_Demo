package Services;

import ConecctionDB.ConexionBD;
import Entities.Ventas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CARLOSL
 */
public class VentaDao {

    int resultado;
    ConexionBD conexion = new ConexionBD();
    PreparedStatement ps;
    ResultSet rs;

    public int registrarVentas(Ventas v) {
        String sql = "INSERT INTO ventas(cliente,vendedor,total) VALUES (?,?,?)";

        try {
            ps = conexion.establecerConexion().prepareStatement(sql);
            ps.setString(1, v.getCliente());
            ps.setString(2, v.getVendedor());
            ps.setDouble(3, v.getTotal());
            ps.execute();

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return resultado;
    }

    public boolean actualizarStock(int cantidadVendida, String codigo) {
        // Primero, obtenemos el stock actual del producto
        String sqlSelect = "SELECT stock FROM producto WHERE codigo_producto = ?";
        try {
            // Establecemos la conexión y preparamos la consulta
            PreparedStatement psSelect = conexion.establecerConexion().prepareStatement(sqlSelect);
            psSelect.setString(1, codigo);

            // Ejecutamos la consulta y obtenemos el stock actual
            ResultSet rs = psSelect.executeQuery();
            if (rs.next()) {
                int stockActual = rs.getInt("stock");

                // Restamos la cantidad vendida al stock actual
                int nuevoStock = stockActual - cantidadVendida;

                // Ahora actualizamos el stock en la base de datos
                if (nuevoStock >= 0) {
                    String sqlUpdate = "UPDATE producto SET stock = ? WHERE codigo_producto = ?";
                    PreparedStatement psUpdate = conexion.establecerConexion().prepareStatement(sqlUpdate);
                    psUpdate.setInt(1, nuevoStock);
                    psUpdate.setString(2, codigo);
                    psUpdate.executeUpdate();
                    return true;  // El stock se actualizó correctamente
                } else {
                    System.out.println("Error: El stock no puede ser negativo.");
                    return false; // No hay suficiente stock para la venta
                }
            } else {
                System.out.println("Error: Producto no encontrado.");
                return false; // Producto no encontrado en la base de datos
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar stock: " + e.toString());
            return false;
        }
    }

   public DefaultTableModel mostrarVentas() {
        // Definimos los nombres de las columnas
        String[] nombresColumnas = {"ID VENTA", "CLIENTE", "VENDEDOR", "TOTAL", "FECHA VENTA"};

        // Creamos el arreglo de registros para cada proveedor
        String[] registros = new String[5];

        // Creamos el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);

        // Definimos la consulta SQL
        String sql = "SELECT * FROM ventas";

        // Inicializamos las variables de conexión y consulta
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            PreparedStatement lectura = conexion.establecerConexion().prepareStatement(sql);
            rs = lectura.executeQuery();

            // Iteramos sobre el resultado de la consulta
            while (rs.next()) {
                // Obtenemos los datos de la consulta para cada proveedor
                registros[0] = rs.getString("idVentas");
                registros[1] = rs.getString("cliente");
                registros[2] = rs.getString("vendedor");
                registros[3] = rs.getString("total");
                registros[4] = rs.getString("fechaVenta");
                

                // Agregamos los registros al modelo de la tabla
                modelo.addRow(registros);
            }
        } catch (SQLException e) {
            // Manejo de excepciones
            JOptionPane.showMessageDialog(null, "Error al conectar: " + e.getMessage());
        } finally {
            // Cerramos las conexiones
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

        // Retornamos el modelo con los datos de los proveedores
        return modelo;
    }
   
    public int idVenta() {
        int id = 0;
        String sql = "SELECT MAX(idVentas) FROM ventas";
        try {
            PreparedStatement lectura = conexion.establecerConexion().prepareStatement(sql);
            ResultSet resultados = lectura.executeQuery();
            if (resultados.next()) {
                id = resultados.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return id;
    }
}
