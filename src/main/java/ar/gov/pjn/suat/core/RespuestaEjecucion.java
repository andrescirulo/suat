package ar.gov.pjn.suat.core;

import java.io.Serializable;

public class RespuestaEjecucion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2897773795006605537L;

	private Boolean conErrores;
	private Boolean conAdvertencias;
	private String mensaje;

	public Boolean getConErrores() {
		return conErrores;
	}

	public void setConErrores(Boolean conErrores) {
		this.conErrores = conErrores;
	}

	public Boolean getConAdvertencias() {
		return conAdvertencias;
	}

	public void setConAdvertencias(Boolean conAdvertencias) {
		this.conAdvertencias = conAdvertencias;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
