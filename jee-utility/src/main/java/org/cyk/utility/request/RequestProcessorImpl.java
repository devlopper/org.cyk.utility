package org.cyk.utility.request;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;

import org.cyk.utility.field.FieldValueCopy;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierStringBuilder;
import org.cyk.utility.map.MapHelper;
import org.cyk.utility.object.ObjectByObjectMap;
import org.cyk.utility.string.StringHelper;

@Dependent
public class RequestProcessorImpl extends AbstractFunctionWithPropertiesAsInputImpl<Object> implements RequestProcessor,Serializable {
	private static final long serialVersionUID = 1L;

	private Client client;
	private UniformResourceIdentifierStringBuilder uniformResourceIdentifierString;
	private ObjectByObjectMap headersMap;
	private Class<?> responseEntityClass;
	private Object responseEntity;
	private String uniformResourceIdentifierStringFormat;
	
	@Override
	protected Object __execute__() throws Exception {
		Object response = null;
		Object responseEntity = getResponseEntity();
		UniformResourceIdentifierStringBuilder uniformResourceIdentifierString = getUniformResourceIdentifierString();
		String uniformResourceIdentifier = null;
		if(uniformResourceIdentifierString == null) {
			String uniformResourceIdentifierStringFormat = __injectValueHelper__().returnOrThrowIfBlank("request processor uniform resource locator string format", getUniformResourceIdentifierStringFormat());
			if( Boolean.TRUE.equals(__inject__(StringHelper.class).isNotBlank(uniformResourceIdentifierStringFormat)) && responseEntity!=null ) {
				uniformResourceIdentifier = String.format(uniformResourceIdentifierStringFormat, __injectFieldHelper__().getFieldValueSystemIdentifier(responseEntity));
			}
		}else {
			uniformResourceIdentifier = uniformResourceIdentifierString.execute().getOutput();
		}
		
		if(__injectStringHelper__().isBlank(uniformResourceIdentifier))
			__injectThrowableHelper__().throwRuntimeException("request processor uniform resource locator cannot be derived");
		
		Client client = getClient();
		if(client == null)
			client = __inject__(RequestHelper.class).getClient(Boolean.TRUE);
		
		Class<?> responseEntityClass = getResponseEntityClass();
		if(responseEntityClass == null && responseEntity!=null)
			responseEntityClass = responseEntity.getClass();
		if(responseEntityClass == null)
			responseEntityClass = String.class;
		
		Invocation.Builder invocationBuilder = client.target(uniformResourceIdentifier).request();
		ObjectByObjectMap headersMap = getHeadersMap();
		if(Boolean.TRUE.equals(__inject__(MapHelper.class).isNotEmpty(headersMap))) {
			for(Map.Entry<Object, Object> index : headersMap.getEntries()) {
				Object key = index.getKey();
				if(key!=null) {
					Object value = index.getValue();
					if(value!=null)
						invocationBuilder.header(key.toString(),value );	
				}
			}
		}
		Invocation invocation = invocationBuilder.buildGet();
		response = invocation.invoke(responseEntityClass);
		if(responseEntity != null) {
			__inject__(FieldValueCopy.class).setSource(response).setDestination(responseEntity).setIsOverridable(Boolean.FALSE).execute();
		}
		return response;
	}
	
	@Override
	public Client getClient() {
		return client;
	}
	
	@Override
	public RequestProcessor setClient(Client client) {
		this.client = client;
		return this;
	}
	
	@Override
	public UniformResourceIdentifierStringBuilder getUniformResourceIdentifierString() {
		return uniformResourceIdentifierString;
	}
	
	@Override
	public UniformResourceIdentifierStringBuilder getUniformResourceIdentifierString(Boolean injectIfNull) {
		return (UniformResourceIdentifierStringBuilder) __getInjectIfNull__(FIELD_UNIFORM_RESOURCE_IDENTIFIER_STRING, injectIfNull);
	}
	
	@Override
	public RequestProcessor setUniformResourceIdentifierString(UniformResourceIdentifierStringBuilder uniformResourceIdentifierString) {
		this.uniformResourceIdentifierString = uniformResourceIdentifierString;
		return this;
	}
	
	@Override
	public RequestProcessor setUniformResourceIdentifierString(String string) {
		getUniformResourceIdentifierString(Boolean.TRUE).setString(string);
		return this;
	}
	
	@Override
	public ObjectByObjectMap getHeadersMap() {
		return headersMap;
	}
	
	@Override
	public ObjectByObjectMap getHeadersMap(Boolean injectIfNull) {
		return (ObjectByObjectMap) __getInjectIfNull__(FIELD_HEADERS_MAP, injectIfNull);
	}
	
	@Override
	public RequestProcessor setHeadersMap(ObjectByObjectMap headersMap) {
		this.headersMap = headersMap;
		return this;
	}
	
	@Override
	public RequestProcessor setHeaders(Object... keyValues) {
		getHeadersMap(Boolean.TRUE).set(keyValues);
		return this;
	}
	
	@Override
	public RequestProcessor setHeader(Object key, Object value) {
		setHeaders(key,value);
		return this;
	}
	
	@Override
	public Class<?> getResponseEntityClass() {
		return responseEntityClass;
	}
	
	@Override
	public RequestProcessor setResponseEntityClass(Class<?> responseEntityClass) {
		this.responseEntityClass = responseEntityClass;
		return this;
	}
	
	@Override
	public Object getResponseEntity() {
		return responseEntity;
	}
	
	@Override
	public RequestProcessor setResponseEntity(Object responseEntity) {
		this.responseEntity = responseEntity;
		return this;
	}
	
	@Override
	public String getUniformResourceIdentifierStringFormat() {
		return uniformResourceIdentifierStringFormat;
	}
	
	@Override
	public RequestProcessor setUniformResourceIdentifierStringFormat(String uniformResourceIdentifierStringFormat) {
		this.uniformResourceIdentifierStringFormat = uniformResourceIdentifierStringFormat;
		return this;
	}
	
	/**/
	
	public static final String FIELD_UNIFORM_RESOURCE_IDENTIFIER_STRING = "uniformResourceIdentifierString";
	public static final String FIELD_HEADERS_MAP = "headersMap";
}
