import java.io.Serializable;

public class Callback implements Serializable {
	public void ejecutarMsj(String mensaje){
		System.out.println("El mensaje recibido es: " + mensaje);
	}
}
