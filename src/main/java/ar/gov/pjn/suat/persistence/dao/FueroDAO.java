package ar.gov.pjn.suat.persistence.dao;

import javax.persistence.EntityManager;
import ar.gov.pjn.suat.core.SUATTareaProcessor;
import ar.gov.pjn.suat.persistence.domain.Fuero;

public class FueroDAO extends DAO<Fuero, Long> {

	private static FueroDAO instance;

	public static FueroDAO getInstance() {
		if (instance == null) {
			instance = new FueroDAO();
		}
		return instance;
	}

	private FueroDAO() {
		super(Fuero.class);
	}

	protected FueroDAO(Class<Fuero> entityClass) {
		super(entityClass);
	}

	protected EntityManager getEm() {
		return SUATTareaProcessor.getInstance().getEntityManager();
	}

}
