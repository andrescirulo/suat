package ar.gov.pjn.suat.persistence;

import javax.persistence.EntityManager;

import ar.gov.pjn.suat.core.SUATTareaProcessor;
import ar.gov.pjn.suat.persistence.domain.EjecucionTarea;

public class EjecucionTareaDAO extends DAO<EjecucionTarea, Long> {

	private static EjecucionTareaDAO instance;

	public static EjecucionTareaDAO getInstance() {
		if (instance == null) {
			instance = new EjecucionTareaDAO();
		}
		return instance;
	}

	private EjecucionTareaDAO() {
		super(EjecucionTarea.class);
	}

	protected EjecucionTareaDAO(Class<EjecucionTarea> entityClass) {
		super(entityClass);
	}

	protected EntityManager getEm() {
		return SUATTareaProcessor.getInstance().getEntityManager();
	}

}
