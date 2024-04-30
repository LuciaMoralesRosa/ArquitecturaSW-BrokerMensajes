import java.rmi.Naming;

/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 * 
 * Fichero: Main.java
 * Comentarios: Es el fichero correspondiente a la implementación clase Main del MOM.
 ******************************************************************************/

public class Main {
    public static void main(String[] args) {
		// Fijar el directorio donde se encuentra el java.policy
		// El segundo argumento es la ruta al java.policy
		System.setProperty("java.security.policy", "./java.policy");

		// Crear administrador de seguridad
        System.setSecurityManager(new SecurityManager());

		try {
            String host = "155.210.154.";
			System.out.println("Complete su IP 155.210.154.XXX:xxxxx: ");
			// host = host + scanner.nextLine();
			host = host + "196:32008";

			MOM objeto = new MOM();
			
			Naming.rebind("//" + host + "/MiMOM_7781", objeto);
			System.out.println("[+] Se ha registrado el objeto remoto");
            
            while (true) {
                objeto.contenidoDelWhile();
            }
		} catch (Exception ex) {
			System.out.println(ex);
		}

    }
}
