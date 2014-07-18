package ob.printer.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author jc
 */
@DatabaseTable(tableName = "ticket")
public class Ticket {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String modulo;
    @DatabaseField
    private String nombre;
    @DatabaseField
    private String valor;
    
    public Ticket() {}

    public int getId() {
        return id;
    }

    public String getModulo() {
        return modulo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getValor() {
        return valor;
    }
    
}
