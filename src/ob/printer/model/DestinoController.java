package ob.printer.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import java.util.List;
import ob.printer.util.Conn;

/**
 *
 * @author jc
 */
public class DestinoController {

    private final Dao<Destino, Integer> dao;
    
    public DestinoController() throws SQLException {
        dao = DaoManager.createDao(Conn.getConnectionSource(), Destino.class);
    }

    public List<Destino> getAll() throws SQLException {
        return dao.queryForAll();
    }
}
