package ob.printer.ticket;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import ob.printer.Cabecera;
import ob.printer.Detalle;
import ob.printer.model.Impuesto;
import ob.printer.util.App;
import ob.lib.ESCPOSPrinter;
import ob.printer.util.Util;

/**
 *
 * @author jc
 */
public class Factura extends Ticket implements TicketInterface {

    private final List<Object> doc;

    public Factura(String printer, Cabecera cabecera, List<Detalle> detalle, List<Object> doc) {
        super(printer, cabecera, detalle);
        this.doc = doc;
    }

    @Override
    public boolean print() {
        try {
            ESCPOSPrinter print = new ESCPOSPrinter(printer);

            print.setFont(ESCPOSPrinter.FONT_B);
            print.setCenter(true);
            if (!cabecera.getNombreComercial().isEmpty()) {
                print.println(cabecera.getNombreComercial());
            }
            print.println(cabecera.getRazonSocial());
            print.println(cabecera.getRuc());
            print.println(cabecera.getDireccion());
            print.println("TLF: ".concat(cabecera.getTelefono()));
            print.setCenter(false);
            print.println("----------------------------------------");
            String sunat = "";
            if (!cabecera.getSerie().isEmpty()) {
                sunat += "Serie: " + cabecera.getSerie();
            }
            if (!cabecera.getAutorizacion().isEmpty()) {
                sunat += " Autorizacion: " + cabecera.getAutorizacion();
            }
            if (!sunat.isEmpty()) {
                print.setCenter(true);
                print.println(sunat);
                print.setCenter(false);
            }
            String seq = "TICKET ";
            if (doc.get(0).equals(App.FACTURA)) {
                seq += "F";
            } else if (doc.get(0).equals(App.BOLETA)) {
                seq += "B";
            }
            seq += "V: " + Util.right(doc.get(1).toString(), 3, "0") + "-" + Util.right(doc.get(2).toString(), 7, "0");
            print.setCenter(true);
            print.println(seq);
            print.setCenter(false);
            print.println("----------------------------------------");
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = cabecera.getFechaHora();
            print.println("FECHA : ".concat(dateFormat.format(date)));
            //print.println("MESA  : ".concat(cabecera.getMesa()).concat(" - ").concat("PAX: ".concat(cabecera.getPax())));
            print.println("CAJERO: ".concat(cabecera.getCajero()));
            //print.println("MOZO  : ".concat(cabecera.getMozo()));
            print.println("----------------------------------------");
            print.println("CANT PRODUCTO            UNIT. TOTAL S/.");
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
            //print.setDoubleHeight(true);
            if ((int)doc.get(0) == App.FACTURA) {
                Double recargo = Impuesto.IGV + cabecera.getServicio();
                Double sTotal = total / ((recargo / 100) + 1);
                Double igv = sTotal * (Impuesto.IGV / 100);
                print.print("BASE                  S/.     ");
                print.println(Util.right(Util.format(sTotal), 10));
                print.print("IGV(" + Impuesto.IGV.intValue() + "%)              S/.     ");
                print.println(Util.right(Util.format(igv), 10));
                Double servicio = cabecera.getServicio() > 0 ? sTotal * (cabecera.getServicio() / 100) : 0.0;
                if (servicio > 0.0) {
                    //servicio = sTotal * (cabecera.getServicio() / 100);
                    print.print("SERVICIO(" + cabecera.getServicio().intValue() + "%)         S/.     ");
                    print.println(Util.right(Util.format(servicio), 10));
                }
            }
            print.print("TOTAL                 S/.     ");
            print.println(Util.right(Util.format(total), 10));
            //print.setDoubleHeight(false);
            //print.setFont(REWPrinter.FONT_B);
            print.feed(1);
            if (cabecera.getCliente() != null) {
                print.println("CLIENTE: " + cabecera.getCliente().getNombre());
                print.println("RUC: " + cabecera.getCliente().getRuc());
            }
            print.feed(1);
            print.setCenter(true);
            print.println(cabecera.getDespedida());
            print.setCenter(false);
            print.cut();
            return print.send();
        } catch (UnknownHostException ex) {
            Util.info(ex.getMessage());
            Util.info("No se pudo imprimir en: " + printer);
            return false;
        } catch (IOException ex) {
            Util.info(ex.getMessage());
            Util.info("No se pudo imprimir en: " + printer);
            return false;
        }
    }

}
