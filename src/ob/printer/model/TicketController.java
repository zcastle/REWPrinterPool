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
public class TicketController {

    private final Dao<Ticket, Integer> dao;

    public TicketController() throws SQLException {
        dao = DaoManager.createDao(Conn.getConnectionSource(), Ticket.class);
    }

    public boolean getByModuloNombre(String modulo, String nombre) throws SQLException {
        QueryBuilder<Ticket, Integer> qb = dao.queryBuilder();
        qb.where().eq("modulo", modulo).and().eq("nombre", nombre);
        return dao.query(qb.prepare()).get(0).getValor().equals("S") ? true : false;
    }
}
