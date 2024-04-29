
class Msj:
    def __init__(self, caducidad, mensaje):
        self.caducidad = caducidad
        self.mensaje = mensaje

class MOM(MOMInterface):
    #<Prioridad, Msj>
    colaMensajes = PriorityQueue()
    listaDeColas = []
    listaDeConsumidores = []

    def __init__(self):

    def declararCola(nombreCola, invocador):
        miSetDeConsumidores = set(listaDeConsumidores)
        existe = invocador in listaDeConsumidores
        if existe 
        listaDeConsumidores.append(invocador, nombreCola)

    
