package ar.gov.pjn.suat.persistence.domain;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.gov.pjn.suat.core.TimeGetter;

@Entity
@DiscriminatorValue("DEFINE_TAREA")
@Table(name="TAREA_AUTO_DEFINIDA")
public class TareaAutoDefinida extends Tarea {

	private static Log LOG=LogFactory.getLog(TareaAutoDefinida.class);
	
	private String timeEndpoint;

	public String getTimeEndpoint() {
		return timeEndpoint;
	}

	public void setTimeEndpoint(String timeEndpoint) {
		this.timeEndpoint = timeEndpoint;
	}

	public Date consultarProximaEjecucion() {
		String endpoint = getTimeEndpoint();
		try {
			LocalDateTime nextTime = TimeGetter.getNextTime(getNombre(), endpoint);
			Date nextFire = Date.from(nextTime.atZone(ZoneId.systemDefault()).toInstant());
			return nextFire;
		} catch (Exception e) {
			LOG.error("Ocurrió un error al obtener el trigger de la tarea",e);
		}
		return null;
	}


}
