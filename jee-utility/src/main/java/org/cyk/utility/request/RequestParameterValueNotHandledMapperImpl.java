package org.cyk.utility.request;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.object.ObjectByStringMap;

@Dependent
public class RequestParameterValueNotHandledMapperImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements RequestParameterValueNotHandledMapper,Serializable {
	private static final long serialVersionUID = 1L;

	private Object parameterName;
	private String parameterValue;
	private ObjectByStringMap parameters;
	
	@Override
	public Object getParameterName() {
		return parameterName;
	}

	@Override
	public RequestParameterValueNotHandledMapper setParameterName(Object parameterName) {
		this.parameterName = parameterName;
		return this;
	}

	@Override
	public String getParameterValue() {
		return parameterValue;
	}

	@Override
	public RequestParameterValueNotHandledMapper setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
		return this;
	}

	@Override
	public ObjectByStringMap getParameters() {
		return parameters;
	}

	@Override
	public RequestParameterValueNotHandledMapper setParameters(ObjectByStringMap parameters) {
		this.parameters = parameters;
		return this;
	}

}
