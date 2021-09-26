package org.cyk.utility.service.server;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.business.Result;
import org.cyk.utility.business.TransactionResult;
import org.cyk.utility.mapping.Mapper;
import org.cyk.utility.rest.RequestExecutor;
import org.cyk.utility.rest.ResponseBuilder;
import org.cyk.utility.service.SpecificService;

public abstract class AbstractSpecificServiceImpl<ENTITY> extends AbstractObject implements SpecificService<ENTITY>,Serializable {

	@Inject protected RequestExecutor requestExecutor;
	/*
	@Override
	public Response get(String filterAsString, Collection<String> projections, Boolean countable, Boolean pageable,Integer firstTupleIndex, Integer numberOfTuples) {
		EntityReaderRequestImpl<PERSISTENCE_ENTITY, SERVICE_ENTITY> e;
		return requestExecutor.execute(request);
	}
	
	protected <PERSISTENCE_ENTITY,SERVICE_ENTITY> EntityReaderRequestImpl<PERSISTENCE_ENTITY,SERVICE_ENTITY> instantiateGetEntityReaderRequest(
			String filterAsString, Collection<String> projections, Boolean countable, Boolean pageable,Integer firstTupleIndex, Integer numberOfTuples) {		
		return null;
	}
	
	@Override
	public Response getOne(String identifier, Collection<String> projections) {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	protected static void processTransactionResult(TransactionResult transactionResult,ResponseBuilder.Arguments responseBuilderArguments) {
		if(transactionResult == null || responseBuilderArguments == null)
			return;
		responseBuilderArguments.setEntity(transactionResult.buildSuccessMessage());
		responseBuilderArguments.setHeader(Action.CREATE.name(), transactionResult.getNumberOfCreation());
		responseBuilderArguments.setHeader(Action.UPDATE.name(), transactionResult.getNumberOfUpdate());
		responseBuilderArguments.setHeader(Action.DELETE.name(), transactionResult.getNumberOfDeletion());
	}
	
	protected static void processResult(Result result,ResponseBuilder.Arguments responseBuilderArguments) {
		if(result == null || responseBuilderArguments == null)
			return;
		
	}
	
	/**/
	
	public static abstract class AbstractRunnableImpl extends AbstractObject implements Runnable,Serializable {
		
		protected ResponseBuilder.Arguments responseBuilderArguments;
		
		public AbstractRunnableImpl(ResponseBuilder.Arguments responseBuilderArguments) {
			this.responseBuilderArguments = responseBuilderArguments;
		}
		
		@Override
		public void run() {
			__run__();
		}
		
		protected abstract void __run__();
		
		public static abstract class TransactionImpl extends AbstractRunnableImpl implements Serializable {
			
			public TransactionImpl(ResponseBuilder.Arguments responseBuilderArguments) {
				super(responseBuilderArguments);
			}
			
			@Override
			protected void __run__() {
				TransactionResult result = transact();
				processTransactionResult(result, responseBuilderArguments);
			}
			
			protected abstract TransactionResult transact();
		}
		
		public static abstract class BusinessImpl extends AbstractRunnableImpl implements Serializable {
			
			public BusinessImpl(ResponseBuilder.Arguments responseBuilderArguments) {
				super(responseBuilderArguments);
			}
			
			@Override
			protected void __run__() {
				Result result = execute();
				processResult(result, responseBuilderArguments);
			}
			
			protected abstract Result execute();
		}
	}
}