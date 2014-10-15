package ob.printer.util;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import ob.printer.Main;

/**
 *
 * @author jc
 */
public class Conn {

    public static ConnectionSource ConnectionSource = null;

    static {
        try {
            String host, port, db, dbuser, dbpass;
            Properties pro = new Properties();
            InputStream input = new FileInputStream("config.properties");
            pro.load(input);
            host = pro.getProperty("HOST", "localhost");
            port = pro.getProperty("PORT", "3306");
            db = pro.getProperty("DB", "dbrewsoft2014");
            dbuser = pro.getProperty("DBUSER", "root");
            dbpass = pro.getProperty("DBPASS", "123456");
            String url = "jdbc:mysql://" + host + ":" + port + "/" + db;
            System.out.println("Conectando a: " + url);
            ConnectionSource = new JdbcPooledConnectionSource("jdbc:mysql://" + host + ":" + port + "/" + db, dbuser, dbpass);
        } catch (SQLException ex) {
            Util.warning(ex.getMessage());
        } catch (FileNotFoundException ex) {
            Util.warning(ex.getMessage());
        } catch (IOException ex) {
            Util.warning(ex.getMessage());
        }
    }
    
    public static ConnectionSource getConnectionSource() {
        return ConnectionSource;
    }

}
