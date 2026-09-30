package integracion;

import java.util.List;
import java.util.Scanner;
import modulo_menu_compras.Compra;
import modulo_menu_compras.Menu;
import modulo_menu_compras.OpcionMenu;
import modulo_monedero_operaciones.CargaSaldo;
import modulo_monedero_operaciones.Operacion;
import modulo_monedero_operaciones.Pago;
import modulo_monedero_operaciones.Transferencia;
import modulo_usuarios_coordinacion.Cajero;
import modulo_usuarios_coordinacion.SistemaMonedero;
import modulo_usuarios_coordinacion.Usuario;

/**
 * Punto de entrada de la aplicación de consola del monedero electrónico.
 */
public class Main {
    private static final Scanner TECLADO = new Scanner(System.in);
    private static int consecutivo = 1;

    public static void main(String[] args) {
        Menu menu = new Menu("Soda institucional");
        SistemaMonedero sistema = new SistemaMonedero(menu);
        cargarDatosIniciales(sistema);

        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero("Seleccione una opción: ");
            try {
                ejecutarOpcion(opcion, sistema);
            } catch (IllegalArgumentException ex) {
                System.out.println("Dato inválido: " + ex.getMessage());
            } catch (IllegalStateException ex) {
                System.out.println("No se pudo completar: " + ex.getMessage());
            }
        } while (opcion != 0);

        System.out.println("Programa finalizado.");
        TECLADO.close();
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== MONEDERO ELECTRÓNICO ===");
        System.out.println("1. Registrar usuario");
        System.out.println("2. Registrar cajero");
        System.out.println("3. Registrar opción del menú");
        System.out.println("4. Consultar menú de la soda");
        System.out.println("5. Cargar saldo");
        System.out.println("6. Realizar compra");
        System.out.println("7. Solicitar transferencia");
        System.out.println("8. Aceptar o rechazar transferencia");
        System.out.println("9. Consultar saldo y movimientos");
        System.out.println("0. Salir");
    }

    private static void ejecutarOpcion(int opcion, SistemaMonedero sistema) {
        switch (opcion) {
            case 1:
                registrarUsuario(sistema);
                break;
            case 2:
                registrarCajero(sistema);
                break;
            case 3:
                registrarOpcionMenu(sistema);
                break;
            case 4:
                mostrarOpciones(sistema.getMenu());
                break;
            case 5:
                cargarSaldo(sistema);
                break;
            case 6:
                realizarCompra(sistema);
                break;
            case 7:
                solicitarTransferencia(sistema);
                break;
            case 8:
                resolverTransferencia(sistema);
                break;
            case 9:
                consultarMovimientos(sistema);
                break;
            case 0:
                break;
            default:
                System.out.println("La opción seleccionada no existe.");
        }
    }

    private static void cargarDatosIniciales(SistemaMonedero sistema) {
        sistema.getMenu().registrarOpcion(
                new OpcionMenu("D1", "Desayuno tradicional", "Desayuno", 1800));
        sistema.getMenu().registrarOpcion(
                new OpcionMenu("A1", "Almuerzo del día", "Almuerzo", 3200));
        sistema.getMenu().registrarOpcion(
                new OpcionMenu("B1", "Bebida natural", "Bebida", 900));
    }

    private static void registrarUsuario(SistemaMonedero sistema) {
        String identificacion = leerTexto("Identificación: ");
        String nombre = leerTexto("Nombre: ");
        boolean registrado = sistema.registrarUsuario(new Usuario(identificacion, nombre));
        System.out.println(registrado ? "Usuario registrado." : "Ya existe ese usuario.");
    }

    private static void registrarCajero(SistemaMonedero sistema) {
        String identificacion = leerTexto("Identificación: ");
        String nombre = leerTexto("Nombre: ");
        String codigo = leerTexto("Código de empleado: ");
        boolean registrado = sistema.registrarCajero(
                new Cajero(identificacion, nombre, codigo));
        System.out.println(registrado ? "Cajero registrado." : "Ya existe ese cajero.");
    }

    private static void registrarOpcionMenu(SistemaMonedero sistema) {
        String codigo = leerTexto("Código del producto: ");
        String nombre = leerTexto("Nombre: ");
        String categoria = leerTexto("Categoría: ");
        double precio = leerDouble("Precio: ");
        boolean registrada = sistema.getMenu().registrarOpcion(
                new OpcionMenu(codigo, nombre, categoria, precio));
        System.out.println(registrada ? "Opción registrada." : "El código ya existe.");
    }

    private static void mostrarOpciones(Menu menu) {
        System.out.println("\n--- " + menu.getNombre() + " ---");
        List<OpcionMenu> opciones = menu.obtenerOpcionesDisponibles();
        if (opciones.isEmpty()) {
            System.out.println("No hay productos disponibles.");
            return;
        }
        for (OpcionMenu opcion : opciones) {
            System.out.println(opcion);
        }
    }

    private static void cargarSaldo(SistemaMonedero sistema) {
        Usuario usuario = solicitarUsuario(sistema, "Identificación del usuario: ");
        if (usuario == null) {
            return;
        }
        double monto = leerDouble("Monto de la carga: ");
        CargaSaldo carga = new CargaSaldo(nuevoId("CAR"), monto, usuario.getMonedero());
        sistema.registrarOperacion(carga);
        System.out.println(carga.ejecutar()
                ? "Carga completada. Saldo: ₡" + formato(usuario.consultarSaldo())
                : "No se pudo realizar la carga.");
    }

    private static void realizarCompra(SistemaMonedero sistema) {
        Usuario usuario = solicitarUsuario(sistema, "Identificación del usuario: ");
        Cajero cajero = solicitarCajero(sistema);
        if (usuario == null || cajero == null) {
            return;
        }

        Compra compra = sistema.registrarCompra(nuevoId("COM"), usuario, cajero);
        mostrarOpciones(sistema.getMenu());
        while (true) {
            String codigo = leerTexto("Código del producto (0 para terminar): ");
            if ("0".equals(codigo)) {
                break;
            }
            OpcionMenu opcion = sistema.getMenu().buscarOpcion(codigo);
            if (opcion == null || !opcion.isDisponible()) {
                System.out.println("Producto no encontrado o no disponible.");
                continue;
            }
            int cantidad = leerEntero("Cantidad: ");
            compra.agregarDetalle(opcion, cantidad);
        }

        Pago pago = compra.crearPago(nuevoId("PAG"));
        sistema.registrarOperacion(pago);
        System.out.println("Total: ₡" + formato(compra.getTotal()));
        String respuesta = leerTexto("¿El cajero aprueba el pago? (s/n): ");
        if (respuesta.equalsIgnoreCase("s")) {
            if (cajero.aprobarPago(pago)) {
                System.out.println("Pago completado.");
                System.out.println(compra.getFactura().generarResumen());
            } else {
                System.out.println("Pago no realizado. Estado: " + pago.getEstado());
            }
        } else {
            cajero.rechazarPago(pago);
            System.out.println("Pago rechazado.");
        }
    }

    private static void solicitarTransferencia(SistemaMonedero sistema) {
        Usuario remitente = solicitarUsuario(sistema, "Identificación del remitente: ");
        Usuario destinatario = solicitarUsuario(sistema, "Identificación del destinatario: ");
        if (remitente == null || destinatario == null) {
            return;
        }
        if (remitente == destinatario) {
            System.out.println("El remitente y el destinatario deben ser diferentes.");
            return;
        }
        double monto = leerDouble("Monto de la transferencia: ");
        Transferencia transferencia = remitente.solicitarTransferencia(
                nuevoId("TRA"), destinatario, monto);
        sistema.registrarOperacion(transferencia);
        System.out.println("Transferencia creada con ID "
                + transferencia.getIdOperacion() + ". Está pendiente de aceptación.");
    }

    private static void resolverTransferencia(SistemaMonedero sistema) {
        String id = leerTexto("ID de la transferencia: ");
        Operacion operacion = buscarOperacion(sistema, id);
        if (!(operacion instanceof Transferencia)) {
            System.out.println("No se encontró la transferencia.");
            return;
        }
        Transferencia transferencia = (Transferencia) operacion;
        String identificacion = leerTexto("Identificación del destinatario: ");
        Usuario destinatario = sistema.buscarUsuario(identificacion);
        if (destinatario == null) {
            System.out.println("Usuario no encontrado.");
            return;
        }
        String respuesta = leerTexto("¿Aceptar transferencia? (s/n): ");
        if (respuesta.equalsIgnoreCase("s")) {
            boolean completada = transferencia.aprobar(destinatario);
            System.out.println(completada
                    ? "Transferencia completada."
                    : "No se pudo completar. Estado: " + transferencia.getEstado());
        } else {
            transferencia.rechazar(destinatario);
            System.out.println("Estado: " + transferencia.getEstado());
        }
    }

    private static void consultarMovimientos(SistemaMonedero sistema) {
        Usuario usuario = solicitarUsuario(sistema, "Identificación del usuario: ");
        if (usuario == null) {
            return;
        }
        System.out.println("Saldo disponible: ₡" + formato(usuario.consultarSaldo()));
        if (usuario.consultarMovimientos().isEmpty()) {
            System.out.println("El usuario no tiene movimientos.");
            return;
        }
        for (Operacion operacion : usuario.consultarMovimientos()) {
            System.out.println(operacion.obtenerResumen());
        }
    }

    private static Usuario solicitarUsuario(SistemaMonedero sistema, String mensaje) {
        Usuario usuario = sistema.buscarUsuario(leerTexto(mensaje));
        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
        }
        return usuario;
    }

    private static Cajero solicitarCajero(SistemaMonedero sistema) {
        Cajero cajero = sistema.buscarCajero(leerTexto("Identificación del cajero: "));
        if (cajero == null) {
            System.out.println("Cajero no encontrado.");
        }
        return cajero;
    }

    private static Operacion buscarOperacion(SistemaMonedero sistema, String id) {
        for (Operacion operacion : sistema.getOperaciones()) {
            if (operacion.getIdOperacion().equalsIgnoreCase(id)) {
                return operacion;
            }
        }
        return null;
    }

    private static String nuevoId(String prefijo) {
        return prefijo + String.format("%04d", consecutivo++);
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return TECLADO.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException ex) {
                System.out.println("Debe escribir un número entero.");
            }
        }
    }

    private static double leerDouble(String mensaje) {
        while (true) {
            try {
                return Double.parseDouble(leerTexto(mensaje).replace(',', '.'));
            } catch (NumberFormatException ex) {
                System.out.println("Debe escribir un número válido.");
            }
        }
    }

    private static String formato(double monto) {
        return String.format("%.2f", monto);
    }
}
