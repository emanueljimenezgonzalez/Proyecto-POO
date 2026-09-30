package modulo_monedero_operaciones;

import modulo_usuarios_coordinacion.Persona;

/** Contrato para una operación que debe aprobarse o rechazarse. */
public interface Aprobable {
    boolean aprobar(Persona responsable);
    void rechazar(Persona responsable);
    boolean estaPendiente();
}
