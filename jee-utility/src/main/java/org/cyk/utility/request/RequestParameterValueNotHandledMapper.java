package org.cyk.utility.request;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.object.ObjectByStringMap;

public interface RequestParameterValueNotHandledMapper extends FunctionWithPropertiesAsInput<Object> {

	Object getParameterName();
	RequestParameterValueNotHandledMapper setParameterName(Object parameterName);
	
	String getParameterValue();
	RequestParameterValueNotHandledMapper setParameterValue(String parameterValue);
	
	ObjectByStringMap getParameters();
	RequestParameterValueNotHandledMapper setParameters(ObjectByStringMap parameters);
}
