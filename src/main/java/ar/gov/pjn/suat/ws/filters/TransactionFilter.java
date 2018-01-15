package ar.gov.pjn.suat.ws.filters;

import java.io.IOException;

import javax.persistence.EntityTransaction;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;

import ar.gov.pjn.suat.core.Context;
import ar.gov.pjn.suat.core.SUATTareaProcessor;

@Provider
public class TransactionFilter implements ContainerRequestFilter,ContainerResponseFilter   {

	@Override
	public void filter(ContainerRequestContext ctxt) throws IOException {
		EntityTransaction transaction = SUATTareaProcessor.getInstance().getEntityManager().getTransaction();
		transaction.begin();
		Context.setTransaction(transaction);
		
		String token = ctxt.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
	}

	@Override
	public void filter(ContainerRequestContext ctxtReq, ContainerResponseContext ctxtRes) throws IOException {
		EntityTransaction transaction = Context.getTransaction();
		if (transaction.getRollbackOnly()) {
			transaction.rollback();
		}
		else {
			transaction.commit();
		}
	}

}
