package ob.printer.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import ob.printer.util.Conn;

/**
 *
 * @author jc
 */
public class VentaController {

    private final Dao<Venta, Integer> dao;
    private final Dao<VentaDetalle, Integer> daoDetalle;
    private final Dao<Pago, Integer> daoPago;

    public VentaController() throws SQLException {
        dao = DaoManager.createDao(Conn.getConnectionSource(), Venta.class);
        daoDetalle = DaoManager.createDao(Conn.getConnectionSource(), VentaDetalle.class);
        daoPago = DaoManager.createDao(Conn.getConnectionSource(), Pago.class);
    }

    public void close() throws SQLException {
        dao.closeLastIterator();
        daoDetalle.closeLastIterator();
        daoPago.closeLastIterator();
    }

    public Venta get(int id) throws SQLException {
        return dao.queryForId(id);
    }

    public List<VentaDetalle> getDetalle(int ventaId) throws SQLException {
        QueryBuilder<VentaDetalle, Integer> qb = daoDetalle.queryBuilder();
        qb.where().eq("venta_id", ventaId);
        return daoDetalle.query(qb.prepare());
    }

    public List<Venta> getByDia(int cajaId, int dia) throws SQLException {
        return getByDia(cajaId, dia, 0);
    }

    public List<Venta> getByDia(int cajaId, int dia, int cajeroId) throws SQLException {
        QueryBuilder<Venta, Integer> qb = dao.queryBuilder();
        Where<Venta, Integer> where = qb.where();
        where.eq("caja_id", cajaId).and().eq("dia", dia);
        if (cajeroId > 0) {
            where.and().eq("cajero_id", cajeroId);
        }
        return dao.query(qb.prepare());
    }

    public List<Venta> getComprobantes(int cajaId, int dia, int tipoDocumento) throws SQLException {
        return getComprobantes(cajaId, dia, 0, tipoDocumento);
    }

    public List<Venta> getComprobantes(int cajaId, int dia, int cajeroId, int tipoDocumento) throws SQLException {
        QueryBuilder<Venta, Integer> qb = dao.queryBuilder();
        Where<Venta, Integer> where = qb.where();
        where.eq("caja_id", cajaId).and().eq("dia", dia).and().eq("tipo_documento_id", tipoDocumento);
        if (cajeroId > 0) {
            where.and().eq("cajero_id", cajeroId);
        }
        return dao.query(qb.prepare());
    }

    public long getAnulados(int cajaId, int dia, int tipoDocumento) throws SQLException {
        return getAnulados(cajaId, dia, 0, tipoDocumento);
    }

    public long getAnulados(int cajaId, int dia, int cajeroId, int tipoDocumento) throws SQLException {
        QueryBuilder<Venta, Integer> qb = dao.queryBuilder();
        Where<Venta, Integer> where = qb.where();
        where.eq("caja_id", cajaId).
                and().eq("dia", dia).
                and().eq("tipo_documento_id", tipoDocumento).
                and().gt("anulado_id", 0);
        if (cajeroId > 0) {
            where.and().eq("cajero_id", cajeroId);
        }
        return qb.countOf();
    }

    public List<Pago> getFormasDePago(int cajaId, int dia) throws SQLException {
        return getFormasDePago(cajaId, dia, 0);
    }

    public List<Pago> getFormasDePago(int cajaId, int dia, int cajeroId) throws SQLException {
        QueryBuilder<Venta, Integer> qbVenta = dao.queryBuilder();
        Where<Venta, Integer> where = qbVenta.where();
        where.eq("caja_id", cajaId).and().eq("dia", dia);
        if (cajeroId > 0) {
            where.and().eq("cajero_id", cajeroId);
        }
        List<Venta> lstVentas = dao.query(qbVenta.prepare());

        String ventasIdIn = "(";

        for (Venta venta : lstVentas) {
            ventasIdIn += venta.getId() + ",";
        }
        ventasIdIn = ventasIdIn.substring(0, ventasIdIn.length() - 1) + ")";
        //System.out.println(ventasIdIn);

        /*QueryBuilder<Pago, Integer> qb = daoPago.queryBuilder();
         qb.selectRaw("id, venta_id, tipopago, SUM(valorpago) AS valorpago, tipocambio");
         //qb.selectColumns("id", "venta_id", "tipopago", "sum(valorpago) AS valorpago", "tipocambio");
         qb.where().in("venta_id", qbVenta);
         qb.groupBy("tipopago");*/
        GenericRawResults<Pago> rawResults
                = daoPago.queryRaw(
                        "SELECT id, venta_id, tipopago, SUM(valorpago) "
                        + "FROM venta_pagos "
                        + "WHERE venta_id IN " + ventasIdIn + " "
                        + "GROUP BY tipopago",
                        new RawRowMapper<Pago>() {
                            Pago pago;

                            @Override
                            public Pago mapRow(String[] columnNames, String[] resultColumns) {
                                pago = new Pago();
                                pago.setId(Integer.parseInt(resultColumns[0]));
                                pago.setVentaId(Integer.parseInt(resultColumns[1]));
                                pago.setTipoPago(resultColumns[2]);
                                pago.setValorPago(Double.parseDouble(resultColumns[3]));
                                //pago.setTipoCambio(Double.parseDouble(resultColumns[4]));
                                return pago;
                            }
                        });
        List<Pago> lstPagos = new ArrayList<>();
        for (Pago pago : rawResults) {
            //System.out.println("Pago: " + ventaDetalle.getVentaId());
            lstPagos.add(pago);
        }
        rawResults.close();
        return lstPagos;
        //return daoPago.query(qb.prepare());
    }

    public List<VentaDetalle> getDetalleByDia(int cajaId, int dia, int cajeroId) throws SQLException {
        QueryBuilder<Venta, Integer> qbVenta = dao.queryBuilder();
        Where<Venta, Integer> where = qbVenta.where();
        where.eq("caja_id", cajaId).and().eq("dia", dia);
        if (cajeroId > 0) {
            where.and().eq("cajero_id", cajeroId);
        }
        List<Venta> lstVentas = dao.query(qbVenta.prepare());

        String ventasIdIn = "(";

        for (Venta venta : lstVentas) {
            ventasIdIn += venta.getId() + ",";
        }
        ventasIdIn = ventasIdIn.substring(0, ventasIdIn.length() - 1) + ")";
        System.out.println(ventasIdIn);

        GenericRawResults<VentaDetalle> rawResults
                = daoDetalle.queryRaw(
                        "SELECT producto_name, precio, SUM(cantidad) "
                        + "FROM venta_detalle "
                        + "WHERE venta_id IN " + ventasIdIn + " "
                        + "GROUP BY producto_name, precio",
                        new RawRowMapper<VentaDetalle>() {
                            VentaDetalle ventaDetalle;

                            @Override
                            public VentaDetalle mapRow(String[] columnNames, String[] resultColumns) {
                                ventaDetalle = new VentaDetalle();
                                System.out.println(resultColumns[0]);
                                ventaDetalle.setProducto(resultColumns[0]);
                                ventaDetalle.setPrecio(Double.parseDouble(resultColumns[1]));
                                ventaDetalle.setCantidad(Integer.parseInt(resultColumns[2]));
                                return ventaDetalle;
                            }
                        });

        List<VentaDetalle> lstVentaDetalle = new ArrayList<>();
        for (VentaDetalle ventaDetalle : rawResults) {
            lstVentaDetalle.add(ventaDetalle);
        }
        rawResults.close();
        return lstVentaDetalle;
    }
}
