package ob.printer.ticket;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import ob.printer.Cabecera;
import ob.printer.Detalle;
import ob.printer.model.Impuesto;
import ob.printer.model.Pago;
import ob.printer.model.Venta;
import ob.printer.util.REWPrinter;
import ob.printer.util.Util;

/**
 *
 * @author jc
 */
public class Cierre extends Ticket implements TicketInterface {

    public Cierre(String printer, Cabecera cabecera, List<Detalle> detalle) {
        super(printer, cabecera, detalle);
    }

    @Override
    public boolean print() {
        try {
            REWPrinter print = new REWPrinter(printer);

            print.setFont(REWPrinter.FONT_B);
            print.setCenter(true);
            print.println("----------------------------------------");

            if (!cabecera.getNombreComercial().isEmpty()) {
                print.println(cabecera.getNombreComercial());
            }
            print.println(cabecera.getRazonSocial() + " - " + cabecera.getRuc());
            print.println(cabecera.getDireccion());
            print.println("TLF: ".concat(cabecera.getTelefono()));
            print.println("----------------------------------------");
            if(cabecera.getCajero().equals("0")){
                print.println("CIERRE TOTAL");
            } else {
                print.println("CIERRE PARCIAL");
            }
            print.println("----------------------------------------");
            print.setCenter(false);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = cabecera.getFechaHora();
            print.println("DIA TRABAJO       : ".concat(cabecera.getDia() + ""));
            print.println("FECHA IMPRESION   : ".concat(dateFormat.format(date)));
            if(!cabecera.getCajero().equals("0")){
                print.println("CAJERO            : ".concat(cabecera.getCajero()));
            }

            List<Venta> lstBoletas = cabecera.getBoletas();
            List<Venta> lstFacturas = cabecera.getFacturas();
            int countBoletas = lstBoletas.size();
            int countFacturas = lstFacturas.size();
            Date fechaInicio;
            Date fechaCierre;
            if (lstBoletas.get(0).getFechaHora().before(lstFacturas.get(0).getFechaHora())) {
                fechaInicio = lstBoletas.get(0).getFechaHora();
            } else {
                fechaInicio = lstFacturas.get(0).getFechaHora();
            }
            //System.out.println(lstBoletas.get(countBoletas - 1).getFechaHora());
            //System.out.println(lstFacturas.get(countFacturas - 1).getFechaHora());
            if (lstBoletas.get(countBoletas - 1).getFechaHora().after(lstFacturas.get(countFacturas - 1).getFechaHora())) {
                fechaCierre = lstBoletas.get(countBoletas - 1).getFechaHora();
            } else {
                fechaCierre = lstFacturas.get(countFacturas - 1).getFechaHora();
            }

            print.println("FECHA INICIO      : ".concat(dateFormat.format(fechaInicio)));
            print.println("FECHA CIERRE      : ".concat(dateFormat.format(fechaCierre)));
            print.println("TIPO DE CAMBIO    : ".concat(Util.format(cabecera.getTipoCambio())));

            Double total = 0.0;
            Double totalBoletas = 0.0;
            Double totalFacturas = 0.0;

            for (Venta boleta : lstBoletas) {
                total += boleta.getTotal();
                totalBoletas += boleta.getTotal();
            }

            for (Venta factura : lstFacturas) {
                total += factura.getTotal();
                totalFacturas += factura.getTotal();
            }

            print.println("----------------------------------------");
            Double recargo = Impuesto.IGV + cabecera.getServicio();
            Double sTotal = total / ((recargo / 100) + 1);
            Double igv = sTotal * (Impuesto.IGV / 100);
            Double servicio = cabecera.getServicio() > 0 ? sTotal * (cabecera.getServicio() / 100) : 0.0;

            print.println("VALOR VENTA       : ".concat(Util.format(sTotal)));
            print.println("IGV(" + Impuesto.IGV.intValue() + "%)          : ".concat(Util.format(igv)));
            if (servicio > 0.0) {
                print.println("SERVICIO(" + cabecera.getServicio().intValue() + "%)     : ".concat(Util.format(servicio)));
            }
            print.println("----------------------------------------");
            print.println("VENTAS REAL       : ".concat(Util.format(total)));
            print.println("----------------------------------------");
            print.setCenter(true);
            print.println("REPORTE DE BOLETAS");
            print.setCenter(false);
            print.feed();
            print.println("No. TRANSACCIONES : ".concat(countBoletas + ""));
            sTotal = totalBoletas / ((recargo / 100) + 1);
            igv = sTotal * (Impuesto.IGV / 100);
            servicio = cabecera.getServicio() > 0 ? sTotal * (cabecera.getServicio() / 100) : 0.0;
            print.println("VALOR VENTA       : ".concat(Util.format(sTotal)));
            print.println("IGV(" + Impuesto.IGV.intValue() + "%)          : ".concat(Util.format(igv)));
            if (servicio > 0.0) {
                print.println("SERVICIO(" + cabecera.getServicio().intValue() + "%)     : ".concat(Util.format(servicio)));
            }
            print.println("VENTAS REAL       : ".concat(Util.format(totalBoletas)));

            String serieBoleta = lstBoletas.get(0).getSerie();
            String numeroBoleta = lstBoletas.get(0).getNumero();
            String ticketInicial = Util.right(serieBoleta, 3, "0") + "-" + Util.right(numeroBoleta, 7, "0");
            serieBoleta = lstBoletas.get(countBoletas - 1).getSerie();
            numeroBoleta = lstBoletas.get(countBoletas - 1).getNumero();
            String ticketFinal = Util.right(serieBoleta, 3, "0") + "-" + Util.right(numeroBoleta, 7, "0");

            print.println("TICKET INICIAL    : ".concat(ticketInicial));
            print.println("TICKET FINAL      : ".concat(ticketFinal));
            print.println("No. ANULACIONES   : ".concat(cabecera.getBoletasAnuladas() + ""));

            print.println("----------------------------------------");

            print.setCenter(true);
            print.println("REPORTE DE FACTURAS");
            print.setCenter(false);
            print.feed();
            print.println("No. TRANSACCIONES : ".concat(countFacturas + ""));
            sTotal = totalFacturas / ((recargo / 100) + 1);
            igv = sTotal * (Impuesto.IGV / 100);
            servicio = cabecera.getServicio() > 0 ? sTotal * (cabecera.getServicio() / 100) : 0.0;
            print.println("VALOR VENTA       : ".concat(Util.format(sTotal)));
            print.println("IGV(" + Impuesto.IGV.intValue() + "%)          : ".concat(Util.format(igv)));
            if (servicio > 0.0) {
                print.println("SERVICIO(" + cabecera.getServicio().intValue() + "%)     : ".concat(Util.format(servicio)));
            }
            print.println("VENTAS REAL       : ".concat(Util.format(totalFacturas)));

            String serieFactura = lstFacturas.get(0).getSerie();
            String numeroFactura = lstFacturas.get(0).getNumero();
            ticketInicial = Util.right(serieFactura, 3, "0") + "-" + Util.right(numeroFactura, 7, "0");
            serieFactura = lstFacturas.get(countFacturas - 1).getSerie();
            numeroFactura = lstFacturas.get(countFacturas - 1).getNumero();
            ticketFinal = Util.right(serieFactura, 3, "0") + "-" + Util.right(numeroFactura, 7, "0");

            print.println("TICKET INICIAL    : ".concat(ticketInicial));
            print.println("TICKET FINAL      : ".concat(ticketFinal));
            print.println("No. ANULACIONES   : ".concat(cabecera.getFacturasAnuladas() + ""));
            print.println("----------------------------------------");

            print.setCenter(true);
            print.println("REPORTE DE FORMAS DE PAGO");
            print.setCenter(false);
            print.feed();
            print.println("TIPO PAGO            MONTO");
            total = 0.0;
            for (Pago pago : cabecera.getPagos()) {
                print.print(Util.left(pago.getTipoPago(), 17)+" : ");
                print.println(Util.right(Util.format(pago.getValorPago()), 10));
                total += pago.getValorPago();
            }
            print.println("----------------------------------------");
            print.println("VENTAS TOTAL      : ".concat(Util.right(Util.format(total), 10)));
            if(!cabecera.getCajero().equals("0")) {
                print.feed();
                print.setCenter(true);
                print.println("REPORTE DE PRODUCTOS");
                print.setCenter(false);
                print.feed();
                print.println("PRODUCTO            UNIT. CANT TOTAL S/.");
                print.println("----------------------------------------");
                total = 0.0;
                for (Detalle fila : detalle) {
                    print.print(Util.left(fila.getProducto(), 17));
                    print.print(Util.right(Util.format(fila.getUnitario()), 9));
                    print.print(Util.right(fila.getCantidad() + "", 4));
                    print.println(Util.right(Util.format(fila.getTotal()), 10));
                    total += fila.getTotal();
                }
                print.println("----------------------------------------");
                print.println("TOTAL POR PRODUCTOS : ".concat(Util.right(Util.format(total), 10)));
            }
            print.feed(3);
            print.println(" ---------------        --------------- ");
            print.println("  Administrador              Cajero     ");
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
