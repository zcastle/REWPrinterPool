package ob.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import ob.printer.util.Util;

/**
 *
 * @author jc
 */
public class ServidorWeb {

    int puerto = 90;

    final int ERROR = 0;
    final int WARNING = 1;
    final int DEBUG = 2;

    // constructor que interpreta los parameros pasados
    public ServidorWeb() {
    }

    public boolean arranca() {
        Util.info("Arrancamos nuestro servidor");
        try {
            ServerSocket s = new ServerSocket(8523);
            Util.info("Quedamos a la espera de conexion");
            Socket entrante;
            while (true) {  // bucle infinito .... ya veremos como hacerlo de otro modo
                entrante = s.accept();
                new PeticionWeb(entrante).start();
            }

            //depura("Hemos terminado");
        } catch (IOException e) {
            Util.info("Error en servidor\n" + e.toString());
        }
        return true;
    }

}
