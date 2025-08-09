

package Services;

import UI.frmLogin;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;



public class GestionInventario {

    public static void main(String[] args) {
       try {
            // Establece el tema FlatLaf
            FlatMaterialLighterIJTheme.setup();
            UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("No se pudo aplicar el tema FlatLaf: " + e.getMessage());
        }

        // Inicia tu ventana principal
        java.awt.EventQueue.invokeLater(() -> {
            new frmLogin().setVisible(true);
        });
    }
}
