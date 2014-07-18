package ob.printer.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author jc
 */
@DatabaseTable(tableName = "caja")
public class Caja {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String nombre;
    @DatabaseField
    private String tipo;
    @DatabaseField(columnName = "impresora_p")
    private String impresoraPrecuenta;
    @DatabaseField(columnName = "impresora_b")
    private String impresoraBoleta;
    @DatabaseField(columnName = "impresora_f")
    private String impresoraFactura;
    @DatabaseField(columnName = "impresora_x")
    private String impresoraCierreParcial;
    @DatabaseField(columnName = "impresora_z")
    private String impresoraCierreTotal;
    @DatabaseField
    private int dia;
    @DatabaseField(columnName = "centrocosto_id")
    private int centroCostoId;
    @DatabaseField(columnName = "seriecaja")
    private String serieCaja;
    @DatabaseField
    private String autorizacion;
    
    public Caja() {}

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public int getCentroCostoId() {
        return centroCostoId;
    }

    public String getImpresoraPrecuenta() {
        return impresoraPrecuenta;
    }

    public String getImpresoraBoleta() {
        return impresoraBoleta;
    }

    public String getImpresoraFactura() {
        return impresoraFactura;
    }

    public String getImpresoraCierreParcial() {
        return impresoraCierreParcial;
    }

    public String getImpresoraCierreTotal() {
        return impresoraCierreTotal;
    }
    
    public int getDia() {
        return dia;
    }

    public String getSerieCaja() {
        return serieCaja;
    }

    public String getAutorizacion() {
        return autorizacion;
    }
    
}
