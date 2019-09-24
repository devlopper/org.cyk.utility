package org.cyk.utility.request;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.object.ObjectByStringMap;

@Deprecated
public interface RequestParameterValueMapper extends FunctionWithPropertiesAsInput<Object> {

	Object getParameterName();
	RequestParameterValueMapper setParameterName(Object parameterName);
	RequestParameterValueMapper setParameterNameAsEntityClass();
	RequestParameterValueMapper setParameterNameAsEntityIdentifier();
	RequestParameterValueMapper setParameterNameAsActionClass();
	RequestParameterValueMapper setParameterNameAsActionIdentifier();
	RequestParameterValueMapper setParameterNameAsNextActionClass();
	RequestParameterValueMapper setParameterNameAsNextActionIdentifier();
	RequestParameterValueMapper setParameterNameAsWindowRenderTypeClass();
	
	String getParameterValue();
	RequestParameterValueMapper setParameterValue(String parameterValue);
	
	ObjectByStringMap getParameters();
	ObjectByStringMap getParameters(Boolean injectIfNull);
	RequestParameterValueMapper setParameters(ObjectByStringMap parameters);
}
