package ob.printer.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import java.sql.SQLException;
import java.util.List;
import ob.printer.util.Conn;

/**
 *
 * @author jc
 */
public class UbigeoController {

    private final Dao<Ubigeo, Integer> dao;
    //private final List<Ubigeo> lstUbigeo;
    
    public UbigeoController() throws SQLException {
        dao = DaoManager.createDao(Conn.getConnectionSource(), Ubigeo.class);
        //lstUbigeo = dao.queryForAll();
    }

    /*public Ubigeo getById(int id) throws SQLException {
        for (Ubigeo ubigeo : lstUbigeo) {
            if(ubigeo.getId()==id) {
                return ubigeo;
            }
        }
        return null;
    }*/
    public Ubigeo getById(int id) throws SQLException {
        return dao.queryForId(id);
    }
}
