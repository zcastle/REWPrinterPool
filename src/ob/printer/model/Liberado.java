package ob.printer.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;

/**
 *
 * @author jc
 */
@DatabaseTable(tableName = "liberado")
public class Liberado {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private Date fecha;
    @DatabaseField
    private String nroatencion;
    @DatabaseField(columnName = "cajero_id")
    private int cajeroId;
    @DatabaseField(columnName = "caja_id")
    private int cajaId;
    @DatabaseField
    private Double total;
    
    public Liberado() {}

    public int getId() {
        return id;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getNroatencion() {
        return nroatencion;
    }

    public int getCajeroId() {
        return cajeroId;
    }

    public int getCajaId() {
        return cajaId;
    }
    
    public Double getTotal() {
        return total;
    }
    
}
