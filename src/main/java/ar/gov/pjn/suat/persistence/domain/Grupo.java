package ar.gov.pjn.suat.persistence.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.ws.rs.FormParam;

@Entity
@Table(name="GRUPO")
public class Grupo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3460977231395842938L;
	
	@Id @GeneratedValue
	@FormParam(value = "id")
	private Long id;
	
	@FormParam(value = "codigo")
	private String codigo;
	
	@FormParam(value = "descripcion")
	private String descripcion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
