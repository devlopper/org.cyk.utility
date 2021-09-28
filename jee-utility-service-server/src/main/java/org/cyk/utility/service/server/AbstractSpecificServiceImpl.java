package org.cyk.utility.service.server;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.configuration.ClassIdentifierGetter;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.business.Result;
import org.cyk.utility.business.TransactionResult;
import org.cyk.utility.rest.RequestExecutor;
import org.cyk.utility.rest.ResponseBuilder;
import org.cyk.utility.service.SpecificService;
import org.eclipse.microprofile.config.ConfigProvider;

public abstract class AbstractSpecificServiceImpl<SERVICE_ENTITY,SERVICE_ENTITY_IMPL,PERSISTENCE_ENTITY,PERSISTENCE_ENTITY_IMPL> extends AbstractObject implements SpecificService<SERVICE_ENTITY>,Serializable {

	@Inject protected RequestExecutor requestExecutor;
	@Inject protected ClassIdentifierGetter classIdentifierGetter;
	protected Class<SERVICE_ENTITY> serviceEntityClass;
	protected Class<SERVICE_ENTITY_IMPL> serviceEntityImplClass;
	protected Class<PERSISTENCE_ENTITY> persistenceEntityClass;
	protected Class<PERSISTENCE_ENTITY_IMPL> persistenceEntityImplClass;
	protected Boolean isResponseHeadersCORSEnabled;
	
	@SuppressWarnings("unchecked")
	public AbstractSpecificServiceImpl() {
		this.serviceEntityClass = (Class<SERVICE_ENTITY>) ClassHelper.getParameterAt(getClass(), 0);
		this.serviceEntityImplClass = (Class<SERVICE_ENTITY_IMPL>) ClassHelper.getParameterAt(getClass(), 1);
		this.persistenceEntityClass = (Class<PERSISTENCE_ENTITY>) ClassHelper.getParameterAt(getClass(), 2);
		this.persistenceEntityImplClass = (Class<PERSISTENCE_ENTITY_IMPL>) ClassHelper.getParameterAt(getClass(), 3);
	}
	
	@PostConstruct
	public void listenPostConstruct() {
		isResponseHeadersCORSEnabled = ConfigProvider.getConfig().getOptionalValue(String.format(IS_RESPONSE_HEADERS_CORS_ENABLED_FORMAT, ""), Boolean.class)
				.orElse(ConfigProvider.getConfig().getOptionalValue(String.format(IS_RESPONSE_HEADERS_CORS_ENABLED_FORMAT, "."+classIdentifierGetter.get(persistenceEntityClass))
						, Boolean.class).orElse(Boolean.FALSE));
	}
	
	@Override
	public Response get(String filterAsString,List<String> projections,Boolean countable,Boolean pageable,Integer firstTupleIndex,Integer numberOfTuples) {
		return requestExecutor.execute(__get__(filterAsString, projections, countable, pageable, firstTupleIndex, numberOfTuples));
	}
	
	protected EntityReaderRequestImpl<PERSISTENCE_ENTITY_IMPL,SERVICE_ENTITY_IMPL> __get__(String filterAsString,List<String> projections,Boolean countable,Boolean pageable,Integer firstTupleIndex,Integer numberOfTuples) {
		EntityReaderRequestImpl<PERSISTENCE_ENTITY_IMPL,SERVICE_ENTITY_IMPL> request = new EntityReaderRequestImpl<PERSISTENCE_ENTITY_IMPL, SERVICE_ENTITY_IMPL>(serviceEntityImplClass)
				.projections(projections).filter(filterAsString)
				.count(ValueHelper.defaultToIfNull(countable, Boolean.TRUE)).page(pageable,firstTupleIndex, numberOfTuples);
		if(Boolean.TRUE.equals(isResponseHeadersCORSEnabled))
			request.enableResponseHeadersCORS();
		return request;
	}
	
	@Override
	public Response getOne(String identifier,List<String> projections) {
		EntityReaderRequestImpl<PERSISTENCE_ENTITY_IMPL,SERVICE_ENTITY_IMPL> request = new EntityReaderRequestImpl<PERSISTENCE_ENTITY_IMPL,SERVICE_ENTITY_IMPL>(serviceEntityImplClass)
				.filterByIdentifier(identifier).projections(projections);
		if(Boolean.TRUE.equals(isResponseHeadersCORSEnabled))
			request.enableResponseHeadersCORS();
		return requestExecutor.execute(request);
	}
	
	@Override
	public Response count(String filterAsString) {
		EntityCounterRequestImpl<PERSISTENCE_ENTITY_IMPL,SERVICE_ENTITY_IMPL> request = new EntityCounterRequestImpl<PERSISTENCE_ENTITY_IMPL,SERVICE_ENTITY_IMPL>(serviceEntityImplClass)
				.filter(filterAsString);
		if(Boolean.TRUE.equals(isResponseHeadersCORSEnabled))
			request.enableResponseHeadersCORS();
		return requestExecutor.execute(request);
	}
	
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
	
	private static final String IS_RESPONSE_HEADERS_CORS_ENABLED_FORMAT = "cyk%s.response.headers.cors.enabled";
}