package org.cyk.utility.service.server;

import java.io.Serializable;

import org.cyk.utility.rest.RequestExecutor;
import org.cyk.utility.rest.ResponseBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractRequestImpl implements RequestExecutor.Request,Serializable {

	protected ResponseBuilder.Arguments responseBuilderArguments;
	
	public ResponseBuilder.Arguments getResponseBuilderArguments(Boolean instantiateIfNull) {
		if(responseBuilderArguments == null && Boolean.TRUE.equals(instantiateIfNull))
			responseBuilderArguments = new ResponseBuilder.Arguments();
		return responseBuilderArguments;
	}
}