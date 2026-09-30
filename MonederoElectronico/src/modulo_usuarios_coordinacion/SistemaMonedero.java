package modulo_usuarios_coordinacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import modulo_menu_compras.Compra;
import modulo_menu_compras.Menu;
import modulo_monedero_operaciones.Operacion;

/**
 * Coordina los usuarios, cajeros, el menú y las operaciones del sistema.
 */
public class SistemaMonedero {
    private Menu menu;
    private final List<Usuario> usuarios;
    private final List<Cajero> cajeros;
    private final List<Operacion> operaciones;

    public SistemaMonedero(Menu menu) {
        this.menu = menu;
        this.usuarios = new ArrayList<Usuario>();
        this.cajeros = new ArrayList<Cajero>();
        this.operaciones = new ArrayList<Operacion>();
    }

    public boolean registrarUsuario(Usuario usuario) {
        if (usuario == null || buscarUsuario(usuario.getIdentificacion()) != null) {
            return false;
        }
        return usuarios.add(usuario);
    }

    public boolean registrarCajero(Cajero cajero) {
        if (cajero == null || buscarCajero(cajero.getIdentificacion()) != null) {
            return false;
        }
        return cajeros.add(cajero);
    }

    public Usuario buscarUsuario(String identificacion) {
        for (Usuario usuario : usuarios) {
            if (usuario.getIdentificacion().equalsIgnoreCase(identificacion)) {
                return usuario;
            }
        }
        return null;
    }

    public Cajero buscarCajero(String identificacion) {
        for (Cajero cajero : cajeros) {
            if (cajero.getIdentificacion().equalsIgnoreCase(identificacion)) {
                return cajero;
            }
        }
        return null;
    }

    public Compra registrarCompra(String numero, Usuario usuario, Cajero cajero) {
        Compra compra = new Compra(numero, usuario, cajero);
        usuario.registrarCompra(compra);
        return compra;
    }

    public void registrarOperacion(Operacion operacion) {
        if (operacion != null && !operaciones.contains(operacion)) {
            operaciones.add(operacion);
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<Usuario> getUsuarios() {
        return Collections.unmodifiableList(usuarios);
    }

    public List<Cajero> getCajeros() {
        return Collections.unmodifiableList(cajeros);
    }

    public List<Operacion> getOperaciones() {
        return Collections.unmodifiableList(operaciones);
    }
}
