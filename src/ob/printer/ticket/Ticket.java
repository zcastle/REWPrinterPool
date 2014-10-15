package ob.printer.ticket;

import java.util.List;
import ob.printer.Cabecera;
import ob.printer.Detalle;

/**
 *
 * @author jc
 */
abstract class Ticket {

    protected String printer;
    protected Cabecera cabecera;
    protected List<Detalle> detalle;

    public Ticket(String printer, Cabecera cabecera, List<Detalle> detalle) {
        this.printer = printer;
        this.cabecera = cabecera;
        this.detalle = detalle;
    }

}
