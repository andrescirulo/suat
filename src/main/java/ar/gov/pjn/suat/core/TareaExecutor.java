package ar.gov.pjn.suat.core;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.persistence.EntityTransaction;
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
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.gov.pjn.suat.persistence.dao.EjecucionTareaDAO;
import ar.gov.pjn.suat.persistence.dao.TareaDAO;
import ar.gov.pjn.suat.persistence.domain.EjecucionTarea;
import ar.gov.pjn.suat.persistence.domain.Tarea;

public class TareaExecutor implements Job {

	private final static Log LOG = LogFactory.getLog(TareaExecutor.class);
	private TareaDAO tareaDao;
	private EjecucionTareaDAO ejecucionTareaDao;

	public TareaExecutor() {
		tareaDao=TareaDAO.getInstance();
		ejecucionTareaDao=EjecucionTareaDAO.getInstance();
	}
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		EntityTransaction transaction = SUATTareaProcessor.getInstance().getEntityManager().getTransaction();
		Context.setTransaction(transaction);
		transaction.begin();
		executeNow(context.getMergedJobDataMap());
		transaction.commit();
	}

	public void executeNow(JobDataMap map) {
		
		Tarea tarea = (Tarea) map.get("tarea");
		EjecucionTarea et=new EjecucionTarea();
		et.setFechaInicio(new Date());
		et.setTareaNombre(tarea.getNombre());
		et.setTareaGrupo(tarea.getGrupo());
		et.setTareaFuero(tarea.getFuero());
		et.setInmediata(map.get("inmediata")!=null && map.getBoolean("inmediata"));

		try {
			Client client = ClientBuilder.newClient();
			WebTarget webTarget = client.target(tarea.getEndpoint());
			Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

			String data = "";
			Response response = invocationBuilder.post(Entity.entity(data, MediaType.APPLICATION_JSON));
			if (response.getStatus()==HttpServletResponse.SC_OK) {
				String responseEntity = (String) response.getEntity();
				ObjectMapper mapper = new ObjectMapper();
				RespuestaEjecucion resp = mapper.readValue(responseEntity, RespuestaEjecucion.class);
				et.setAdvertencia(resp.getConAdvertencias());
				et.setError(resp.getConErrores());
				et.setMensaje(resp.getMensaje());
				et.setFechaFin(new Date());
			}
			else {
				throw new RuntimeException(response.getStatusInfo().getStatusCode() + " - " + response.getStatusInfo().getReasonPhrase());
			}
		} catch (Exception e) {
			LOG.error("Error al ejecutar la tarea " + tarea.getNombre(), e);
			et.setMensaje("Error al ejecutar la tarea");
			et.setError(true);
			et.setFechaFin(new Date());
			StringWriter sw = new StringWriter(1024);
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.close();
			et.setStackTrace(sw.toString());
		}
		finally {
			ejecucionTareaDao.save(et);
			tareaDao.actualizarEjecucion(tarea,et.getFechaInicio());
		}
	}

}
