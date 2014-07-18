package ob.printer.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 *
 * @author jc
 */
@DatabaseTable(tableName = "venta_pagos")
public class Pago {

    @DatabaseField(id = true)
    private int id;
    @DatabaseField(columnName = "venta_id")
    private int ventaId;
    @DatabaseField(columnName = "tipopago")
    private String tipoPago;
    @DatabaseField(columnName = "valorpago")
    private Double valorPago;
    @DatabaseField(columnName = "tipocambio")
    private Double tipoCambio;
    
    public Pago() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVentaId() {
        return ventaId;
    }

    public void setVentaId(int ventaId) {
        this.ventaId = ventaId;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public Double getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(Double tipoCambio) {
        this.tipoCambio = tipoCambio;
    }
    
}
