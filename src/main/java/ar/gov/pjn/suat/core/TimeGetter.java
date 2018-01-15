package ar.gov.pjn.suat.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TimeGetter {
	
	private final static Log LOG = LogFactory.getLog(TimeGetter.class);
	
	public static LocalDateTime getNextTime(String nombreTarea,String endpoint) {
		
		try {
			Client client = ClientBuilder.newClient();
			WebTarget webTarget = client.target(endpoint);
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

			String data = "";
			Response response = invocationBuilder.post(Entity.entity(data, MediaType.APPLICATION_JSON));
			if (response.getStatus()==HttpServletResponse.SC_OK) {
				String responseEntity = (String) response.getEntity();
				LocalDateTime dateTime = LocalDateTime.parse(responseEntity,DateTimeFormatter.ISO_OFFSET_DATE_TIME);
				return dateTime;
			}
			else {
				throw new RuntimeException(response.getStatusInfo().getStatusCode() + " - " + response.getStatusInfo().getReasonPhrase());
			}
		} catch (Exception e) {
			LOG.error("Error al obtener el proximo disparo de la tarea " + nombreTarea, e);
			return null;
		}
	}
}
