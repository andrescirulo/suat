package ar.gov.pjn.suat.ws.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.PathParam;

import ar.gov.pjn.suat.persistence.domain.TipoTareaEnum;

public class TareaWSInput implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3833304577887902366L;

	@PathParam("id")
	private Long id;

	@FormParam("tipo")
	@NotNull
	private TipoTareaEnum tipo;
	
	@FormParam("nombre")
	@NotNull
	private String nombre;

	@FormParam("grupo")
	@NotNull
	private Long grupo;

	@FormParam("fuero")
	@NotNull
	private Long fuero;

	@FormParam("pausable")
	@NotNull
	private Boolean pausable;
	
	@FormParam("activa")
	@NotNull
	private Boolean activa;

	@FormParam("endpoint")
	@NotNull
	private String endpoint;
	
	@FormParam("unidad")
	private IntervaloEnum unidad;
	
	@FormParam("intervalo")
	private Integer intervalo;
	
	@FormParam("timeEndpoint")
	private String timeEndpoint;
	
	@FormParam("expresion")
	private String expresion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoTareaEnum getTipo() {
		return tipo;
	}

	public void setTipo(TipoTareaEnum tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getGrupo() {
		return grupo;
	}

	public void setGrupo(Long grupo) {
		this.grupo = grupo;
	}

	public Long getFuero() {
		return fuero;
	}

	public void setFuero(Long fuero) {
		this.fuero = fuero;
	}

	public Boolean getPausable() {
		return pausable;
	}

	public void setPausable(Boolean pausable) {
		this.pausable = pausable;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public IntervaloEnum getUnidad() {
		return unidad;
	}

	public void setUnidad(IntervaloEnum unidad) {
		this.unidad = unidad;
	}

	public Integer getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Integer intervalo) {
		this.intervalo = intervalo;
	}

	public String getTimeEndpoint() {
		return timeEndpoint;
	}

	public void setTimeEndpoint(String timeEndpoint) {
		this.timeEndpoint = timeEndpoint;
	}

	public String getExpresion() {
		return expresion;
	}

	public void setExpresion(String expresion) {
		this.expresion = expresion;
	}

	public Boolean getActiva() {
		return activa;
	}

	public void setActiva(Boolean activa) {
		this.activa = activa;
	}
}
