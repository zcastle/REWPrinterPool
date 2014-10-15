package ob.printer;

/**
 *
 * @author Pilar
 */
public class Detalle {
    
    private int atencionId;
    private int cantidad;
    private String producto;
    private Double unitario;
    private String mensaje;

    public Detalle() {
    }

    public Detalle(int cantidad, String producto, Double unitario) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.unitario = unitario;
    }
    
    public Detalle(int cantidad, int atencionId, String producto, String mensaje) {
        this.cantidad = cantidad;
        this.atencionId = atencionId;
        this.producto = producto;
        this.mensaje = mensaje;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getAtencionId() {
        return atencionId;
    }

    public String getProducto() {
        return producto;
    }

    public Double getUnitario() {
        return unitario;
    }

    public String getMensaje() {
        return mensaje;
    }
    
    public Double getTotal() {
        return cantidad*unitario;
    }
}
