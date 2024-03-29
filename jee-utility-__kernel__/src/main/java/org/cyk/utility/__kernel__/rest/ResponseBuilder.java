package org.cyk.utility.__kernel__.rest;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.TypeHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.Message;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface ResponseBuilder {

	Response build(Arguments arguments);
	
	default Response build(RuntimeException.Dto runtimeException) {
		if(runtimeException == null)
			throw new IllegalArgumentException("runtime exception is required");
		return build(new Arguments().setRuntimeException(runtimeException));
	}
	
	default Response buildRuntimeException(String messageIdentifier,String messageSummary,String messageDetails) {
		return build(new RuntimeException.Dto(messageIdentifier, messageSummary, messageDetails));
	}
	
	default Response buildRuntimeException(String messageIdentifier,String messageSummary) {
		return build(new RuntimeException.Dto(messageIdentifier, messageSummary));
	}
	
	default Response buildRuntimeException(String messageIdentifier) {
		return build(new RuntimeException.Dto(messageIdentifier));
	}
	
	default Response build(Throwable throwable) {
		if(throwable == null)
			throw new IllegalArgumentException("throwable is required");
		return buildRuntimeException(null,throwable.getMessage());
	}
	
	default Response build(org.cyk.utility.__kernel__.user.interface_.message.Message message) {
		if(message == null)
			throw new IllegalArgumentException("message is required");
		return buildRuntimeException(null,message.getSummary(),message.getDetails());
	}
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements ResponseBuilder,Serializable {
		@Override
		public Response build(Arguments arguments) {
			if(arguments == null)
				throw new IllegalArgumentException("arguments are required");
			javax.ws.rs.core.Response.ResponseBuilder responseBuilder = Response.status(__buildStatus__(arguments));
			responseBuilder.entity(__buildEntity__(arguments));
			if(arguments.processingStartTime != null && arguments.processingEndTime == null)
				arguments.processingEndTime = System.currentTimeMillis();
			if(arguments.processingStartTime != null && arguments.processingEndTime != null && arguments.processingDuration == null)
				arguments.processingDuration = arguments.processingEndTime - arguments.processingStartTime;
			addHeader(responseBuilder, ResponseHelper.HEADER_X_TOTAL_COUNT, arguments.xTotalCount);
			addHeader(responseBuilder, ResponseHelper.HEADER_PROCESSING_START_TIME, arguments.processingStartTime);
			addHeader(responseBuilder, ResponseHelper.HEADER_PROCESSING_END_TIME, arguments.processingEndTime);
			addHeader(responseBuilder, ResponseHelper.HEADER_PROCESSING_DURATION, arguments.processingDuration);
			return responseBuilder.build();
		}
		
		protected Status __buildStatus__(Arguments arguments) {
			Response.Status status = arguments.getStatus();
			if(status == null) {
				if(arguments.getRuntimeException() == null)
					status = Response.Status.OK;
				else
					status = Response.Status.BAD_REQUEST;
			}
			return status;
		}
		
		protected Object __buildEntity__(Arguments arguments) {
			Object entity = arguments.getEntity();
			if(entity == null) {
				if(arguments.getRuntimeException() == null) {
					if(CollectionHelper.isEmpty(arguments.entities)) {
						
					}else {
						entity = new GenericEntity<List<?>>((List<?>) arguments.entities,(Type) TypeHelper.instantiateCollectionParameterizedType(List.class, arguments.entities.iterator().next().getClass()));
					}					
				}else {
					entity = arguments.getRuntimeException();
				}
			}
			return entity;
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public static class Arguments extends AbstractObject implements Serializable {
		private Response.Status status;
		private Object entity;
		private RuntimeException.Dto runtimeException;
		private Collection<Message.Dto> messageDtos;
		private Collection<?> entities;
		private Long xTotalCount;
		private Long processingStartTime,processingEndTime,processingDuration;
		
		public Arguments(RuntimeException.Dto runtimeException,Response.Status status) {
			this.runtimeException = runtimeException;
			this.status = status;
		}
		
		public Arguments(RuntimeException.Dto runtimeException) {
			this(runtimeException,Response.Status.BAD_REQUEST);
		}
	}
	
	static void addHeader(javax.ws.rs.core.Response.ResponseBuilder responseBuilder,String name,Object value) {
		if(StringHelper.isBlank(name) || value == null)
			return;
		responseBuilder.header(name, value);
	}
	
	/**/
	
	static ResponseBuilder getInstance() {
		return Helper.getInstance(ResponseBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}