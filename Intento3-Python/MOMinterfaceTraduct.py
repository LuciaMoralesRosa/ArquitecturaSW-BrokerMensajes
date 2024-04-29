import rpc

# Autores: Lizer Bernad Ferrando, 779035
# Lucia Morales Rosa, 816906
#
# Fichero: MOMInterface.py
# Comentarios: Es el fichero correspondiente a la interfaz para clientes del 
# broker.

class MOMInterface(rpc.Remote):
    """Definición de la interfaz remota MOMInterface"""

    # Métodos de la interfaz remota

    def declararCola(self, nombre):
        """declararCola"""
        pass

    def publicar(self, nombreCola, mensaje, prioridad):
        """publicar"""
        pass

    def consumir(self, nombreConsumidor, hostRemoto):
        """consumir"""
        pass

    def suscribirse(self, nombreCola, nombreConsumidor):
        """suscribirse"""
        pass

