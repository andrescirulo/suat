package ar.gov.pjn.suat.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ar.gov.pjn.suat.persistence.domain.EjecucionTarea;

public class EjecucionTareaDAO extends DAO<EjecucionTarea, Long> {

	private static EntityManager em;
	private static EjecucionTareaDAO instance;

	static {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("suat-pu");
		em = emf.createEntityManager(); // Retrieve an application managed entity manager
	}

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
		return em;
	}

}
