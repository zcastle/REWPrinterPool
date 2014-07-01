package ob.printer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Pilar
 */
public class Detalle {
    
    private Double cantidad;
    private String producto;
    private Double unitario;

    public Detalle() {
    }

    public Detalle(Double cantidad, String producto, Double unitario) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.unitario = unitario;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public Double getUnitario() {
        return unitario;
    }

    public void setUnitario(Double unitario) {
        this.unitario = unitario;
    }
    
    public Double getTotal() {
        return cantidad*unitario;
    }

}
