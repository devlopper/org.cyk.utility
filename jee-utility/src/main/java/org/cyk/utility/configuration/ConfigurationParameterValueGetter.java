package org.cyk.utility.configuration;

import org.cyk.utility.__kernel__.function.Function;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.value.IsNullChecker;

/**
 * Get configuration parameter value. The order of check is the following : </br>
 * 1 - request</br>
 * 2 - running system</br>
 * 3 - context</br>
 * 4 - running operating system</br>
 * @author CYK
 *
 */
public interface ConfigurationParameterValueGetter extends FunctionWithPropertiesAsInput<Object> {

	Object getContext();
	ConfigurationParameterValueGetter setContext(Object context);
	
	Object getRequest();
	ConfigurationParameterValueGetter setRequest(Object request);
	
	String getName();
	ConfigurationParameterValueGetter setName(String name);
	
	Class<? extends IsNullChecker> getIsNullValueCheckerClass();
	ConfigurationParameterValueGetter setIsNullValueCheckerClass(Class<? extends IsNullChecker> isNullValueCheckerClass);
	
	Class<?> getIsNullValueCheckerQualifierClass();
	ConfigurationParameterValueGetter setIsNullValueCheckerQualifierClass(Class<?> isNullValueCheckerQualifierClass);
	
	Object getNullValue();
	ConfigurationParameterValueGetter setNullValue(Object nullValue);
	
	Function<Properties, Object> execute(String name,Object context,Object request,Object nullValue);
	Function<Properties, Object> execute(String name,Object context,Object request);
}
