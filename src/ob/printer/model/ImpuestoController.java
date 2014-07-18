package ob.printer.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import java.sql.SQLException;
import ob.printer.util.Conn;

/**
 *
 * @author jc
 */
public class ImpuestoController {

    private final Dao<Impuesto, Integer> dao;
    
    public ImpuestoController() throws SQLException {
        dao = DaoManager.createDao(Conn.getConnectionSource(), Impuesto.class);
    }

    public Double getIgv() throws SQLException {
        QueryBuilder<Impuesto, Integer> qb = dao.queryBuilder();
        qb.where().eq("nombre", "IGV");
        return dao.query(qb.prepare()).get(0).getValor();
    }
}
