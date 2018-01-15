package ar.gov.pjn.suat.transformers;

import org.quartz.DateBuilder.IntervalUnit;

import ar.gov.pjn.suat.persistence.dao.FueroDAO;
import ar.gov.pjn.suat.persistence.dao.GrupoDAO;
import ar.gov.pjn.suat.persistence.domain.Tarea;
import ar.gov.pjn.suat.persistence.domain.TareaAutoDefinida;
import ar.gov.pjn.suat.persistence.domain.TareaCron;
import ar.gov.pjn.suat.persistence.domain.TareaPeriodica;
import ar.gov.pjn.suat.ws.domain.IntervaloEnum;
import ar.gov.pjn.suat.ws.domain.TareaWSInput;

public class TareaTransformer {

	public static Tarea createFromInput(TareaWSInput tInput) {
		switch (tInput.getTipo()) {
		case CRON: {
			TareaCron tc = new TareaCron();
			llenarDatosBase(tc, tInput);
			tc.setExpresion(tInput.getExpresion());
			return tc;
		}
		case DEFINE_TAREA: {
			TareaAutoDefinida tdt = new TareaAutoDefinida();
			llenarDatosBase(tdt, tInput);
			tdt.setTimeEndpoint(tInput.getTimeEndpoint());
			return tdt;
		}
		case PERIODICA: {
			TareaPeriodica tp = new TareaPeriodica();
			llenarDatosBase(tp, tInput);
			tp.setUnidad(obtenerUnidadIntervalo(tInput.getUnidad()));
			tp.setIntervalo(tInput.getIntervalo());
			return tp;
		}
		default:
			return null;
		}
	}
	
	public static Tarea updateFromInput(TareaWSInput tInput,Tarea tarea) {
		switch (tInput.getTipo()) {
		case CRON: {
			TareaCron tc = (TareaCron) tarea;
			llenarDatosBase(tc, tInput);
			tc.setExpresion(tInput.getExpresion());
			return tc;
		}
		case DEFINE_TAREA: {
			TareaAutoDefinida tdt = (TareaAutoDefinida) tarea;
			llenarDatosBase(tdt, tInput);
			tdt.setTimeEndpoint(tInput.getTimeEndpoint());
			return tdt;
		}
		case PERIODICA: {
			TareaPeriodica tp = (TareaPeriodica) tarea;
			llenarDatosBase(tp, tInput);
			tp.setUnidad(obtenerUnidadIntervalo(tInput.getUnidad()));
			tp.setIntervalo(tInput.getIntervalo());
			return tp;
		}
		default:
			return null;
		}
	}
	
	private static IntervalUnit obtenerUnidadIntervalo(IntervaloEnum unidad) {
		switch (unidad) {
		case MINUTO: {
			return IntervalUnit.MINUTE;
		}
		case HORA: {
			return IntervalUnit.HOUR;
		}
		case DIA: {
			return IntervalUnit.DAY;
		}
		case SEMANA: {
			return IntervalUnit.WEEK;
		}
		case MES: {
			return IntervalUnit.MONTH;
		}
		}
		
		return null;
	}

	private static void llenarDatosBase(Tarea tarea ,TareaWSInput tInput) {
		tarea.setEndpoint(tInput.getEndpoint());
		tarea.setFuero(FueroDAO.getInstance().getById(tInput.getFuero()));
		tarea.setGrupo(GrupoDAO.getInstance().getById(tInput.getGrupo()));
		tarea.setNombre(tInput.getNombre());
		tarea.setId(tInput.getId());
		tarea.setPausable(tInput.getPausable());
		tarea.setTipo(tInput.getTipo());
		tarea.setActiva(tInput.getActiva());
	}
	
}
