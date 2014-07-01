package ob.printer.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import ob.priner.util.Conn;

/**
 *
 * @author jc
 */
public class CajaController {

    private final Dao<Caja, Integer> dao;

    public CajaController() throws SQLException {
        dao = DaoManager.createDao(Conn.ConnectionSource, Caja.class);
    }

    public Caja getById(int id) throws SQLException {
        return dao.queryForId(id);
    }
}
