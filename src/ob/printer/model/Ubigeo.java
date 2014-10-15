package ob.printer.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author jc
 */
@DatabaseTable(tableName = "ubigeo")
public class Ubigeo {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String nombre;
    
    public Ubigeo() {}

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

}
