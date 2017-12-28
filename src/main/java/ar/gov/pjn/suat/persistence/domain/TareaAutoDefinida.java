package ar.gov.pjn.suat.persistence.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("DEFINE_TAREA")
@Table(name="TAREA_AUTO_DEFINIDA")
public class TareaAutoDefinida extends Tarea {

	private String className;

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
