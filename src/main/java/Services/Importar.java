package Services;

import ConecctionDB.ConexionBD;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static org.apache.poi.ss.usermodel.CellType.BOOLEAN;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

public class Importar {

    ConexionBD conexion = new ConexionBD();

    public void importarExcelProveedor() {
        JFileChooser chooser = new JFileChooser();
      
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos de Excel (*.xlsx)", "xlsx"));

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();

            FileInputStream file = null;
            XSSFWorkbook workbook = null;

            try {
                file = new FileInputStream(archivo);
                workbook = new XSSFWorkbook(file);  // Cambiado a XSSFWorkbook para .xlsx

                Sheet hoja = workbook.getSheetAt(0);

                // Preparar la sentencia SQL para la inserción
                String sqlInsert = "INSERT INTO proveedor (rut_proveedor,nombre_proveedor,telefono_proveedor,email_proveedor,direccion_proveedor,rol_proveedor) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmtInsert = conexion.establecerConexion().prepareStatement(sqlInsert);

                // Preparar la sentencia SQL para verificar si el rut ya existe
                String sqlCheck = "SELECT rut_proveedor FROM proveedor WHERE rut_proveedor = ?";
                PreparedStatement stmtCheck = conexion.establecerConexion().prepareStatement(sqlCheck);

                // Iterar sobre las filas del archivo Excel
                for (int i = 1; i <= hoja.getLastRowNum(); i++) { // Empieza en 1 para saltar la fila de encabezados
                    Row fila = hoja.getRow(i);
                    if (fila == null) {
                        continue; // Ignorar filas vacías
                    }
                    String rut = getCellValue(fila.getCell(0)).trim(); // Eliminar espacios antes de verificar
                    if (rut.isEmpty()) {
                        System.out.println("Rut vacío, saltando fila.");
                        continue; // Saltar si el rut está vacío
                    }

                    System.out.println("Verificando rut: " + rut);

                    // Verificar si el rut ya existe
                    stmtCheck.setString(1, rut);
                    ResultSet rs = stmtCheck.executeQuery();

                    // Si el rut ya existe, se salta la inserción
                    if (rs.next()) {
                        System.out.println("Rut ya existe: " + rut); // Depuración
                        continue;
                    }

                    // Si el rut no existe, insertar el nuevo registro
                    stmtInsert.setString(1, rut);
                    stmtInsert.setString(2, getCellValue(fila.getCell(1)));
                    stmtInsert.setString(3, getCellValue(fila.getCell(2)));
                    stmtInsert.setString(4, getCellValue(fila.getCell(3)));
                    stmtInsert.setString(5, getCellValue(fila.getCell(4)));
                    stmtInsert.setString(6, getCellValue(fila.getCell(5)));

                    // Ejecutar la inserción
                    stmtInsert.executeUpdate();
                    System.out.println("Datos insertados para rut: " + rut); // Depuración
                }

                JOptionPane.showMessageDialog(null, "Datos importados correctamente.");

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al importar datos: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if (file != null) {
                        file.close(); // Cerrar solo el FileInputStream
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void importarExcelCliente() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos de Excel (*.xlsx)", "xlsx"));

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();

            FileInputStream file = null;
            XSSFWorkbook workbook = null;

            try {
                file = new FileInputStream(archivo);
                workbook = new XSSFWorkbook(file);  // Cambiado a XSSFWorkbook para .xlsx

                Sheet hoja = workbook.getSheetAt(0);

                // Preparar la sentencia SQL para la inserción
                String sqlInsert = "INSERT INTO cliente (rut_cliente,nombre_cliente,telefono_cliente,email_cliente,direccion_cliente,rol_cliente) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmtInsert = conexion.establecerConexion().prepareStatement(sqlInsert);

                // Preparar la sentencia SQL para verificar si el rut ya existe
                String sqlCheck = "SELECT rut_cliente FROM cliente WHERE rut_cliente = ?";
                PreparedStatement stmtCheck = conexion.establecerConexion().prepareStatement(sqlCheck);

                // Iterar sobre las filas del archivo Excel
                for (int i = 1; i <= hoja.getLastRowNum(); i++) { // Empieza en 1 para saltar la fila de encabezados
                    Row fila = hoja.getRow(i);
                    if (fila == null) {
                        continue; // Ignorar filas vacías
                    }
                    String rut = getCellValue(fila.getCell(0)).trim(); // Eliminar espacios antes de verificar
                    if (rut.isEmpty()) {
                        System.out.println("Rut vacío, saltando fila.");
                        continue; // Saltar si el rut está vacío
                    }

                    System.out.println("Verificando rut: " + rut);

                    // Verificar si el rut ya existe
                    stmtCheck.setString(1, rut);
                    ResultSet rs = stmtCheck.executeQuery();

                    // Si el rut ya existe, se salta la inserción
                    if (rs.next()) {
                        System.out.println("Rut ya existe: " + rut); // Depuración
                        continue;
                    }

                    // Si el rut no existe, insertar el nuevo registro
                    stmtInsert.setString(1, rut);
                    stmtInsert.setString(2, getCellValue(fila.getCell(1)));
                    stmtInsert.setString(3, getCellValue(fila.getCell(2)));
                    stmtInsert.setString(4, getCellValue(fila.getCell(3)));
                    stmtInsert.setString(5, getCellValue(fila.getCell(4)));
                    stmtInsert.setString(6, getCellValue(fila.getCell(5)));

                    // Ejecutar la inserción
                    stmtInsert.executeUpdate();
                    System.out.println("Datos insertados para rut: " + rut); // Depuración
                }

                JOptionPane.showMessageDialog(null, "Datos importados correctamente.");

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al importar datos: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if (file != null) {
                        file.close(); // Cerrar solo el FileInputStream
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void importarExcelProducto() {
        JFileChooser chooser = new JFileChooser();

        chooser.setFileFilter(new FileNameExtensionFilter("Archivos de Excel (*.xlsx)", "xlsx"));

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();

            FileInputStream file = null;
            XSSFWorkbook workbook = null;

            try {
                file = new FileInputStream(archivo);
                workbook = new XSSFWorkbook(file);  // Cambiado a XSSFWorkbook para .xlsx

                Sheet hoja = workbook.getSheetAt(0);

                // Preparar la sentencia SQL para la inserción
                String sqlInsert = "INSERT INTO producto (codigo_producto,nombre_producto,precio,stock,nombre_proveedor) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmtInsert = conexion.establecerConexion().prepareStatement(sqlInsert);

                // Preparar la sentencia SQL para verificar si el rut ya existe
                String sqlCheck = "SELECT codigo_producto FROM producto WHERE codigo_producto = ?";
                PreparedStatement stmtCheck = conexion.establecerConexion().prepareStatement(sqlCheck);

                // Iterar sobre las filas del archivo Excel
                for (int i = 1; i <= hoja.getLastRowNum(); i++) { // Empieza en 1 para saltar la fila de encabezados
                    Row fila = hoja.getRow(i);
                    if (fila == null) {
                        continue; // Ignorar filas vacías
                    }
                    String rut = getCellValue(fila.getCell(0)).trim(); // Eliminar espacios antes de verificar
                    if (rut.isEmpty()) {
                        System.out.println("Rut vacío, saltando fila.");
                        continue; // Saltar si el rut está vacío
                    }

                    System.out.println("Verificando rut: " + rut);

                    // Verificar si el rut ya existe
                    stmtCheck.setString(1, rut);
                    ResultSet rs = stmtCheck.executeQuery();

                    // Si el rut ya existe, se salta la inserción
                    if (rs.next()) {
                        System.out.println("Rut ya existe: " + rut); // Depuración
                        continue;
                    }

                    // Si el rut no existe, insertar el nuevo registro
                    stmtInsert.setString(1, rut);
                    stmtInsert.setString(2, getCellValue(fila.getCell(1)));
                    stmtInsert.setString(3, getCellValue(fila.getCell(2)));
                    stmtInsert.setString(4, getCellValue(fila.getCell(3)));
                    stmtInsert.setString(5, getCellValue(fila.getCell(4)));

                    // Ejecutar la inserción
                    stmtInsert.executeUpdate();
                    System.out.println("Datos insertados para rut: " + rut); // Depuración
                }

                JOptionPane.showMessageDialog(null, "Datos importados correctamente.");

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al importar datos: " + e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if (file != null) {
                        file.close(); // Cerrar solo el FileInputStream
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Método para obtener el valor de una celda de forma segura
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return ""; // Retorna una cadena vacía si la celda está vacía
        }

        switch (cell.getCellType()) {  // Usar getCellType() en lugar de getCellTypeEnum()
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                // Si la celda es numérica, se la convierte a cadena (para valores numéricos como semestre o rut)
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
    

    public void descargarPlantillaProveedor() {
        JFileChooser chooser = new JFileChooser();


        chooser.setDialogTitle("Guardar plantilla de Excel");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos de Excel (*.xlsx)", "xlsx"));

        // Establecer una ruta predeterminada (por ejemplo, dentro de los documentos del usuario)
        String userHome = System.getProperty("user.home");
        chooser.setCurrentDirectory(new File(userHome + "/Documents")); // Ruta personalizada

        // Mostrar el diálogo de guardado
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File destino = chooser.getSelectedFile();

            // Asegurar que el archivo tiene la extensión correcta
            if (!destino.getName().toLowerCase().endsWith(".xlsx")) {
                destino = new File(destino.getAbsolutePath() + ".xlsx");
            }

            // Intentar cargar la plantilla desde los recursos empaquetados en el .jar
            InputStream plantillaStream = getClass().getClassLoader().getResourceAsStream("assets/PlantillaProveedor.xlsx");

            if (plantillaStream == null) {
                JOptionPane.showMessageDialog(null, "No se pudo encontrar la plantilla en los recursos.");
                return;
            }

            // Copiar el archivo de plantilla al destino seleccionado
            try (FileOutputStream fos = new FileOutputStream(destino)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = plantillaStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }

                JOptionPane.showMessageDialog(null, "Plantilla descargada exitosamente en: " + destino.getAbsolutePath());

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al descargar la plantilla: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void descargarPlantillaCliente() {
        JFileChooser chooser = new JFileChooser();


        chooser.setDialogTitle("Guardar plantilla de Excel");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos de Excel (*.xlsx)", "xlsx"));

        // Establecer una ruta predeterminada (por ejemplo, dentro de los documentos del usuario)
        String userHome = System.getProperty("user.home");
        chooser.setCurrentDirectory(new File(userHome + "/Documents")); // Ruta personalizada

        // Mostrar el diálogo de guardado
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File destino = chooser.getSelectedFile();

            // Asegurar que el archivo tiene la extensión correcta
            if (!destino.getName().toLowerCase().endsWith(".xlsx")) {
                destino = new File(destino.getAbsolutePath() + ".xlsx");
            }

            // Intentar cargar la plantilla desde los recursos empaquetados en el .jar
            InputStream plantillaStream = getClass().getClassLoader().getResourceAsStream("assets/PlantillaCliente.xlsx");

            if (plantillaStream == null) {
                JOptionPane.showMessageDialog(null, "No se pudo encontrar la plantilla en los recursos.");
                return;
            }

            // Copiar el archivo de plantilla al destino seleccionado
            try (FileOutputStream fos = new FileOutputStream(destino)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = plantillaStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }

                JOptionPane.showMessageDialog(null, "Plantilla descargada exitosamente en: " + destino.getAbsolutePath());

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al descargar la plantilla: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void descargarPlantillaProducto() {
        JFileChooser chooser = new JFileChooser();


        chooser.setDialogTitle("Guardar plantilla de Excel");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos de Excel (*.xlsx)", "xlsx"));

        // Establecer una ruta predeterminada (por ejemplo, dentro de los documentos del usuario)
        String userHome = System.getProperty("user.home");
        chooser.setCurrentDirectory(new File(userHome + "/Documents")); // Ruta personalizada

        // Mostrar el diálogo de guardado
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File destino = chooser.getSelectedFile();

            // Asegurar que el archivo tiene la extensión correcta
            if (!destino.getName().toLowerCase().endsWith(".xlsx")) {
                destino = new File(destino.getAbsolutePath() + ".xlsx");
            }

            // Intentar cargar la plantilla desde los recursos empaquetados en el .jar
            InputStream plantillaStream = getClass().getClassLoader().getResourceAsStream("assets/PlantillaProducto.xlsx");

            if (plantillaStream == null) {
                JOptionPane.showMessageDialog(null, "No se pudo encontrar la plantilla en los recursos.");
                return;
            }

            // Copiar el archivo de plantilla al destino seleccionado
            try (FileOutputStream fos = new FileOutputStream(destino)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = plantillaStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }

                JOptionPane.showMessageDialog(null, "Plantilla descargada exitosamente en: " + destino.getAbsolutePath());

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al descargar la plantilla: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
