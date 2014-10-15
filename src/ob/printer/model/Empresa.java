package ob.printer.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author jc
 */
@DatabaseTable(tableName = "empresa")
public class Empresa {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField(columnName = "razon_social")
    private String razonSocial;
    @DatabaseField(columnName = "nombre_comercial")
    private String nombreComercial;
    @DatabaseField
    private String ruc;
    @DatabaseField
    private String direccion;
    @DatabaseField
    private String telefono;
    @DatabaseField(columnName = "ubigeo_id")
    private int ubigeoId;
    
    public Empresa() {}

    public int getId() {
        return id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public String getRuc() {
        return ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getUbigeoId() {
        return ubigeoId;
    }

}
