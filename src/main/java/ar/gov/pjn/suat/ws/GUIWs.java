package ar.gov.pjn.suat.ws;

import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.BeanParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.gov.pjn.suat.core.EstadoSUATTareas;
import ar.gov.pjn.suat.core.SUATTareaProcessor;
import ar.gov.pjn.suat.persistence.FiltroTareas;
import ar.gov.pjn.suat.persistence.TareaDAO;
import ar.gov.pjn.suat.persistence.domain.Tarea;
import ar.gov.pjn.suat.transformers.TareaTransformer;
import ar.gov.pjn.suat.ws.domain.TareaWSInput;

@Path("/")
public class GUIWs {

	private static Log LOG=LogFactory.getLog(GUIWs.class); 
	
	private TareaDAO tareaDao;
	
	public GUIWs() {
		tareaDao=TareaDAO.getInstance();
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
	
	@Path("tareas/")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public List<Tarea> getTareas(@BeanParam FiltroTareas filtro) {
		return tareaDao.getTareas(filtro);
	}
	
	@Path("tareas/ejecutando")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public List<Tarea> getTareasEjecutando() {
		return SUATTareaProcessor.getInstance().getRunningJobs();
	}
	
	@Path("tareas/")
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public Respuesta add(@BeanParam TareaWSInput tarea) {
		try {
			Tarea t=null;
			if (tarea.getId()==null) {
				t=TareaTransformer.createFromInput(tarea);
			}
			else {
				return new Respuesta(false,"El campo ID no puede venir especificado");
			}
			tareaDao.save(t);
			return new Respuesta(true,"Tarea creada correctamente");
		} catch (Exception e) {
			LOG.error("Error al crear la tarea",e);
			return new Respuesta(false,"Error al crear la tarea");
		}
	}
	
	@Path("tareas/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@PATCH
	public Respuesta update(@BeanParam TareaWSInput tarea) {
		try {
			Tarea t=null;
			if (tarea.getId()==null) {
				return new Respuesta(false,"Se debe especificar el ID de la tarea");
			}
			else {
				t=tareaDao.getById(tarea.getId());
				t=TareaTransformer.updateFromInput(tarea,t);
			}
			tareaDao.save(t);
			return new Respuesta(true,"Tarea actualizada correctamente");
		} catch (NoResultException e) {
			throw new NotFoundException("La tarea especificada no existe");
		} catch (Exception e) {
			LOG.error("Error al actualizar la tarea",e);
			return new Respuesta(false,"Error al actualizar la tarea");
		}
	}
	
	@Path("tareas/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@DELETE
	public Respuesta delete(@PathParam("id") Long id) {
		try {
			Tarea t=null;
			if (id==null) {
				return new Respuesta(false,"Se debe especificar el ID de la tarea");
			}
			else {
				t=tareaDao.getById(id);
				tareaDao.delete(t);
				return new Respuesta(true,"Tarea eliminada correctamente");
			}
		} catch (NoResultException e) {
			throw new NotFoundException("La tarea especificada no existe");
		} catch (Exception e) {
			LOG.error("Error al eliminar la tarea",e);
			return new Respuesta(false,"Error al eliminar la tarea");
		}
	}
	
	@Path("tareas/{id}/run")
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public Respuesta add(@PathParam("id") Long id) {
		try {
			if (id==null) {
				return new Respuesta(false,"Se debe especificar el ID de la tarea");
			}
			else {
				Tarea t=tareaDao.getById(id);
				SUATTareaProcessor.getInstance().executeThisTaskNow(t);
				return new Respuesta(true,"Tarea ejecutada correctamente");
			}
		} catch (Exception e) {
			LOG.error("Error al crear la tarea",e);
			return new Respuesta(false,"Error al ejecutar la tarea");
		}
	}
	
}
