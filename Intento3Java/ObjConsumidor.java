/*******************************************************************************
 * Autores: Lizer Bernad Ferrando, 779035
 * Lucia Morales Rosa, 816906
 * 
 * Fichero: ObjComsumidor.java
 * Comentarios: Implementa el objeto ObjComsumidor. ******************************************************************************/
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
