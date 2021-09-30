package org.cyk.utility.service.server;

import java.io.Serializable;

import javax.ws.rs.core.Response.Status;

import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.business.Result;

@lombok.Getter @lombok.Setter @lombok.experimental.Accessors(chain=true) @Deprecated
public class EntityBusinessRequestImpl extends AbstractRequestImpl implements Serializable {

	private Business business;
	private Class<?> persistenceEntityClass;

	public EntityBusinessRequestImpl(Business business,Class<?> persistenceEntityClass) {
		this.business = business;
		this.persistenceEntityClass = persistenceEntityClass;		
	}
	
	@Override
	protected void __execute__() {
		if(business == null)
			throw new RuntimeException("Function is required");
		Result result = business.execute();
		if(persistenceEntityClass != null && Boolean.TRUE.equals(result.isCountGreaterThanZero(persistenceEntityClass, Action.CREATE)))
			responseBuilderArguments.setStatus(Status.CREATED);
		responseBuilderArguments.setProcessingStartTime(result.getStartingTime());
		responseBuilderArguments.setProcessingEndTime(result.getStoppingTime());
		responseBuilderArguments.setProcessingDuration(result.getDuration());
		responseBuilderArguments.setEntity(String.format("%s executed", result.getName()));
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

	public static interface Business {		
		Result execute();
	}
}