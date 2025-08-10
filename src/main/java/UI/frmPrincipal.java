package UI;

import ConecctionDB.ConexionBD;
import Entities.Cliente;
import Entities.Producto;
import Entities.Proveedor;
import Entities.Ventas;
import Services.ClienteDao;
import Services.Detalle;
import Services.DetalleDao;
import Services.ProveedorDao;
import Services.Eventos;
import Services.Exportar;
import Services.GraficoDao;
import Services.Importar;
import Services.ProductoDao;
import Services.VentaDao;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import java.io.IOException;

public class frmPrincipal extends javax.swing.JFrame {

    Eventos eventos = new Eventos();
    frmLogin login = new frmLogin();
    ConexionBD conexionBd = new ConexionBD();
    ProveedorDao proDao = new ProveedorDao();
    Proveedor pro = new Proveedor();
    Producto produ = new Producto();
    ProductoDao produDao = new ProductoDao();
    Cliente cliente = new Cliente();
    ClienteDao clienteDao = new ClienteDao();
    DefaultTableModel modelo = new DefaultTableModel();
    int item;
    Ventas venta = new Ventas();
    VentaDao ventaDao = new VentaDao();
    Detalle detalle = new Detalle();
    DetalleDao detalleDao = new DetalleDao();
    Importar importar = new Importar();
    Exportar objeto = new Exportar();
    double totalPagar = 0.00;

    public frmPrincipal() {

        initComponents();
        initStyles();
        setDate();
        setLocationRelativeTo(null);
        setResizable(false);
        proDao.llenarProveedores(cmbProovedor);
        eventos.agregarKeyListenerRut(txtRutCl);
        eventos.agregarKeyListenerRut(txtRutProveedor);
        txtIdVenta.setVisible(false);
        txtDireccion.setVisible(false);
        txtTelefono.setVisible(false);
        txtEmail.setVisible(false);
        txtRol.setVisible(false);
        btnGenerarV.setEnabled(false);
        txtIdDetalle.setVisible(false);

    }

    //Método para capturar la fecha actual en formato español
    private void setDate() {
        LocalDate now = LocalDate.now();
        Locale spanishLocale = new Locale("es", "ES");
        lblFecha.setText(now.format(DateTimeFormatter.ofPattern("EEEE dd ' de ' MMMM ' de ' yyyy", spanishLocale)));
    }

    //Método para inicializar los estilos
    private void initStyles() {

        // Sección Venta
        lblBienvenida.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblBienvenida.setForeground(Color.BLACK);
        lblFecha.putClientProperty("FlatLaf.style", "font: 30 $light.font");
        lblFecha.setForeground(Color.WHITE);
        lblHeaderUno.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblHeaderUno.setForeground(Color.WHITE);
        lblLogoEmpresa.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblLogoEmpresa.setForeground(Color.WHITE);
        btnPrincipal.putClientProperty("JButton.buttonType", "roundRect");
        lblCodigo.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblCodigo.setForeground(Color.BLACK);
        lblDescripcion.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblDescripcion.setForeground(Color.BLACK);
        lblPrecio.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblPrecio.setForeground(Color.BLACK);
        lblCantidad.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblCantidad.setForeground(Color.BLACK);
        lblStock.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblStock.setForeground(Color.BLACK);
        btnGenerarV.putClientProperty("JButton.buttonType", "roundRect");
        btnGenerarV.setForeground(Color.black);
        btnEliminarV.putClientProperty("JButton.buttonType", "roundRect");
        btnEliminarV.setForeground(Color.black);
        btnGenerarV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnEliminarV.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Sección Proveedor
        btnGuardarP.putClientProperty("JButton.buttonType", "roundRect");
        btnGuardarP.setForeground(Color.black);
        btnGuardarP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarP.putClientProperty("JButton.buttonType", "roundRect");
        btnEliminarP.setForeground(Color.black);
        btnActualizarP.putClientProperty("JButton.buttonType", "roundRect");
        btnActualizarP.setForeground(Color.black);
        btnImportarP.putClientProperty("JButton.buttonType", "roundRect");
        btnImportarP.setForeground(Color.black);
        lblRutProveedor.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblRutProveedor.setForeground(Color.BLACK);
        lblNombreProveedor.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblNombreProveedor.setForeground(Color.BLACK);
        lblDireccionProveedor.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblDireccionProveedor.setForeground(Color.BLACK);
        lblTelefonoProveedor.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblTelefonoProveedor.setForeground(Color.BLACK);
        lblEmailProveedor.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblEmailProveedor.setForeground(Color.BLACK);
        lblRolProveedor.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblRolProveedor.setForeground(Color.BLACK);

        //Sección Venta
        btnPdf.putClientProperty("JButton.buttonType", "roundRect");
        btnPdf.setForeground(Color.black);

        //Sección Producto
        lblCodigoProducto.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblCodigoProducto.setForeground(Color.BLACK);
        lblPrecioProducto.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblPrecioProducto.setForeground(Color.BLACK);
        lblCantidadProducto.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblCantidadProducto.setForeground(Color.BLACK);
        lblDescripcionProducto.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblDescripcionProducto.setForeground(Color.BLACK);
        lblProovedorProducto.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblProovedorProducto.setForeground(Color.BLACK);
        lblBuscar.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblBuscar.setForeground(Color.BLACK);
        lblBuscarProducto.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblBuscarProducto.setForeground(Color.BLACK);
        lblTotal.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblTotal.setForeground(Color.BLACK);
        lblLimpiarVenta.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblLimpiarVenta.setForeground(Color.BLACK);
        lblLimpiarProducto.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblLimpiarProducto.setForeground(Color.BLACK);
        btnGuardarProducto.putClientProperty("JButton.buttonType", "roundRect");
        btnGuardarProducto.setForeground(Color.black);
        btnActualizarProducto.putClientProperty("JButton.buttonType", "roundRect");
        btnActualizarProducto.setForeground(Color.black);
        btnEliminarProducto.putClientProperty("JButton.buttonType", "roundRect");
        btnEliminarProducto.setForeground(Color.black);
        btnImportarProducto.putClientProperty("JButton.buttonType", "roundRect");
        btnImportarProducto.setForeground(Color.black);
        btnExel.putClientProperty("JButton.buttonType", "roundRect");
        btnExel.setForeground(Color.black);
        btnGrafico.putClientProperty("JButton.buttonType", "roundRect");
        btnGrafico.setForeground(Color.black);
        lblRut.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblRut.setForeground(Color.BLACK);
        lblNombre.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblNombre.setForeground(Color.BLACK);
        lblGrafico.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblGrafico.setForeground(Color.BLACK);

        //Sección Clientes
        btnGuardarCl.putClientProperty("JButton.buttonType", "roundRect");
        btnGuardarCl.setForeground(Color.black);
        btnEliminarCl.putClientProperty("JButton.buttonType", "roundRect");
        btnEliminarCl.setForeground(Color.black);
        btnActualizarCl.putClientProperty("JButton.buttonType", "roundRect");
        btnActualizarCl.setForeground(Color.black);
        btnImportarCl.putClientProperty("JButton.buttonType", "roundRect");
        btnImportarCl.setForeground(Color.black);
        lblRutCl.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblRutCl.setForeground(Color.BLACK);
        lblNombreCl.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblNombreCl.setForeground(Color.BLACK);
        lblDireccionCl.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblDireccionCl.setForeground(Color.BLACK);
        lblTelefonoCl.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblTelefonoCl.setForeground(Color.BLACK);
        lblEmailCl.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblEmailCl.setForeground(Color.BLACK);
        lblRolCl.putClientProperty("FlatLaf.style", "font: 14 $light.font");
        lblRolCl.setForeground(Color.BLACK);
        btnPrincipal.putClientProperty("JButton.buttonType", "roundRect");
       

    }

    //Método para validar el guardado de proveedores
    private void validarRegistroPro() {
        String rutPro = txtRutProveedor.getText().trim();
        String nombrePro = txtNombreProveedor.getText().trim();
        String telefonoPro = txtTelefonoProveedor.getText().trim();
        String emailPro = txtEmailProveedor.getText().trim();
        String direccionPro = txtDireccionProveedor.getText().trim();
        String rolPro = txtRolP.getText().trim();

        // Validación: Si algún campo está vacío, se muestra un mensaje y se detiene el proceso
        if (rutPro.isEmpty() || nombrePro.isEmpty() || telefonoPro.isEmpty() || emailPro.isEmpty() || direccionPro.isEmpty() || rolPro.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return; // Salir del método sin registrar
        }

        // Asignar valores al objeto proveedor
        pro.setRut(rutPro);
        pro.setNombre(nombrePro);
        pro.setEmail(emailPro);
        pro.setTelefono(telefonoPro);
        pro.setDireccion(direccionPro);
        pro.setRol(rolPro);

        // Intentar registrar el proveedor
        if (proDao.registrarProveedor(pro)) {
            proDao.llenarProveedores(cmbProovedor);
            JOptionPane.showMessageDialog(null, "Registro Exitoso", "Mensaje", JOptionPane.DEFAULT_OPTION);
        } else {
            JOptionPane.showMessageDialog(null, "El proveedor ya está registrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validarRegistroCliente() {
        String rut = txtRutCl.getText().trim();
        String nombre = txtNombreCl.getText().trim();
        String telefono = txtTelefonoCl.getText().trim();
        String email = txtEmailCl.getText().trim();
        String direccion = txtDireccionCl.getText().trim();
        String rol = txtRolCliente.getText().trim();

        if (rut.isEmpty() || nombre.isEmpty() || telefono.isEmpty() || email.isEmpty() || direccion.isEmpty() || rol.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Los campos no pueden estar vacios", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        cliente.setNombre(nombre);
        cliente.setRut(rut);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);
        cliente.setDireccion(direccion);
        cliente.setRol(rol);

        // Intentar registrar el cliente
        if (clienteDao.registrarCliente(cliente)) {
            JOptionPane.showMessageDialog(null, "Registro Exitoso", "Congratulation", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "El cliente ya está registrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validarRegistroProducto() {
        if (!"".equals(txtCodigoProducto1.getText())
                && !"".equals(txtDescripcionProducto1.getText())
                && !"".equals(txtCantidadProducto1.getText())
                && !"".equals(txtPrecioProducto.getText())
                && cmbProovedor.getSelectedItem() != null) {

            produ.setCodigo(txtCodigoProducto1.getText());
            produ.setNombrePro(txtDescripcionProducto1.getText());
            produ.setStock(Integer.parseInt(txtCantidadProducto1.getText()));
            produ.setPrecio(Double.parseDouble(txtPrecioProducto.getText()));
            produ.setProveedor(cmbProovedor.getSelectedItem().toString());

            if (produDao.registrarProducto(produ)) {
                JOptionPane.showMessageDialog(null, "Registro Exitoso", "Congratulation", JOptionPane.DEFAULT_OPTION);
            } else {
                JOptionPane.showMessageDialog(null, "El producto ya se encuentra registrado");
            }

        } else {
            JOptionPane.showMessageDialog(null, "Los Campos están vacíos.");
        }
    }

    private void validarRutVenta() {
        String rut = txtRut.getText().trim();
        if (!rut.isEmpty()) {
            Cliente cliente = clienteDao.buscarClientePorRut(rut);  // ← obtener el objeto Cliente
            if (cliente != null) {
                txtNombre.setText(cliente.getNombre());
                txtTelefono.setText(cliente.getTelefono());
                txtDireccion.setText(cliente.getDireccion());
                btnGenerarV.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "El RUT no se encuentra registrado.");
                txtRut.setText("");
                txtNombre.setText("");
                txtTelefono.setText("");
                txtDireccion.setText("");
                btnGenerarV.setEnabled(false);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar un RUT.");
            txtRut.requestFocus();
            btnGenerarV.setEnabled(false);
        }

    }

    private void actualizarCliente() {
        cliente.setRut(txtRutCl.getText());
        cliente.setNombre(txtNombreCl.getText());
        cliente.setTelefono(txtTelefonoCl.getText());
        cliente.setEmail(txtEmailCl.getText());
        cliente.setDireccion(txtDireccionCl.getText());
        cliente.setRol(txtRolCliente.getText());

        if (clienteDao.actualizarCliente(cliente)) {
            JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
            listarCliente(); // si tienes una tabla que actualizar
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el cliente.");
        }
    }

    private void actualizarProveedor() {
        pro.setRut(txtRutProveedor.getText());
        pro.setNombre(txtNombreProveedor.getText());
        pro.setTelefono(txtTelefonoProveedor.getText());
        pro.setEmail(txtEmailProveedor.getText());
        pro.setDireccion(txtDireccionProveedor.getText());
        pro.setRol(txtRolP.getText());

        if (proDao.actualizarProveedor(pro)) {
            JOptionPane.showMessageDialog(this, "Proveedor actualizado correctamente.");
            listarProveedor(); // si tienes una tabla que actualizar
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el proveedor.");
        }
    }

    private void actualizarProducto() {
        produ.setCodigo(txtCodigoProducto1.getText());
        produ.setNombrePro(txtDescripcionProducto1.getText());
        produ.setStock(Integer.parseInt(txtCantidadProducto1.getText()));
        produ.setPrecio(Double.parseDouble(txtPrecioProducto.getText()));
        produ.setProveedor(cmbProovedor.getSelectedItem().toString());

        if (produDao.actualizarProducto(produ)) {
            JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
            listarProductos(); // si tienes una tabla que actualizar
            limpiarCamposProducto();
            proDao.llenarProveedores(cmbProovedor);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el producto.");
        }

    }

    public void listarCliente() {
        DefaultTableModel modelo = clienteDao.mostrarClientes();
        tblListadoClientes.setModel(modelo);
    }

    public void listarProveedor() {
        DefaultTableModel modelo = proDao.mostrarProveedores();
        tblListadoProveedor.setModel(modelo);
    }

    public void listarProductos() {
        DefaultTableModel modelo = produDao.mostrarProducto();
        tblListaProducto.setModel(modelo);
    }

    public void listarVentas() {
        DefaultTableModel modelo = ventaDao.mostrarVentas();
        tblListadoVentaRealizada.setModel(modelo);
    }

    public void eliminarCliente() {
        String rut = txtRutCl.getText().trim();

        if (!rut.isEmpty()) {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                int eliminado = clienteDao.eliminarClientePorRut(rut);

                if (eliminado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
                    limpiarTableCliente();
                    listarCliente();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el cliente. Verifica el RUT.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un RUT válido.");
        }
    }

    private void eliminarProveedor() {
        String rut = txtRutProveedor.getText().trim();

        if (!rut.isEmpty()) {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                int eliminado = proDao.eliminarProveedorPorRut(rut);

                if (eliminado > 0) {
                    JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
                    limpiarTableCliente();
                    listarProveedor();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el cliente. Verifica el RUT.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un RUT válido.");
        }
    }

    private void eliminarProducto() {
        String cod = txtCodigoProducto1.getText().trim();

        if (!cod.isEmpty()) {
            int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                int eliminado = produDao.eliminarProductoPorCod(cod);

                if (eliminado > 0) {
                    JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
                    limpiarTableProveedor();
                    listarProductos();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo eliminar el Producto. Verifica el Código.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un codigo válido.");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Background = new javax.swing.JPanel();
        menu = new javax.swing.JPanel();
        btnVentas = new javax.swing.JButton();
        lblLogoEmpresa = new javax.swing.JLabel();
        btnClientes = new javax.swing.JButton();
        btnProveedor = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnPrincipal = new javax.swing.JButton();
        btncerrarSesion = new javax.swing.JButton();
        lblVendedor = new javax.swing.JLabel();
        header = new javax.swing.JPanel();
        lblHeaderUno = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        lblBienvenida = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanelPrincipal = new javax.swing.JPanel();
        lblPrecio = new javax.swing.JLabel();
        lblStock = new javax.swing.JLabel();
        lblCantidad = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        lblLimpiarVenta = new javax.swing.JLabel();
        lblBuscar = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListadoVentas = new javax.swing.JTable();
        btnGenerarV = new javax.swing.JButton();
        btnEliminarV = new javax.swing.JButton();
        txtIdVenta = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtRut = new javax.swing.JTextField();
        lblRut = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtRol = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        txtDescripcion = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtStock = new javax.swing.JTextField();
        lblTotal = new javax.swing.JLabel();
        jPanelVentas = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblListadoVentaRealizada = new javax.swing.JTable();
        btnPdf = new javax.swing.JButton();
        txtIdDetalle = new javax.swing.JTextField();
        jPanelProveedor = new javax.swing.JPanel();
        lblRutProveedor = new javax.swing.JLabel();
        lblNombreProveedor = new javax.swing.JLabel();
        lblTelefonoProveedor = new javax.swing.JLabel();
        lblDireccionProveedor = new javax.swing.JLabel();
        lblEmailProveedor = new javax.swing.JLabel();
        lblRolProveedor = new javax.swing.JLabel();
        btnActualizarP = new javax.swing.JButton();
        btnGuardarP = new javax.swing.JButton();
        btnEliminarP = new javax.swing.JButton();
        btnImportarP = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblListadoProveedor = new javax.swing.JTable();
        lblBuscarProveedor = new javax.swing.JLabel();
        lblLimpiarProveedor = new javax.swing.JLabel();
        txtRutProveedor = new javax.swing.JTextField();
        txtNombreProveedor = new javax.swing.JTextField();
        txtEmailProveedor = new javax.swing.JTextField();
        txtDireccionProveedor = new javax.swing.JTextField();
        txtTelefonoProveedor = new javax.swing.JTextField();
        txtRolP = new javax.swing.JTextField();
        jPanelProducto = new javax.swing.JPanel();
        lblProovedorProducto = new javax.swing.JLabel();
        lblPrecioProducto = new javax.swing.JLabel();
        lblDescripcionProducto = new javax.swing.JLabel();
        lblCantidadProducto = new javax.swing.JLabel();
        lblCodigoProducto = new javax.swing.JLabel();
        lblBuscarProducto = new javax.swing.JLabel();
        lblLimpiarProducto = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblListaProducto = new javax.swing.JTable();
        btnGuardarProducto = new javax.swing.JButton();
        btnImportarProducto = new javax.swing.JButton();
        btnExel = new javax.swing.JButton();
        btnActualizarProducto = new javax.swing.JButton();
        btnEliminarProducto = new javax.swing.JButton();
        btnGrafico = new javax.swing.JButton();
        txtPrecioProducto = new javax.swing.JTextField();
        txtCodigoProducto1 = new javax.swing.JTextField();
        txtCantidadProducto1 = new javax.swing.JTextField();
        txtDescripcionProducto1 = new javax.swing.JTextField();
        cmbProovedor = new javax.swing.JComboBox<>();
        jDate = new com.toedter.calendar.JDateChooser();
        lblGrafico = new javax.swing.JLabel();
        jPanelClientes = new javax.swing.JPanel();
        lblRutCl = new javax.swing.JLabel();
        lblEmailCl = new javax.swing.JLabel();
        lblNombreCl = new javax.swing.JLabel();
        lblDireccionCl = new javax.swing.JLabel();
        lblRolCl = new javax.swing.JLabel();
        lblTelefonoCl = new javax.swing.JLabel();
        btnEliminarCl = new javax.swing.JButton();
        btnImportarCl = new javax.swing.JButton();
        btnActualizarCl = new javax.swing.JButton();
        btnGuardarCl = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblListadoClientes = new javax.swing.JTable();
        lblBuscarCliente = new javax.swing.JLabel();
        lblLimpiarCliente = new javax.swing.JLabel();
        txtRolCliente = new javax.swing.JTextField();
        txtRutCl = new javax.swing.JTextField();
        txtNombreCl = new javax.swing.JTextField();
        txtEmailCl = new javax.swing.JTextField();
        txtDireccionCl = new javax.swing.JTextField();
        txtTelefonoCl = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        menuCliente = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        menuExportarCliente = new javax.swing.JMenu();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenu4 = new javax.swing.JMenu();
        menuDescargarProveedor = new javax.swing.JMenu();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        menuExportarProveedor = new javax.swing.JMenu();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenu2 = new javax.swing.JMenu();
        menuPlanillaProducto = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Background.setBackground(new java.awt.Color(255, 255, 255));
        Background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        menu.setBackground(new java.awt.Color(204, 255, 153));
        menu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnVentas.setBackground(new java.awt.Color(0, 204, 204));
        btnVentas.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnVentas.setForeground(new java.awt.Color(255, 255, 255));
        btnVentas.setIcon(new javax.swing.ImageIcon("C:\\Users\\carlo\\Documents\\NetBeansProjects\\GestionInventario\\src\\main\\Resources\\ventas.png")); // NOI18N
        btnVentas.setText("Ventas");
        btnVentas.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        btnVentas.setBorderPainted(false);
        btnVentas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVentas.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnVentas.setIconTextGap(15);
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });
        menu.add(btnVentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 330, 60));

        lblLogoEmpresa.setForeground(new java.awt.Color(255, 255, 255));
        lblLogoEmpresa.setIcon(new javax.swing.ImageIcon("C:\\Users\\carlo\\Documents\\NetBeansProjects\\GestionInventario\\src\\main\\Resources\\pngwing.com.png")); // NOI18N
        lblLogoEmpresa.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lblLogoEmpresa.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        menu.add(lblLogoEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 242, 167));

        btnClientes.setBackground(new java.awt.Color(0, 204, 204));
        btnClientes.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnClientes.setForeground(new java.awt.Color(255, 255, 255));
        btnClientes.setIcon(new javax.swing.ImageIcon("C:\\Users\\carlo\\Documents\\NetBeansProjects\\GestionInventario\\src\\main\\Resources\\cliente(1).png")); // NOI18N
        btnClientes.setText("Clientes");
        btnClientes.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        btnClientes.setBorderPainted(false);
        btnClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClientes.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnClientes.setIconTextGap(15);
        btnClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientesActionPerformed(evt);
            }
        });
        menu.add(btnClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 330, 60));

        btnProveedor.setBackground(new java.awt.Color(0, 204, 204));
        btnProveedor.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnProveedor.setForeground(new java.awt.Color(255, 255, 255));
        btnProveedor.setIcon(new javax.swing.ImageIcon("C:\\Users\\carlo\\Documents\\NetBeansProjects\\GestionInventario\\src\\main\\Resources\\proveedor.png")); // NOI18N
        btnProveedor.setText("proveedor");
        btnProveedor.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        btnProveedor.setBorderPainted(false);
        btnProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProveedor.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnProveedor.setIconTextGap(15);
        btnProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorActionPerformed(evt);
            }
        });
        menu.add(btnProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 330, 60));

        btnProductos.setBackground(new java.awt.Color(0, 204, 204));
        btnProductos.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnProductos.setForeground(new java.awt.Color(255, 255, 255));
        btnProductos.setIcon(new javax.swing.ImageIcon("C:\\Users\\carlo\\Documents\\NetBeansProjects\\GestionInventario\\src\\main\\Resources\\producto(1).png")); // NOI18N
        btnProductos.setText("Productos");
        btnProductos.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        btnProductos.setBorderPainted(false);
        btnProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProductos.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnProductos.setIconTextGap(15);
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });
        menu.add(btnProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 430, 330, 60));

        btnPrincipal.setBackground(new java.awt.Color(0, 204, 204));
        btnPrincipal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btnPrincipal.setForeground(new java.awt.Color(255, 255, 255));
        btnPrincipal.setIcon(new javax.swing.ImageIcon("C:\\Users\\carlo\\Documents\\NetBeansProjects\\GestionInventario\\src\\main\\Resources\\pagina-principal.png")); // NOI18N
        btnPrincipal.setText("Principal");
        btnPrincipal.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        btnPrincipal.setBorderPainted(false);
        btnPrincipal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrincipal.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btnPrincipal.setIconTextGap(15);
        btnPrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnPrincipalMouseClicked(evt);
            }
        });
        menu.add(btnPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 330, 60));

        btncerrarSesion.setBackground(new java.awt.Color(0, 204, 204));
        btncerrarSesion.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        btncerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        btncerrarSesion.setIcon(new javax.swing.ImageIcon("C:\\Users\\carlo\\Documents\\NetBeansProjects\\GestionInventario\\src\\main\\Resources\\cerrar-sesion.png")); // NOI18N
        btncerrarSesion.setText("Cierre Sesión");
        btncerrarSesion.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 15, 1, 1, new java.awt.Color(0, 0, 0)));
        btncerrarSesion.setBorderPainted(false);
        btncerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btncerrarSesion.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        btncerrarSesion.setIconTextGap(15);
        btncerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncerrarSesionActionPerformed(evt);
            }
        });
        menu.add(btncerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 750, 330, 60));

        lblVendedor.setBackground(new java.awt.Color(255, 255, 255));
        lblVendedor.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        lblVendedor.setForeground(new java.awt.Color(255, 255, 255));
        lblVendedor.setText("Information Factory");
        menu.add(lblVendedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 180, -1));

        Background.add(menu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        header.setBackground(new java.awt.Color(0, 204, 204));
        header.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblHeaderUno.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblHeaderUno.setForeground(new java.awt.Color(255, 255, 255));
        lblHeaderUno.setText("Administrador/Control/Inventario");
        header.add(lblHeaderUno, new org.netbeans.lib.awtextra.AbsoluteConstraints(29, 6, 358, 42));

        lblFecha.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(255, 255, 255));
        header.add(lblFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 556, 55));

        Background.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 70, 1160, 150));

        lblBienvenida.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblBienvenida.setForeground(new java.awt.Color(0, 0, 0));
        lblBienvenida.setText("Bienvenido Administrador!!");
        Background.add(lblBienvenida, new org.netbeans.lib.awtextra.AbsoluteConstraints(348, 23, 426, 37));

        jPanelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        jPanelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPrecio.setBackground(new java.awt.Color(0, 0, 102));
        lblPrecio.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        lblPrecio.setForeground(new java.awt.Color(0, 0, 0));
        lblPrecio.setText("Precio");
        lblPrecio.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelPrincipal.add(lblPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 70, 60, 20));

        lblStock.setBackground(new java.awt.Color(0, 0, 102));
        lblStock.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        lblStock.setForeground(new java.awt.Color(0, 0, 0));
        lblStock.setText("Stock ");
        lblStock.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelPrincipal.add(lblStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 60, 60, 30));

        lblCantidad.setBackground(new java.awt.Color(0, 0, 102));
        lblCantidad.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        lblCantidad.setForeground(new java.awt.Color(0, 0, 0));
        lblCantidad.setText("Cantidad");
        lblCantidad.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelPrincipal.add(lblCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 60, 70, 30));

        lblDescripcion.setBackground(new java.awt.Color(0, 0, 102));
        lblDescripcion.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        lblDescripcion.setForeground(new java.awt.Color(0, 0, 0));
        lblDescripcion.setText("Descripción");
        lblDescripcion.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelPrincipal.add(lblDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, 90, 20));

        lblCodigo.setBackground(new java.awt.Color(0, 0, 102));
        lblCodigo.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        lblCodigo.setForeground(new java.awt.Color(0, 0, 0));
        lblCodigo.setText("Codígo");
        lblCodigo.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelPrincipal.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 60, 20));

        lblLimpiarVenta.setForeground(new java.awt.Color(0, 0, 0));
        lblLimpiarVenta.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLimpiarVenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/actualizar (1).png"))); // NOI18N
        lblLimpiarVenta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLimpiarVenta.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        lblLimpiarVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLimpiarVentaMouseClicked(evt);
            }
        });
        jPanelPrincipal.add(lblLimpiarVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 50, 50));

        lblBuscar.setForeground(new java.awt.Color(0, 0, 0));
        lblBuscar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscar-usuario.png"))); // NOI18N
        lblBuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        lblBuscar.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        lblBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBuscarMouseClicked(evt);
            }
        });
        jPanelPrincipal.add(lblBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 50, 50));

        tblListadoVentas.setBackground(new java.awt.Color(255, 255, 255));
        tblListadoVentas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 0, 0), new java.awt.Color(0, 0, 0), null, null));
        tblListadoVentas.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblListadoVentas.setForeground(new java.awt.Color(0, 0, 0));
        tblListadoVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Descripción", "Cantidad", "Precio ", "Total"
            }
        ));
        tblListadoVentas.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(tblListadoVentas);

        jPanelPrincipal.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 1100, 200));

        btnGenerarV.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnGenerarV.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerarV.setText("Generar Venta");
        btnGenerarV.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGenerarV.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnGenerarV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarVActionPerformed(evt);
            }
        });
        jPanelPrincipal.add(btnGenerarV, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 550, 280, 50));

        btnEliminarV.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnEliminarV.setForeground(new java.awt.Color(255, 255, 255));
        btnEliminarV.setText("Eliminar Venta");
        btnEliminarV.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarV.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnEliminarV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarVActionPerformed(evt);
            }
        });
        jPanelPrincipal.add(btnEliminarV, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 550, 280, 50));

        txtIdVenta.setText("jTextField1");
        jPanelPrincipal.add(txtIdVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 40, 20, -1));

        txtNombre.setBackground(new java.awt.Color(255, 255, 255));
        txtNombre.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtNombre.setForeground(new java.awt.Color(0, 0, 0));
        jPanelPrincipal.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, 280, 40));

        txtRut.setBackground(new java.awt.Color(255, 255, 255));
        txtRut.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtRut.setForeground(new java.awt.Color(0, 0, 0));
        txtRut.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtRutKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutKeyTyped(evt);
            }
        });
        jPanelPrincipal.add(txtRut, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 280, 40));

        lblRut.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        lblRut.setForeground(new java.awt.Color(0, 0, 0));
        lblRut.setText("Rut");
        jPanelPrincipal.add(lblRut, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 110, 20));

        lblNombre.setFont(new java.awt.Font("Arial Black", 1, 12)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(0, 0, 0));
        lblNombre.setText("Nombre");
        jPanelPrincipal.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 470, 100, 20));

        txtTelefono.setText("jTextField3");
        jPanelPrincipal.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 370, 30, 30));

        txtDireccion.setText("jTextField4");
        jPanelPrincipal.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 370, 30, -1));

        txtEmail.setText("jTextField5");
        jPanelPrincipal.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, 30, -1));

        txtRol.setText("jTextField6");
        jPanelPrincipal.add(txtRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 370, 30, -1));

        txtTotal.setBackground(new java.awt.Color(255, 255, 255));
        txtTotal.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtTotal.setForeground(new java.awt.Color(0, 0, 0));
        jPanelPrincipal.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 400, 280, 40));

        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigo.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtCodigo.setForeground(new java.awt.Color(0, 0, 0));
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
        });
        jPanelPrincipal.add(txtCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 190, 30));

        txtDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcion.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtDescripcion.setForeground(new java.awt.Color(0, 0, 0));
        jPanelPrincipal.add(txtDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, 250, 30));

        txtCantidad.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidad.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtCantidad.setForeground(new java.awt.Color(0, 0, 0));
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadKeyTyped(evt);
            }
        });
        jPanelPrincipal.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 90, 80, 30));

        txtPrecio.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecio.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtPrecio.setForeground(new java.awt.Color(0, 0, 0));
        jPanelPrincipal.add(txtPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 90, 100, 30));

        txtStock.setBackground(new java.awt.Color(255, 255, 255));
        txtStock.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtStock.setForeground(new java.awt.Color(0, 51, 153));
        jPanelPrincipal.add(txtStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 90, 60, 30));

        lblTotal.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(0, 0, 0));
        lblTotal.setText("Precio");
        lblTotal.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelPrincipal.add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 370, 150, 30));

        jTabbedPane2.addTab("tab1", jPanelPrincipal);

        jPanelVentas.setBackground(new java.awt.Color(0, 255, 255));
        jPanelVentas.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblListadoVentaRealizada.setBackground(new java.awt.Color(255, 255, 255));
        tblListadoVentaRealizada.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblListadoVentaRealizada.setForeground(new java.awt.Color(0, 0, 0));
        tblListadoVentaRealizada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID ", "CLIENTE", "VENDEDOR", "TOTAL", "FECHA VENTA"
            }
        ));
        tblListadoVentaRealizada.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListadoVentaRealizadaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblListadoVentaRealizada);

        jPanelVentas.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(24, 53, 1111, -1));

        btnPdf.setText("Abrir");
        btnPdf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfActionPerformed(evt);
            }
        });
        jPanelVentas.add(btnPdf, new org.netbeans.lib.awtextra.AbsoluteConstraints(883, 552, 252, 46));

        txtIdDetalle.setText("jTextField1");
        jPanelVentas.add(txtIdDetalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 540, 50, -1));

        jTabbedPane2.addTab("tab2", jPanelVentas);

        jPanelProveedor.setBackground(new java.awt.Color(0, 255, 255));
        jPanelProveedor.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRutProveedor.setBackground(new java.awt.Color(255, 255, 255));
        lblRutProveedor.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblRutProveedor.setForeground(new java.awt.Color(0, 0, 0));
        lblRutProveedor.setText("Rut");
        lblRutProveedor.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanelProveedor.add(lblRutProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 100, 20));

        lblNombreProveedor.setBackground(new java.awt.Color(255, 255, 255));
        lblNombreProveedor.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblNombreProveedor.setForeground(new java.awt.Color(0, 0, 0));
        lblNombreProveedor.setText("Nombre");
        lblNombreProveedor.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelProveedor.add(lblNombreProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 110, 30));

        lblTelefonoProveedor.setBackground(new java.awt.Color(255, 255, 255));
        lblTelefonoProveedor.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblTelefonoProveedor.setForeground(new java.awt.Color(0, 0, 0));
        lblTelefonoProveedor.setText("Telefono");
        lblTelefonoProveedor.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelProveedor.add(lblTelefonoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 210, 60));

        lblDireccionProveedor.setBackground(new java.awt.Color(255, 255, 255));
        lblDireccionProveedor.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblDireccionProveedor.setForeground(new java.awt.Color(0, 0, 0));
        lblDireccionProveedor.setText("Dirección");
        lblDireccionProveedor.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelProveedor.add(lblDireccionProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 180, 60));

        lblEmailProveedor.setBackground(new java.awt.Color(255, 255, 255));
        lblEmailProveedor.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblEmailProveedor.setForeground(new java.awt.Color(0, 0, 0));
        lblEmailProveedor.setText("Email");
        lblEmailProveedor.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelProveedor.add(lblEmailProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 150, 60));

        lblRolProveedor.setBackground(new java.awt.Color(255, 255, 255));
        lblRolProveedor.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblRolProveedor.setForeground(new java.awt.Color(0, 0, 0));
        lblRolProveedor.setText("Rol");
        lblRolProveedor.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelProveedor.add(lblRolProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 60, 60));

        btnActualizarP.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnActualizarP.setText("Actualizar");
        btnActualizarP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizarP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActualizarPMouseClicked(evt);
            }
        });
        jPanelProveedor.add(btnActualizarP, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 510, 190, 43));

        btnGuardarP.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnGuardarP.setText("Guardar");
        btnGuardarP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarPActionPerformed(evt);
            }
        });
        jPanelProveedor.add(btnGuardarP, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 410, 190, 43));

        btnEliminarP.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnEliminarP.setText("Eliminar");
        btnEliminarP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPActionPerformed(evt);
            }
        });
        jPanelProveedor.add(btnEliminarP, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 410, 190, 43));

        btnImportarP.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnImportarP.setText("Importar");
        btnImportarP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImportarP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarPActionPerformed(evt);
            }
        });
        jPanelProveedor.add(btnImportarP, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 510, 190, 43));

        tblListadoProveedor.setBackground(new java.awt.Color(255, 255, 255));
        tblListadoProveedor.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblListadoProveedor.setForeground(new java.awt.Color(0, 0, 0));
        tblListadoProveedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "RUT", "NOMBRE", "EMAIL", "TELEFONO", "DIRECCIÓN", "ROL"
            }
        ));
        tblListadoProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListadoProveedorMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblListadoProveedor);

        jPanelProveedor.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 730, 350));

        lblBuscarProveedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBuscarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscar-usuario.png"))); // NOI18N
        lblBuscarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblBuscarProveedor.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        lblBuscarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBuscarProveedorMouseClicked(evt);
            }
        });
        jPanelProveedor.add(lblBuscarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 40, 40));

        lblLimpiarProveedor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLimpiarProveedor.setIcon(new javax.swing.ImageIcon("C:\\Users\\carlo\\Documents\\NetBeansProjects\\GestionInventario\\src\\main\\Resources\\actualizar (1).png")); // NOI18N
        lblLimpiarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLimpiarProveedor.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        lblLimpiarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLimpiarProveedorMouseClicked(evt);
            }
        });
        jPanelProveedor.add(lblLimpiarProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 40, 40));

        txtRutProveedor.setBackground(new java.awt.Color(255, 255, 255));
        txtRutProveedor.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtRutProveedor.setForeground(new java.awt.Color(0, 0, 0));
        txtRutProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutProveedorKeyTyped(evt);
            }
        });
        jPanelProveedor.add(txtRutProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 270, 30));

        txtNombreProveedor.setBackground(new java.awt.Color(255, 255, 255));
        txtNombreProveedor.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtNombreProveedor.setForeground(new java.awt.Color(0, 0, 0));
        txtNombreProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreProveedorKeyTyped(evt);
            }
        });
        jPanelProveedor.add(txtNombreProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 270, 30));

        txtEmailProveedor.setBackground(new java.awt.Color(255, 255, 255));
        txtEmailProveedor.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtEmailProveedor.setForeground(new java.awt.Color(0, 0, 0));
        jPanelProveedor.add(txtEmailProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 270, 30));

        txtDireccionProveedor.setBackground(new java.awt.Color(255, 255, 255));
        txtDireccionProveedor.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtDireccionProveedor.setForeground(new java.awt.Color(0, 0, 0));
        jPanelProveedor.add(txtDireccionProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 270, 30));

        txtTelefonoProveedor.setBackground(new java.awt.Color(255, 255, 255));
        txtTelefonoProveedor.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtTelefonoProveedor.setForeground(new java.awt.Color(0, 0, 0));
        txtTelefonoProveedor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoProveedorKeyTyped(evt);
            }
        });
        jPanelProveedor.add(txtTelefonoProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 270, 30));

        txtRolP.setBackground(new java.awt.Color(255, 255, 255));
        txtRolP.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtRolP.setForeground(new java.awt.Color(0, 0, 0));
        txtRolP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRolPKeyTyped(evt);
            }
        });
        jPanelProveedor.add(txtRolP, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 270, 30));

        jTabbedPane2.addTab("tab3", jPanelProveedor);

        jPanelProducto.setBackground(new java.awt.Color(0, 255, 255));
        jPanelProducto.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblProovedorProducto.setBackground(new java.awt.Color(255, 255, 255));
        lblProovedorProducto.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblProovedorProducto.setForeground(new java.awt.Color(0, 0, 0));
        lblProovedorProducto.setText("Proveedor:");
        lblProovedorProducto.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelProducto.add(lblProovedorProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 140, 60));

        lblPrecioProducto.setBackground(new java.awt.Color(255, 255, 255));
        lblPrecioProducto.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblPrecioProducto.setForeground(new java.awt.Color(0, 0, 0));
        lblPrecioProducto.setText("Precio:");
        lblPrecioProducto.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelProducto.add(lblPrecioProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 260, 60));

        lblDescripcionProducto.setBackground(new java.awt.Color(255, 255, 255));
        lblDescripcionProducto.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblDescripcionProducto.setForeground(new java.awt.Color(0, 0, 0));
        lblDescripcionProducto.setText("Descripción");
        lblDescripcionProducto.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelProducto.add(lblDescripcionProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 220, 60));

        lblCantidadProducto.setBackground(new java.awt.Color(255, 255, 255));
        lblCantidadProducto.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblCantidadProducto.setForeground(new java.awt.Color(0, 0, 0));
        lblCantidadProducto.setText("Cantidad:");
        lblCantidadProducto.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelProducto.add(lblCantidadProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 240, 60));

        lblCodigoProducto.setBackground(new java.awt.Color(255, 255, 255));
        lblCodigoProducto.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblCodigoProducto.setForeground(new java.awt.Color(0, 0, 0));
        lblCodigoProducto.setText("Código:");
        lblCodigoProducto.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelProducto.add(lblCodigoProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 110, 20));

        lblBuscarProducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBuscarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscar-usuario.png"))); // NOI18N
        lblBuscarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblBuscarProducto.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        lblBuscarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBuscarProductoMouseClicked(evt);
            }
        });
        jPanelProducto.add(lblBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 40, 40));

        lblLimpiarProducto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLimpiarProducto.setIcon(new javax.swing.ImageIcon("C:\\Users\\carlo\\Documents\\NetBeansProjects\\GestionInventario\\src\\main\\Resources\\actualizar (1).png")); // NOI18N
        lblLimpiarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLimpiarProducto.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        lblLimpiarProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLimpiarProductoMouseClicked(evt);
            }
        });
        jPanelProducto.add(lblLimpiarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 40, 40));

        tblListaProducto.setBackground(new java.awt.Color(255, 255, 255));
        tblListaProducto.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblListaProducto.setForeground(new java.awt.Color(0, 0, 0));
        tblListaProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "CÓDIGO", "DESCRIPCIÓN", "PRECIO", "STOCK", "PROVEEDOR"
            }
        ));
        tblListaProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListaProductoMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblListaProducto);

        jPanelProducto.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 720, 370));

        btnGuardarProducto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnGuardarProducto.setText("Guardar");
        btnGuardarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarProducto.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnGuardarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProductoActionPerformed(evt);
            }
        });
        jPanelProducto.add(btnGuardarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 450, 150, 40));

        btnImportarProducto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnImportarProducto.setText("Importar");
        btnImportarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImportarProducto.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnImportarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarProductoActionPerformed(evt);
            }
        });
        jPanelProducto.add(btnImportarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 530, 150, 40));

        btnExel.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnExel.setText("Exel");
        btnExel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnExel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExelActionPerformed(evt);
            }
        });
        jPanelProducto.add(btnExel, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 530, 150, 40));

        btnActualizarProducto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnActualizarProducto.setText("Actualizar");
        btnActualizarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizarProducto.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnActualizarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarProductoActionPerformed(evt);
            }
        });
        jPanelProducto.add(btnActualizarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 450, 150, 40));

        btnEliminarProducto.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnEliminarProducto.setText("Eliminar");
        btnEliminarProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarProducto.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnEliminarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProductoActionPerformed(evt);
            }
        });
        jPanelProducto.add(btnEliminarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 450, 150, 40));

        btnGrafico.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnGrafico.setText("Grafico");
        btnGrafico.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGrafico.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnGrafico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGraficoActionPerformed(evt);
            }
        });
        jPanelProducto.add(btnGrafico, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 530, 150, 40));

        txtPrecioProducto.setBackground(new java.awt.Color(255, 255, 255));
        txtPrecioProducto.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtPrecioProducto.setForeground(new java.awt.Color(0, 0, 0));
        txtPrecioProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecioProductoKeyTyped(evt);
            }
        });
        jPanelProducto.add(txtPrecioProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 270, 30));

        txtCodigoProducto1.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigoProducto1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtCodigoProducto1.setForeground(new java.awt.Color(0, 0, 0));
        jPanelProducto.add(txtCodigoProducto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 270, 30));

        txtCantidadProducto1.setBackground(new java.awt.Color(255, 255, 255));
        txtCantidadProducto1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtCantidadProducto1.setForeground(new java.awt.Color(0, 0, 0));
        txtCantidadProducto1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCantidadProducto1KeyTyped(evt);
            }
        });
        jPanelProducto.add(txtCantidadProducto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 270, 30));

        txtDescripcionProducto1.setBackground(new java.awt.Color(255, 255, 255));
        txtDescripcionProducto1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtDescripcionProducto1.setForeground(new java.awt.Color(0, 0, 0));
        jPanelProducto.add(txtDescripcionProducto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 270, 30));

        cmbProovedor.setBackground(new java.awt.Color(255, 255, 255));
        cmbProovedor.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        cmbProovedor.setForeground(new java.awt.Color(0, 0, 0));
        jPanelProducto.add(cmbProovedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 270, 30));

        jDate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanelProducto.add(jDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 270, 30));

        lblGrafico.setBackground(new java.awt.Color(255, 255, 255));
        lblGrafico.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblGrafico.setForeground(new java.awt.Color(0, 0, 0));
        lblGrafico.setText("Fecha Graficar");
        lblGrafico.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelProducto.add(lblGrafico, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 140, 60));

        jTabbedPane2.addTab("tab4", jPanelProducto);

        jPanelClientes.setBackground(new java.awt.Color(0, 255, 255));
        jPanelClientes.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRutCl.setBackground(new java.awt.Color(255, 255, 255));
        lblRutCl.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblRutCl.setForeground(new java.awt.Color(0, 0, 0));
        lblRutCl.setText("Rut");
        lblRutCl.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jPanelClientes.add(lblRutCl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 90, 20));

        lblEmailCl.setBackground(new java.awt.Color(255, 255, 255));
        lblEmailCl.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblEmailCl.setForeground(new java.awt.Color(0, 0, 0));
        lblEmailCl.setText("Email");
        lblEmailCl.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelClientes.add(lblEmailCl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 230, 60));

        lblNombreCl.setBackground(new java.awt.Color(255, 255, 255));
        lblNombreCl.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblNombreCl.setForeground(new java.awt.Color(0, 0, 0));
        lblNombreCl.setText("Nombre");
        lblNombreCl.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelClientes.add(lblNombreCl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 110, 30));

        lblDireccionCl.setBackground(new java.awt.Color(255, 255, 255));
        lblDireccionCl.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblDireccionCl.setForeground(new java.awt.Color(0, 0, 0));
        lblDireccionCl.setText("Dirección");
        lblDireccionCl.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelClientes.add(lblDireccionCl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 210, 60));

        lblRolCl.setBackground(new java.awt.Color(255, 255, 255));
        lblRolCl.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblRolCl.setForeground(new java.awt.Color(0, 0, 0));
        lblRolCl.setText("Rol");
        lblRolCl.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanelClientes.add(lblRolCl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 450, 230, 60));

        lblTelefonoCl.setBackground(new java.awt.Color(255, 255, 255));
        lblTelefonoCl.setFont(new java.awt.Font("Arial Black", 1, 14)); // NOI18N
        lblTelefonoCl.setForeground(new java.awt.Color(0, 0, 0));
        lblTelefonoCl.setText("Telefono");
        lblTelefonoCl.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        lblTelefonoCl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                lblTelefonoClKeyTyped(evt);
            }
        });
        jPanelClientes.add(lblTelefonoCl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 210, 30));

        btnEliminarCl.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnEliminarCl.setText("Eliminar");
        btnEliminarCl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminarCl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarClActionPerformed(evt);
            }
        });
        jPanelClientes.add(btnEliminarCl, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 410, 190, 43));

        btnImportarCl.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnImportarCl.setText("Importar");
        btnImportarCl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImportarCl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarClActionPerformed(evt);
            }
        });
        jPanelClientes.add(btnImportarCl, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 510, 190, 43));

        btnActualizarCl.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnActualizarCl.setText("Actualizar");
        btnActualizarCl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizarCl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarClActionPerformed(evt);
            }
        });
        jPanelClientes.add(btnActualizarCl, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 510, 190, 43));

        btnGuardarCl.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnGuardarCl.setText("Guardar");
        btnGuardarCl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardarCl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarClActionPerformed(evt);
            }
        });
        jPanelClientes.add(btnGuardarCl, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 410, 190, 43));

        tblListadoClientes.setBackground(new java.awt.Color(255, 255, 255));
        tblListadoClientes.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        tblListadoClientes.setForeground(new java.awt.Color(0, 0, 0));
        tblListadoClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "RUT", "NOMBRE", "EMAIL", "TELEFONO", "DIRECCIÓN", "ROL"
            }
        ));
        tblListadoClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListadoClientesMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblListadoClientes);

        jPanelClientes.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 730, 350));

        lblBuscarCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBuscarCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buscar-usuario.png"))); // NOI18N
        lblBuscarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblBuscarCliente.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        lblBuscarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBuscarClienteMouseClicked(evt);
            }
        });
        jPanelClientes.add(lblBuscarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 50, 40, 40));

        lblLimpiarCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLimpiarCliente.setIcon(new javax.swing.ImageIcon("C:\\Users\\carlo\\Documents\\NetBeansProjects\\GestionInventario\\src\\main\\Resources\\actualizar (1).png")); // NOI18N
        lblLimpiarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblLimpiarCliente.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        lblLimpiarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblLimpiarClienteMouseClicked(evt);
            }
        });
        jPanelClientes.add(lblLimpiarCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 50, 40, 40));

        txtRolCliente.setBackground(new java.awt.Color(255, 255, 255));
        txtRolCliente.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtRolCliente.setForeground(new java.awt.Color(0, 0, 0));
        txtRolCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRolClienteKeyTyped(evt);
            }
        });
        jPanelClientes.add(txtRolCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 510, 270, 30));

        txtRutCl.setBackground(new java.awt.Color(255, 255, 255));
        txtRutCl.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtRutCl.setForeground(new java.awt.Color(0, 0, 0));
        txtRutCl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtRutClKeyTyped(evt);
            }
        });
        jPanelClientes.add(txtRutCl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 270, 30));

        txtNombreCl.setBackground(new java.awt.Color(255, 255, 255));
        txtNombreCl.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtNombreCl.setForeground(new java.awt.Color(0, 0, 0));
        txtNombreCl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreClKeyTyped(evt);
            }
        });
        jPanelClientes.add(txtNombreCl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 270, 30));

        txtEmailCl.setBackground(new java.awt.Color(255, 255, 255));
        txtEmailCl.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtEmailCl.setForeground(new java.awt.Color(0, 0, 0));
        jPanelClientes.add(txtEmailCl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 270, 30));

        txtDireccionCl.setBackground(new java.awt.Color(255, 255, 255));
        txtDireccionCl.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtDireccionCl.setForeground(new java.awt.Color(0, 0, 0));
        jPanelClientes.add(txtDireccionCl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 270, 30));

        txtTelefonoCl.setBackground(new java.awt.Color(255, 255, 255));
        txtTelefonoCl.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        txtTelefonoCl.setForeground(new java.awt.Color(0, 0, 0));
        txtTelefonoCl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTelefonoClKeyTyped(evt);
            }
        });
        jPanelClientes.add(txtTelefonoCl, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 270, 30));

        jTabbedPane2.addTab("tab5", jPanelClientes);

        Background.add(jTabbedPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, 1160, 650));

        jMenu1.setText("Archivos");
        jMenu1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N

        jMenu3.setText("Clientes");
        jMenu3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        menuCliente.setText("Descargar Plantilla");
        menuCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuClienteMouseClicked(evt);
            }
        });
        jMenu3.add(menuCliente);
        jMenu3.add(jSeparator1);

        menuExportarCliente.setText("Exportar Clientes");
        menuExportarCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuExportarCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuExportarClienteMouseClicked(evt);
            }
        });
        jMenu3.add(menuExportarCliente);

        jMenu1.add(jMenu3);
        jMenu1.add(jSeparator4);

        jMenu4.setText("Proveedor");
        jMenu4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        menuDescargarProveedor.setText("Descargar plantilla");
        menuDescargarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuDescargarProveedor.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        menuDescargarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuDescargarProveedorMouseClicked(evt);
            }
        });
        jMenu4.add(menuDescargarProveedor);
        jMenu4.add(jSeparator2);

        menuExportarProveedor.setText("Exportar Proveedor");
        menuExportarProveedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuExportarProveedor.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        menuExportarProveedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuExportarProveedorMouseClicked(evt);
            }
        });
        jMenu4.add(menuExportarProveedor);

        jMenu1.add(jMenu4);
        jMenu1.add(jSeparator3);

        jMenu2.setText("Producto");
        jMenu2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jMenu2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        menuPlanillaProducto.setText("Descargar Planilla");
        menuPlanillaProducto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menuPlanillaProducto.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        menuPlanillaProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPlanillaProductoMouseClicked(evt);
            }
        });
        jMenu2.add(menuPlanillaProducto);

        jMenu1.add(jMenu2);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrincipalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrincipalMouseClicked
        jTabbedPane2.setSelectedIndex(0);

    }//GEN-LAST:event_btnPrincipalMouseClicked

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        listarVentas();
        jTabbedPane2.setSelectedIndex(1);
    }//GEN-LAST:event_btnVentasActionPerformed

    private void btnProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorActionPerformed
        listarProveedor();
        jTabbedPane2.setSelectedIndex(2);
    }//GEN-LAST:event_btnProveedorActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        listarProductos();
        jTabbedPane2.setSelectedIndex(3);
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientesActionPerformed

        listarCliente();
        jTabbedPane2.setSelectedIndex(4);

    }//GEN-LAST:event_btnClientesActionPerformed

    private void btncerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncerrarSesionActionPerformed
        this.dispose();
        login.setVisible(true);
    }//GEN-LAST:event_btncerrarSesionActionPerformed

    private void lblLimpiarVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLimpiarVentaMouseClicked
        limpiarVenta();
    }//GEN-LAST:event_lblLimpiarVentaMouseClicked

    private void lblBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBuscarMouseClicked
        validarCodigoVenta();
    }//GEN-LAST:event_lblBuscarMouseClicked

    private void btnGenerarVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarVActionPerformed
        if (tblListadoVentas.getRowCount() > 0) {
            registrarVenta();
            registrarDetalle();
            actualizarStock();
            generarVentaPDF();
            
            limpiarTableVenta();
            limpiarDatosCl();

        } else {
            JOptionPane.showMessageDialog(null, "No hay productos seleccionados");
        }

    }//GEN-LAST:event_btnGenerarVActionPerformed

    private void btnEliminarVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarVActionPerformed
        eliminarVentaTabla();
    }//GEN-LAST:event_btnEliminarVActionPerformed

    private void btnGuardarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarPActionPerformed
        validarRegistroPro();
        listarProveedor();
        limpiarCampos();
    }//GEN-LAST:event_btnGuardarPActionPerformed

    private void btnGuardarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProductoActionPerformed
        validarRegistroProducto();
        listarProductos();
        limpiarCamposProducto();
        proDao.llenarProveedores(cmbProovedor);

    }//GEN-LAST:event_btnGuardarProductoActionPerformed

    private void btnGuardarClActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarClActionPerformed
        validarRegistroCliente();
        listarCliente();
        limpiarCampos();
    }//GEN-LAST:event_btnGuardarClActionPerformed

    private void txtRutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutKeyPressed
        // Verificar si se presionó la tecla Enter
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            validarRutVenta();
        }
    }//GEN-LAST:event_txtRutKeyPressed

    private void txtRutKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutKeyTyped
        eventos.agregarKeyListenerRut(txtRut);
    }//GEN-LAST:event_txtRutKeyTyped

    private void tblListadoVentaRealizadaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListadoVentaRealizadaMouseClicked
        int filaSeleccionada = tblListadoVentaRealizada.rowAtPoint(evt.getPoint());
        txtIdDetalle.setText(tblListadoVentaRealizada.getValueAt(filaSeleccionada, 0).toString());
    }//GEN-LAST:event_tblListadoVentaRealizadaMouseClicked

    private void btnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfActionPerformed
        try {
            int id = Integer.parseInt(txtIdDetalle.getText());
            File file = new File("src/main/Reportes_Ventas/venta_N°" + id + ".pdf");
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            System.out.println("La venta no se encuentra registrada correctamente en la base de datos");
        }
    }//GEN-LAST:event_btnPdfActionPerformed

    private void btnImportarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarPActionPerformed

        try {
            importar.importarExcelProveedor();// Llama al método que muestra el JFileChooser y realiza la importación
            System.out.println("Importación completada.");
            modelo = (DefaultTableModel) proDao.mostrarProveedores();
            tblListadoProveedor.setModel(modelo);
            proDao.llenarProveedores(cmbProovedor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnImportarPActionPerformed

    private void btnImportarClActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarClActionPerformed
        try {
            importar.importarExcelCliente();// Llama al método que muestra el JFileChooser y realiza la importación
            System.out.println("Importación completada.");
            modelo = (DefaultTableModel) clienteDao.mostrarClientes();
            tblListadoClientes.setModel(modelo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnImportarClActionPerformed

    private void btnExelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExelActionPerformed
        try {
            objeto.exportarExcel(tblListaProducto);
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
    }//GEN-LAST:event_btnExelActionPerformed

    private void txtCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyTyped
        eventos.presionDeNumeros(evt);
    }//GEN-LAST:event_txtCantidadKeyTyped

    private void txtRutProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutProveedorKeyTyped
        eventos.agregarKeyListenerRut(txtRutProveedor);
    }//GEN-LAST:event_txtRutProveedorKeyTyped

    private void txtNombreProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProveedorKeyTyped
        eventos.presionLetras(evt);
    }//GEN-LAST:event_txtNombreProveedorKeyTyped

    private void txtTelefonoProveedorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoProveedorKeyTyped
        eventos.presionNumeroTelefonicos(evt);
    }//GEN-LAST:event_txtTelefonoProveedorKeyTyped

    private void txtCantidadProducto1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadProducto1KeyTyped
        eventos.presionDeNumeros(evt);
    }//GEN-LAST:event_txtCantidadProducto1KeyTyped

    private void txtPrecioProductoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecioProductoKeyTyped
        eventos.presionDeNumeros(evt);
    }//GEN-LAST:event_txtPrecioProductoKeyTyped

    private void txtRutClKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRutClKeyTyped
        eventos.agregarKeyListenerRut(txtRutCl);
    }//GEN-LAST:event_txtRutClKeyTyped

    private void txtNombreClKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreClKeyTyped
        eventos.presionLetras(evt);
    }//GEN-LAST:event_txtNombreClKeyTyped

    private void txtTelefonoClKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefonoClKeyTyped
        eventos.presionNumeroTelefonicos(evt);
    }//GEN-LAST:event_txtTelefonoClKeyTyped

    private void tblListadoProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListadoProveedorMouseClicked
        int fila = tblListadoProveedor.rowAtPoint(evt.getPoint());
        txtRutProveedor.setText(tblListadoProveedor.getValueAt(fila, 0).toString());
        txtNombreProveedor.setText(tblListadoProveedor.getValueAt(fila, 1).toString());
        txtTelefonoProveedor.setText(tblListadoProveedor.getValueAt(fila, 2).toString());
        txtEmailProveedor.setText(tblListadoProveedor.getValueAt(fila, 3).toString());
        txtDireccionProveedor.setText(tblListadoProveedor.getValueAt(fila, 4).toString());
        txtRolP.setText(tblListadoProveedor.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_tblListadoProveedorMouseClicked

    private void tblListaProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListaProductoMouseClicked
        int fila = tblListaProducto.rowAtPoint(evt.getPoint());
        txtCodigoProducto1.setText(tblListaProducto.getValueAt(fila, 0).toString());
        txtDescripcionProducto1.setText(tblListaProducto.getValueAt(fila, 1).toString());
        txtPrecioProducto.setText(tblListaProducto.getValueAt(fila, 2).toString());
        txtCantidadProducto1.setText(tblListaProducto.getValueAt(fila, 3).toString());
        cmbProovedor.setSelectedItem(tblListaProducto.getValueAt(fila, 4).toString());

    }//GEN-LAST:event_tblListaProductoMouseClicked

    private void btnImportarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarProductoActionPerformed
        try {
            importar.importarExcelProducto();// Llama al método que muestra el JFileChooser y realiza la importación
            System.out.println("Importación completada.");
            modelo = (DefaultTableModel) produDao.mostrarProducto();
            tblListaProducto.setModel(modelo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnImportarProductoActionPerformed

    private void tblListadoClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListadoClientesMouseClicked
        int fila = tblListadoClientes.rowAtPoint(evt.getPoint());
        txtRutCl.setText(tblListadoClientes.getValueAt(fila, 0).toString());
        txtNombreCl.setText(tblListadoClientes.getValueAt(fila, 1).toString());
        txtTelefonoCl.setText(tblListadoClientes.getValueAt(fila, 2).toString());
        txtEmailCl.setText(tblListadoClientes.getValueAt(fila, 3).toString());
        txtDireccionCl.setText(tblListadoClientes.getValueAt(fila, 4).toString());
        txtRolCliente.setText(tblListadoClientes.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_tblListadoClientesMouseClicked

    private void btnEliminarClActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarClActionPerformed
        eliminarCliente();
        limpiarCampos();
    }//GEN-LAST:event_btnEliminarClActionPerformed

    private void btnEliminarPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPActionPerformed
        eliminarProveedor();
        proDao.llenarProveedores(cmbProovedor);
        limpiarCampos();
    }//GEN-LAST:event_btnEliminarPActionPerformed

    private void btnEliminarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProductoActionPerformed
        eliminarProducto();
        listarProductos();
        limpiarCamposProducto();
        proDao.llenarProveedores(cmbProovedor);
    }//GEN-LAST:event_btnEliminarProductoActionPerformed

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed
        // Verificar si se presionó la tecla Enter
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            validarCodigoVenta();
        }
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            agregarCantidadVenta();
        }
    }//GEN-LAST:event_txtCantidadKeyPressed

    private void lblLimpiarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLimpiarProductoMouseClicked
        limpiarCamposProducto();
        proDao.llenarProveedores(cmbProovedor);

    }//GEN-LAST:event_lblLimpiarProductoMouseClicked

    private void lblLimpiarProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLimpiarProveedorMouseClicked
        limpiarCampos();
    }//GEN-LAST:event_lblLimpiarProveedorMouseClicked

    private void lblLimpiarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblLimpiarClienteMouseClicked
        limpiarCampos();
    }//GEN-LAST:event_lblLimpiarClienteMouseClicked

    private void lblBuscarProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBuscarProveedorMouseClicked
        buscarProveedor();
    }//GEN-LAST:event_lblBuscarProveedorMouseClicked

    private void lblBuscarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBuscarClienteMouseClicked
        buscarCliente();
    }//GEN-LAST:event_lblBuscarClienteMouseClicked

    private void lblBuscarProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBuscarProductoMouseClicked
        buscarProducto();
    }//GEN-LAST:event_lblBuscarProductoMouseClicked

    private void btnActualizarClActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarClActionPerformed
        actualizarCliente();
    }//GEN-LAST:event_btnActualizarClActionPerformed

    private void btnActualizarPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarPMouseClicked
        actualizarProveedor();
    }//GEN-LAST:event_btnActualizarPMouseClicked

    private void btnActualizarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarProductoActionPerformed
        actualizarProducto();
    }//GEN-LAST:event_btnActualizarProductoActionPerformed

    private void lblTelefonoClKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblTelefonoClKeyTyped
        eventos.presionLetras(evt);
    }//GEN-LAST:event_lblTelefonoClKeyTyped

    private void txtRolClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRolClienteKeyTyped
        eventos.presionLetras(evt);
    }//GEN-LAST:event_txtRolClienteKeyTyped

    private void txtRolPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRolPKeyTyped
        eventos.presionLetras(evt);
    }//GEN-LAST:event_txtRolPKeyTyped

    private void btnGraficoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGraficoActionPerformed
        graficarRepoortes();
    }//GEN-LAST:event_btnGraficoActionPerformed

    private void menuDescargarProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuDescargarProveedorMouseClicked

        try {
            importar.descargarPlantillaProveedor();// Llama al método que muestra el JFileChooser y realiza la importación
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_menuDescargarProveedorMouseClicked

    private void menuExportarClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuExportarClienteMouseClicked
        try {
            objeto.exportarExcel(tblListadoClientes);
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
    }//GEN-LAST:event_menuExportarClienteMouseClicked

    private void menuClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuClienteMouseClicked
        try {
            importar.descargarPlantillaCliente();// Llama al método que muestra el JFileChooser y realiza la importación
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_menuClienteMouseClicked

    private void menuPlanillaProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuPlanillaProductoMouseClicked
         try {
            importar.descargarPlantillaProducto();// Llama al método que muestra el JFileChooser y realiza la importación
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_menuPlanillaProductoMouseClicked

    private void menuExportarProveedorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuExportarProveedorMouseClicked
        try {
            objeto.exportarExcel(tblListadoProveedor);
        } catch (IOException ex) {
            System.out.println("Error: " + ex);
        }
    }//GEN-LAST:event_menuExportarProveedorMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        FlatCyanLightIJTheme.setup();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Background;
    private javax.swing.JButton btnActualizarCl;
    private javax.swing.JButton btnActualizarP;
    private javax.swing.JButton btnActualizarProducto;
    private javax.swing.JButton btnClientes;
    private javax.swing.JButton btnEliminarCl;
    private javax.swing.JButton btnEliminarP;
    private javax.swing.JButton btnEliminarProducto;
    private javax.swing.JButton btnEliminarV;
    private javax.swing.JButton btnExel;
    private javax.swing.JButton btnGenerarV;
    private javax.swing.JButton btnGrafico;
    private javax.swing.JButton btnGuardarCl;
    private javax.swing.JButton btnGuardarP;
    private javax.swing.JButton btnGuardarProducto;
    private javax.swing.JButton btnImportarCl;
    private javax.swing.JButton btnImportarP;
    private javax.swing.JButton btnImportarProducto;
    private javax.swing.JButton btnPdf;
    private javax.swing.JButton btnPrincipal;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedor;
    private javax.swing.JButton btnVentas;
    private javax.swing.JButton btncerrarSesion;
    private javax.swing.JComboBox<String> cmbProovedor;
    private javax.swing.JPanel header;
    private com.toedter.calendar.JDateChooser jDate;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanelClientes;
    private javax.swing.JPanel jPanelPrincipal;
    private javax.swing.JPanel jPanelProducto;
    private javax.swing.JPanel jPanelProveedor;
    private javax.swing.JPanel jPanelVentas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblBuscarCliente;
    private javax.swing.JLabel lblBuscarProducto;
    private javax.swing.JLabel lblBuscarProveedor;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblCantidadProducto;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigoProducto;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblDescripcionProducto;
    private javax.swing.JLabel lblDireccionCl;
    private javax.swing.JLabel lblDireccionProveedor;
    private javax.swing.JLabel lblEmailCl;
    private javax.swing.JLabel lblEmailProveedor;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblGrafico;
    private javax.swing.JLabel lblHeaderUno;
    private javax.swing.JLabel lblLimpiarCliente;
    private javax.swing.JLabel lblLimpiarProducto;
    private javax.swing.JLabel lblLimpiarProveedor;
    private javax.swing.JLabel lblLimpiarVenta;
    private javax.swing.JLabel lblLogoEmpresa;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombreCl;
    private javax.swing.JLabel lblNombreProveedor;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblPrecioProducto;
    private javax.swing.JLabel lblProovedorProducto;
    private javax.swing.JLabel lblRolCl;
    private javax.swing.JLabel lblRolProveedor;
    private javax.swing.JLabel lblRut;
    private javax.swing.JLabel lblRutCl;
    private javax.swing.JLabel lblRutProveedor;
    private javax.swing.JLabel lblStock;
    private javax.swing.JLabel lblTelefonoCl;
    private javax.swing.JLabel lblTelefonoProveedor;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblVendedor;
    private javax.swing.JPanel menu;
    private javax.swing.JMenu menuCliente;
    private javax.swing.JMenu menuDescargarProveedor;
    private javax.swing.JMenu menuExportarCliente;
    private javax.swing.JMenu menuExportarProveedor;
    private javax.swing.JMenu menuPlanillaProducto;
    private javax.swing.JTable tblListaProducto;
    private javax.swing.JTable tblListadoClientes;
    private javax.swing.JTable tblListadoProveedor;
    private javax.swing.JTable tblListadoVentaRealizada;
    private javax.swing.JTable tblListadoVentas;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCantidadProducto1;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoProducto1;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtDescripcionProducto1;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtDireccionCl;
    private javax.swing.JTextField txtDireccionProveedor;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtEmailCl;
    private javax.swing.JTextField txtEmailProveedor;
    private javax.swing.JTextField txtIdDetalle;
    private javax.swing.JTextField txtIdVenta;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNombreCl;
    private javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtPrecioProducto;
    private javax.swing.JTextField txtRol;
    private javax.swing.JTextField txtRolCliente;
    private javax.swing.JTextField txtRolP;
    private javax.swing.JTextField txtRut;
    private javax.swing.JTextField txtRutCl;
    private javax.swing.JTextField txtRutProveedor;
    private javax.swing.JTextField txtStock;
    private javax.swing.JTextField txtTelefono;
    private javax.swing.JTextField txtTelefonoCl;
    private javax.swing.JTextField txtTelefonoProveedor;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

    //Método registrarDetalle
    private void registrarDetalle() {
        int id = ventaDao.idVenta();
        for (int i = 0; i < tblListadoVentas.getRowCount(); i++) {
            String cod = tblListadoVentas.getValueAt(i, 0).toString();
            int cant = Integer.parseInt(tblListadoVentas.getValueAt(i, 2).toString());
            double precio = Double.parseDouble(tblListadoVentas.getValueAt(i, 3).toString());

            detalle.setCodigo(cod);
            detalle.setCantidad(cant);
            detalle.setPrecio(precio);
            detalle.setId(id);
            detalleDao.registrarDetalle(detalle);
            btnGenerarV.setEnabled(false);
            txtNombre.setEnabled(false);
        }
    }

    private void graficarRepoortes() {
        if (jDate.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione una fecha antes de graficar.");
            return;
        }

        String fechaReporte = new SimpleDateFormat("yyyy-MM-dd").format(jDate.getDate());

        GraficoDao.graficarProductoMasVendido(fechaReporte);
    }

    //Limpiar campo Venta
    private void limpiarVenta() {
        txtCodigo.setText("");
        txtDescripcion.setText("");
        txtCantidad.setText("");
        txtStock.setText("");
        txtPrecio.setText("");
        txtIdVenta.setText("0");
    }

    //Limpiar campo Cliente
    public void limpiarDatosCl() {
        txtTotal.setText("");
        txtRut.setText("");
        txtNombre.setText("");
    }

    public void limpiarCamposProducto() {
        txtCodigoProducto1.setText("");
        txtDescripcionProducto1.setText("");
        txtPrecioProducto.setText("");
        txtCantidadProducto1.setText("");
        cmbProovedor.removeAllItems();

    }

    public void limpiarCampos() {
        txtRutCl.setText("");
        txtNombreCl.setText("");
        txtEmailCl.setText("");
        txtDireccionCl.setText("");
        txtTelefonoCl.setText("");
        txtRolCliente.setText("");
        txtRutProveedor.setText("");
        txtNombreProveedor.setText("");
        txtEmailProveedor.setText("");
        txtDireccionProveedor.setText("");
        txtTelefonoProveedor.setText("");
        txtRolP.setText("");
    }

    //Método para limpiar la tabla de registro Producto
    private void limpiarTableVenta() {
        modelo = (DefaultTableModel) tblListadoVentas.getModel();
        int fila = tblListadoVentas.getRowCount();
        for (int i = 0; i < fila; i++) {
            modelo.removeRow(0);
        }
    }
   

    private void limpiarTableCliente() {
        modelo = (DefaultTableModel) tblListadoClientes.getModel();
        int fila = tblListadoClientes.getRowCount();
        for (int i = 0; i < fila; i++) {
            modelo.removeRow(0);
        }
    }

    private void limpiarTableProveedor() {
        modelo = (DefaultTableModel) tblListadoProveedor.getModel();
        int fila = tblListadoProveedor.getRowCount();
        for (int i = 0; i < fila; i++) {
            modelo.removeRow(0);
        }
    }

    private void limpiarTableProducto() {
        modelo = (DefaultTableModel) tblListaProducto.getModel();
        int fila = tblListaProducto.getRowCount();
        for (int i = 0; i < fila; i++) {
            modelo.removeRow(0);
        }
    }

    //Método para extraer el total
    private void totalPagar() {
        totalPagar = 0.00;
        int numeroFila = tblListadoVentas.getRowCount();

        for (int i = 0; i < numeroFila; i++) {
            double cal = Double.parseDouble(String.valueOf(tblListadoVentas.getModel().getValueAt(i, 4)));
            totalPagar = totalPagar + cal;
        }

        txtTotal.setText(String.format("%.2f", totalPagar));

    }

    //Método para registra la venta
    private void registrarVenta() {
        String cliente = txtNombre.getText();
        String vendedor = lblVendedor.getText();
        double monto = totalPagar;
        venta.setCliente(cliente);
        venta.setVendedor(vendedor);
        venta.setTotal(monto);
        ventaDao.registrarVentas(venta);
    }

    //Método para actualizar el stock
    private void actualizarStock() {
        for (int i = 0; i < tblListadoVentas.getRowCount(); i++) {
            // Obtener el código del producto y la cantidad vendida
            String codigo = tblListadoVentas.getValueAt(i, 0).toString();
            int cantidad = Integer.parseInt(tblListadoVentas.getValueAt(i, 2).toString());

            // Llamamos a la función para actualizar el stock con la cantidad vendida
            boolean actualizado = ventaDao.actualizarStock(cantidad, codigo);

            if (!actualizado) {
                JOptionPane.showMessageDialog(null, "Error al actualizar el stock del producto: " + codigo);
            }
        }
    }

    //Método para generar reporte de venta en PDF
    private void generarVentaPDF() {
        try {
            int id = ventaDao.idVenta();
            FileOutputStream archivo;
            File file = new File("src/main/Reportes_Ventas/venta_N°" + id + ".pdf");
            archivo = new FileOutputStream(file);
            Document doc = new Document(PageSize.A4);
            PdfWriter.getInstance(doc, archivo);
            doc.open();

            // Logo de la empresa
            com.itextpdf.text.Image imagen = com.itextpdf.text.Image.getInstance("src/main/Resources/logoFrmOf.png");
            imagen.scaleToFit(100, 100);

            // Encabezado
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            PdfPTable encabezado = new PdfPTable(2);
            encabezado.setWidthPercentage(100);
            encabezado.setWidths(new float[]{20f, 80f});
            encabezado.addCell(imagen);
            encabezado.addCell(new Paragraph("Information Factory"
                    + "\nR.U.T.: 78.456.789-0" + "\nTeléfono: " + "+452 440044"
                    + "\nDirección: Aldunate 478 edificio Crisoft piso 4 ", negrita));
            doc.add(encabezado);
            doc.add(Chunk.NEWLINE);

            // Fecha y número de factura
            Date date = new Date();
            Paragraph fecha = new Paragraph("VENTA ELECTRÓNICA\nN° " + id + "\nFecha: "
                    + new SimpleDateFormat("dd/MM/yyyy").format(date), negrita);
            fecha.setAlignment(Element.ALIGN_RIGHT);
            doc.add(fecha);
            doc.add(Chunk.NEWLINE);

            // Datos del cliente
            PdfPTable datosCliente = new PdfPTable(4);
            datosCliente.setWidthPercentage(100);
            datosCliente.setWidths(new float[]{25f, 35f, 20f, 20f});
            datosCliente.addCell(new Phrase("RUT", negrita));
            datosCliente.addCell(new Phrase("Nombre", negrita));
            datosCliente.addCell(new Phrase("Teléfono", negrita));
            datosCliente.addCell(new Phrase("Dirección", negrita));
            datosCliente.addCell(txtRut.getText());
            datosCliente.addCell(txtNombre.getText());
            datosCliente.addCell(txtTelefono.getText());
            datosCliente.addCell(txtDireccion.getText());
            doc.add(datosCliente);
            doc.add(Chunk.NEWLINE);

            // Tabla de productos
            PdfPTable tablaProductos = new PdfPTable(5);
            tablaProductos.setWidthPercentage(100);
            tablaProductos.setWidths(new float[]{10f, 40f, 15f, 15f, 20f});
            String[] headers = {"Código", "Descripción", "Cantidad", "Precio Unitario", "Total"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, negrita));
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                tablaProductos.addCell(cell);
            }

            for (int i = 0; i < tblListadoVentas.getRowCount(); i++) {
                tablaProductos.addCell(tblListadoVentas.getValueAt(i, 0).toString());
                tablaProductos.addCell(tblListadoVentas.getValueAt(i, 1).toString());
                tablaProductos.addCell(tblListadoVentas.getValueAt(i, 2).toString());
                tablaProductos.addCell(tblListadoVentas.getValueAt(i, 3).toString());
                tablaProductos.addCell(tblListadoVentas.getValueAt(i, 4).toString());
            }
            doc.add(tablaProductos);
            doc.add(Chunk.NEWLINE);

            // Totales
            PdfPTable tablaTotales = new PdfPTable(2);
            tablaTotales.setWidthPercentage(40);
            tablaTotales.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tablaTotales.addCell(new Phrase("TOTAL", negrita));
            tablaTotales.addCell(new Phrase("$ " + txtTotal.getText()));
            doc.add(tablaTotales);
            doc.add(Chunk.NEWLINE);

            // Firma
            Paragraph firma = new Paragraph("\n\nFirma del Cliente:\n\n\n______________________", negrita);
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);
            doc.add(Chunk.NEWLINE);

            // Mensaje final
            Paragraph mensaje = new Paragraph("Gracias por su compra", negrita);
            mensaje.setAlignment(Element.ALIGN_CENTER);
            doc.add(mensaje);

            doc.close();
            archivo.close();
            Desktop.getDesktop().open(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validarCodigoVenta() {
        // Verificar si el campo de texto no está vacío antes de llamar a buscarProducto()
        if (!txtCodigo.getText().isEmpty()) {
            // Llamar al método de búsqueda de producto y obtener el producto
            Producto producto = produDao.buscarProducto(txtCodigo.getText());

            if (producto != null) {
                // Actualizar los campos con los datos del producto
                txtCodigo.setText(producto.getCodigo());
                txtDescripcion.setText(producto.getNombrePro());
                txtStock.setText(String.valueOf(producto.getStock()));
                txtPrecio.setText(String.valueOf(producto.getPrecio()));

                // Mover el foco al siguiente campo
                txtCantidad.requestFocus();
            } else {
                // Producto no encontrado, mostrar mensaje
                JOptionPane.showMessageDialog(null, "El producto ingresado no existe.");
                txtCodigo.requestFocus();
                txtCodigo.setText("");
            }
        } else {
            // Si el código está vacío, mostrar mensaje
            JOptionPane.showMessageDialog(null, "El código no puede estar vacío.");
            txtCodigo.requestFocus();
        }
    }

    private void buscarProducto() {
        // Verificar si el campo de texto no está vacío antes de llamar a buscarProducto()
        if (!txtCodigoProducto1.getText().isEmpty()) {
            // Llamar al método de búsqueda de producto y obtener el producto
            Producto producto = produDao.buscarProducto(txtCodigoProducto1.getText());

            if (producto != null) {
                // Actualizar los campos con los datos del producto
                txtCodigoProducto1.setText(producto.getCodigo());
                txtDescripcionProducto1.setText(producto.getNombrePro());
                txtCantidadProducto1.setText(String.valueOf(producto.getStock()));
                txtPrecioProducto.setText(String.valueOf(producto.getPrecio()));
                cmbProovedor.setSelectedItem(producto.getProveedor());

            } else {
                // Producto no encontrado, mostrar mensaje
                JOptionPane.showMessageDialog(null, "El producto ingresado no existe.");
                txtCodigoProducto1.requestFocus();
                txtCodigoProducto1.setText("");
            }
        } else {
            // Si el código está vacío, mostrar mensaje
            JOptionPane.showMessageDialog(null, "El código no puede estar vacío.");
            txtCodigoProducto1.requestFocus();
        }
    }

    private void buscarProveedor() {
        // Verificar si el campo de texto no está vacío antes de llamar a buscarProducto()
        if (!txtRutProveedor.getText().isEmpty()) {
            // Llamar al método de búsqueda de producto y obtener el producto
            Proveedor proveedor = proDao.buscarProveedor(txtRutProveedor.getText());

            if (proveedor != null) {

                txtRutProveedor.setText(proveedor.getRut());
                txtNombreProveedor.setText(proveedor.getNombre());
                txtEmailProveedor.setText(String.valueOf(proveedor.getEmail()));
                txtDireccionProveedor.setText(String.valueOf(proveedor.getDireccion()));
                txtTelefonoProveedor.setText(String.valueOf(proveedor.getTelefono()));
                txtRolP.setText(String.valueOf(proveedor.getRol()));

            } else {
                // Producto no encontrado, mostrar mensaje
                JOptionPane.showMessageDialog(null, "El proveedor ingresado no existe.");
                txtRutProveedor.requestFocus();
                txtRutProveedor.setText("");
            }
        } else {
            // Si el código está vacío, mostrar mensaje
            JOptionPane.showMessageDialog(null, "El rut no puede estar vacío.");
            txtRutProveedor.requestFocus();
        }
    }

    private void buscarCliente() {
        // Verificar si el campo de texto no está vacío antes de llamar a buscarProducto()
        if (!txtRutCl.getText().isEmpty()) {
            // Llamar al método de búsqueda de producto y obtener el producto
            Cliente cliente = clienteDao.buscarCliente(txtRutCl.getText());

            if (cliente != null) {

                txtRutCl.setText(cliente.getRut());
                txtNombreCl.setText(cliente.getNombre());
                txtEmailCl.setText(String.valueOf(cliente.getEmail()));
                txtDireccionCl.setText(String.valueOf(cliente.getDireccion()));
                txtTelefonoCl.setText(String.valueOf(cliente.getTelefono()));
                txtRolCliente.setText(String.valueOf(cliente.getRol()));

            } else {
                // Producto no encontrado, mostrar mensaje
                JOptionPane.showMessageDialog(null, "El proveedor ingresado no existe.");
                txtRutProveedor.requestFocus();
                txtRutProveedor.setText("");
            }
        } else {
            // Si el código está vacío, mostrar mensaje
            JOptionPane.showMessageDialog(null, "El rut no puede estar vacío.");
            txtRutProveedor.requestFocus();
        }
    }

    private void agregarCantidadVenta() {
        if (!"".equals(txtCantidad.getText())) {
            String cod = txtCodigo.getText();
            String descripcion = txtDescripcion.getText();
            int cant = Integer.parseInt(txtCantidad.getText());
            double precio = Double.parseDouble(txtPrecio.getText());
            double total = cant * precio;
            int stock = Integer.parseInt(txtStock.getText());
            if (stock >= cant) {
                item = item + 1;
                DefaultTableModel nuevaTabla = (DefaultTableModel) tblListadoVentas.getModel();
                for (int i = 0; i < tblListadoVentas.getRowCount(); i++) {
                    if (tblListadoVentas.getValueAt(i, 1).equals(txtDescripcion.getText())) {
                        JOptionPane.showMessageDialog(null, "El producto ya esta registrado");
                        limpiarVenta();
                        return;
                    }
                }
                ArrayList lista = new ArrayList();
                lista.add(item);
                lista.add(cod);
                lista.add(descripcion);
                lista.add(cant);
                lista.add(precio);
                lista.add(total);

                Object[] objeto = new Object[5];
                objeto[0] = lista.get(1);
                objeto[1] = lista.get(2);
                objeto[2] = lista.get(3);
                objeto[3] = lista.get(4);
                objeto[4] = lista.get(5);
                nuevaTabla.addRow(objeto);
                tblListadoVentas.setModel(nuevaTabla);
                totalPagar();
                limpiarVenta();
                txtRut.requestFocus();
            } else {
                JOptionPane.showMessageDialog(null, "Stock no dispobible");

            }
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese Cantidad");

        }
    }

    private void eliminarVentaTabla() {
        modelo = (DefaultTableModel) tblListadoVentas.getModel();
        int filaSeleccionada = tblListadoVentas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            modelo.removeRow(filaSeleccionada);
            txtCodigo.requestFocus();

        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una fila para eliminar");
        }
    }

}
