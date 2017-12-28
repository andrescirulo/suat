package ar.gov.pjn.suat.persistence.domain;

import java.util.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.quartz.JobKey;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="TIPO_TAREA")
@Table(name="TAREA")
public abstract class Tarea {
	
	@Id @GeneratedValue
	private Long id;
	
	private String nombre;
	
	private String grupo;
	
	private String fuero;
	
	private Boolean pausable;
	
	private String endpoint;
	
	private TipoTareaEnum tipo;
	
	private Boolean activa;
	
	private Date ultimaEjecucion;
	
	private Date proximaEjecucion;

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public Boolean getPausable() {
		return pausable;
	}

	public void setPausable(Boolean pausable) {
		this.pausable = pausable;
	}

	public String getFuero() {
		return fuero;
	}

	public void setFuero(String fuero) {
		this.fuero = fuero;
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

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	@JsonIgnore
	public JobKey getJobKey() {
		return new JobKey(getNombre() + "Job", getGrupo() + "_" + getFuero() + "Group");
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getActiva() {
		return activa;
	}

	public void setActiva(Boolean activa) {
		this.activa = activa;
	}

	public Date getUltimaEjecucion() {
		return ultimaEjecucion;
	}

	public void setUltimaEjecucion(Date ultimaEjecucion) {
		this.ultimaEjecucion = ultimaEjecucion;
	}

	public Date getProximaEjecucion() {
		return proximaEjecucion;
	}

	public void setProximaEjecucion(Date proximaEjecucion) {
		this.proximaEjecucion = proximaEjecucion;
	}
}
