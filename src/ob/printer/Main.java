package ob.printer;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import ob.priner.util.REWPrinter;
import ob.priner.util.Util;
import ob.printer.model.Atencion;
import ob.printer.model.AtencionController;
import ob.printer.model.Caja;
import ob.printer.model.CajaController;
import ob.printer.model.Empresa;
import ob.printer.model.EmpresaController;
import ob.printer.model.Ubigeo;
import ob.printer.model.UbigeoController;

/**
 *
 * @author Pilar
 */
public class Main {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Logger logger = Logger.getLogger("MyLog");
    private static FileHandler fh;
    private UbigeoController ubigeoController;

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        try {
            fh = new FileHandler("data.log", true);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        try {
            ubigeoController = new UbigeoController();
        } catch (SQLException ex) {
            log(ex.getMessage());
        }
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("Buscando Precuenta...");
                precuenta();
            }
        }, 1, 1, SECONDS);
    }

    public void precuenta() {
        try {
            AtencionController ctrl = new AtencionController();
            Atencion atencion = ctrl.getToPrint();
            if (atencion != null) {
                String nroAtencion = atencion.getNroatencion();
                Caja caja = new CajaController().getById(atencion.getCajaId());
                Empresa empresa = new EmpresaController().getById(caja.getCentroCostoId());
                Cabecera cabecera = new Cabecera();
                List<Detalle> detalle = new ArrayList<>();
                cabecera.setRazonSocial(empresa.getRazonSocial());
                cabecera.setNombreComercial(empresa.getNombreComercial());
                cabecera.setRuc(empresa.getRuc());
                Ubigeo ubigeo = ubigeoController.getById(empresa.getUbigeoId());
                cabecera.setDireccion(empresa.getDireccion()+" "+ubigeo.getNombre());
                cabecera.setTelefono(empresa.getTelefono());
                cabecera.setAutorizacion(caja.getAutorizacion());
                cabecera.setSerie(caja.getSerieCaja());
                cabecera.setDespedida("AGRADECEMOS SU VISITA");
                cabecera.setMesa(nroAtencion);
                cabecera.setPax(atencion.getPax());
                cabecera.setCajero(atencion.getCajeroId());
                cabecera.setMozo(atencion.getMozoId());
                List<Atencion> atenciones = ctrl.getByMesa(nroAtencion);
                for (Atencion fila : atenciones) {
                    detalle.add(new Detalle(fila.getCantidad(), fila.getProducto(), fila.getTotal()));
                }
                //\\\\10.10.10.19\\PRECUENTA
                boolean success = new Precuenta(caja.getImpresoraPrecuenta(), cabecera, detalle).print();
                
                if (success) {
                    info("Print Precuenta OK [".concat(caja.getImpresoraPrecuenta()).concat("]"));
                    //ctrl.update(nroAtencion);
                } else {
                    log("ALgun error en la Precuenta");
                }
                System.exit(1);
            }
        } catch (SQLException ex) {
            log(ex.getMessage());
        }

    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void log(String message) {
        logger.warning(message);
    }

}
