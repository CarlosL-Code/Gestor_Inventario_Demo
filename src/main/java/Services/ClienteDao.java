
package Services;

import ConecctionDB.ConexionBD;
import Entities.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ClienteDao {

    ConexionBD conexionBd = new ConexionBD();
    Cliente cliente = new Cliente();

    public boolean registrarCliente(Cliente reg) {
        String sql = "CALL registrarCliente(?,?,?,?,?,?)";
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
            System.out.println("Error al registrar el Cliente: " + e.toString());
            return false;
        }
    }
    
    public boolean actualizarCliente(Cliente cliente) {
    String sql = "UPDATE cliente SET nombre_cliente = ?, telefono_cliente = ?, email_cliente = ?, direccion_cliente = ?, rol_cliente = ? WHERE rut_cliente = ?";
    try (PreparedStatement ps = conexionBd.establecerConexion().prepareStatement(sql)) {
        ps.setString(1, cliente.getNombre());
        ps.setString(2, cliente.getTelefono());
        ps.setString(3, cliente.getEmail());
        ps.setString(4, cliente.getDireccion());
        ps.setString(5, cliente.getRol());
        ps.setString(6, cliente.getRut());

        int filasActualizadas = ps.executeUpdate();
        return filasActualizadas > 0;

    } catch (SQLException e) {
        System.out.println("Error al actualizar cliente: " + e.toString());
        return false;
    }
}
    
    // Método para buscar un producto por código
    public Cliente buscarCliente(String rut) {
        Cliente cliente = null;
        String sql = "SELECT * FROM cliente WHERE rut_cliente = ?";

        try {

            PreparedStatement lectura = conexionBd.establecerConexion().prepareStatement(sql);
            lectura.setString(1, rut);
            ResultSet resultado = lectura.executeQuery();

            if (resultado.next()) {
                cliente = new Cliente();
                cliente.setRut(resultado.getString("rut_cliente"));
                cliente.setNombre(resultado.getString("nombre_cliente"));
                cliente.setEmail(resultado.getString("email_cliente"));
                cliente.setDireccion(resultado.getString("direccion_cliente"));
                cliente.setTelefono(resultado.getString("telefono_cliente"));
                cliente.setRol(resultado.getString("rol_cliente"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar cliente: " + e.toString());
        }
        return cliente; // Retorna el producto encontrado o null si no existe
    }

    public Cliente buscarClientePorRut(String rut) {
        Cliente cliente = null;
        String sql = "SELECT rut_cliente, nombre_cliente, telefono_cliente, direccion_cliente FROM cliente WHERE rut_cliente = ?";

        try {
            PreparedStatement ps = conexionBd.establecerConexion().prepareStatement(sql);
            ps.setString(1, rut);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setRut(rs.getString("rut_cliente"));
                cliente.setNombre(rs.getString("nombre_cliente"));
                cliente.setTelefono(rs.getString("telefono_cliente"));
                cliente.setDireccion(rs.getString("direccion_cliente"));
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar cliente por RUT: " + e.toString());
        } finally {
            try {
                conexionBd.cerrarConexion();
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión: " + e.toString());
            }
        }

        return cliente;
    }

    public DefaultTableModel mostrarClientes() {
        // Definimos los nombres de las columnas
        String[] nombresColumnas = {"RUT", "NOMBRE", "TELÉFONO", "CORREO", "DIRECCIÓN", "ROL"};

        // Creamos el arreglo de registros para cada proveedor
        String[] registros = new String[6];

        // Creamos el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);

        // Definimos la consulta SQL
        String sql = "SELECT * FROM cliente";

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
                registros[0] = rs.getString("rut_cliente");
                registros[1] = rs.getString("nombre_cliente");
                registros[2] = rs.getString("telefono_cliente");
                registros[3] = rs.getString("email_cliente");
                registros[4] = rs.getString("direccion_cliente");
                registros[5] = rs.getString("rol_cliente");

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

    public int eliminarClientePorRut(String rut) {
        int resultado = 0;
        String sql = "DELETE FROM cliente WHERE rut_Cliente = ?";

        try (PreparedStatement ps = conexionBd.establecerConexion().prepareStatement(sql)) {
            ps.setString(1, rut);  // No int, porque tu RUT tiene puntos y guión
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // O mejor: loguealo con Logger
        }

        return resultado;
    }
}
