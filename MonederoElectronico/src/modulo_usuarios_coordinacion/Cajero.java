package modulo_usuarios_coordinacion;

import modulo_monedero_operaciones.Pago;

/**
 * Representa al cajero encargado de registrar y autorizar pagos.
 */
public class Cajero extends Persona {
    private String codigoEmpleado;

    public Cajero(String identificacion, String nombre, String codigoEmpleado) {
        super(identificacion, nombre);
        this.codigoEmpleado = codigoEmpleado;
    }

    public boolean aprobarPago(Pago pago) {
        return pago.aprobar(this);
    }

    public void rechazarPago(Pago pago) {
        pago.rechazar(this);
    }

    @Override
    public String getTipoPersona() {
        return "Cajero";
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }
}
