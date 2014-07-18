package ob.printer.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import ob.printer.util.Conn;

/**
 *
 * @author jc
 */
public class ProductoController {

    private final Dao<Producto, Integer> dao;
    
    public ProductoController() throws SQLException {
        dao = DaoManager.createDao(Conn.getConnectionSource(), Producto.class);
    }

    public Producto getById(int id) throws SQLException {
        return dao.queryForId(id);
    }
}
