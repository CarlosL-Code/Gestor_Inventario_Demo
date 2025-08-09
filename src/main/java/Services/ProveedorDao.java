package Services;

import ConecctionDB.ConexionBD;
import Entities.Proveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProveedorDao {

    ConexionBD conexionBd = new ConexionBD();

    // Método para buscar un producto por código
    public Proveedor buscarProveedor(String rut) {
        Proveedor proveedor = null;
        String sql = "SELECT * FROM proveedor WHERE rut_proveedor = ?";

        try {

            PreparedStatement lectura = conexionBd.establecerConexion().prepareStatement(sql);
            lectura.setString(1, rut);
            ResultSet resultado = lectura.executeQuery();

            if (resultado.next()) {
                proveedor = new Proveedor();
                proveedor.setRut(resultado.getString("rut_proveedor"));
                proveedor.setNombre(resultado.getString("nombre_proveedor"));
                proveedor.setEmail(resultado.getString("email_proveedor"));
                proveedor.setDireccion(resultado.getString("direccion_proveedor"));
                proveedor.setTelefono(resultado.getString("telefono_proveedor"));
                proveedor.setRol(resultado.getString("rol_proveedor"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar proveedor: " + e.toString());
        }
        return proveedor; // Retorna el producto encontrado o null si no existe
    }

    // Método para llenar el JComboBox con proveedores desde la base de datos
    public void llenarProveedores(JComboBox<String> cmbProovedor) {
        // Limpiar el JComboBox antes de agregar nuevos datos
        cmbProovedor.removeAllItems();

        try {
            String sql = "SELECT DISTINCT nombre_proveedor FROM proveedor";
            PreparedStatement lecturaSql = conexionBd.establecerConexion().prepareStatement(sql);
            ResultSet resultado = lecturaSql.executeQuery();

            while (resultado.next()) {
                cmbProovedor.addItem(resultado.getString("nombre_proveedor"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ocurrió un error al capturar los datos de la base de datos: " + e.toString());
        }
    }

    public boolean registrarProveedor(Proveedor reg) {
        String sql = "CALL registrarProveedor(?,?,?,?,?,?)";
        try (PreparedStatement lectura = conexionBd.establecerConexion().prepareStatement(sql)) {
            lectura.setString(1, reg.getRut());
            lectura.setString(2, reg.getNombre());
            lectura.setString(3, reg.getTelefono());
            lectura.setString(4, reg.getEmail());
            lectura.setString(5, reg.getDireccion());
            lectura.setString(6, reg.getRol());
            lectura.execute();
            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar el Proveedor: " + e.toString());
            return false;
        }
    }

    public DefaultTableModel mostrarProveedores() {
        // Definimos los nombres de las columnas
        String[] nombresColumnas = {"RUT", "NOMBRE", "TELÉFONO", "CORREO", "DIRECCIÓN", "ROL"};

        // Creamos el arreglo de registros para cada proveedor
        String[] registros = new String[6];

        // Creamos el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);

        // Definimos la consulta SQL
        String sql = "SELECT * FROM proveedor";

        // Inicializamos las variables de conexión y consulta
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            PreparedStatement lectura = conexionBd.establecerConexion().prepareStatement(sql);
            rs = lectura.executeQuery();

            // Iteramos sobre el resultado de la consulta
            while (rs.next()) {
                // Obtenemos los datos de la consulta para cada proveedor
                registros[0] = rs.getString("rut_proveedor");
                registros[1] = rs.getString("nombre_proveedor");
                registros[2] = rs.getString("telefono_proveedor");
                registros[3] = rs.getString("email_proveedor");
                registros[4] = rs.getString("direccion_proveedor");
                registros[5] = rs.getString("rol_proveedor");

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

    public int eliminarProveedorPorRut(String rut) {
        int resultado = 0;
        String sql = "DELETE FROM proveedor WHERE rut_proveedor = ?";

        try (PreparedStatement ps = conexionBd.establecerConexion().prepareStatement(sql)) {
            ps.setString(1, rut);  // No int, porque tu RUT tiene puntos y guión
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // O mejor: loguealo con Logger
        }

        return resultado;
    }

    public boolean actualizarProveedor(Proveedor proveedor) {
        String sql = "UPDATE proveedor SET nombre_proveedor = ?, telefono_proveedor = ?, email_proveedor = ?, direccion_proveedor = ?, rol_proveedor = ? WHERE rut_proveedor = ?";
        try (PreparedStatement ps = conexionBd.establecerConexion().prepareStatement(sql)) {
            ps.setString(1, proveedor.getNombre());
            ps.setString(2, proveedor.getTelefono());
            ps.setString(3, proveedor.getEmail());
            ps.setString(4, proveedor.getDireccion());
            ps.setString(5, proveedor.getRol());
            ps.setString(6, proveedor.getRut());

            int filasActualizadas = ps.executeUpdate();
            return filasActualizadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar proveedor: " + e.toString());
            return false;
        }
    }
}
