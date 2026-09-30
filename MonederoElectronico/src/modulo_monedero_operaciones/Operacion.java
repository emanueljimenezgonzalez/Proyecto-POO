package modulo_monedero_operaciones;

import java.text.SimpleDateFormat;
import java.util.Date;

/** Clase base para todos los movimientos financieros del sistema. */
public abstract class Operacion {
    private String idOperacion;
    private Date fecha;
    private double monto;
    private String estado;

    protected Operacion(String idOperacion, double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero.");
        }
        this.idOperacion = idOperacion;
        this.fecha = new Date();
        this.monto = monto;
        this.estado = "PENDIENTE";
    }

    public abstract boolean ejecutar();
    public abstract String getTipo();

    public String obtenerResumen() {
        return idOperacion + " | " + getTipo() + " | ₡"
                + String.format("%.2f", monto) + " | " + estado + " | "
                + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(fecha);
    }

    public String getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(String idOperacion) {
        this.idOperacion = idOperacion;
    }

    public Date getFecha() {
        return new Date(fecha.getTime());
    }

    public void setFecha(Date fecha) {
        this.fecha = new Date(fecha.getTime());
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero.");
        }
        this.monto = monto;
    }

    public String getEstado() {
        return estado;
    }

    protected void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return obtenerResumen();
    }
}
