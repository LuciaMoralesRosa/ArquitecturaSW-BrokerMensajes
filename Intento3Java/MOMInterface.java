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
 * Definición de la interfaz remota MOMInterface
 */
 public interface MOMInterface extends Remote {

    // Métodos de la interfaz remota

    //declararCola
    public void declararCola(String nombreCola, String invocador) throws RemoteException;

    //publicar
    public void publicar(String nombrePublicador, String nombreCola, String mensaje, Integer prioridad) 
            throws RemoteException;

    //consumir
    public void consumir(String nombreConsumidor, Callback metodoCallback, String nombreCola) throws RemoteException;

}