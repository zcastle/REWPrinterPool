package ob.printer;

import java.util.Date;
import java.util.List;
import ob.printer.model.Cliente;
import ob.printer.model.Pago;
import ob.printer.model.Venta;
import ob.printer.model.VentaDetalle;

/**
 *
 * @author Pilar
 */
public class Cabecera {

    private String razonSocial;
    private String nombreComercial;
    private String ruc;
    private String direccion;
    private String telefono;
    private String autorizacion;
    private String serie;
    private String despedida;
    private String mesa;
    private String cajero;
    private String mozo = "";
    private String pax;
    private Date fechaHora = new Date();
    private Cliente cliente;
    private Double servicio;
    private Double tipoCambio;
    private String destino;
    private Double total;
    private int dia;
    private Date fechaIni;
    private Date fechafin;
    private List<Venta> boletas;
    private List<Venta> facturas;
    private long boletasAnuladas;
    private long facturasAnuladas;
    private List<Pago> pagos;
    private List<VentaDetalle> productos;
    
    public Cabecera() {
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(String autorizacion) {
        this.autorizacion = autorizacion;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getDespedida() {
        return despedida;
    }

    public void setDespedida(String despedida) {
        this.despedida = despedida;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public String getCajero() {
        return cajero;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    public String getMozo() {
        return mozo;
    }

    public void setMozo(String mozo) {
        this.mozo = mozo;
    }

    public String getPax() {
        return pax;
    }

    public void setPax(String pax) {
        this.pax = pax;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Double getServicio() {
        return servicio;
    }

    public void setServicio(Double servicio) {
        this.servicio = servicio;
    }

    public Double getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(Double tipoCambio) {
        this.tipoCambio = tipoCambio;
    }
    
    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public Date getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(Date fechaIni) {
        this.fechaIni = fechaIni;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public List<Venta> getBoletas() {
        return boletas;
    }

    public void setBoletas(List<Venta> boletas) {
        this.boletas = boletas;
    }

    public List<Venta> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Venta> facturas) {
        this.facturas = facturas;
    }

    public long getBoletasAnuladas() {
        return boletasAnuladas;
    }

    public void setBoletasAnuladas(long boletasAnuladas) {
        this.boletasAnuladas = boletasAnuladas;
    }

    public long getFacturasAnuladas() {
        return facturasAnuladas;
    }

    public void setFacturasAnuladas(long facturasAnuladas) {
        this.facturasAnuladas = facturasAnuladas;
    }

    public List<Pago> getPagos() {
        return pagos;
    }

    public void setPagos(List<Pago> pagos) {
        this.pagos = pagos;
    }

    public List<VentaDetalle> getProductos() {
        return productos;
    }

    public void setProductos(List<VentaDetalle> productos) {
        this.productos = productos;
    }
    
}
