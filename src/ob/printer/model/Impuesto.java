package ob.printer.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author jc
 */
@DatabaseTable(tableName = "impuesto")
public class Impuesto {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String nombre;
    @DatabaseField
    private Double valor;
    
    public static Double IGV;
    
    public Impuesto() {}

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getValor() {
        return valor;
    }

}
