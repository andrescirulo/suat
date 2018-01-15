package ar.gov.pjn.suat.persistence.dao;

import javax.persistence.EntityManager;
import ar.gov.pjn.suat.core.SUATTareaProcessor;
import ar.gov.pjn.suat.persistence.domain.Grupo;

public class GrupoDAO extends DAO<Grupo, Long> {

	private static GrupoDAO instance;

	public static GrupoDAO getInstance() {
		if (instance == null) {
			instance = new GrupoDAO();
		}
		return instance;
	}

	private GrupoDAO() {
		super(Grupo.class);
	}

	protected GrupoDAO(Class<Grupo> entityClass) {
		super(entityClass);
	}

	protected EntityManager getEm() {
		return SUATTareaProcessor.getInstance().getEntityManager();
	}

}
