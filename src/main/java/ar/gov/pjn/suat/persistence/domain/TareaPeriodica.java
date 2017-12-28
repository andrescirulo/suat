package ar.gov.pjn.suat.persistence.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.quartz.DateBuilder.IntervalUnit;

@Entity
@DiscriminatorValue("PERIODICA")
@Table(name="TAREA_PERIODICA")
public class TareaPeriodica extends Tarea {
	private IntervalUnit unidad;
	private Integer intervalo;

	public IntervalUnit getUnidad() {
		return unidad;
	}

	public void setUnidad(IntervalUnit unidad) {
		this.unidad = unidad;
	}

	public Integer getIntervalo() {
		return intervalo;
	}

	public void setIntervalo(Integer intervalo) {
		this.intervalo = intervalo;
	}
}
