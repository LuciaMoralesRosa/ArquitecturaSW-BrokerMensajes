public class ObjConsumidor {
	String nombreConsumidor;
	Callback metodoCallback;

	
	public ObjConsumidor(String nombreConsumidor, Callback metodoCallback) {
		this.nombreConsumidor = nombreConsumidor;
		this.metodoCallback = metodoCallback;
	}


	public String getNombreConsumidor() {
		return nombreConsumidor;
	}


	public void setNombreConsumidor(String nombreConsumidor) {
		this.nombreConsumidor = nombreConsumidor;
	}


	public Callback getMetodoCallback() {
		return metodoCallback;
	}


	public void setMetodoCallback(Callback metodoCallback) {
		this.metodoCallback = metodoCallback;
	}

	
}
