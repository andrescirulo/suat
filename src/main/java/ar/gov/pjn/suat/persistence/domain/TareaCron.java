package ar.gov.pjn.suat.persistence.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("CRON")
@Table(name="TAREA_CRON")
public class TareaCron extends Tarea {
	private String expresion;

	public String getExpresion() {
		return expresion;
	}

	public void setExpresion(String expresion) {
		this.expresion = expresion;
	}
}
