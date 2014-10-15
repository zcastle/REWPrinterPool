package ob.printer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ob.printer.model.Atencion;
import ob.printer.model.AtencionController;
import ob.printer.model.Caja;
import ob.printer.model.CajaController;
import ob.printer.model.CentroCosto;
import ob.printer.model.CentroCostoController;
import ob.printer.model.ClienteController;
import ob.printer.model.Destino;
import ob.printer.model.DestinoController;
import ob.printer.model.Empresa;
import ob.printer.model.EmpresaController;
import ob.printer.model.Impuesto;
import ob.printer.model.ImpuestoController;
import ob.printer.model.Liberado;
import ob.printer.model.LiberadoController;
import ob.printer.model.Pago;
import ob.printer.model.TicketController;
import ob.printer.model.Ubigeo;
import ob.printer.model.UbigeoController;
import ob.printer.model.UsuarioController;
import ob.printer.model.Venta;
import ob.printer.model.VentaController;
import ob.printer.model.VentaDetalle;
import ob.printer.ticket.Cierre;
import ob.printer.ticket.Factura;
import ob.printer.ticket.Liberar;
import ob.printer.ticket.Pedido;
import ob.printer.ticket.Precuenta;
import ob.printer.util.App;
import ob.printer.util.Util;

/**
 *
 * @author jc
 */
public class Imprimir {

    public Imprimir() {
    }

    public boolean precuenta(int cajaId, String nroAtencion) {
        try {
            AtencionController ctrl = new AtencionController();
            List<Atencion> atenciones = ctrl.getAll(cajaId, nroAtencion);
            if (atenciones.size() > 0) {
                Atencion atencion = atenciones.get(0);
                Caja caja = new CajaController().getById(cajaId);
                Cabecera cabecera = getCabecera(caja);
                cabecera.setMesa(atencion.getNroatencion());
                cabecera.setPax(atencion.getPax());
                cabecera.setCajero(new UsuarioController().getById(atencion.getCajeroId()).getNombre());
                if (atencion.getMozoId() > 0) {
                    cabecera.setMozo(new UsuarioController().getById(atencion.getMozoId()).getNombre());
                } else {
                    cabecera.setMozo("SIN MOZO");
                }
                List<Detalle> lstDetalle = new ArrayList<>();
                for (Atencion fila : atenciones) {
                    lstDetalle.add(new Detalle(fila.getCantidad(), fila.getProducto(), fila.getPrecio()));
                }
                Precuenta.CABECERA = new TicketController().getByModuloNombre("PRECUENTA", "CABECERA");
                boolean success = new Precuenta(caja.getImpresoraPrecuenta(), cabecera, lstDetalle).print();
                if (success) {
                    Util.info("Print Precuenta OK [".concat(caja.getImpresoraPrecuenta()).concat("]"));
                    //ctrl.update(atencion.getNroatencion());
                } else {
                    Util.info("Algun error en la Precuenta");
                }
                ctrl.close();
                return success;
            } else {
                return true;
            }
        } catch (SQLException ex) {
            Util.info(ex.getMessage());
            return false;
        }
    }
    
    public boolean liberar(int id) {
        try {
            Liberado liberado = new LiberadoController().getById(id);
            if (liberado!=null) {
                Caja caja = new CajaController().getById(liberado.getCajaId());
                Cabecera cabecera = new Cabecera();
                cabecera.setMesa(liberado.getNroatencion());
                cabecera.setCajero(new UsuarioController().getById(liberado.getCajeroId()).getNombre());
                cabecera.setTotal(liberado.getTotal());

                boolean success = new Liberar(caja.getImpresoraPrecuenta(), cabecera).print();
                if (success) {
                    Util.info("Print Liberado OK [".concat(caja.getImpresoraPrecuenta()).concat("]"));
                } else {
                    Util.info("Algun error al Liberar");
                }
                return success;
            } else {
                return true;
            }
        } catch (SQLException ex) {
            Util.info(ex.getMessage());
            return false;
        }
    }

    public boolean factura(int id) {
        try {
            VentaController ctrl = new VentaController();
            Venta venta = ctrl.get(id);
            if (venta != null) {
                Caja caja = new CajaController().getById(venta.getCajaId());
                Cabecera cabecera = getCabecera(caja);
                cabecera.setFechaHora(venta.getFechaHora());
                cabecera.setPax(venta.getPax());
                cabecera.setCajero(new UsuarioController().getById(venta.getCajeroId()).getNombre());
                if (venta.getMozoId() > 0) {
                    cabecera.setMozo(new UsuarioController().getById(venta.getMozoId()).getNombre());
                } else {
                    cabecera.setMozo("MOZO DEMO");
                }
                if (venta.getClienteId() > 0) {
                    cabecera.setCliente(new ClienteController().getById(venta.getClienteId()));
                }
                List<Detalle> lstDetalle = new ArrayList<>();
                for (VentaDetalle fila : ctrl.getDetalle(venta.getId())) {
                    lstDetalle.add(new Detalle(fila.getCantidad(), fila.getProducto(), fila.getPrecio()));
                }
                String printer = "";
                if (venta.getTipoDocumentoId() == App.FACTURA) {
                    printer = caja.getImpresoraFactura();
                    Impuesto.IGV = new ImpuestoController().getIgv();

                } else {
                    printer = caja.getImpresoraBoleta();
                }
                List<Object> doc = new ArrayList<>();
                doc.add(venta.getTipoDocumentoId());
                doc.add(venta.getSerie());
                doc.add(venta.getNumero());
                boolean success = new Factura(printer, cabecera, lstDetalle, doc).print();
                if (success) {
                    Util.info("Print Factura OK [".concat(printer).concat("]"));
                } else {
                    Util.info("ALgun error en la Factura");
                }
                ctrl.close();
                return success;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Util.info(ex.getMessage());
            return false;
        }
    }
    
    public boolean cierre(int cajaId, int cajeroId) {
        try {
            VentaController ctrl = new VentaController();
            Caja caja = new CajaController().getById(cajaId);
            List<Venta> ventas = ctrl.getByDia(cajaId, caja.getDia(), cajeroId);
            if (ventas.size()>0) {
                
                Cabecera cabecera = getCabecera(caja);
                cabecera.setDia(caja.getDia());
                cabecera.setCajero(cajeroId == 0 ? "0" : new UsuarioController().getById(cajeroId).getNombre());
                
                cabecera.setBoletas(ctrl.getComprobantes(cajaId, caja.getDia(), cajeroId, App.BOLETA));
                cabecera.setFacturas(ctrl.getComprobantes(cajaId, caja.getDia(), cajeroId, App.FACTURA));
                cabecera.setBoletasAnuladas(ctrl.getAnulados(cajaId, caja.getDia(), cajeroId, App.BOLETA));
                cabecera.setFacturasAnuladas(ctrl.getAnulados(cajaId, caja.getDia(), cajeroId, App.FACTURA));
                
                List<Detalle> lstDetalle = new ArrayList<>();
                
                cabecera.setPagos(ctrl.getFormasDePago(cajaId, caja.getDia(), cajeroId));
                
                for (VentaDetalle fila : ctrl.getDetalleByDia(cajaId, caja.getDia(), cajeroId)) {
                    lstDetalle.add(new Detalle(fila.getCantidad(), fila.getProducto(), fila.getPrecio()));
                }
                
                Impuesto.IGV = new ImpuestoController().getIgv();
                
                String printer;
                if (cajeroId > 0) {
                    printer = caja.getImpresoraCierreParcial();
                } else {
                    printer = caja.getImpresoraCierreTotal();
                }
                
                boolean success = new Cierre(printer, cabecera, lstDetalle).print();
                if (success) {
                    Util.info("Print CIERRE OK [".concat(printer).concat("]"));
                } else {
                    Util.info("ALgun error en CIERRE");
                }
                ctrl.close();
                return success;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            Util.info(ex.getMessage());
            return false;
        }
    }

    public boolean enviarPedido(int cajaId, String nroAtencion) {
        try {
            AtencionController ctrl = new AtencionController();
            List<Atencion> atenciones = ctrl.getNoEnviados(cajaId, nroAtencion);
            if (atenciones.size() > 0) {
                return enviar(atenciones, ctrl, Pedido.ADD);
            }
            return true;
        } catch (SQLException ex) {
            Util.info(ex.getMessage());
            Util.info("No se puede enviar Pedido");
            return false;
        }
    }

    public boolean editarEnvio(String tipo, int atencionId, int cantidad) {
        try {
            AtencionController ctrl = new AtencionController();
            List<Atencion> atenciones = ctrl.getById(atencionId);
            if (atenciones.size() > 0) {
                atenciones.get(0).setCantidad(cantidad);
                return enviar(atenciones, ctrl, tipo);
            }
            return true;
        } catch (SQLException ex) {
            Util.info(ex.getMessage());
            Util.info("No se puede enviar Pedido");
            return false;
        }
    }

    public boolean enviar(List<Atencion> atenciones, AtencionController ctrl, String tipo) throws SQLException {
        boolean success = true;
        Atencion atencion = atenciones.get(0);
        Cabecera cabecera = new Cabecera();
        cabecera.setMesa(atencion.getNroatencion());
        if (atencion.getMozoId() > 0) {
            cabecera.setMozo(new UsuarioController().getById(atencion.getMozoId()).getNombre());
        } else {
            cabecera.setMozo("MOZO DEMO");
        }
        List<Destino> destinos = new DestinoController().getAll();
        for (Destino destino : destinos) {
            List<Detalle> lstDetalle = new ArrayList<>();
            for (Atencion fila : atenciones) {
                cabecera.setDestino(destino.getNombre());
                if (fila.getDestinoId() == destino.getId()) {
                    lstDetalle.add(new Detalle(fila.getCantidad(), fila.getId(), fila.getProducto(), fila.getMensaje()));
                }
            }
            if (lstDetalle.size() > 0) {
                success = new Pedido(destino.getDestino(), cabecera, lstDetalle, tipo).print();
                if (success) {
                    ctrl.updateEnviados(lstDetalle);
                    Util.info("Print Enviar Pedido OK [".concat(destino.getDestino()).concat("]"));
                } else {
                    Util.info("Algun error al Enviar Pedido:" + destino.getNombre());
                }
            }
        }
        return success;
    }

    private Cabecera getCabecera(Caja caja) throws SQLException {
        CentroCosto cc = new CentroCostoController().getById(caja.getCentroCostoId());
        Empresa emp = new EmpresaController().getById(cc.getEmpresaId());
        Cabecera cabecera = new Cabecera();
        cabecera.setRazonSocial(emp.getRazonSocial());
        cabecera.setNombreComercial(emp.getNombreComercial());
        cabecera.setRuc(emp.getRuc());
        Ubigeo ubigeo = new UbigeoController().getById(emp.getUbigeoId());
        cabecera.setDireccion(emp.getDireccion() + " " + ubigeo.getNombre());
        cabecera.setTelefono(emp.getTelefono());
        cabecera.setAutorizacion(caja.getAutorizacion() == null ? "" : caja.getAutorizacion());
        cabecera.setSerie(caja.getSerieCaja());
        cabecera.setServicio(cc.getServicio());
        cabecera.setTipoCambio(cc.getTipoCambio());
        cabecera.setDespedida(cc.getMensaje());
        return cabecera;
    }

    public boolean testDb() {
        try {
            AtencionController ctrl = new AtencionController();
            List<Atencion> atenciones = ctrl.get();
            return true;
        } catch (SQLException ex) {
            Util.info(ex.getMessage());
            Util.info("No se puede conectar a la DB");
            return false;
        }
    }
}
