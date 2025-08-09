/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;


public class DetalleVenta {
    
    private int id;
    private String codigoP;
    private int cantidad;
    private double precio;
    private int idVentas;

    public DetalleVenta() {
    }

    public DetalleVenta(int id, String codigoP, int cantidad, double precio, int idVentas) {
        this.id = id;
        this.codigoP = codigoP;
        this.cantidad = cantidad;
        this.precio = precio;
        this.idVentas = idVentas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigoP() {
        return codigoP;
    }

    public void setCodigoP(String codigoP) {
        this.codigoP = codigoP;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdVentas() {
        return idVentas;
    }

    public void setIdVentas(int idVentas) {
        this.idVentas = idVentas;
    }
    
    
    
}
