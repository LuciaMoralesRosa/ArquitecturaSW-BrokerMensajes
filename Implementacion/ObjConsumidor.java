/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 * 
 * Fichero: ObjComsumidor.java
 * Comentarios: Implementa el objeto ObjComsumidor. ******************************************************************************/

/**
 * Clase que representa un consumidor en el sistema MOM (Message Oriented Middleware).
 */
public class ObjConsumidor {
    String nombreConsumidor;
    Callback metodoCallback;

    /**
     * Constructor de la clase ObjConsumidor.
     *
     * @param nombreConsumidor Nombre del consumidor.
     * @param metodoCallback   Callback asociado al consumidor.
     */
    public ObjConsumidor(String nombreConsumidor, Callback metodoCallback) {
        this.nombreConsumidor = nombreConsumidor;
        this.metodoCallback = metodoCallback;
    }

    /**
     * Método para obtener el nombre del consumidor.
     *
     * @return El nombre del consumidor.
     */
    public String getNombreConsumidor() {
        return nombreConsumidor;
    }

    /**
     * Método para establecer el nombre del consumidor.
     *
     * @param nombreConsumidor El nuevo nombre del consumidor.
     */
    public void setNombreConsumidor(String nombreConsumidor) {
        this.nombreConsumidor = nombreConsumidor;
    }

    /**
     * Método para obtener el callback asociado al consumidor.
     *
     * @return El callback asociado al consumidor.
     */
    public Callback getMetodoCallback() {
        return metodoCallback;
    }

    /**
     * Método para establecer el callback asociado al consumidor.
     *
     * @param metodoCallback El nuevo callback asociado al consumidor.
     */
    public void setMetodoCallback(Callback metodoCallback) {
        this.metodoCallback = metodoCallback;
    }
}
