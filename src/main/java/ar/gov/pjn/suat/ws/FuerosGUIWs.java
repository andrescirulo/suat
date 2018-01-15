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
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.gov.pjn.suat.persistence.dao.FueroDAO;
import ar.gov.pjn.suat.persistence.domain.Fuero;
import ar.gov.pjn.suat.transformers.FueroTransformer;

@Path("/fueros")
public class FuerosGUIWs {

	private static Log LOG=LogFactory.getLog(FuerosGUIWs.class); 
	
	private FueroDAO fueroDao;
	
	public FuerosGUIWs() {
		fueroDao=FueroDAO.getInstance();
	}
	
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public List<Fuero> getFueros() {
		return fueroDao.getAll();
	}
	
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public Respuesta add(@BeanParam Fuero fuero) {
		try {
			if (fuero.getId()==null) {
				fueroDao.save(fuero);
				return new Respuesta(true,"Fuero creado correctamente");
			}
			else {
				return new Respuesta(false,"El campo ID no puede venir especificado");
			}
		} catch (Exception e) {
			LOG.error("Error al crear el fuero",e);
			return new Respuesta(false,"Error al crear el fuero");
		}
	}
	
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@PATCH
	public Respuesta update(@BeanParam Fuero fuero) {
		try {
			Fuero f=null;
			if (fuero.getId()==null) {
				return new Respuesta(false,"Se debe especificar el ID del fuero");
			}
			else {
				f=fueroDao.getById(fuero.getId());
				f=FueroTransformer.updateFromInput(fuero,f);
				fueroDao.save(f);
				return new Respuesta(true,"Fuero actualizado correctamente");
			}
		} catch (NoResultException e) {
			throw new NotFoundException("El fuero especificado no existe");
		} catch (Exception e) {
			LOG.error("Error al actualizar el fuero",e);
			return new Respuesta(false,"Error al actualizar el fuero");
		}
	}
	
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@DELETE
	public Respuesta delete(@PathParam("id") Long id) {
		try {
			Fuero fuero=null;
			if (id==null) {
				return new Respuesta(false,"Se debe especificar el ID del fuero");
			}
			else {
				fuero=fueroDao.getById(id);
				fueroDao.delete(fuero);
				return new Respuesta(true,"Fuero eliminado correctamente");
			}
		} catch (NoResultException e) {
			throw new NotFoundException("El fuero especificado no existe");
		} catch (Exception e) {
			LOG.error("Error al eliminar el fuero",e);
			return new Respuesta(false,"Error al eliminar el fuero");
		}
	}
	
}
