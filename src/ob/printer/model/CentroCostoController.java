package ob.printer.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import ob.printer.util.Conn;

/**
 *
 * @author jc
 */
public class CentroCostoController {

    private final Dao<CentroCosto, Integer> dao;

    public CentroCostoController() throws SQLException {
        dao = DaoManager.createDao(Conn.getConnectionSource(), CentroCosto.class);
    }

    public CentroCosto getById(int id) throws SQLException {
        return dao.queryForId(id);
    }
}
