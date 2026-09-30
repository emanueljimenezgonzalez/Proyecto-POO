package modulo_usuarios_coordinacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import modulo_menu_compras.Compra;
import modulo_monedero_operaciones.Monedero;
import modulo_monedero_operaciones.Operacion;
import modulo_monedero_operaciones.Transferencia;

/**
 * Representa a un usuario de la soda que posee un monedero electrónico.
 */
public class Usuario extends Persona {
    private boolean activo;
    private Monedero monedero;
    private final List<Compra> compras;

    public Usuario(String identificacion, String nombre) {
        super(identificacion, nombre);
        this.activo = true;
        this.monedero = new Monedero();
        this.compras = new ArrayList<Compra>();
    }

    public double consultarSaldo() {
        return monedero.getSaldo();
    }

    public void cargarSaldo(double monto) {
        monedero.cargar(monto);
    }

    public Transferencia solicitarTransferencia(
            String idOperacion, Usuario destinatario, double monto) {
        return new Transferencia(
                idOperacion, monto, this, destinatario,
                monedero, destinatario.getMonedero());
    }

    public List<Operacion> consultarMovimientos() {
        return monedero.getMovimientos();
    }

    public void registrarCompra(Compra compra) {
        if (compra != null && !compras.contains(compra)) {
            compras.add(compra);
        }
    }

    @Override
    public String getTipoPersona() {
        return "Usuario";
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Monedero getMonedero() {
        return monedero;
    }

    public void setMonedero(Monedero monedero) {
        this.monedero = monedero;
    }

    public List<Compra> getCompras() {
        return Collections.unmodifiableList(compras);
    }
}
