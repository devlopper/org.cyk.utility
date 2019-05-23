package org.cyk.utility.request;

import javax.ws.rs.client.Client;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierStringBuilder;
import org.cyk.utility.object.ObjectByObjectMap;

public interface RequestProcessor extends FunctionWithPropertiesAsInput<Object> {

	Client getClient();
	RequestProcessor setClient(Client client);
	
	UniformResourceIdentifierStringBuilder getUniformResourceIdentifierString();
	UniformResourceIdentifierStringBuilder getUniformResourceIdentifierString(Boolean injectIfNull);
	RequestProcessor setUniformResourceIdentifierString(UniformResourceIdentifierStringBuilder uniformResourceIdentifierString);
	RequestProcessor setUniformResourceIdentifierString(String string);
	
	String getUniformResourceIdentifierStringFormat();
	RequestProcessor setUniformResourceIdentifierStringFormat(String uniformResourceIdentifierStringFormat);
	
	ObjectByObjectMap getHeadersMap();
	ObjectByObjectMap getHeadersMap(Boolean injectIfNull);
	RequestProcessor setHeadersMap(ObjectByObjectMap headersMap);
	RequestProcessor setHeaders(Object...keyValues);
	RequestProcessor setHeader(Object key,Object value);
	
	Class<?> getResponseEntityClass();
	RequestProcessor setResponseEntityClass(Class<?> responseEntityClass);
	
	Object getResponseEntity();
	RequestProcessor setResponseEntity(Object responseEntity);
}
