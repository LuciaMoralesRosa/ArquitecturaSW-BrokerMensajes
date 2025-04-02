import java.rmi.Remote;
import java.rmi.RemoteException;

/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 * 
 * Fichero: MOMInterface.java
 * Comentarios: Es el fichero correspondiente a la interfaz para clientes del 
 * broker.
 ******************************************************************************/

/**
 * Definición de la interfaz remota MOMInterface.
 */
public interface MOMInterface extends Remote {

    /**
     * Método para declarar una cola.
     *
     * @param nombreCola  Nombre de la cola a declarar.
     * @param invocador   Nombre del invocador que declara la cola.
     * @throws RemoteException Si ocurre un error de red.
     */
    public void declararCola(String nombreCola, String invocador) throws RemoteException;

    /**
     * Método para publicar un mensaje en una cola.
     *
     * @param nombrePublicador Nombre del publicador del mensaje.
     * @param nombreCola       Nombre de la cola donde se publica el mensaje.
     * @param mensaje          Mensaje a publicar.
     * @param prioridad        Prioridad del mensaje.
     * @throws RemoteException Si ocurre un error de red.
     */
    public void publicar(String nombrePublicador, String nombreCola, String mensaje, Integer prioridad) 
            throws RemoteException;

    /**
     * Método para consumir mensajes de una cola.
     *
     * @param nombreConsumidor Nombre del consumidor.
     * @param metodoCallback   Callback que se ejecutará al recibir un mensaje.
     * @param nombreCola       Nombre de la cola de la que se consumen los mensajes.
     * @throws RemoteException Si ocurre un error de red.
     */
    public void consumir(String nombreConsumidor, Callback metodoCallback, String nombreCola) throws RemoteException;

}