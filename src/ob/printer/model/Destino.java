package ob.printer.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author jc
 */
@DatabaseTable(tableName = "destino")
public class Destino {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String nombre;
    @DatabaseField
    private String destino;
    
    public Destino() {}

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDestino() {
        return destino;
    }
    
}
