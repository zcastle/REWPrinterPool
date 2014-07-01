package ob.printer.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author jc
 */
@DatabaseTable(tableName = "centrocosto")
public class CentroCosto {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField(columnName = "empresa_id")
    private int empresaId;
    
    public CentroCosto() {}

    public int getId() {
        return id;
    }

    public int getEmpresaId() {
        return empresaId;
    }

}
