package org.cyk.utility.service.server;

import java.io.Serializable;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.business.Result;

import lombok.experimental.Accessors;

@lombok.Getter @lombok.Setter @Accessors(chain=true)
public class EntityBusinessRequestImpl extends AbstractRequestImpl implements org.cyk.utility.rest.RequestExecutor.Request,Serializable {

	private Business business;
	
	@Override
	public org.cyk.utility.rest.ResponseBuilder.Arguments execute() {
		if(business == null)
			throw new RuntimeException("Function is required");
		Result result = business.execute();
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
		return responseBuilderArguments;
	}

	public static interface Business {		
		Result execute();
	}
}