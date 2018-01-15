package ar.gov.pjn.suat.persistence.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EJECUCION_TAREA")
public class EjecucionTarea {

	@Id @GeneratedValue
	private Long id;

	private String tareaNombre;

	private Grupo tareaGrupo;

	private Fuero tareaFuero;

	private Date fechaInicio;

	private Date fechaFin;
	
	private Boolean inmediata=false;
	
	private Boolean error=false;
	
	private Boolean advertencia=false;
	
	@Column(length=1024)
	private String mensaje;
	
	@Column(length=1024)
	private String stackTrace;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTareaNombre() {
		return tareaNombre;
	}

	public void setTareaNombre(String tareaNombre) {
		this.tareaNombre = tareaNombre;
	}

	public Grupo getTareaGrupo() {
		return tareaGrupo;
	}

	public void setTareaGrupo(Grupo tareaGrupo) {
		this.tareaGrupo = tareaGrupo;
	}

	public Fuero getTareaFuero() {
		return tareaFuero;
	}

	public void setTareaFuero(Fuero tareaFuero) {
		this.tareaFuero = tareaFuero;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Boolean getInmediata() {
		return inmediata;
	}

	public void setInmediata(Boolean inmediata) {
		this.inmediata = inmediata;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public Boolean getAdvertencia() {
		return advertencia;
	}

	public void setAdvertencia(Boolean advertencia) {
		this.advertencia = advertencia;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}
	
	
}
