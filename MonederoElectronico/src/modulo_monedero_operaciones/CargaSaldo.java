package modulo_monedero_operaciones;

/** Operación que agrega dinero a un monedero. */
public class CargaSaldo extends Operacion {
    private Monedero monedero;

    public CargaSaldo(String idOperacion, double monto, Monedero monedero) {
        super(idOperacion, monto);
        this.monedero = monedero;
    }

    @Override
    public boolean ejecutar() {
        if (!"PENDIENTE".equals(getEstado())) {
            return false;
        }
        monedero.cargar(getMonto());
        setEstado("COMPLETADA");
        monedero.registrarMovimiento(this);
        return true;
    }

    @Override
    public String getTipo() {
        return "CARGA_DE_SALDO";
    }

    public Monedero getMonedero() {
        return monedero;
    }

    public void setMonedero(Monedero monedero) {
        this.monedero = monedero;
    }
}
