package modulo_monedero_operaciones;

import modulo_menu_compras.Compra;
import modulo_usuarios_coordinacion.Cajero;
import modulo_usuarios_coordinacion.Persona;

/** Pago asociado a una compra y sujeto a la aprobación de un cajero. */
public class Pago extends Operacion implements Aprobable {
    private Monedero monedero;
    private Compra compra;
    private Cajero cajeroAprobador;

    public Pago(String idOperacion, double monto, Monedero monedero, Compra compra) {
        super(idOperacion, monto);
        this.monedero = monedero;
        this.compra = compra;
    }

    @Override
    public boolean aprobar(Persona responsable) {
        if (!(responsable instanceof Cajero) || !estaPendiente()) {
            return false;
        }
        cajeroAprobador = (Cajero) responsable;
        setEstado("APROBADO");
        return ejecutar();
    }

    @Override
    public void rechazar(Persona responsable) {
        if (responsable instanceof Cajero && estaPendiente()) {
            cajeroAprobador = (Cajero) responsable;
            setEstado("RECHAZADO");
            compra.setEstado("PAGO_RECHAZADO");
        }
    }

    @Override
    public boolean estaPendiente() {
        return "PENDIENTE".equals(getEstado());
    }

    @Override
    public boolean ejecutar() {
        if (!"APROBADO".equals(getEstado())) {
            return false;
        }
        if (!monedero.debitar(getMonto())) {
            setEstado("SIN_SALDO");
            compra.setEstado("SIN_SALDO");
            return false;
        }
        setEstado("COMPLETADO");
        compra.setEstado("PAGADA");
        compra.generarFactura();
        monedero.registrarMovimiento(this);
        return true;
    }

    @Override
    public String getTipo() {
        return "PAGO";
    }

    public Monedero getMonedero() {
        return monedero;
    }

    public void setMonedero(Monedero monedero) {
        this.monedero = monedero;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Cajero getCajeroAprobador() {
        return cajeroAprobador;
    }

    public void setCajeroAprobador(Cajero cajeroAprobador) {
        this.cajeroAprobador = cajeroAprobador;
    }
}
