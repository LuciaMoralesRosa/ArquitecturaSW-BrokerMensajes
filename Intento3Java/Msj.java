/**
 * Clase que representa un mensaje en el sistema MOM (Message Oriented Middleware).
 */
public class Msj {
    Integer prioridad;
    long caducidad;
    String mensaje;

    /**
     * Constructor de la clase Msj.
     *
     * @param prioridad  Prioridad del mensaje.
     * @param caducidad  Tiempo de caducidad del mensaje.
     * @param mensaje    Contenido del mensaje.
     */
    public Msj(Integer prioridad, long caducidad, String mensaje) {
        this.prioridad = prioridad;
        this.caducidad = caducidad;
        this.mensaje = mensaje;
    }

    /**
     * Método para obtener la prioridad del mensaje.
     *
     * @return La prioridad del mensaje.
     */
    public Integer getPrioridad() {
        return prioridad;
    }

    /**
     * Método para establecer la prioridad del mensaje.
     *
     * @param prioridad La nueva prioridad del mensaje.
     */
    public void setPrioridad(Integer prioridad) {
        this.prioridad = prioridad;
    }

    /**
     * Método para obtener el tiempo de caducidad del mensaje.
     *
     * @return El tiempo de caducidad del mensaje.
     */
    public long getCaducidad() {
        return caducidad;
    }

    /**
     * Método para establecer el tiempo de caducidad del mensaje.
     *
     * @param caducidad El nuevo tiempo de caducidad del mensaje.
     */
    public void setCaducidad(Integer caducidad) {
        this.caducidad = caducidad;
    }

    /**
     * Método para obtener el contenido del mensaje.
     *
     * @return El contenido del mensaje.
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Método para establecer el contenido del mensaje.
     *
     * @param mensaje El nuevo contenido del mensaje.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}