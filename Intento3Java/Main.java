package Intento3Java;

import java.rmi.Naming;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Set<Entry<String, Queue<Msj>>> setListaColas;

		Scanner scanner = new Scanner(System.in);
		// Fijar el directorio donde se encuentra el java.policy
		// El segundo argumento es la ruta al java.policy
		System.setProperty("java.security.policy", "./java.policy");

		// Crear administrador de seguridad
        System.setSecurityManager(new SecurityManager());

        MOM objeto = new MOM();
		try {
			String host = "155.210.154.";
			System.out.println("Complete su IP 155.210.154.XXX:xxxxx: ");
			// host = host + scanner.nextLine();
			host = host + "196:32008";
			Naming.rebind("//" + host + "/MiMOM_7781", objeto);
			System.out.println("[+] Se ha registrado el objeto remoto");

		} catch (Exception ex) {
			System.out.println(ex);
		}

		while (true) {
			setListaColas = listaColas.entrySet();
			for (Entry<String, Queue<Msj>> entrada : setListaColas) {
				String nombreCola = entrada.getKey();
				Queue<Msj> colaDeMensajas = entrada.getValue();

				if (!colaDeMensajas.isEmpty()) {
					// Obtener primer consumidor de la cola "nombreCola"
					Queue<ObjConsumidor> listaConsumidores = listaConsumidoresCola.get(nombreCola);
					ObjConsumidor primerConsumidor = listaConsumidores.poll();
					// Devolvemos el consumidor a la cola en la ultima posicion
					listaConsumidores.add(primerConsumidor);

					// Obtener mensaje a enviar y borrarlo de la cola
					Msj msj = colaDeMensajas.poll();
					String mensajeAEnviar = msj.getMensaje();

					// Enviar el mensaje
					primerConsumidor.metodoCallback.ejecutarMsj(mensajeAEnviar);
				}
			}
		}
    }
}
