package org.cyk.utility.__kernel__.protocol.http;

import java.io.Serializable;
import java.net.http.HttpClient;

public abstract class AbstractHttpClientGetterImpl implements HttpClientGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public HttpClient get() {
		HttpClient.Builder builder = __getBuilder__();
		if(builder == null)
			return null;
		return builder.build();
	}
	
	protected HttpClient.Builder __getBuilder__() {
		return HttpClient.newBuilder();
	}
	
}
