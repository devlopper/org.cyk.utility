package org.cyk.utility.service.server;

import java.io.Serializable;

import javax.ws.rs.core.Response.Status;

import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.business.Result;

@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true)
public class RequestImpl extends org.cyk.utility.rest.RequestExecutor.Request.AbstractImpl implements Serializable {

	private Class<?> persistenceEntityClass;

	public RequestImpl(Class<?> persistenceEntityClass) {
		this.persistenceEntityClass = persistenceEntityClass;		
	}
	
	@Override
	protected void __execute__() {
		Result result = executeBusiness();
		processResult(result);
	}
	
	protected Result executeBusiness() {
		throw new RuntimeException("Not yet implemented");
	}
	
	protected void processResult(Result result) {
		if(persistenceEntityClass != null && Boolean.TRUE.equals(result.isCountGreaterThanZero(persistenceEntityClass, Action.CREATE)))
			responseBuilderArguments.setStatus(Status.CREATED);
		setResponseBuilderArgumentsEntity(result);
		if(MapHelper.isNotEmpty(result.getCountsMap())) {
			result.getCountsMap().entrySet().forEach( entry -> {
				if(MapHelper.isEmpty(entry.getValue()))
					return;
				entry.getValue().forEach( (k,v) -> {
					responseBuilderArguments.setHeader(entry.getKey().getSimpleName()+"."+k.name(), v);
				});
			});
		}
	}

	protected void setResponseBuilderArgumentsEntity(Result result) {
		if(result.getValue() == null)
			responseBuilderArguments.setEntity(String.format("%s executed", result.getName()));
		else
			responseBuilderArguments.setEntity(result.getValue());
	}
	
	public static interface Business {		
		Result execute();
	}
}