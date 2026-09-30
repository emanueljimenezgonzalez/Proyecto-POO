package modulo_menu_compras;

/** Contrato para los objetos que pueden generar una factura. */
public interface Facturable {
    Factura generarFactura();
}
