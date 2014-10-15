package ob.printer.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import ob.printer.util.Conn;

/**
 *
 * @author jc
 */
public class UsuarioController {

    private final Dao<Usuario, Integer> dao;

    public UsuarioController() throws SQLException {
        dao = DaoManager.createDao(Conn.getConnectionSource(), Usuario.class);
    }

    public Usuario getById(int id) throws SQLException {
        return dao.queryForId(id);
    }
}
