/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Services;

import ConecctionDB.ConexionBD;
import Entities.Producto;
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
public class ProductoDao {

    ConexionBD conexion = new ConexionBD();

    public boolean registrarProducto(Producto producto) {
        String sql = "CALL registrarProducto(?, ?, ?, ?, ?)"; // Llamada al procedimiento almacenado

        try (PreparedStatement lectura = conexion.establecerConexion().prepareStatement(sql)) {
            // Establecer los parámetros del procedimiento almacenado
            lectura.setString(1, producto.getCodigo());
            lectura.setString(2, producto.getNombrePro());
            lectura.setDouble(3, producto.getPrecio());
            lectura.setInt(4, producto.getStock());
            lectura.setString(5, producto.getProveedor());

            // Ejecutar la consulta
            lectura.execute();
            return true; // Retorna true si se registró correctamente

        } catch (SQLException e) {
            System.out.println("Error al registrar el producto: " + e.toString());
            return false; // Retorna false si hubo un error
        }
    }

    // Método para buscar un producto por código
    public Producto buscarProducto(String codigoProducto) {
        Producto producto = null;
        String sql = "SELECT * FROM producto WHERE codigo_producto = ?";

        try {

            PreparedStatement lectura = conexion.establecerConexion().prepareStatement(sql);
            lectura.setString(1, codigoProducto);
            ResultSet resultado = lectura.executeQuery();

            if (resultado.next()) {
                producto = new Producto();
                producto.setCodigo(resultado.getString("codigo_producto"));
                producto.setNombrePro(resultado.getString("nombre_producto"));
                producto.setStock(resultado.getInt("stock"));
                producto.setPrecio(resultado.getDouble("precio"));
                producto.setProveedor(resultado.getString("nombre_proveedor"));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar producto: " + e.toString());
        }
        return producto; // Retorna el producto encontrado o null si no existe
    }

    public DefaultTableModel mostrarProducto() {
        // Definimos los nombres de las columnas
        String[] nombresColumnas = {"CÓDIGO", "DESCRIPCIÓN", "PRECIO", "STOCK", "PROVEEDOR"};

        // Creamos el arreglo de registros para cada proveedor
        String[] registros = new String[5];

        // Creamos el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel(null, nombresColumnas);

        // Definimos la consulta SQL
        String sql = "SELECT * FROM producto";

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
                registros[0] = rs.getString("codigo_producto");
                registros[1] = rs.getString("nombre_producto");
                registros[2] = rs.getString("precio");
                registros[3] = rs.getString("stock");
                registros[4] = rs.getString("nombre_proveedor");

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

    public int eliminarProductoPorCod(String cod) {
        int resultado = 0;
        String sql = "DELETE FROM producto WHERE codigo_producto = ?";

        try (PreparedStatement ps = conexion.establecerConexion().prepareStatement(sql)) {
            ps.setString(1, cod);
            resultado = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // O mejor: loguealo con Logger
        }

        return resultado;
    }

    public boolean actualizarProducto(Producto produ) {
        String sql = "UPDATE producto SET  nombre_producto = ?, precio = ?, stock = ?, nombre_proveedor = ? WHERE codigo_producto = ?";
        try (PreparedStatement ps = conexion.establecerConexion().prepareStatement(sql)) {
            ps.setString(1, produ.getNombrePro());  // Nombre del producto
            ps.setDouble(2, produ.getPrecio());     // Precio del producto
            ps.setInt(3, produ.getStock());         // Stock del producto
            ps.setString(4, produ.getProveedor());  // Nombre del proveedor
            ps.setString(5, produ.getCodigo());

            int filasActualizadas = ps.executeUpdate();
            return filasActualizadas > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar Producto: " + e.toString());
            return false;
        }
    }
}
