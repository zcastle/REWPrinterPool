package ob.printer.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author jc
 */
@DatabaseTable(tableName = "atenciones")
public class Atencion {

    @DatabaseField
    private int id;
    @DatabaseField
    private String nroatencion;
    @DatabaseField
    private String print;
    @DatabaseField
    private String pax;
    @DatabaseField(columnName = "mozo_id")
    private String mozoId;
    @DatabaseField(columnName = "cajero_id")
    private String cajeroId;
    @DatabaseField(columnName = "caja_id")
    private int cajaId;
    @DatabaseField
    private Double cantidad;
    @DatabaseField
    private Double precio;
    @DatabaseField(columnName = "producto_name")
    private String producto;
    
    public Atencion() {}

    public int getId() {
        return id;
    }

    public String getNroatencion() {
        return nroatencion;
    }

    public String getPrint() {
        return print;
    }

    public String getPax() {
        return pax;
    }

    public String getMozoId() {
        return mozoId;
    }

    public String getCajeroId() {
        return cajeroId;
    }

    public int getCajaId() {
        return cajaId;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public String getProducto() {
        return producto;
    }
    
    public Double getTotal() {
        return cantidad*precio;
    }
    
}
