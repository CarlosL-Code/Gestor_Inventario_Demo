package Services;

import java.awt.event.KeyEvent;
import javax.swing.JTextField;

public class Eventos {

    public void presionLetras(KeyEvent evt) {
// declaramos una variable y le asignamos un evento
        char car = evt.getKeyChar();
        if ((car < 'a' || car > 'z') && (car < 'A' || car > 'Z')
                && (car != (char) KeyEvent.VK_BACK_SPACE) && (car != (char) KeyEvent.VK_SPACE)) {
            evt.consume();
        }
    }

    public void presionDeNumeros(KeyEvent evt) {
// declaramos una variable y le asignamos un evento
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9') && (car != (char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
        }
    }

    public void precionDeNumerosDecimales(KeyEvent evt, JTextField textField) {
// declaramos una variable y le asignamos un evento
        char car = evt.getKeyChar();
        if ((car < '0' || car > '9') && textField.getText().contains(".") && (car != (char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
        } else if ((car < '0' || car > '9') && (car != '.') && (car != (char) KeyEvent.VK_BACK_SPACE)) {
            evt.consume();
        }
    }

    public void presionNumeroTelefonicos(KeyEvent evt) {
        char caracter = evt.getKeyChar();

        if (!((caracter >= '0' && caracter <= '9')
                || // Es un número
                (caracter >= 'a' && caracter <= 'z')
                || // Es una letra minúscula
                (caracter >= 'A' && caracter <= 'Z')
                || // Es una letra mayúscula
                caracter == '+')
                || (caracter == ' ')) {
            evt.consume();
        }
    }
    
     public void agregarKeyListenerRut(JTextField txtField) {
        txtField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String rut = txtField.getText().replaceAll("[^0-9Kk]", "");

                // Agregar guion antes del dígito verificador
                if (rut.length() > 1) {
                    rut = rut.substring(0, rut.length() - 1) + "-" + rut.substring(rut.length() - 1);
                }

                // Agregar primer punto después del tercer dígito
                if (rut.length() > 5) {
                    rut = rut.substring(0, rut.length() - 5) + "." + rut.substring(rut.length() - 5);
                }

                // Agregar segundo punto después del sexto dígito
                if (rut.length() > 9) {
                    rut = rut.substring(0, rut.length() - 9) + "." + rut.substring(rut.length() - 9);
                }

                // Actualizar el campo de texto con el RUT formateado
                txtField.setText(rut.toUpperCase());
            }
        });
    }
}
