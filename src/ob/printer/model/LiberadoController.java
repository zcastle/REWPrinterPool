package ob.printer.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import ob.printer.util.Conn;

/**
 *
 * @author jc
 */
public class LiberadoController {

    private final Dao<Liberado, Integer> dao;

    public LiberadoController() throws SQLException {
        dao = DaoManager.createDao(Conn.getConnectionSource(), Liberado.class);
    }

    public Liberado getById(int id) throws SQLException {
        return dao.queryForId(id);
    }
}
