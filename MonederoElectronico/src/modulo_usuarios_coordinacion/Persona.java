package modulo_usuarios_coordinacion;

/**
 * Representa la información común de las personas que utilizan el sistema.
 */
public abstract class Persona {
    private String identificacion;
    private String nombre;

    public Persona(String identificacion, String nombre) {
        this.identificacion = identificacion;
        this.nombre = nombre;
    }

    public abstract String getTipoPersona();

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return getTipoPersona() + ": " + nombre + " (" + identificacion + ")";
    }
}
