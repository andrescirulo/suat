package ar.gov.pjn.suat.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import ar.gov.pjn.suat.persistence.domain.Tarea;
import ar.gov.pjn.suat.persistence.domain.TareaAutoDefinida;
import ar.gov.pjn.suat.persistence.domain.TareaCron;
import ar.gov.pjn.suat.persistence.domain.TareaPeriodica;

public class SUATUtils {

	private static final String AVISO_DAEMON_ERROR_INSERTADO = "AVISO DE DAEMON ERROR";
	private static Log LOG=LogFactory.getLog(SUATUtils.class);

	public static Trigger createTrigger(Tarea tarea) {
		Trigger trigger;
		String triggerName = tarea.getNombre() + "Trigger";
		String groupName = tarea.getGrupo() + "_" + tarea.getFuero() + "Group";
		
		TriggerBuilder<Trigger> tb = TriggerBuilder.newTrigger().withIdentity(triggerName, groupName);
		if (tarea.getProximaEjecucion()!=null && tarea.getProximaEjecucion().after(new Date())) {
			tb.startAt(tarea.getProximaEjecucion());
		}
		switch (tarea.getTipo()) {
		case PERIODICA:
			CalendarIntervalScheduleBuilder csb=CalendarIntervalScheduleBuilder.calendarIntervalSchedule();
			csb.withInterval(((TareaPeriodica) tarea).getIntervalo(), ((TareaPeriodica) tarea).getUnidad());
			tb.withSchedule(csb);
			break;
		case DEFINE_TAREA:
			Date nextFire = ((TareaAutoDefinida) tarea).consultarProximaEjecucion();
			if (nextFire!=null) {
				tb.startAt(nextFire);
			}
			break;
		case CRON:
			String expresion = ((TareaCron)tarea).getExpresion();
			CronScheduleBuilder crsb = CronScheduleBuilder.cronSchedule(expresion);
			tb.withSchedule(crsb);
			break;
		default:
			break;
		}
		trigger = tb.build();
		return trigger;
	}
	

	public static JobDetail createJobDetail(Tarea tarea) throws ClassNotFoundException {
		return JobBuilder
				.newJob(TareaExecutor.class)
				.withIdentity(tarea.getNombre() + "Job", tarea.getGrupo() + "_" + tarea.getFuero() + "Group")
				.build();
	}

	public static void enviarEmailDaemonError(String cuerpoMail) throws EmailException {
//		List<String> mailsCopias = DaemonMailsCopia.obtenerMailsCopiaErrores();
		List<String> mailsCopias = new ArrayList<String>();

		// Si tengo a quien mandarselo
		if (mailsCopias.size() > 0) {
			SimpleEmail email = new SimpleEmail();
//			email.setHostName(SpringUtils.getProperty("ipSmtpMailDaemon"));
//			email.setFrom(SpringUtils.getProperty("mailSender"));
			email.addTo(mailsCopias.remove(0));

			// agrego copias ocultas con proposito de testing
			for (String emailBcc : mailsCopias) {
				email.addBcc(emailBcc);
			}

			email.setSubject(AVISO_DAEMON_ERROR_INSERTADO);
			email.setMsg(cuerpoMail);

			email.send();
		}
	}

	public static Trigger getTrigger(Scheduler sched,Tarea tarea) {
		String triggerName=tarea.getNombre() + "Trigger";
		String groupName= tarea.getGrupo() + "_" + tarea.getFuero() + "Group";
		try {
			TriggerKey tk = new TriggerKey(triggerName, groupName);
			return sched.getTrigger(tk);
		} catch (SchedulerException e) {
			LOG.error("NO SE ENCONTRO EL TRIGGER: " + triggerName +" - " + groupName);
			return null;
		}
	}
	
}
