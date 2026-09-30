package modulo_menu_compras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import modulo_monedero_operaciones.Pago;
import modulo_usuarios_coordinacion.Cajero;
import modulo_usuarios_coordinacion.Usuario;

/** Agrupa los productos adquiridos por un usuario en una transacción. */
public class Compra implements Facturable {
    private String numero;
    private Date fecha;
    private Usuario usuario;
    private Cajero cajero;
    private final List<DetalleCompra> detalles;
    private double total;
    private String estado;
    private Pago pago;
    private Factura factura;

    public Compra(String numero, Usuario usuario, Cajero cajero) {
        this.numero = numero;
        this.fecha = new Date();
        this.usuario = usuario;
        this.cajero = cajero;
        this.detalles = new ArrayList<DetalleCompra>();
        this.estado = "ABIERTA";
    }

    public void agregarDetalle(OpcionMenu opcion, int cantidad) {
        detalles.add(new DetalleCompra(opcion, cantidad));
        calcularTotal();
    }

    public double calcularTotal() {
        total = 0;
        for (DetalleCompra detalle : detalles) {
            total += detalle.calcularSubtotal();
        }
        return total;
    }

    public Pago crearPago(String idOperacion) {
        if (detalles.isEmpty()) {
            throw new IllegalStateException("La compra no contiene productos.");
        }
        pago = new Pago(idOperacion, calcularTotal(), usuario.getMonedero(), this);
        estado = "PENDIENTE_DE_PAGO";
        return pago;
    }

    @Override
    public Factura generarFactura() {
        factura = new Factura("F-" + numero, new Date(), calcularTotal());
        return factura;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return new Date(fecha.getTime());
    }

    public void setFecha(Date fecha) {
        this.fecha = new Date(fecha.getTime());
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cajero getCajero() {
        return cajero;
    }

    public void setCajero(Cajero cajero) {
        this.cajero = cajero;
    }

    public List<DetalleCompra> getDetalles() {
        return Collections.unmodifiableList(detalles);
    }

    public double getTotal() {
        return total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
}
