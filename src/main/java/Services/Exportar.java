package Services;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.awt.Desktop;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Exportar {

    public void exportarExcel(JTable t) throws IOException {

        // Crea el JFileChooser para seleccionar el archivo
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        chooser.setApproveButtonText("Guardar");
        chooser.setFileFilter(new FileNameExtensionFilter("Archivos de Excel (*.xlsx)", "xlsx")); // Usamos ".xlsx" en lugar de ".xls"
        chooser.setCurrentDirectory(new File(System.getProperty("user.home")));  // Establece la carpeta inicial

        // Muestra el cuadro de diálogo para guardar el archivo
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            String ruta = chooser.getSelectedFile().toString();
            if (!ruta.endsWith(".xlsx")) {
                ruta = ruta.concat(".xlsx");  // Asegura que el archivo tenga la extensión .xlsx
            }

            File archivoXLS = new File(ruta);
            if (archivoXLS.exists()) { // comprueba si existe ya el archivo
                int opcion = JOptionPane.showConfirmDialog(null,
                        "El archivo ya existe. ¿Deseas sobreescribirlo?",
                        "Confirmar sobreescritura",
                        JOptionPane.YES_NO_OPTION);
                if (opcion != JOptionPane.YES_OPTION) {
                    return;  // Si el usuario no acepta, no se sobreescribe el archivo
                }
            }

            // Se crea el FileOutputStream fuera del bloque try
            FileOutputStream archivo = null;
            XSSFWorkbook libro = null; // Usamos XSSFWorkbook en lugar de HSSFWorkbook para .xlsx
            try {
                // Crea el archivo Excel y la hoja de trabajo
                archivo = new FileOutputStream(archivoXLS);
                libro = new XSSFWorkbook(); // Usamos XSSFWorkbook para trabajar con archivos .xlsx
                Sheet hoja = libro.createSheet("Mi hoja de trabajo 1");
                hoja.setDisplayGridlines(false);  // Desactiva las líneas de la cuadrícula

                // Crea un estilo de celda para los encabezados
                CellStyle estiloEncabezado = libro.createCellStyle();
                Font fuenteEncabezado = libro.createFont();
                fuenteEncabezado.setBold(true);  // Usamos setBold(true)
                estiloEncabezado.setFont(fuenteEncabezado);

                // Crea la fila de encabezado una sola vez, fuera del bucle
                Row filaEncabezado = hoja.createRow(0);

                // Coloca los encabezados de las columnas
                for (int c = 0; c < t.getColumnCount(); c++) {
                    Cell celdaEncabezado = filaEncabezado.createCell(c);
                    celdaEncabezado.setCellValue(t.getColumnName(c)); // Asigna el nombre de la columna
                    celdaEncabezado.setCellStyle(estiloEncabezado); // Aplica el estilo del encabezado
                }
                for (int c = 0; c < t.getColumnCount(); c++) {
                    hoja.autoSizeColumn(c);
                    hoja.setColumnWidth(c, hoja.getColumnWidth(c) + 5000); // Agrega margen adicional
                }

                // Crea un estilo de celda para las celdas de la tabla
                CellStyle estiloTabla = libro.createCellStyle();
                estiloTabla.setBorderBottom(BorderStyle.THIN);  // Usa BorderStyle.THIN
                estiloTabla.setBorderTop(BorderStyle.THIN);     // Usa BorderStyle.THIN
                estiloTabla.setBorderLeft(BorderStyle.THIN);    // Usa BorderStyle.THIN
                estiloTabla.setBorderRight(BorderStyle.THIN);   // Usa BorderStyle.THIN
                // Agrega los datos de las celdas
                int filaInicio = 1;
                for (int f = 0; f < t.getRowCount(); f++) {
                    Row fila = hoja.createRow(filaInicio++);
                    for (int c = 0; c < t.getColumnCount(); c++) {
                        Cell celda = fila.createCell(c);

                        // Obtener el valor de la celda
                        Object valorCelda = t.getValueAt(f, c);

                        // Comprobar si el valor de la celda es nulo antes de convertirlo a String
                        if (valorCelda != null) {
                            // Si el valor no es nulo, verificamos su tipo y lo asignamos
                            if (valorCelda instanceof Double) {
                                celda.setCellValue((Double) valorCelda);
                            } else {
                                celda.setCellValue(valorCelda.toString());  // Usamos el valor convertido a String
                            }
                        } else {
                            celda.setCellValue("");  // Asignamos un valor vacío si la celda está vacía
                        }

                        // Aplica el estilo a la celda
                        celda.setCellStyle(estiloTabla);
                    }
                }

                // Escribe el archivo y luego lo abre
                libro.write(archivo);
                Desktop.getDesktop().open(archivoXLS);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Cierra los recursos manualmente
                if (archivo != null) {
                    try {
                        archivo.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
