
package UI;

import Entities.Usuario;
import Services.usuarioDao;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import java.awt.Color;
import java.util.Arrays;
import javax.management.StringValueExp;
import javax.swing.JOptionPane;

/**
 *
 * @author CARLOSL
 */
public class frmLogin extends javax.swing.JFrame {

    usuarioDao usuDao = new usuarioDao();

    public frmLogin() {
        initComponents();
        diseñoBotones();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void validarIngreso() {
        String email = txtUsuario.getText();
        char[] passw = pswContrasenia.getPassword();

        if (email.isEmpty() || passw.length == 0) {
            JOptionPane.showMessageDialog(null, "Debe ingresar usuario y contraseña", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String pass = new String(passw);
        Arrays.fill(passw, ' '); // Borra el array de contraseña por seguridad

        Usuario usuario = usuDao.ingresarLogin(email, pass);

        if (usuario != null) {

            frmPrincipal principal = new frmPrincipal(); // Crear instancia del formulario
            principal.setVisible(true); // Mostrar ventana principal
            principal.setLocationRelativeTo(null); // Centrar ventana

            this.dispose(); // Cerrar la ventana de login
        } else {
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void diseñoBotones() {
        btnIngresar.putClientProperty("JButton.buttonType", "roundRect");
        btnIngresar.setForeground(Color.black);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Background = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        btnIngresar = new javax.swing.JButton();
        pswContrasenia = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        lblVendedor = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Background.setBackground(new java.awt.Color(255, 255, 255));
        Background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\carlo\\Documents\\NetBeansProjects\\GestionInventario\\src\\main\\Resources\\jason-leung-yf9hEzG8EKI-unsplash (1).jpg")); // NOI18N
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 746));

        Background.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 600, 746));

        jLabel3.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Usuario");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Background.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 260, 330, 42));

        jLabel5.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Contraseña");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Background.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 370, -1, 42));

        txtUsuario.setFont(new java.awt.Font("Arial Black", 0, 12)); // NOI18N
        txtUsuario.setText("Ingrese su usuario para continuar. . .");
        txtUsuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUsuarioFocusLost(evt);
            }
        });
        txtUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtUsuarioMouseClicked(evt);
            }
        });
        Background.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 310, 310, 45));

        btnIngresar.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        btnIngresar.setForeground(new java.awt.Color(0, 0, 0));
        btnIngresar.setText("Ingresar");
        btnIngresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });
        Background.add(btnIngresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 550, 310, 54));

        pswContrasenia.setText("*****************");
        pswContrasenia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                pswContraseniaFocusLost(evt);
            }
        });
        pswContrasenia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pswContraseniaMouseClicked(evt);
            }
        });
        Background.add(pswContrasenia, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 410, 310, 44));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\carlo\\Documents\\NetBeansProjects\\GestionInventario\\src\\main\\Resources\\pngwing.com.png")); // NOI18N
        Background.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 10, 240, 200));

        lblVendedor.setBackground(new java.awt.Color(0, 0, 0));
        lblVendedor.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblVendedor.setForeground(new java.awt.Color(0, 0, 0));
        lblVendedor.setText("Information Factory");
        Background.add(lblVendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 210, 190, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUsuarioFocusLost
        if (txtUsuario.getText().trim().isEmpty()) {
            txtUsuario.setText("Ingrese su usuario para continuar. . .");
            txtUsuario.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_txtUsuarioFocusLost

    private void pswContraseniaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pswContraseniaFocusLost
        if (String.valueOf(pswContrasenia.getPassword()).trim().isEmpty()) {
            pswContrasenia.setText("*****************");
            pswContrasenia.setForeground(Color.GRAY);

        }
    }//GEN-LAST:event_pswContraseniaFocusLost

    private void txtUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtUsuarioMouseClicked
        if (txtUsuario.getText().equals("Ingrese su usuario para continuar. . .")) {
            txtUsuario.setText("");
            txtUsuario.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_txtUsuarioMouseClicked

    private void pswContraseniaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pswContraseniaMouseClicked
        if (String.valueOf(pswContrasenia.getPassword()).equals("*****************")) {
            pswContrasenia.setText("");
            pswContrasenia.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_pswContraseniaMouseClicked

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        validarIngreso();
    }//GEN-LAST:event_btnIngresarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatMaterialLighterIJTheme.setup();


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Background;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblVendedor;
    private javax.swing.JPasswordField pswContrasenia;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
