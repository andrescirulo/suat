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

import ar.gov.pjn.suat.persistence.dao.GrupoDAO;
import ar.gov.pjn.suat.persistence.domain.Grupo;
import ar.gov.pjn.suat.transformers.GrupoTransformer;

@Path("/grupos")
public class GruposGUIWs {

	private static Log LOG=LogFactory.getLog(GruposGUIWs.class); 
	
	private GrupoDAO grupoDao;
	
	public GruposGUIWs() {
		grupoDao=GrupoDAO.getInstance();
	}
	
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public List<Grupo> getGrupos() {
		return grupoDao.getAll();
	}
	
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public Respuesta add(@BeanParam Grupo grupo) {
		try {
			if (grupo.getId()==null) {
				grupoDao.save(grupo);
				return new Respuesta(true,"Grupo creado correctamente");
			}
			else {
				return new Respuesta(false,"El campo ID no puede venir especificado");
			}
			
		} catch (Exception e) {
			LOG.error("Error al crear el grupo",e);
			return new Respuesta(false,"Error al crear el grupo");
		}
	}
	
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@PATCH
	public Respuesta update(@BeanParam Grupo grupo) {
		try {
			Grupo g=null;
			if (grupo.getId()==null) {
				return new Respuesta(false,"Se debe especificar el ID del fuero");
			}
			else {
				g=grupoDao.getById(grupo.getId());
				g=GrupoTransformer.updateFromInput(grupo,g);
				grupoDao.save(g);
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
			Grupo grupo=null;
			if (id==null) {
				return new Respuesta(false,"Se debe especificar el ID del grupo");
			}
			else {
				grupo=grupoDao.getById(id);
				grupoDao.delete(grupo);
				return new Respuesta(true,"Grupo eliminado correctamente");
			}
		} catch (NoResultException e) {
			throw new NotFoundException("El grupo especificado no existe");
		} catch (Exception e) {
			LOG.error("Error al eliminar el grupo",e);
			return new Respuesta(false,"Error al eliminar el grupo");
		}
	}
	
}
