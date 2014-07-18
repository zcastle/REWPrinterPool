package ob.printer.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import ob.printer.util.Conn;

/**
 *
 * @author jc
 */
public class ClienteController {

    private final Dao<Cliente, Integer> dao;
    
    public ClienteController() throws SQLException {
        dao = DaoManager.createDao(Conn.getConnectionSource(), Cliente.class);
    }

    public Cliente getById(int id) throws SQLException {
        Cliente cliente = dao.queryForId(id);
        cliente.setDistrito(new UbigeoController().getById(cliente.getUbigeoId()).getNombre());
        return cliente;
    }
}
