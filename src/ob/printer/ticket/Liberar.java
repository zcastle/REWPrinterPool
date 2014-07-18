package ob.printer.ticket;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import ob.printer.Cabecera;
import ob.printer.Detalle;
import ob.printer.util.REWPrinter;
import ob.printer.util.Util;

/**
 *
 * @author jc
 */
public class Liberar extends Ticket implements TicketInterface {
    
    public Liberar(String printer, Cabecera cabecera) {
        super(printer, cabecera, null);
    }

    @Override
    public boolean print() {
        try {
            REWPrinter print = new REWPrinter(printer);
            
            print.setFont(REWPrinter.FONT_B);
            print.println("----------------------------------------");
            print.setCenter(true);
            print.setDoubleHeight(true);
            print.println("M E S A  L I B E R A D A");
            print.setDoubleHeight(false);
            print.setFont(REWPrinter.FONT_B);
            print.setCenter(false);
            print.println("----------------------------------------");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = cabecera.getFechaHora();
            print.println("FECHA : ".concat(dateFormat.format(date)));
            print.println("MESA  : ".concat(cabecera.getMesa()));
            print.println("CAJERO: ".concat(cabecera.getCajero()));
            print.println("MONTO : ".concat(Util.format(cabecera.getTotal())));
            
            print.cut();
            return print.send();
        } catch (UnknownHostException ex) {
            Util.info(ex.getMessage());
            Util.info("No se pudo imprimir en: "+printer);
            return false;
        } catch (IOException ex) {
            Util.info(ex.getMessage());
            Util.info("No se pudo imprimir en: "+printer);
            return false;
        }
    }
}