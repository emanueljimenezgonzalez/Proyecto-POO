package modulo_menu_compras;

/** Representa un producto u opción disponible en la soda. */
public class OpcionMenu {
    private String codigo;
    private String nombre;
    private String categoria;
    private double precio;
    private boolean disponible;

    public OpcionMenu(String codigo, String nombre, String categoria, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.categoria = categoria;
        setPrecio(precio);
        this.disponible = true;
    }

    public void actualizarPrecio(double nuevoPrecio) {
        setPrecio(nuevoPrecio);
    }

    public void activar() {
        disponible = true;
    }

    public void desactivar() {
        disponible = false;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        if (precio < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo.");
        }
        this.precio = precio;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " - ₡" + String.format("%.2f", precio)
                + (disponible ? "" : " (no disponible)");
    }
}
