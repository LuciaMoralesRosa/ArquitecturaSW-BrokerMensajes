import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 * 
 * Fichero: Productor.java
 * Comentarios: Implementa el productor. ******************************************************************************/

/** 
 * Clase que representa un consumidor en el sistema MOM
*/
public class Productor extends UnicastRemoteObject{
    static String miNombre;

    protected Productor() throws RemoteException {
        super();
    }
	
    // Metodos privados de la clase
	/**
	 * Muestra las opciones de instrucciones disponibles y espera la elección del
	 * usuario.
	 * 
	 * @param scanner Scanner para leer la entrada del usuario.
	 * @return Cadena que representa la opción elegida por el usuario.
	 */
	private static String intruccionAEjecutar(Scanner scanner) {
		System.out.println("Escriba el numero de la instruccion que desea realizar: \n" +
				"1. Declarar una cola\n" +
				"2. Publicar en una cola");
		return scanner.nextLine();
	}

    private static void declararCola(Scanner scanner, MOMInterface mom) throws RemoteException {
        System.out.println("Escriba el nombre de la cola que quiere declarar:  ");
        String nombreCola = scanner.nextLine();
        mom.declararCola(nombreCola, miNombre);
        System.out.println("[+] Se ha declarado la cola\n");
    }

    private static void publicar(Scanner scanner, MOMInterface mom) throws RemoteException {

        System.out.println("Escriba el nombre de la cola en la que quiere publicar:  ");
        String nombreCola = scanner.nextLine();

        System.out.println("Escriba el mensaje que quiere publicar:  ");
        String mensaje = scanner.nextLine();

        System.out.println("Indique la prioridad del mensaje, siendo 1 el más urgente:  ");
        String prioridad = scanner.nextLine();

        mom.publicar(miNombre, nombreCola, mensaje, Integer.parseInt(prioridad));
        System.out.println("[+] Se ha enviado el mensaje a la cola\n");
    }

    public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		String nombreProductor = "/MiProductor7781";
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

			System.out.println("Introduzca el nombre del productor: ");
			respuesta = scanner.nextLine();
			miNombre = "//" + host + "/MiProductor7781" + respuesta;
			nombreProductor = nombreProductor + respuesta;

			// Crear una instancia del serivdor
			Productor obj = new Productor();
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
						publicar(scanner, mom);
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
