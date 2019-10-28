package org.cyk.utility.__kernel__.protocol.http;

import java.net.http.HttpClient;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface HttpClientGetter {

	HttpClient get();
	
	static HttpClientGetter getInstance() {
		HttpClientGetter instance = (HttpClientGetter) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(HttpClientGetter.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", HttpClientGetter.class);
		return instance;
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
	
}
