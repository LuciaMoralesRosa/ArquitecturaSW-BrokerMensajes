import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 * 
 * Fichero: Comsumidor.java
 * Comentarios: Es el fichero correspondiente a la función implementación del consumidor.
 ******************************************************************************/

/**
 * Clase que representa un Consumidor en un sistema de mensajería.
 * Extiende UnicastRemoteObject para permitir la comunicación remota.
 */
public class Consumidor extends UnicastRemoteObject {

 // Callback utilizado por el consumidor
	static Callback moduloCallback;
 // Nombre del consumidor
	static String miNombre;

/**
     * Constructor de la clase Consumidor.
     * Inicializa el Callback y llama al constructor de UnicastRemoteObject.
     *
     * @throws RemoteException Si ocurre un error durante la inicialización remota.
     */
	public Consumidor() throws RemoteException {
		super();
		moduloCallback = new Callback();
	}
	
	// Métodos privados de la clase

    /**
     * Muestra las opciones de instrucciones disponibles y espera la elección del usuario.
     *
     * @param scanner Scanner para leer la entrada del usuario.
     * @return Cadena que representa la opción elegida por el usuario.
     */
	private static String intruccionAEjecutar(Scanner scanner) {
		System.out.println("Escriba el numero de la instruccion que desea realizar: \n" +
				"1. Declarar una cola\n" +
				"2. Consumir de una cola");
		return scanner.nextLine();
	}

	/**
 * Declara una cola en el servidor MOM.
 *
 * @param scanner Scanner para leer la entrada del usuario.
 * @param mom Interfaz del servidor MOM para realizar la declaración de la cola.
 * @throws RemoteException Si ocurre un error durante la comunicación remota.
 */
private static void declararCola(Scanner scanner, MOMInterface mom) throws RemoteException {

    System.out.println("Escriba el nombre de la cola que quiere declarar:  ");
    String nombreCola = scanner.nextLine();
    mom.declararCola(nombreCola, miNombre);
    System.out.println("[+] Se ha declarado la cola\n");
}

/**
 * Consumir mensajes de una cola específica en el servidor MOM.
 *
 * @param scanner Scanner para leer la entrada del usuario.
 * @param mom Interfaz del servidor MOM para consumir mensajes de la cola.
 * @throws RemoteException Si ocurre un error durante la comunicación remota.
 */
private static void consumir(Scanner scanner, MOMInterface mom) throws RemoteException {
    System.out.println("Escriba el nombre de la cola que quiere consumir:  ");
    String nombreCola = scanner.nextLine();
    mom.consumir(miNombre, moduloCallback, nombreCola);
    System.out.println("[+] Se esta consumiendo la cola " + nombreCola + "\n");
}

/** 
 * Función principal de Consumidor
 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String nombreConsumidor = "/MiConsumidor7781";
		String respuesta;
		
		// Fijar el directorio donde se encuentra el java.policy
		// El segundo argumento es la ruta al java.policy
		System.setProperty("java.security.policy", "./java.policy");

		// Crear administrador de seguridad
		System.setSecurityManager(new SecurityManager());

		try {
			// Obtener IP del servidor
			String host = "155.210.154.";
			System.out.println("Complete su IP 155.210.154.XXX:XXXXX: ");
			host = host + scanner.nextLine();
			//host = host + "201:32008";

			// Obtener IP del broker
			String hostMOM = "155.210.154.";
			System.out.println("Complete la IP del MOM 155.210.154.XXX:XXXXX: ");
			hostMOM = hostMOM + scanner.nextLine();
			// hostMOM = hostMOM + "200:32009";

			System.out.println("Introduzca el nombre del consumidor: ");
			respuesta = scanner.nextLine();
			miNombre = "//" + host + "/MiConsumidor7781" + respuesta;
			nombreConsumidor = nombreConsumidor + respuesta;

			// Crear una instancia del serivdor
			Consumidor obj = new Consumidor();
			// Registro de la instancia en RMI registry
			Naming.rebind(miNombre, obj);
			System.out.println("[ * ] Se ha registrado el objeto en RMI");

			// Busqueda del broker en RMI registry
			MOMInterface mom = (MOMInterface) Naming.lookup("//" + hostMOM + "/MiMOM_7781");
			System.out.println("[ * ] Se ha establecido la conexion con el broker");

			// Ejecucion de un bucle infinito para realizar las instrucciones
			while (true) {
				respuesta = intruccionAEjecutar(scanner);
				switch (respuesta) {
					case "1":
						declararCola(scanner, mom);
						break;

					case "2":
						consumir(scanner, mom);
						break;
					default:
						break;
				}
			}

		} catch (Exception ex) {
			System.out.println(ex);
		}

	}

	
}
