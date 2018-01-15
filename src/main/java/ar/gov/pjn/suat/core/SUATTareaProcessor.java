package ar.gov.pjn.suat.core;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import ar.gov.pjn.suat.persistence.FiltroTareas;
import ar.gov.pjn.suat.persistence.dao.TareaDAO;
import ar.gov.pjn.suat.persistence.domain.Tarea;

public class SUATTareaProcessor {

	private final static Log LOG = LogFactory.getLog(SUATTareaProcessor.class);

	private static SUATTareaProcessor instance = new SUATTareaProcessor();
	private Scheduler sched;
	private EstadoSUATTareas estado = new EstadoSUATTareas();
	private static EntityManager em;
	private TareaDAO tareaDao;
	
	static {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("suat-pu");
		em = emf.createEntityManager(); // Retrieve an application managed entity manager
	}
	
	private SUATTareaProcessor() {
		tareaDao=TareaDAO.getInstance();
	}

	public static SUATTareaProcessor getInstance() {
		return instance;
	}
	
	public EntityManager getEntityManager() {
		return em;
	}
	

	public void init() {
		try {
			sched = new StdSchedulerFactory().getScheduler();
			List<Tarea> tareasActivas = tareaDao.getTareasActivas();
			for (Tarea tarea : tareasActivas) {
				Trigger trigger = SUATUtils.createTrigger(tarea);
				JobDetail jobDetail = SUATUtils.createJobDetail(tarea);
				jobDetail.getJobDataMap().put("tarea", tarea);
				
				sched.scheduleJob(jobDetail, trigger);
			}
			listJobsAndTriggers("Jobs antes de arrancar el Scheduler:");
			sched.start();
			listJobsAndTriggers("Jobs luego de arrancar el Scheduler:");
			estado.setIniciado();
		} catch (SchedulerException e) {
			throw new RuntimeException("Ocurrio un error en el Processor de "
					+ "Tareas del daemon", e);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void listJobsAndTriggers(){
		listJobsAndTriggers("");
	}
	
	@SuppressWarnings("unchecked")
	public void listJobsAndTriggers(String extraMessage){
		List<String> jobGroups;
		Set<JobKey> jobsInGroup;

		LOG.debug("#######################Jobs#######################");
		LOG.debug(Calendar.getInstance().getTime());
		try {
			LOG.debug("isStarted: " +sched.isStarted());
			LOG.debug("");
			LOG.debug(extraMessage);
			jobGroups = sched.getJobGroupNames();
	
			for (String group:jobGroups){
				LOG.debug("Group: " + group + " contains the following jobs");
				jobsInGroup = sched.getJobKeys(GroupMatcher.jobGroupEquals(group));
				for (JobKey job:jobsInGroup) {
					LOG.debug("|- " + job.getName());
					List<Trigger> jobTriggers = (List<Trigger>) sched.getTriggersOfJob(job);
					for (Trigger trigger:jobTriggers) {
				    	  LOG.debug("|---- " + trigger.getDescription());
					}
				}
			}
		
			LOG.debug("##################################################");
		} catch (SchedulerException e) {
			throw new RuntimeException("Ocurrio un error en el Processor de Tareas del daemon", e);
		}
	}

	public boolean addJob(Tarea tarea) {
		try {
			JobKey jk = tarea.getJobKey();
			sched.deleteJob(jk);
			Trigger trigger = SUATUtils.createTrigger(tarea);
			JobDetail jobDetail = SUATUtils.createJobDetail(tarea);
			
			sched.scheduleJob(jobDetail, trigger);
			return true;
		} catch (Exception e) {
			LOG.error("Error al querer agregar la tarea: " + tarea.getNombre() + " del fuero " + tarea.getFuero(), e);
			return false;
		}
	}

	public boolean removeJob(Tarea tarea)  {
		try {
			JobKey jk = tarea.getJobKey();
			sched.deleteJob(jk);
			return true;
		}catch(Exception e) {
			LOG.error("Error al querer quitar la tarea: " + tarea.getNombre() + " del fuero " + tarea.getFuero(), e);
			return false;
		}
	}

	public void executeThisTaskNow(final Tarea tarea) throws Exception  {
		final TareaExecutor daemonJob = new TareaExecutor();
		JobDataMap map = new JobDataMap();
		map.put("tarea", tarea);
		map.put("inmediata", true);
		Thread tr = new Thread(new Runnable() {
			public void run() {
				daemonJob.executeNow(map);
			}
		});
		tr.start();
	}

	public void startDaemon() throws SchedulerException {
		sched.start();
		estado.setIniciado();
	}

	public void stopDaemon() throws SchedulerException {
		sched.standby();
		estado.setDetenido();
	}

	public void pauseDaemon() throws SchedulerException {
		sched.standby();
		estado.setPausado();
	}
	
	public void resumeDaemon() throws SchedulerException {
		startDaemon();
	}
	
	public EstadoSUATTareas getEstado() {
		return estado;
	}

	public boolean isActive() {
		return estado.isActive();
	}

	public String getInfoForTarea(Tarea tarea) {
		String info = "";
		Trigger trigger = SUATUtils.getTrigger(sched,tarea);
		
		if(trigger == null){
			info="Desactivada";
		}else{
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			info = df.format(trigger.getNextFireTime());
		}
		
		return info;
	}

	public List<Tarea> getRunningJobs() {
		try {
			List<Tarea> lista = new ArrayList<Tarea>();
			for (JobExecutionContext jec:sched.getCurrentlyExecutingJobs())
			{
				JobKey key = jec.getJobDetail().getKey();
				FiltroTareas filtro = new FiltroTareas();
				filtro.setNombre(key.getName());
				filtro.setGrupo(key.getGroup());
				lista.addAll(tareaDao.getTareas(filtro));
			}
			return lista;
		} catch (SchedulerException e) {
			LOG.error("Error al solicitar la lista de tareas ejecutandose",e );
			return null;
		}
	}

	public Scheduler getScheduler() {
		return sched;
	}


}
