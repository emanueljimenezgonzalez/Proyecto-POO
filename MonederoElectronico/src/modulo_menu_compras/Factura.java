package modulo_menu_compras;

import java.text.SimpleDateFormat;
import java.util.Date;

/** Contiene el comprobante generado para una compra. */
public class Factura {
    private String numero;
    private Date fecha;
    private double total;

    public Factura(String numero, Date fecha, double total) {
        this.numero = numero;
        this.fecha = fecha;
        this.total = total;
    }

    public String generarResumen() {
        return "Factura " + numero
                + " | Fecha: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(fecha)
                + " | Total: ₡" + String.format("%.2f", total);
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return generarResumen();
    }
}
