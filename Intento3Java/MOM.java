import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Time;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MOM extends UnicastRemoteObject implements MOMInterface {

	// Definicion de la constante cincoMinutos con el valor de 5' en segundos.
	private final int cincoMinutos = 300000;
	private Time tiempo;

	// Atributos privados de la clase
	private static Map<String, Queue<Msj>> listaColas;
	private static Map<String, String> listaInvocadores;
	private static Map<String, Queue<ObjConsumidor>> listaConsumidoresCola;

	MOM() throws RemoteException {
		listaColas = new HashMap<String, Queue<Msj>>();
		listaInvocadores = new HashMap<String, String>();
		listaConsumidoresCola = new HashMap<String, Queue<ObjConsumidor>>();

		tiempo = new Time(0);
		Timer timer = new Timer();
		TimerTask tarea = new TimerTask() {
			public void run() {
				eliminarCaducadas();
			}
		};
		timer.schedule(tarea, cincoMinutos, cincoMinutos);
	}

	// Metodos privados de la clase

	private void ordenarColaMensajes(Queue<Msj> cola) {
		// Construimos la cola auxiliar
		Queue<Msj> colaAuxiliar = new LinkedList<Msj>();

		// Vaciamos la cola y añadimos los elementos en orden de prioridad en la
		// auxiliar
		while (!cola.isEmpty()) {
			Msj mayorObjeto = null;
			Integer prioridadMayor = 0;
			for (Msj objeto : cola) {
				if (objeto.getPrioridad() > prioridadMayor) {
					prioridadMayor = objeto.getPrioridad();
					mayorObjeto = objeto;
				}
			}
			colaAuxiliar.add(mayorObjeto);
			cola.remove(mayorObjeto);
		}

		// Devolvemos los elementos a la cola original
		cola.addAll(colaAuxiliar);
	}

	private void eliminarCaducadas() {
		// Comprobamos que la lista de colas no esta vacia
		if (!listaColas.isEmpty()) {
			long momentoEntrada;
			long momentoActual = tiempo.getTime();

			Set<String> keys = listaColas.keySet();
			for (String nombreCola : keys) {
				Queue<Msj> cola = listaColas.get(nombreCola);
				// Recorremos todos los objetos de la cola del mapa
				for (Msj objeto : cola) {
					momentoEntrada = objeto.getCaducidad();
					if ((momentoActual - momentoEntrada) >= cincoMinutos) {
						// El mensaje ha caducado
						cola.remove(objeto);
					}
				}
			}
		}
	}

	private Boolean laColaExiste(String nombreCola) {
		Set<String> colasExistentes = listaColas.keySet();
		return colasExistentes.contains(nombreCola);
	}

	private Boolean elInvocadorExiste(String nombreInvocador) {
		Set<String> invocadoresExistentes = listaInvocadores.keySet();
		return invocadoresExistentes.contains(nombreInvocador);
	}

	/*
	 * Comprueba que el invocador existe y ha declarado la cola dada
	 */
	private Boolean elInvocadorHaDeclaradoLaCola(String nombrePublicador, String nombreCola) {
		Boolean res = false;
		Boolean invocadorExistente = elInvocadorExiste(nombrePublicador);
		if (invocadorExistente) {
			String nombreColaDeclarada = listaInvocadores.get(nombrePublicador);
			res = nombreColaDeclarada.equals(nombreCola);
		}
		return res;
	}

	private Boolean consumidorEnCola(Queue<ObjConsumidor> consumidores, String nombreConsumidor) {
		for (ObjConsumidor consumidor : consumidores) {
			if (consumidor.getNombreConsumidor().equals(nombreConsumidor)) {
				return true;
			}
		}
		return false;
	}

	public void contenidoDelWhile() {
		Set<Entry<String, Queue<Msj>>> setListaColas = listaColas.entrySet();
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

	// Metodos publicos de la clase

	@Override
	public void declararCola(String nombreCola, String invocador) throws RemoteException {
		// Comprobamos si el invocador ya ha declarado una cola previamente
		Boolean invocadorTieneColaDeclarada = elInvocadorExiste(invocador);
		if (invocadorTieneColaDeclarada) {
			System.out.println("[+] El invocador ya tiene una cola declarada");
		} else {
			// Añadimos el invocador a la lista de invocadores con la cola declarada
			listaInvocadores.put(invocador, nombreCola);

			// Comprobamos si la cola existe previamente
			Boolean colaExiste = laColaExiste(nombreCola);
			if (colaExiste) {
				System.out.println("[+] La cola " + nombreCola + " ya habia sido declarada");
			} else {
				// Si la cola no existe, la declaramos y añadimos a la lista de colas
				Queue<Msj> nuevaCola = new LinkedList<Msj>();
				listaColas.put(nombreCola, nuevaCola);
			}
		}
	}

	@Override
	public void publicar(String nombrePublicador, String nombreCola, String mensaje, Integer prioridad)
			throws RemoteException {
		// Comprobar si existe la cola con el nombre dado
		Boolean colaExiste = laColaExiste(nombreCola);

		if (colaExiste) {
			// Comprobar si el publicador existe y ha declarado la cola
			Boolean colaDeclaradaPorPublicador = elInvocadorHaDeclaradoLaCola(nombrePublicador, nombreCola);
			if (colaDeclaradaPorPublicador) {
				// Obtener la cola destino
				Queue<Msj> colaDestino = listaColas.get(nombreCola);
				// Creacion del mensaje a insertar e insercion en la cola
				Msj msj = new Msj(prioridad, tiempo.getTime(), mensaje);
				colaDestino.add(msj);
				// Ordenar la cola segun las prioridades de los mensajes
				ordenarColaMensajes(colaDestino);
			} else {
				System.out.println("[+] La cola especificada no ha sido declarada por el publicador");
				System.out.println("[+] El mensaje se ha perdido");
			}
		} else {
			System.out.println("[+] La cola especificada por el productor no existe");
			System.out.println("[+] El mensaje se ha perdido");
		}
	}

	@Override
	public void consumir(String nombreConsumidor, Callback metodoCallback, String nombreCola) throws RemoteException {
		// Comprobar si existe la cola con el nombre dado
		Boolean colaExiste = laColaExiste(nombreCola);

		if (colaExiste) {
			// Comprobar si el publicador existe y ha declarado la cola
			Boolean colaDeclaradaPorConsumidor = elInvocadorHaDeclaradoLaCola(nombreConsumidor, nombreCola);
			if (colaDeclaradaPorConsumidor) {
				// Creamos un objeto consumidor y guardamos su funcion de callback
				ObjConsumidor consumidor = new ObjConsumidor(nombreConsumidor, metodoCallback);

				// Comprobamos si la cola ya tiene una lista de consumidores
				Boolean laColaTieneConsumidores = listaConsumidoresCola.containsKey(nombreCola);

				if (laColaTieneConsumidores) {
					// Si la cola ya tiene consumidores comprobamos que no está ya el consumidor
					Queue<ObjConsumidor> consumidores = listaConsumidoresCola.get(nombreCola);

					// Comprobamos si el consumidor esta en la cola
					Boolean consumidorEnCola = consumidorEnCola(consumidores, nombreConsumidor);
					if (consumidorEnCola) {
						System.out.println("[+] El consumidor ya esta en la lista de consumidores de la cola");
					} else {
						// Si no está ya en la lista lo añadimos al final
						consumidores.add(consumidor);
						System.out.println("[+] Se ha añadido el consumidor a la lista de consumidores de la cola");
					}
				} else {
					// Si no tiene consumidores se declara una cola
					Queue<ObjConsumidor> listaConsumidores = new LinkedList<ObjConsumidor>();
					// Añadimos el consumidor a la cola
					listaConsumidores.add(consumidor);
					// Añadimos el par <nombreCola, listaConsumidores> al mapa
					listaConsumidoresCola.put(nombreCola, listaConsumidores);
					System.out.println(
							"[+] Se ha creado la lista de consumidores de la cola y se ha añadido a ella el consumidor");
				}
			}
		}
	}

	/*public static void main(String[] args) {
		Set<Entry<String, Queue<Msj>>> setListaColas;

		Scanner scanner = new Scanner(System.in);
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

			System.out.println("Aqui");
			MOM objeto = new MOM();
			System.out.println("Aqui");
			Naming.rebind("//" + host + "/MiMOM_7781", objeto);
			System.out.println("Aqui");
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
	}*/

}
