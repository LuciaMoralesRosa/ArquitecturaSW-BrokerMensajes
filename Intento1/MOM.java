import java.rmi.RemoteException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class MOM implements MOMInterface{

    private final int cincoMinutos = 300000;
    private List<Map<String, Queue<Objeto>>> listaColas;
    private Map<String, String> listaConsumidores;
    private Time tiempo;

    private void ordenarCola(Queue<Objeto> cola) {
        // Construimos la cola auxiliar
        Queue<Objeto> colaAuxiliar = new LinkedList<Objeto> ();

        // Vaciamos la cola y añadimos los elementos en orden de prioridad en la auxiliar
        while(!cola.isEmpty()){
            Objeto mayorObjeto = null;
            Integer prioridadMayor= 0;
            for(Objeto objeto : cola){
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

            // Recorremos todos los mapas de la lista
            for(Map<String, Queue<Objeto>> mapa : listaColas){
                Set<String> keys = mapa.keySet();
                for(String nombreCola :  keys){
                    Queue<Objeto> cola = mapa.get(nombreCola);
                    // Recorremos todos los objetos de la cola del mapa
                    for(Objeto objeto : cola){
                        momentoEntrada = objeto.getMomentoEntrada();
                        if((momentoActual - momentoEntrada) <= cincoMinutos){
                            //El mensaje ha caducado
                            cola.remove(objeto);
                        } 
                    }
                }
            }
        }
    }


    public MOM() {
        listaColas = new ArrayList<Map<String, Queue<Objeto>>>();
        listaConsumidores = new HashMap<String, String>();

        tiempo = new Time(0);
        Timer timer = new Timer();
        TimerTask tarea = new TimerTask() {
            public void run(){
                eliminarCaducadas();
            }    
        };    
        timer.schedule(tarea, cincoMinutos, cincoMinutos);
    }    


    @Override
    public void declararCola(String nombre) {
        Queue<Objeto> nuevaCola = new LinkedList<Objeto> ();
        Map<String, Queue<Objeto>> mapa = new HashMap<String, Queue<Objeto>>();
        mapa.put(nombre, nuevaCola);
        listaColas.add(mapa);
    }

    

    @Override
    public void publicar(String nombreCola, Object mensaje, Integer prioridad) {
        Boolean laColaExiste = false;
        Queue<Objeto> colaDestino = new LinkedList<Objeto> ();

        //Comprobar caducidades
        
        //Comprobar si una cola con el nombre dado existe
        for(Map<String, Queue<Objeto>> cola : listaColas){
            if(cola.keySet().contains(nombreCola)){
                laColaExiste = true;
                colaDestino = cola.get(nombreCola);
                break;
            }
        }
        if(laColaExiste){
            Objeto objeto = new Objeto(prioridad, tiempo.getTime(), mensaje);
            colaDestino.add(objeto);
            
            //Reordenar la cola para ajustar las prioridades
            ordenarCola(colaDestino);
        }
        else{
            System.out.println("[+] La cola especificada por el productor no existe");
        }

    }

    @Override
    public void suscribirse(String nombreCola, String nombreConsumidor) throws RemoteException {
        if(listaConsumidores.containsKey(nombreConsumidor)){
            System.out.println("[+] El consumidor " + nombreConsumidor + " ya está suscrito a una cola");
        }
        else{
            System.out.println("[+] El consumidor " + nombreConsumidor + " se ha suscrito a la cola " + nombreCola);
            listaConsumidores.put(nombreConsumidor, nombreCola);
        }
    }

    @Override
    public void consumir(String nombreConsumidor, String hostRemoto) throws RemoteException {
        if(!listaConsumidores.containsKey(nombreConsumidor)){
            System.out.println("[+] El consumidor " + nombreConsumidor + " no está suscrito a una cola");
        }
        else{
            listaConsumidores.
        }
    }
    

    
}

