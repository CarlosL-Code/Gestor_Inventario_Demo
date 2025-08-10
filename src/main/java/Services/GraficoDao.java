
package Services;

import ConecctionDB.ConexionBD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;



public class GraficoDao {
    
 public static void graficarProductoMasVendido(String fecha) {
    ConexionBD objConexion = new ConexionBD();

    try {
        String sql = """
            SELECT p.nombre_producto, SUM(d.cantidad) AS total_vendido
            FROM detalle d
            INNER JOIN producto p ON d.codigo_Producto = p.codigo_producto
            INNER JOIN ventas v ON d.idVentas = v.idVentas
            WHERE DATE(v.fechaVenta) = ?
            GROUP BY p.nombre_producto
            ORDER BY total_vendido DESC
        """;

        PreparedStatement lectura = objConexion.establecerConexion().prepareStatement(sql);
        lectura.setString(1, fecha); // formato yyyy-MM-dd

        ResultSet resultado = lectura.executeQuery();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        boolean hayDatos = false;
        while (resultado.next()) {
            String producto = resultado.getString("nombre_producto");
            int total = resultado.getInt("total_vendido");
            dataset.addValue(total, "Ventas", producto); // "Ventas" es la serie
            hayDatos = true;
        }

        if (!hayDatos) {
            JOptionPane.showMessageDialog(null, "No hay datos de ventas para la fecha seleccionada.");
            return;
        }

        // Crear gráfico de barras
        JFreeChart chart = ChartFactory.createBarChart(
            "Productos más vendidos (" + fecha + ")", // Título
            "Producto",                                // Eje X
            "Cantidad vendida",                        // Eje Y
            dataset,                                   // Datos
            PlotOrientation.VERTICAL,                  // Orientación
            true, true, false                          // Leyenda, tooltips, URLs
        );

        ChartFrame frame = new ChartFrame("Gráfico de ventas", chart);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.toFront(); // Asegura que quede al frente
        frame.requestFocus();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al generar gráfico: " + e.getMessage());
    }
}
}
