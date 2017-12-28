package ar.gov.pjn.suat.persistence;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.quartz.SchedulerException;
import org.quartz.Trigger;

import ar.gov.pjn.suat.core.SUATTareaProcessor;
import ar.gov.pjn.suat.persistence.domain.Tarea;

public class TareaDAO extends DAO<Tarea, Long> {

	private static EntityManager em;
	private static TareaDAO instance;

	static {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("suat-pu");
		em = emf.createEntityManager(); // Retrieve an application managed entity manager
	}

	public static TareaDAO getInstance() {
		if (instance == null) {
			instance = new TareaDAO();
		}
		return instance;
	}

	private TareaDAO() {
		super(Tarea.class);
	}

	protected TareaDAO(Class<Tarea> entityClass) {
		super(entityClass);
	}

	protected EntityManager getEm() {
		return em;
	}

	public List<Tarea> getTareasActivas() {
		FiltroTareas filtro = new FiltroTareas();
		filtro.setActiva(1);
		return getTareas(filtro);
	}

	public List<Tarea> getTareas(FiltroTareas filtro) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tarea> cq = cb.createQuery(Tarea.class);
		Root<Tarea> root = cq.from(Tarea.class);
		List<Predicate> predicates = buildFilter(cb, root, filtro);
		if (predicates.size() > 0) {
			cq.where(predicates.toArray(new Predicate[] {}));
		}

		cq.select(root);
		TypedQuery<Tarea> q = em.createQuery(cq);
		List<Tarea> items = q.getResultList();
		return items;
	}

	private List<Predicate> buildFilter(CriteriaBuilder cb, Root<Tarea> root, FiltroTareas filtro) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (filtro.getId() != null) {
			predicates.add(cb.equal(root.get("id"), filtro.getId()));
		}
		if (filtro.getActiva() != null) {
			predicates.add(cb.equal(root.get("activa"), filtro.getActiva() == 1));
		}
		if (filtro.getNombre() != null) {
			predicates.add(cb.equal(root.get("nombre"), filtro.getNombre()));
		}
		if (filtro.getFuero() != null) {
			predicates.add(cb.equal(root.get("fuero"), filtro.getFuero()));
		}
		if (filtro.getGrupo() != null) {
			predicates.add(cb.equal(root.get("grupo"), filtro.getGrupo()));
		}
		return predicates;
	}

	@SuppressWarnings("unchecked")
	public void actualizarEjecucion(Tarea tarea, Date ultimaEjecucion) {
		try {
			List<Trigger> triggers = (List<Trigger>) SUATTareaProcessor.getInstance().getScheduler()
					.getTriggersOfJob(tarea.getJobKey());
			Date proximaEjecucion = triggers.get(0).getNextFireTime();

			em.getTransaction().begin();
			Tarea t = findOne(tarea.getId());
			t.setUltimaEjecucion(ultimaEjecucion);
			t.setProximaEjecucion(proximaEjecucion);
			save(t);
			em.getTransaction().commit();
		} catch (SchedulerException e) {
			em.getTransaction().rollback();
		}
	}

}
