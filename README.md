# ArqSW-P4 - Implementación de un Broker de Mensajes

## Práctica 4 de la asignatura Arquitectura de Software

### Versión básica
Las funcionalidades del MOM serán muy parecidas a las del primer y segundo tutorial de RabbitMQ. Por tanto, el MOM permitirá que un programa productor escriba mensajes en una cola y que estos puedan ser leídos por un programa consumidor.

En esta versión, el MOM ofrecerá al menos los siguientes servicios:

- **Declarar cola (parámetros necesarios):** Al igual que en RabbitMQ, esta operación será idempotente. Es decir, aunque se invoque varias veces, el MOM creará una única cola con ese nombre. Por tanto, esta operación la usará tanto el productor como el consumidor. Se puede utilizar el patrón Singleton [1] para ello.
- **Publicar (parámetros necesarios):** Esta operación la usa el productor para publicar mensajes en una cola que previamente ha declarado.
- **Consumir (parámetros necesarios):** Esta operación la usa el consumidor para consumir mensajes de una cola previamente declarada.

**Consideraciones:**

- Si se publica un mensaje en una cola que no existe, el mensaje se pierde.
- El programa consumidor tendrá una operación de *callback* que el MOM llamará para consumir los mensajes publicados.
- Si al publicar un mensaje no hay un consumidor disponible, el mensaje permanecerá en la cola un máximo de **5 minutos**. Si en ese tiempo ningún consumidor se ha unido a la cola, el broker eliminará el mensaje.
- Si más de un consumidor se suscribe a la misma cola, el MOM asignará los mensajes con política *round-robin*, como en el tutorial 2 de RabbitMQ.

### Versiones avanzadas

- Implementar un programa que liste las colas existentes y que permita borrar colas.
- Implementar un mecanismo de reconocimiento de mensajes procesados, como en el tutorial 2 de RabbitMQ.
- Implementar política *fair dispatch*, como en el tutorial 2 de RabbitMQ.
- Implementar *message durability*, como en el tutorial 2 de RabbitMQ. Es decir, si el MOM se apaga, las colas creadas como persistentes se recuperarán cuando el MOM se reinicie, y los mensajes duraderos que permanecieran en esas colas también serán recuperados.
- Los programas productor y consumidor pueden estar en máquinas diferentes.
