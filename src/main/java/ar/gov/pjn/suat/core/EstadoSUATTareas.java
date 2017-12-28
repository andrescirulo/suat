package ar.gov.pjn.suat.core;

import java.io.Serializable;

public class EstadoSUATTareas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1313436488067899482L;
	private static final String STR_ESTADO_INICIADO = "Iniciado";
	private static final String STR_ESTADO_DETENIDO = "Detenido";
	private static final String STR_ESTADO_PAUSADO = "Pausado";
	
	private String detalle = "";

	private boolean iniciado = false;

	public EstadoSUATTareas() {
		this.detalle = STR_ESTADO_DETENIDO;
	}

	public EstadoSUATTareas(String detalle) {
		super();
		this.detalle = detalle;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setIniciado(){
		detalle = STR_ESTADO_INICIADO;
		iniciado = true;
	}

	public void setDetenido(){
		detalle = STR_ESTADO_DETENIDO;
		iniciado = false;
	}

	public boolean isActive() {
		return iniciado;
	}

	public void setPausado() {
		detalle = STR_ESTADO_PAUSADO;
	}
}
