package ob.printer.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author jc
 */
@DatabaseTable(tableName = "producto")
public class Producto {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField(columnName = "destino_id")
    private int destinoId;
    
    public Producto() {}

    public int getId() {
        return id;
    }

    public int getDestinoId() {
        return destinoId;
    }
    
}
