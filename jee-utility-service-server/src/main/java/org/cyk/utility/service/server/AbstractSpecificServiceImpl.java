package org.cyk.utility.service.server;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.configuration.ClassIdentifierGetter;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.business.Result;
import org.cyk.utility.business.TransactionResult;
import org.cyk.utility.rest.ResponseBuilder;
import org.cyk.utility.service.FilterFormat;
import org.cyk.utility.service.SpecificService;
import org.eclipse.microprofile.config.ConfigProvider;

public abstract class AbstractSpecificServiceImpl<SERVICE_ENTITY,SERVICE_ENTITY_IMPL,PERSISTENCE_ENTITY,PERSISTENCE_ENTITY_IMPL> extends AbstractServiceImpl implements SpecificService<SERVICE_ENTITY>,Serializable {

	@Inject protected ClassIdentifierGetter classIdentifierGetter;
	protected Class<SERVICE_ENTITY> serviceEntityClass;
	protected Class<SERVICE_ENTITY_IMPL> serviceEntityImplClass;
	protected Class<PERSISTENCE_ENTITY> persistenceEntityClass;
	protected Class<PERSISTENCE_ENTITY_IMPL> persistenceEntityImplClass;
	protected Boolean isResponseHeadersCORSEnabled;
	
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
	public Response get(String filter,FilterFormat filterFormat,Boolean defaultable,List<String> projections,Boolean countable,Boolean pageable,Integer firstTupleIndex,Integer numberOfTuples) {
		return execute(__get__(filter,filterFormat,defaultable, mapProjections(projections), countable, pageable, firstTupleIndex, numberOfTuples));
	}
	
	@Override
	public Response getUsingPost(String filter, FilterFormat filterFormat,Boolean defaultable, List<String> projections, Boolean countable,Boolean pageable, Integer firstTupleIndex, Integer numberOfTuples) {
		return execute(__get__(filter,filterFormat,defaultable, mapProjections(projections), countable, pageable, firstTupleIndex, numberOfTuples));
	}
	
	protected EntityReaderRequestImpl<SERVICE_ENTITY_IMPL,PERSISTENCE_ENTITY_IMPL> instantiateEntityReaderRequest() {
		return new EntityReaderRequestImpl<SERVICE_ENTITY_IMPL,PERSISTENCE_ENTITY_IMPL>(serviceEntityImplClass);
	}
	
	protected EntityReaderRequestImpl<SERVICE_ENTITY_IMPL,PERSISTENCE_ENTITY_IMPL> __get__(String filter,FilterFormat filterFormat,Boolean defaultable,List<String> projections,Boolean countable,Boolean pageable,Integer firstTupleIndex,Integer numberOfTuples) {
		EntityReaderRequestImpl<SERVICE_ENTITY_IMPL,PERSISTENCE_ENTITY_IMPL> request = instantiateEntityReaderRequest().projections(projections).filter(filter,filterFormat,defaultable)
				.count(ValueHelper.defaultToIfNull(countable, Boolean.TRUE)).page(pageable,firstTupleIndex, numberOfTuples);
		if(Boolean.TRUE.equals(isResponseHeadersCORSEnabled))
			request.enableResponseHeadersCORS();
		return request;
	}
	
	protected EntityReaderRequestImpl<SERVICE_ENTITY_IMPL,PERSISTENCE_ENTITY_IMPL> __getByIdentifier__(String identifier,String filter, FilterFormat filterFormat,List<String> projections) {
		EntityReaderRequestImpl<SERVICE_ENTITY_IMPL,PERSISTENCE_ENTITY_IMPL> request = new EntityReaderRequestImpl<SERVICE_ENTITY_IMPL,PERSISTENCE_ENTITY_IMPL>(serviceEntityImplClass)
				.filter(filter, filterFormat, null).filterByIdentifier(identifier).projections(projections);
		request.getResponseBuilderArguments().setIsThrowNotFoundIfBlank(Boolean.TRUE);
		if(Boolean.TRUE.equals(isResponseHeadersCORSEnabled))
			request.enableResponseHeadersCORS();
		return request;
	}
	
	@Override
	public Response getByIdentifier(String identifier,String filter, FilterFormat filterFormat,List<String> projections) {
		return execute(__getByIdentifier__(identifier,filter,filterFormat, mapProjections(projections)));
	}
	
	public EntityCounterRequestImpl<SERVICE_ENTITY_IMPL,PERSISTENCE_ENTITY_IMPL> __count__(String filter,FilterFormat filterFormat,Boolean defaultable) {
		EntityCounterRequestImpl<SERVICE_ENTITY_IMPL,PERSISTENCE_ENTITY_IMPL> request = new EntityCounterRequestImpl<SERVICE_ENTITY_IMPL,PERSISTENCE_ENTITY_IMPL>(serviceEntityImplClass)
				.filter(filter,filterFormat,defaultable);
		if(Boolean.TRUE.equals(isResponseHeadersCORSEnabled))
			request.enableResponseHeadersCORS();
		return request;
	}
	
	@Override
	public Response count(String filter,FilterFormat filterFormat,Boolean defaultable) {
		return execute(__count__(filter,filterFormat,defaultable));
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
	
	public List<String> mapProjections(List<String> projections) {
		if(CollectionHelper.isEmpty(projections))
			return projections;
		return projections.stream().map(projection -> {
			if(PROJECTIONS_MAPS.containsKey(serviceEntityImplClass) && PROJECTIONS_MAPS.get(serviceEntityImplClass).containsKey(projection))
				return PROJECTIONS_MAPS.get(serviceEntityImplClass).get(projection);			
			return projection;
		}).collect(Collectors.toList());
	}
	
	protected Response buildResponseOk(Result result,ResponseBuilderListener listener) {
		ResponseBuilderListener __listener__ = listener == null ? ResponseBuilderListener.AbstractImpl.DefaultImpl.INSTANCE : listener;
		javax.ws.rs.core.Response.ResponseBuilder responseBuilder = Response.status(__listener__.buildStatus(result));
		//Object entity = __listener__.buildEntity(responseBuilder, result, __listener__); //result.getValue() == null ? StringHelper.concatenate(result.getMessages(), "<br/>") : result.getValue();
		responseBuilder.entity(__listener__.buildEntity(responseBuilder, result, __listener__));
		//javax.ws.rs.core.Response.ResponseBuilder responseBuilder = Response.ok(entity);
		if(result.getMap() != null)
			result.getMap().entrySet().stream().filter(entry -> entry.getKey() != null && entry.getValue() != null).forEach(entry -> {
				__listener__.buildHeader(responseBuilder,entry.getKey(), entry.getValue());
			});
		return responseBuilder.build();
	}
	
	protected Response buildResponseOk(Result result) {
		return buildResponseOk(result, null);
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

	public static interface ResponseBuilderListener{
		Response.Status buildStatus(Result result);
		void buildHeader(javax.ws.rs.core.Response.ResponseBuilder builder,Object key,Object value);
		Object buildEntity(javax.ws.rs.core.Response.ResponseBuilder builder,Result result,Object value);
		
		public static abstract class AbstractImpl implements ResponseBuilderListener,Serializable{
			@Override
			public Status buildStatus(Result result) {
				return Response.Status.OK;
			}
			@Override
			public void buildHeader(javax.ws.rs.core.Response.ResponseBuilder builder, Object key, Object value) {
				builder.header(buildHeaderName(key), buildHeaderValue(value));
			}
			
			protected String buildHeaderName(Object value) {
				if(value instanceof Class)
					return ((Class<?>)value).getSimpleName();
				return value.toString();
			}
			
			protected String buildHeaderValue(Object value) {
				if(value instanceof Collection)
					return StringHelper.concatenate((Collection<String>) value, ",");
				return value.toString();
			}
			
			@Override
			public Object buildEntity(javax.ws.rs.core.Response.ResponseBuilder builder,Result result, Object value) {
				return result.getValue() == null ? StringHelper.concatenate(result.getMessages(), "<br/>") : result.getValue();
			}
			
			public static class DefaultImpl extends AbstractImpl implements Serializable{
				public static DefaultImpl INSTANCE = new DefaultImpl();
			}
		}
	}
	
	private static final String IS_RESPONSE_HEADERS_CORS_ENABLED_FORMAT = "cyk%s.response.headers.cors.enabled";
}