package org.cyk.utility.identifier.resource;

import org.cyk.utility.string.StringFunction;

/**
 * build a string following this format : scheme://host:port/path
 * @author CYK
 *
 */
public interface UniformResourceIdentifierStringBuilder extends StringFunction {

	UniformResourceIdentifierStringBuilder setFormatArguments(Object...arguments);

	/**/
	
	/**
	 * scheme://host:port/path
	 */
	String FORMAT = "%s://%s:%s/%s";
	
	Integer FORMAT_ARGUMENT_SCHEME = 0;
	Integer FORMAT_ARGUMENT_HOST = 1;
	Integer FORMAT_ARGUMENT_PORT = 2;
	Integer FORMAT_ARGUMENT_PATH = 3;
}
