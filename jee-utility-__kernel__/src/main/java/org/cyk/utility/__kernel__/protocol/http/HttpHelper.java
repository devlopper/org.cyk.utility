package org.cyk.utility.__kernel__.protocol.http;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;

import org.cyk.utility.__kernel__.string.StringHelper;

public interface HttpHelper {

	@SuppressWarnings("unchecked")
	static <T> HttpResponse<T> get(HttpHelperGetParameter parameter,Class<T> bodyType,HttpClient client) {
		if(parameter.getUri() == null)
			return null;
		if(bodyType == null)
			bodyType = (Class<T>) String.class;
		if(client == null)
			client = HttpClientGetter.getInstance().get();
		HttpRequest request = HttpRequest.newBuilder().GET().uri(parameter.getUri()).build();
		BodyHandler<T> bodyHandler = (BodyHandler<T>) parameter.getBodyHandler();
		if(bodyHandler == null) {
			if(String.class.equals(bodyType))
				bodyHandler = (BodyHandler<T>) BodyHandlers.ofString();
		}
		try {
			return client.send(request, bodyHandler);
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	static <T> HttpResponse<T> get(URI uri,Class<T> bodyType,HttpClient client) {
		if(uri == null)
			return null;
		return get(new HttpHelperGetParameter().setUri(uri),bodyType,client);
	}
	
	static HttpResponse<String> get(URI uri) {
		if(uri == null)
			return null;
		return get(new HttpHelperGetParameter().setUri(uri),String.class,HttpClientGetter.getInstance().get());
	}
	
	static <T> HttpResponse<T> get(String uri,Class<T> bodyType,HttpClient client) {
		if(StringHelper.isBlank(uri))
			return null;
		try {
			return get(new HttpHelperGetParameter().setUri(new URI(uri)),bodyType,client);
		} catch (URISyntaxException exception) {
			throw new RuntimeException(exception);
		}
	}
	
	static HttpResponse<String> get(String uri) {
		if(StringHelper.isBlank(uri))
			return null;
		try {
			return get(new HttpHelperGetParameter().setUri(new URI(uri)),String.class,HttpClientGetter.getInstance().get());
		} catch (URISyntaxException exception) {
			throw new RuntimeException(exception);
		}
	}
	
}
