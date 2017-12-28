package ar.gov.pjn.suat.persistence;

import java.io.Serializable;

import javax.ws.rs.QueryParam;

public class FiltroTareas implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 112121359988804390L;

	@QueryParam("id")
	private Long id;

	@QueryParam("activa")
	private Integer activa;

	@QueryParam("nombre")
	private String nombre;
	
	@QueryParam("fuero")
	private String fuero;

	@QueryParam("grupo")
	private String grupo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getActiva() {
		return activa;
	}

	public void setActiva(Integer activa) {
		this.activa = activa;
	}

	public String getFuero() {
		return fuero;
	}

	public void setFuero(String fuero) {
		this.fuero = fuero;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
