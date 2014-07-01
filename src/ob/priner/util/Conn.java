package ob.priner.util;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jc
 */
public class Conn {
    
    public static ConnectionSource ConnectionSource = null;
    
    static {
        try {
            ConnectionSource = new JdbcConnectionSource("jdbc:mysql://localhost/dbrewsoft2014", "root", "123456");
        } catch (SQLException ex) {
            Logger.getLogger(Conn.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
