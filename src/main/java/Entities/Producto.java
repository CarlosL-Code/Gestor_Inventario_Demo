
package Entities;


public class Producto {
    
    private int id;
    private String codigo;
    private String nombrePro;
    private double precio;
    private int stock;
    private String proveedor;

    public Producto() {
    }

    public Producto(int id, String codigo, String nombrePro, double precio, int stock, String proveedor) {
        this.id = id;
        this.codigo = codigo;
        this.nombrePro = nombrePro;
        this.precio = precio;
        this.stock = stock;
        this.proveedor = proveedor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombrePro() {
        return nombrePro;
    }

    public void setNombrePro(String nombrePro) {
        this.nombrePro = nombrePro;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }
    
    
}
