package org.cyk.utility.controller;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.TypeHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.rest.ResponseHelper;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface RequestExecutor {

	<T> T execute(Request request,Class<T> responseClass);
	String executeString(Request request);
	<T> List<T> executeList(Request request,Class<T> elementClass);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements RequestExecutor,Serializable {
		
		@Override
		public <T> T execute(Request request,Class<T> responseClass) {
			Response response = request.execute();
			if(Boolean.TRUE.equals(ResponseHelper.isStatusClientErrorNotFound(response)))
				return (T) getStatusClientErrorNotFoundEntity(responseClass);
			return readEntity(request,response,responseClass);
		}
		
		protected Object getStatusClientErrorNotFoundEntity(Class<?> responseClass) {
			if(String.class.equals(responseClass))
				return "*** ERREUR! SERVICE INDISPONIBLE ***"; 
			return null;
		}
		
		protected <T> T readEntity(Request request,Response response,Class<T> klass) {
			if(ClassHelper.isInstanceOf(klass, Collection.class)) {
				Collection<?> collection = (Collection<?>) response.readEntity(TypeHelper.instantiateGenericCollectionParameterizedTypeForJaxrs(Collection.class,request.getRepresentationEntityClass()));
				if(CollectionHelper.isEmpty(collection))
					return null;
				return (T) MappingHelper.getSources(collection, request.getEntityClass());
			}
			return response.readEntity(klass);
		}
		
		public String executeString(Request request) {
			return execute(request, String.class);
		}
		
		public <T> List<T> executeList(Request request, java.lang.Class<T> elementClass) {
			return execute(request.setEntityClass(elementClass), List.class);
		}
	}	
	
	public static interface Request {		
		Response execute();
		Class<?> getEntityClass();
		Request setEntityClass(Class<?> entityClass);
		Class<?> getRepresentationEntityClass();		
		
		public static abstract class AbstractImpl extends AbstractObject implements Request,Serializable {
			
			@Getter @Setter @Accessors(chain=true)
			protected Class<?> entityClass;
			
			@Override
			public Class<?> getRepresentationEntityClass() {
				if(entityClass == null)
					return null;
				return RepresentationEntityClassGetter.getInstance().get(entityClass);
			}
		}
	}
	
	static RequestExecutor getInstance() {
		return Helper.getInstance(RequestExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}