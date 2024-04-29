package Intento3Java;

import java.rmi.RemoteException;
import java.sql.Time;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MOM_FirstTry implements MOMInterface{

	// Definicion de la constante cincoMinutos con el valor de 5' en segundos.
	private final int cincoMinutos = 300000;
	private Time tiempo;

	//Atributos privados de la clase
	private Map<String, Queue<Msj>> listaColas;
	private Map<String, String> listaInvocadores;

	
	public MOM_FirstTry() {
		listaColas = new HashMap<String, Queue<Msj>>();
        listaInvocadores = new HashMap<String, String>();

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

	private void ordenarCola(Queue<Msj> cola) {
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

	private Boolean elConsumidorExiste(String nombreConsumidor){
		Set<String> invocadoresExistentes = listaInvocadores.keySet();
		return invocadoresExistentes.contains(nombreConsumidor);
	}


	// Metodos publicos de la clase

	@Override
	public void declararCola(String nombreCola, String invocador) {
		Set<String> invocadores = listaInvocadores.keySet();
		Boolean yaTieneColaDeclarada = invocadores.contains(invocador);
		if(yaTieneColaDeclarada){
			System.out.println("[+] El invocador ya tiene una cola declarada");
		}
		else{
			listaInvocadores.put(invocador, nombreCola);
			Boolean colaExiste = laColaExiste(nombreCola);
			if(colaExiste){
				System.out.println("[+] La cola " + nombreCola + " ya habia sido declarada");
			}
			else{
				Queue<Msj> nuevaCola = new LinkedList<Msj>();
				listaColas.put(nombreCola, nuevaCola);
			}
		}
	}


	@Override
	public void publicar(String nombreCola, String mensaje, Integer prioridad) {

		//Comprobar si existe la cola con el nombre dado
		Boolean colaExiste = laColaExiste(nombreCola);
        
		if(colaExiste){
			// Obtener la cola destino
			Queue<Msj> colaDestino = listaColas.get(nombreCola);

			// Creacion del mensaje a insertar e insercion en la cola
			Msj msj = new Msj(prioridad, tiempo.getTime(), mensaje);
			colaDestino.add(msj);

			// Ordenar la cola segun las prioridades de los mensajes
			ordenarCola(colaDestino);
        }
        else{
            System.out.println("[+] La cola especificada por el productor no existe");
			System.out.println("[+] El mensaje se ha perdido");
        }
	}

	@Override
	public void consumir(String nombreConsumidor, Callback moduloCallback, String nombreCola) throws RemoteException {
		// Comprobar si la cola existe
		Boolean colaExiste = laColaExiste(nombreCola);
		Boolean consumidorSuscrito = elConsumidorExiste(nombreConsumidor);
		
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




	public static void main(String[] args) {
		
	}
	
}
