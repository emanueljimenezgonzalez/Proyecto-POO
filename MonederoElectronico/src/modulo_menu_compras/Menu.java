package modulo_menu_compras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Administra las opciones que ofrece la soda. */
public class Menu {
    private String nombre;
    private final List<OpcionMenu> opciones;

    public Menu(String nombre) {
        this.nombre = nombre;
        this.opciones = new ArrayList<OpcionMenu>();
    }

    public boolean registrarOpcion(OpcionMenu opcion) {
        if (opcion == null || buscarOpcion(opcion.getCodigo()) != null) {
            return false;
        }
        return opciones.add(opcion);
    }

    public OpcionMenu buscarOpcion(String codigo) {
        for (OpcionMenu opcion : opciones) {
            if (opcion.getCodigo().equalsIgnoreCase(codigo)) {
                return opcion;
            }
        }
        return null;
    }

    public boolean eliminarOpcion(String codigo) {
        OpcionMenu opcion = buscarOpcion(codigo);
        return opcion != null && opciones.remove(opcion);
    }

    public List<OpcionMenu> obtenerOpcionesDisponibles() {
        List<OpcionMenu> disponibles = new ArrayList<OpcionMenu>();
        for (OpcionMenu opcion : opciones) {
            if (opcion.isDisponible()) {
                disponibles.add(opcion);
            }
        }
        return disponibles;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<OpcionMenu> getOpciones() {
        return Collections.unmodifiableList(opciones);
    }
}
