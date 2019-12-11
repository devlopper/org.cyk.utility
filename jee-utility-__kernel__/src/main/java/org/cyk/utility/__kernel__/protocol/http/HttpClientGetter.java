package org.cyk.utility.__kernel__.protocol.http;

import java.net.http.HttpClient;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface HttpClientGetter {

	HttpClient get();
	
	/**/
	
	static HttpClientGetter getInstance() {
		return Helper.getInstance(HttpClientGetter.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
}
