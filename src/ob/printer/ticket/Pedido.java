package ob.printer.ticket;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ob.printer.Cabecera;
import ob.printer.Detalle;
import ob.printer.util.REWPrinter;
import ob.printer.util.Util;

/**
 *
 * @author jc
 */
public class Pedido extends Ticket implements TicketInterface {
    
    public static final String ADD = "add";
    public static final String REMOVE = "remove";
    private final String tipo;
    
    public Pedido(String printer, Cabecera cabecera, List<Detalle> detalle, String tipo) {
        super(printer, cabecera, detalle);
        this.tipo = tipo;
    }

    @Override
    public boolean print() {
        try {
            REWPrinter print = new REWPrinter(printer);
            
            print.setFont(REWPrinter.FONT_B);
            print.println("----------------------------------------");
            print.setCenter(true);
            print.setDoubleHeight(true);
            if(tipo.equals(Pedido.ADD)){
                print.println("PEDIDO NUEVO");
            } else {
                print.println("PEDIDO ELIMINADO");
            }
            print.setDoubleHeight(false);
            print.setCenter(false);
            print.setFont(REWPrinter.FONT_B);
            print.println("----------------------------------------");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = cabecera.getFechaHora();
            print.println("FECHA  : ".concat(dateFormat.format(date)));
            print.println("MESA   : ".concat(cabecera.getMesa()).concat(" MOZO: ").concat(cabecera.getMozo()));
            print.println("DESTINO: ".concat(cabecera.getDestino()));
            print.println("----------------------------------------");
            print.println("CANT PRODUCTO");
            print.println("----------------------------------------");
            for (Detalle fila : detalle) {
                print.print(Util.left(fila.getCantidad() + "", 4));
                print.println(Util.left(fila.getProducto(), 36));
                if(!fila.getMensaje().isEmpty() && tipo.equals(Pedido.ADD)) {
                    print.println("   >"+fila.getMensaje());
                }
            }
            print.println("----------------------------------------");
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