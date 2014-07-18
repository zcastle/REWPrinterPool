package ob.printer.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import ob.printer.util.Conn;

/**
 *
 * @author jc
 */
public class EmpresaController {

    private final Dao<Empresa, Integer> dao;

    public EmpresaController() throws SQLException {
        dao = DaoManager.createDao(Conn.getConnectionSource(), Empresa.class);
    }

    public Empresa getById(int id) throws SQLException {
        return dao.queryForId(id);
    }
}
