package Intento3Java;

public class ObjConsumidor {
	String nombreConsumidor;
	Callback metodoCallback;

	
	public ObjConsumidor(String nombreConsumidor, Callback metodoCallback) {
		this.nombreConsumidor = nombreConsumidor;
		this.metodoCallback = metodoCallback;
	}

	
}
