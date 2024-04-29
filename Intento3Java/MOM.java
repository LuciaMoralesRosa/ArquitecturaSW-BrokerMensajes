package Intento3Java;

import java.rmi.RemoteException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MOM implements MOMInterface{

	// Definicion de la constante cincoMinutos con el valor de 5' en segundos.
	private final int cincoMinutos = 300000;
	private Time tiempo;

	//Atributos privados de la clase
	private Map<String, Queue<Msj>> listaColas;
	private Map<String, String> listaInvocadores;
	private Map<String, Queue<ObjConsumidor>> listaConsumidoresCola;

	MOM(){
		listaColas = new HashMap<String, Queue<Msj>>();
        listaInvocadores = new HashMap<String, String>();
		listaConsumidoresCola = new HashMap<String, Queue<ObjConsumidor>>();

		tiempo = new Time(0);
        Timer timer = new Timer();
        TimerTask tarea = new TimerTask() {
            public void run(){
                eliminarCaducadas();
            }    
        };    
        timer.schedule(tarea, cincoMinutos, cincoMinutos);
	}

	// Metodos privados de la clase

	private void ordenarColaMensajes(Queue<Msj> cola) {
        // Construimos la cola auxiliar
        Queue<Msj> colaAuxiliar = new LinkedList<Msj> ();

        // Vaciamos la cola y añadimos los elementos en orden de prioridad en la auxiliar
        while(!cola.isEmpty()){
            Msj mayorObjeto = null;
            Integer prioridadMayor= 0;
            for(Msj objeto : cola){
                if(objeto.getPrioridad() > prioridadMayor){
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
        if(!listaColas.isEmpty()){
            long momentoEntrada;
            long momentoActual = tiempo.getTime();

			Set<String> keys = listaColas.keySet();
			for(String nombreCola :  keys){
				Queue<Msj> cola = listaColas.get(nombreCola);
				// Recorremos todos los objetos de la cola del mapa
				for(Msj objeto : cola){
					momentoEntrada = objeto.getCaducidad();
					if((momentoActual - momentoEntrada) >= cincoMinutos){
						//El mensaje ha caducado
						cola.remove(objeto);
					} 
				}
			}
        }
    }

	private Boolean laColaExiste(String nombreCola){
		Set<String> colasExistentes = listaColas.keySet();
		return colasExistentes.contains(nombreCola);
	}

	private Boolean elInvocadorExiste(String nombreInvocador){
		Set<String> invocadoresExistentes = listaInvocadores.keySet();
		return invocadoresExistentes.contains(nombreInvocador);
	}

	/*
	 * Comprueba que el invocador existe y ha declarado la cola dada
	 */
	private Boolean elInvocadorHaDeclaradoLaCola(String nombrePublicador, String nombreCola){
		Boolean res = false;
		Boolean invocadorExistente = elInvocadorExiste(nombrePublicador);
		if(invocadorExistente){
			String nombreColaDeclarada = listaInvocadores.get(nombrePublicador);
			res = nombreColaDeclarada.equals(nombreCola);	
		}
		return res;
	}

	// Metodos publicos de la clase

	@Override
	public void declararCola(String nombreCola, String invocador) {
		// Comprobamos si el invocador ya ha declarado una cola previamente
		Boolean invocadorTieneColaDeclarada = elInvocadorExiste(invocador);
		if(invocadorTieneColaDeclarada){
			System.out.println("[+] El invocador ya tiene una cola declarada");
		}
		else{
			// Añadimos el invocador a la lista de invocadores con la cola declarada
			listaInvocadores.put(invocador, nombreCola);

			// Comprobamos si la cola existe previamente
			Boolean colaExiste = laColaExiste(nombreCola);
			if(colaExiste){
				System.out.println("[+] La cola " + nombreCola + " ya habia sido declarada");
			}
			else{
				// Si la cola no existe, la declaramos y añadimos a la lista de colas
				Queue<Msj> nuevaCola = new LinkedList<Msj>();
				listaColas.put(nombreCola, nuevaCola);
			}
		}
	}

	@Override
	public void publicar(String nombrePublicador, String nombreCola, String mensaje, Integer prioridad) {
		//Comprobar si existe la cola con el nombre dado
		Boolean colaExiste = laColaExiste(nombreCola);
        
		if(colaExiste){
			// Comprobar si el publicador existe y ha declarado la cola
			Boolean colaDeclaradaPorPublicador = elInvocadorHaDeclaradoLaCola(nombrePublicador, nombreCola);
			if(colaDeclaradaPorPublicador){
				// Obtener la cola destino
				Queue<Msj> colaDestino = listaColas.get(nombreCola);
				// Creacion del mensaje a insertar e insercion en la cola
				Msj msj = new Msj(prioridad, tiempo.getTime(), mensaje);
				colaDestino.add(msj);
				// Ordenar la cola segun las prioridades de los mensajes
				ordenarColaMensajes(colaDestino);
			}
			else{
				System.out.println("[+] La cola especificada no ha sido declarada por el publicador");
				System.out.println("[+] El mensaje se ha perdido");
			}
        }
        else{
            System.out.println("[+] La cola especificada por el productor no existe");
			System.out.println("[+] El mensaje se ha perdido");
        }
	}

	@Override
	public void consumir(String nombreConsumidor, Callback metodoCallback, String nombreCola) throws RemoteException {
		//Comprobar si existe la cola con el nombre dado
		Boolean colaExiste = laColaExiste(nombreCola);
				
		if(colaExiste){
			// Comprobar si el publicador existe y ha declarado la cola
			Boolean colaDeclaradaPorConsumidor = elInvocadorHaDeclaradoLaCola(nombreConsumidor, nombreCola);
			if(colaDeclaradaPorConsumidor){
				// Creamos un objeto consumidor y guardamos su funcion de callback
				ObjConsumidor consumidor = new ObjConsumidor(nombreConsumidor, metodoCallback);
				
				// Comprobamos si la cola ya tiene una lista de consumidores
				Boolean laColaTieneConsumidores = listaConsumidoresCola.containsKey(nombreCola);

				if(laColaTieneConsumidores){

				}
				else{
					List<ObjConsumidor> listaConsumidores = new ArrayList<ObjConsumidor>();

				}

				// Añadimos el consumidor a la cola
			}
		
		
		
		
		if(colaExiste && consumidorSuscrito){
			// Comprobar que el consumidor dado esta suscrito a la cola dada
			String colaConsumidorSuscrito = listaInvocadores.get(consumidorSuscrito);
			Boolean haDeclaradoLaCola = colaConsumidorSuscrito.equals(nombreCola);

			// Si está suscrito a la cola, obtiene el mensaje de mayor prioridad
			if(haDeclaradoLaCola){
				Queue<Msj> listaMensajes = listaColas.get(nombreCola);
				Msj mensaje = listaMensajes.poll();
				moduloCallback.ejecutarMsj(mensaje.getMensaje());
			}
			else{
				System.out.println("[+] El consumidor no esta suscrito a la cola dada");
			}
		}		
	}

	
}
