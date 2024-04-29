package Intento3Java;

public class Msj {
	Integer prioridad;
	long caducidad;
	String mensaje;
	
	public Msj(Integer prioridad, long caducidad, String mensaje) {
		this.prioridad = prioridad;
		this.caducidad = caducidad;
		this.mensaje = mensaje;
	}

	public Integer getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(Integer prioridad) {
		this.prioridad = prioridad;
	}

	public long getCaducidad() {
		return caducidad;
	}

	public void setCaducidad(Integer caducidad) {
		this.caducidad = caducidad;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	
}
