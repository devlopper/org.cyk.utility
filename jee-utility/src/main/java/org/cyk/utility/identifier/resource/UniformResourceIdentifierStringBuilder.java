package org.cyk.utility.identifier.resource;

import org.cyk.utility.object.ObjectByObjectMap;
import org.cyk.utility.string.StringFunction;

/**
 * build a string following this format : scheme://host:port/path
 * @author CYK
 *
 */
public interface UniformResourceIdentifierStringBuilder extends StringFunction {

	UniformResourceIdentifierStringBuilder setFormatArguments(Object...arguments);

	UniformResourceIdentifierStringBuilder setScheme(Object scheme);
	Object getScheme();
	
	UniformResourceIdentifierStringBuilder setHost(Object host);
	Object getHost();
	
	UniformResourceIdentifierStringBuilder setPort(Object port);
	Object getPort();
	
	UniformResourceIdentifierStringBuilder setContext(Object context);
	Object getContext();
	
	UniformResourceIdentifierStringBuilder setPath(Object path);
	Object getPath();
	
	UniformResourceIdentifierStringBuilder setQuery(Object path);
	Object getQuery();
	
	UniformResourceIdentifierStringBuilder setParameterMap(ObjectByObjectMap parameterMap);
	ObjectByObjectMap getParameterMap(Boolean injectIfNull);
	ObjectByObjectMap getParameterMap();
	UniformResourceIdentifierStringBuilder setParameters(Object...keyValues);
	
	Object getRequest();
	UniformResourceIdentifierStringBuilder setRequest(Object request);
	
	String getString();
	UniformResourceIdentifierStringBuilder setString(String string);
	
	/**/
	
	/**
	 * scheme://host:port/path
	 */
	String FORMAT = "%s://%s%s%s/%s%s%s";
	
	Integer FORMAT_ARGUMENT_SCHEME = 0;
	Integer FORMAT_ARGUMENT_HOST = 1;
	Integer FORMAT_ARGUMENT_HOST_PORT_SEPARATOR = 2;
	Integer FORMAT_ARGUMENT_PORT = 3;
	Integer FORMAT_ARGUMENT_PATH = 4;
	Integer FORMAT_ARGUMENT_PATH_QUERY_SEPARATOR = 5;
	Integer FORMAT_ARGUMENT_QUERY = 6;
}
