package ar.gov.pjn.suat.ws;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ar.gov.pjn.suat.core.EstadoSUATTareas;
import ar.gov.pjn.suat.core.SUATTareaProcessor;

@Path("/")
public class GUIWs {

	public GUIWs() {
	}
	
	@Path("echo")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Respuesta echo(@QueryParam("nombre")String nombre) {
       return new Respuesta("Hola " + nombre);
    }
	
	@Path("status")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public EstadoSUATTareas status() {
		return SUATTareaProcessor.getInstance().getEstado();
	}
	
}
