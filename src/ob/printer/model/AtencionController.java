package ob.printer.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import java.sql.SQLException;
import java.util.List;
import ob.printer.Detalle;
import ob.printer.util.Conn;

/**
 *
 * @author jc
 */
public class AtencionController {

    private final Dao<Atencion, Integer> dao;

    public AtencionController() throws SQLException {
        dao = DaoManager.createDao(Conn.getConnectionSource(), Atencion.class);
    }

    public void close() throws SQLException {
        dao.closeLastIterator();
    }
    /*public void update(String nroatencion) throws SQLException {
     UpdateBuilder<Atencion, Integer> ub = dao.updateBuilder();
     ub.where().eq("nroatencion", nroatencion);
     ub.updateColumnValue("print", "N");
     ub.update();
     }

     public Atencion getToPrint() throws SQLException {
     QueryBuilder<Atencion, Integer> qb = dao.queryBuilder();
     qb.where().eq("print", "S");
     qb.groupBy("nroatencion");
     List<Atencion> list = dao.query(qb.prepare());
     if (list.size() > 0) {
     return list.getAll(0);
     } else {
     return null;
     }
     }*/

    public void updateEnviados(List<Detalle> lstDetalle) throws SQLException {
        UpdateBuilder<Atencion, Integer> ub = dao.updateBuilder();
        for (Detalle detalle : lstDetalle) {
            ub.where().idEq(detalle.getAtencionId());
            ub.updateColumnValue("enviado", "S");
            ub.update();
        }
    }

    public List<Atencion> get() throws SQLException {
        return dao.queryForAll();
    }

    public List<Atencion> getAll(int cajaId, String nroatencion) throws SQLException {
        QueryBuilder<Atencion, Integer> qb = dao.queryBuilder();
        qb.where().eq("caja_id", cajaId).and().eq("nroatencion", nroatencion);
        return dao.query(qb.prepare());
    }

    public List<Atencion> getById(int idAtencion) throws SQLException {
        List<Atencion> atenciones;
        QueryBuilder<Atencion, Integer> qb = dao.queryBuilder();
        qb.where().idEq(idAtencion);
        atenciones = dao.query(qb.prepare());
        for (Atencion atencion : atenciones) {
            atencion.setDestinoId(new ProductoController().getById(atencion.getProductoId()).getDestinoId());
        }
        return atenciones;
    }

    public List<Atencion> getNoEnviados(int cajaId, String nroatencion) throws SQLException {
        List<Atencion> atenciones;
        QueryBuilder<Atencion, Integer> qb = dao.queryBuilder();
        qb.where().eq("caja_id", cajaId).and().eq("nroatencion", nroatencion).and().eq("enviado", "N");
        atenciones = dao.query(qb.prepare());
        for (Atencion atencion : atenciones) {
            atencion.setDestinoId(new ProductoController().getById(atencion.getProductoId()).getDestinoId());
        }
        return atenciones;
    }
}
