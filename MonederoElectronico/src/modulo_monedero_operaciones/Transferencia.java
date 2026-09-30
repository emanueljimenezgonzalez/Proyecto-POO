package modulo_monedero_operaciones;

import modulo_usuarios_coordinacion.Persona;
import modulo_usuarios_coordinacion.Usuario;

/** Transferencia que requiere la aceptación del usuario destinatario. */
public class Transferencia extends Operacion implements Aprobable {
    private Usuario remitente;
    private Usuario destinatario;
    private Monedero origen;
    private Monedero destino;

    public Transferencia(String idOperacion, double monto,
            Usuario remitente, Usuario destinatario,
            Monedero origen, Monedero destino) {
        super(idOperacion, monto);
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.origen = origen;
        this.destino = destino;
    }

    @Override
    public boolean aprobar(Persona responsable) {
        if (!estaPendiente() || !esDestinatario(responsable)) {
            return false;
        }
        setEstado("APROBADA");
        return ejecutar();
    }

    @Override
    public void rechazar(Persona responsable) {
        if (estaPendiente() && esDestinatario(responsable)) {
            setEstado("RECHAZADA");
        }
    }

    private boolean esDestinatario(Persona responsable) {
        return responsable != null
                && destinatario.getIdentificacion().equals(responsable.getIdentificacion());
    }

    @Override
    public boolean estaPendiente() {
        return "PENDIENTE".equals(getEstado());
    }

    @Override
    public boolean ejecutar() {
        if (!"APROBADA".equals(getEstado())) {
            return false;
        }
        if (!origen.debitar(getMonto())) {
            setEstado("SIN_SALDO");
            return false;
        }
        destino.cargar(getMonto());
        setEstado("COMPLETADA");
        origen.registrarMovimiento(this);
        destino.registrarMovimiento(this);
        return true;
    }

    @Override
    public String getTipo() {
        return "TRANSFERENCIA";
    }

    public Usuario getRemitente() {
        return remitente;
    }

    public void setRemitente(Usuario remitente) {
        this.remitente = remitente;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    public Monedero getOrigen() {
        return origen;
    }

    public void setOrigen(Monedero origen) {
        this.origen = origen;
    }

    public Monedero getDestino() {
        return destino;
    }

    public void setDestino(Monedero destino) {
        this.destino = destino;
    }
}
