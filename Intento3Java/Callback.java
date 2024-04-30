import java.io.Serializable;

/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 * 
 * Fichero: Callback.java
 * Comentarios: Es el fichero correspondiente a la función callback pasada como parámetro por el consumidor.
 ******************************************************************************/

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
