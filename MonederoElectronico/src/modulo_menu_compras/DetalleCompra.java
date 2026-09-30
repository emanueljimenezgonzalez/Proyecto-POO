package modulo_menu_compras;

/** Representa una opción del menú y su cantidad dentro de una compra. */
public class DetalleCompra {
    private OpcionMenu opcion;
    private int cantidad;
    private double precioUnitario;

    public DetalleCompra(OpcionMenu opcion, int cantidad) {
        if (opcion == null || !opcion.isDisponible()) {
            throw new IllegalArgumentException("La opción no está disponible.");
        }
        this.opcion = opcion;
        setCantidad(cantidad);
        this.precioUnitario = opcion.getPrecio();
    }

    public double calcularSubtotal() {
        return cantidad * precioUnitario;
    }

    public OpcionMenu getOpcion() {
        return opcion;
    }

    public void setOpcion(OpcionMenu opcion) {
        this.opcion = opcion;
        this.precioUnitario = opcion.getPrecio();
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva.");
        }
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        if (precioUnitario < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.precioUnitario = precioUnitario;
    }
}
