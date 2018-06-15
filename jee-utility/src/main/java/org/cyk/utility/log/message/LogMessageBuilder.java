package org.cyk.utility.log.message;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.log.Log;

public interface LogMessageBuilder extends FunctionWithPropertiesAsInput<LogMessage> {

	Log getParent();
	
	String getTemplateFormat();
	LogMessageBuilder setTemplateFormat(String templateFormat);
	
	String getParameterFormat();
	LogMessageBuilder setParameterFormat(String parameterFormat);
	
	String getParameterSeparator();
	LogMessageBuilder setParameterSeparator(String parameterSeparator);
	
	Collection<Object> getParameters();
	LogMessageBuilder setParameters(Collection<Object> parameters);
	LogMessageBuilder addParameter(Object parameter);
	LogMessageBuilder addParameter(Object key,Object value);
}
