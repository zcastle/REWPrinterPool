package ob.printer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ob.priner.util.REWPrinter;
import ob.priner.util.Util;

/**
 *
 * @author jc
 */
public class Precuenta extends Ticket implements TicketInterface {
    
    public Precuenta(String printer, Cabecera cabecera, List<Detalle> detalle) {
        super(printer, cabecera, detalle);
    }

    @Override
    public boolean print() {
        try {
            REWPrinter print = new REWPrinter(printer);
            
            print.setFont(REWPrinter.FONT_B);
            print.setCenter(true);
            print.println("----------------------------------------");
            print.setDoubleHeight(true);
            print.println("P R E C U E N T A");
            print.setDoubleHeight(false);
            print.setFont(REWPrinter.FONT_B);
            print.println("Comprobante no autorizado");
            print.println("----------------------------------------");
            if (!cabecera.getNombreComercial().isEmpty()) {
                print.println(cabecera.getNombreComercial());
            }
            print.println(cabecera.getRazonSocial()+" - "+cabecera.getRuc());
            print.println(cabecera.getDireccion());
            print.println("TLF: ".concat(cabecera.getTelefono()));
            
            print.setCenter(false);
            print.println("----------------------------------------");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();
            print.println("FECHA : ".concat(dateFormat.format(date)));
            print.println("MESA  : ".concat(cabecera.getMesa()).concat(" - ").concat("PAX: ".concat(cabecera.getPax())));
            print.println("CAJERO: ".concat(cabecera.getCajero()));
            print.println("MOZO  : ".concat(cabecera.getMozo()));
            print.println("----------------------------------------");
            print.println("CANT PRODUCTO           UNIT.  TOTAL S/.");
            print.println("----------------------------------------");
            Double total = 0.0;
            for (Detalle fila : detalle) {
                print.print(Util.left(fila.getCantidad() + "", 4));
                print.print(Util.left(fila.getProducto(), 17));
                print.print(Util.right(Util.format(fila.getUnitario()), 9));
                print.println(Util.right(Util.format(fila.getTotal()), 10));
                total += fila.getTotal();
            }
            print.println("----------------------------------------");
            print.setDoubleHeight(true);
            print.print("TOTAL                 S/.     ");
            print.println(Util.right(Util.format(total), 10));
            print.setDoubleHeight(false);
            print.feed(2);
            print.setFont(REWPrinter.FONT_B);
            print.setCenter(true);
            print.println(cabecera.getDespedida());
            print.setCenter(false);
            print.cut();
            print.send();
            return true;
        } catch (IOException ex) {
            Main.log(ex.getMessage());
            return false;
        }
    }
    
}
