package ar.gov.pjn.suat.core;

import javax.persistence.EntityTransaction;

public class Context {
	public static final ThreadLocal<EntityTransaction> userThreadLocal = new ThreadLocal<EntityTransaction>();
	
	public static void setTransaction(EntityTransaction transaction) {
		userThreadLocal.set(transaction);
	}

	public static void unsetTransaction() {
		userThreadLocal.remove();
	}

	public static EntityTransaction getTransaction() {
		return userThreadLocal.get();
	}
}
