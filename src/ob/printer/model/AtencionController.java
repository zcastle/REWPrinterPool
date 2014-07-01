package ob.printer.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import java.sql.SQLException;
import java.util.List;
import ob.priner.util.Conn;

/**
 *
 * @author jc
 */
public class AtencionController {

    private final Dao<Atencion, Integer> dao;

    public AtencionController() throws SQLException {
        dao = DaoManager.createDao(Conn.ConnectionSource, Atencion.class);
    }

    public void update(String nroatencion) throws SQLException {
        UpdateBuilder<Atencion, Integer> ub = dao.updateBuilder();
        ub.where().eq("nroatencion", nroatencion);
        ub.updateColumnValue("print", "N");
        ub.update();
    }

    public Atencion getToPrint() throws SQLException {
        QueryBuilder<Atencion, Integer> qb = dao.queryBuilder();
        qb.where().like("print", "S");
        qb.groupBy("nroatencion");
        List<Atencion> list = dao.query(qb.prepare());
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public List<Atencion> getByMesa(String nroatencion) throws SQLException {
        QueryBuilder<Atencion, Integer> qb = dao.queryBuilder();
        qb.where().like("nroatencion", nroatencion);
        return dao.query(qb.prepare());
    }
}
