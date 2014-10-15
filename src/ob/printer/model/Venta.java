package ob.printer.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import java.util.Date;

/**
 *
 * @author jc
 */
@DatabaseTable(tableName = "venta")
public class Venta {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private int dia;
    @DatabaseField
    private Date fechaHora;
    @DatabaseField
    private String pax;
    @DatabaseField(columnName = "mozo_id")
    private int mozoId;
    @DatabaseField(columnName = "cajero_id")
    private int cajeroId;
    @DatabaseField(columnName = "cliente_id")
    private int clienteId;
    @DatabaseField(columnName = "caja_id")
    private int cajaId;
    @DatabaseField(columnName = "tipo_documento_id")
    private int tipoDocumentoId;
    @DatabaseField
    private String serie;
    @DatabaseField
    private String numero;
    @DatabaseField
    private Double total;
    @DatabaseField(columnName = "anulado_id")
    private int anuladoId;
    
    public Venta() {}

    public int getId() {
        return id;
    }

    public int getDia() {
        return dia;
    }
    
    public Date getFechaHora() {
        return fechaHora;
    }

    public String getPax() {
        return pax;
    }

    public int getMozoId() {
        return mozoId;
    }

    public int getCajeroId() {
        return cajeroId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public int getCajaId() {
        return cajaId;
    }

    public int getTipoDocumentoId() {
        return tipoDocumentoId;
    }

    public String getSerie() {
        return serie;
    }

    public String getNumero() {
        return numero;
    }

    public Double getTotal() {
        return total;
    }

    public int getAnuladoId() {
        return anuladoId;
    }
    
}
