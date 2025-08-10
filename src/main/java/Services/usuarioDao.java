package Services;

import ConecctionDB.ConexionBD;
import Entities.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class usuarioDao {

    Usuario usuario = new Usuario();

    ConexionBD conexion = new ConexionBD();

  public Usuario ingresarLogin(String email, String pass) {
    Usuario usuario = null;
    try {
        String sql = "CALL seleccionUsuario(?, ?)";
        PreparedStatement lectura = conexion.establecerConexion().prepareStatement(sql);
        lectura.setString(1, email);
        lectura.setString(2, pass);
        ResultSet resultado = lectura.executeQuery();

        if (resultado.next()) {
            usuario = new Usuario(); // Crear el objeto Usuario solo si la consulta devuelve datos
            usuario.setNombre(resultado.getString("nombre_usuario"));
            usuario.setTelefono(resultado.getString("telefono"));
            usuario.setEmail(resultado.getString("email_usuario"));
            usuario.setRol(resultado.getString("rol_usuario"));
            usuario.setContrasenia(resultado.getString("contrasenia_usuario"));
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error en la autenticación: " + e.getMessage());
    }
    
    return usuario;
}
}
