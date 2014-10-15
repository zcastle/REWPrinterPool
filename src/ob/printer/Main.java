package ob.printer;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import ob.printer.model.UbigeoController;
import ob.printer.util.Conn;
import ob.printer.util.Util;
import ob.web.ServidorWeb;

/**
 *
 * @author Pilar
 */
public class Main {

    public static void main(String[] args) {
        if (!Util.isFileshipAlreadyRunning()) {
            System.out.println("La instancia se encuentra cargada");
            System.exit(1);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().run();
            }
        });
    }

    public void run() {
        if(Conn.ConnectionSource.isOpen()) {
            Util.info("Conexion a la Base de Datos -> Success");
        } else {
            Util.info("Conexion a la Base de Datos -> Failure");
            System.exit(1);
        }
        
        ServidorWeb instancia = new ServidorWeb();
        instancia.arranca();
        
    }
}