package ob.printer.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author jc
 */
@DatabaseTable(tableName = "atenciones")
public class Atencion {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String nroatencion;
    @DatabaseField
    private String pax;
    @DatabaseField(columnName = "mozo_id")
    private int mozoId;
    @DatabaseField(columnName = "cajero_id")
    private int cajeroId;
    @DatabaseField
    private int cantidad;
    @DatabaseField(columnName = "producto_id")
    private int productoId;
    @DatabaseField(columnName = "producto_name")
    private String producto;
    @DatabaseField
    private Double precio;
    @DatabaseField(columnName = "caja_id")
    private int cajaId;
    @DatabaseField
    private String enviado;
    private int destinoId;
    @DatabaseField
    private String mensaje;
    
    public Atencion() {}

    public int getId() {
        return id;
    }

    public String getNroatencion() {
        return nroatencion;
    }

    public String getPax() {
        return pax;
    }

    public int getMozoId() {
        return mozoId;
    }

    public int getCajeroId() {
        return cajeroId;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public int getCantidad() {
        return cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public int getProductoId() {
        return productoId;
    }
    
    public String getProducto() {
        return producto;
    }
    
    public Double getTotal() {
        return cantidad*precio;
    }

    public int getCajaId() {
        return cajaId;
    }

    public String getEnviado() {
        return enviado;
    }

    public void setEnviado(String enviado) {
        this.enviado = enviado;
    }

    public int getDestinoId() {
        return destinoId;
    }

    public void setDestinoId(int destinoId) {
        this.destinoId = destinoId;
    }

    public String getMensaje() {
        return mensaje;
    }
    
}
