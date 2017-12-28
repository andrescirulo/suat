package ar.gov.pjn.suat.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public abstract class DAO<E,ID extends Serializable> {

	
	private Class<E> entityClass;

	public DAO(Class<E> entityClass) {
		this.entityClass = entityClass;
	}
	
	abstract protected EntityManager getEm();
	
	public E findOne(final ID id) {
		return getEm().find(entityClass, id);
	}
	
	public E getById(final ID id) {
		E elem=getEm().find(entityClass, id);
		if (elem==null) {
			throw new NoResultException();
		}
		return elem;
	}
	
	public List<E> getAll(){
		CriteriaBuilder cb = getEm().getCriteriaBuilder();
		CriteriaQuery<E> cq = cb.createQuery(entityClass);
		
		Root<E> root = cq.from(entityClass);
		
		cq.select(root);
		TypedQuery<E> q = getEm().createQuery(cq);
		List<E> items = q.getResultList();
		return items;
	}
	
	public E save(E elem){
		getEm().getTransaction().begin();
		E savedEntity;

        if (getEm().contains(elem) ) {
            savedEntity = getEm().merge(elem);
        } else {
        	getEm().persist(elem);
            savedEntity = elem;
        }
        getEm().flush();
        getEm().getTransaction().commit();
		return savedEntity;
	}
	
	public void delete(E elem){
		getEm().getTransaction().begin();
		getEm().remove(elem);
		getEm().flush();
		getEm().getTransaction().commit();
	}
	
	public void delete(ID id){
		getEm().getTransaction().begin();
		getEm().remove(findOne(id));
		getEm().flush();
		getEm().getTransaction().commit();
	}
}
