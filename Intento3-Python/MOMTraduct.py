import time
from collections import deque, defaultdict
from typing import Dict, List, Tuple

class Objeto:
    def __init__(self, prioridad: int, momento_entrada: float):
        self.prioridad = prioridad
        self.momento_entrada = momento_entrada

def ordenar_cola(cola: deque[Objeto]):
    # Construimos la cola auxiliar
    cola_auxiliar = deque()

    # Vaciamos la cola y añadimos los elementos en orden de prioridad en la auxiliar
    while cola:
        mayor_objeto = None
        prioridad_mayor = 0
        for objeto in cola:
            if objeto.prioridad > prioridad_mayor:
                prioridad_mayor = objeto.prioridad
                mayor_objeto = objeto
        cola_auxiliar.append(mayor_objeto)
        cola.remove(mayor_objeto)

    # Devolvemos los elementos a la cola original
    cola.extend(cola_auxiliar)

def eliminar_caducadas(lista_colas: List[Dict[str, deque[Objeto]]], tiempo_actual: float, cinco_minutos: float):
    # Comprobamos que la lista de colas no esta vacia
    if lista_colas:
        momento_entrada: float
        for mapa in lista_colas:
            for cola in mapa.values():
                # Recorremos todos los objetos de la cola del mapa
                for objeto in list(cola):
                    momento_entrada = objeto.momento_entrada
                    if (tiempo_actual - momento_entrada) <= cinco_minutos:
                        # El mensaje ha caducado
                        cola.remove(objeto)

class MOM:
    def __init__(self):
        self.lista_colas: List[Dict[str, deque[Objeto]]] = []
        self.lista_consumidores: Dict[str, str] = {}
        self.tiempo = time.time()
        self.cinco_minutos = 300

        self.timer = time.time()
        while True:
            if time.time() - self.timer >= self.cinco_minutos:
                self.timer = time.time()
                eliminar_caducadas(self.lista_colas, self.tiempo, self.cinco_minutos)

    def declarar_cola(self, nombre: str):
        nueva_cola = deque()
        mapa = {nombre: nueva_cola}
        self.lista_colas.append(mapa)

from collections import deque
from time import time

def publicar(nombre_cola, mensaje, prioridad):
    la_cola_existe = False
    cola_destino = deque()

    # Comprobar caducidades
    
    # Comprobar si una cola con el nombre dado existe
    for cola in lista_colas:
        if nombre_cola in cola:
            la_cola_existe = True
            cola_destino = cola[nombre_cola]
            break
    
    if la_cola_existe:
        objeto = {'prioridad': prioridad, 'tiempo': time(), 'mensaje': mensaje}
        cola_destino.append(objeto)
        
        # Reordenar la cola para ajustar las prioridades
        ordenar_cola(cola_destino)
    else:
        print("[+] La cola especificada por el productor no existe")

def suscribirse(nombre_cola, nombre_consumidor):
    if nombre_consumidor in lista_consumidores:
        print(f"[+] El consumidor {nombre_consumidor} ya está suscrito a una cola")
    else:
        print(f"[+] El consumidor {nombre_consumidor} se ha suscrito a la cola {nombre_cola}")
        lista_consumidores[nombre_consumidor] = nombre_cola

def ordenar_cola(cola):
    # Implementar la lógica para ordenar la cola según la prioridad
    pass

lista_colas = []
lista_consumidores = {}

