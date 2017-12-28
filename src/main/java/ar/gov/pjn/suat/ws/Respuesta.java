package ar.gov.pjn.suat.ws;

public class Respuesta {
	private Boolean resultado;
	private String mensaje;

	public Respuesta() {
	}

	public Respuesta(Boolean res,String msg) {
		mensaje = msg;
		resultado=res;
	}
	public Respuesta(String msg) {
		mensaje = msg;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Boolean getResultado() {
		return resultado;
	}

	public void setResultado(Boolean resultado) {
		this.resultado = resultado;
	}
}
