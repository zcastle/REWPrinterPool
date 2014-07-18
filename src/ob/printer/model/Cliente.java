package ob.printer.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author jc
 */
@DatabaseTable(tableName = "cliente_proveedor")
public class Cliente {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private String ruc;
    @DatabaseField
    private String nombre;
    @DatabaseField
    private String direccion;
    @DatabaseField(columnName = "ubigeo_id")
    private int ubigeoId;
    private String distrito;
    
    public Cliente() {}

    public int getId() {
        return id;
    }

    public String getRuc() {
        return ruc;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getUbigeoId() {
        return ubigeoId;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }
    
}
