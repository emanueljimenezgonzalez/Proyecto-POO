package modulo_monedero_operaciones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Almacena el saldo y el historial de movimientos de un usuario. */
public class Monedero {
    private double saldo;
    private final List<Operacion> movimientos;

    public Monedero() {
        this.saldo = 0;
        this.movimientos = new ArrayList<Operacion>();
    }

    public void cargar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero.");
        }
        saldo += monto;
    }

    public boolean debitar(double monto) {
        if (monto <= 0 || !tieneSaldoSuficiente(monto)) {
            return false;
        }
        saldo -= monto;
        return true;
    }

    public boolean tieneSaldoSuficiente(double monto) {
        return saldo >= monto;
    }

    public void registrarMovimiento(Operacion operacion) {
        if (operacion != null && !movimientos.contains(operacion)) {
            movimientos.add(operacion);
        }
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        if (saldo < 0) {
            throw new IllegalArgumentException("El saldo no puede ser negativo.");
        }
        this.saldo = saldo;
    }

    public List<Operacion> getMovimientos() {
        return Collections.unmodifiableList(movimientos);
    }
}
