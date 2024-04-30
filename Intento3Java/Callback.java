import java.io.Serializable;

/**
 * Clase que representa un callback para ejecutar un mensaje.
 * Implementa la interfaz Serializable para permitir la serialización.
 */
public class Callback implements Serializable {

    /**
     * Método para ejecutar un mensaje.
     * Imprime el mensaje recibido por consola.
     *
     * @param mensaje El mensaje a ser ejecutado.
     */
    public void ejecutarMsj(String mensaje){
        System.out.println("El mensaje recibido es: " + mensaje);
    }
}
