package org.cyk.utility.__kernel__.protocol.http;

import java.io.Serializable;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface HttpClientGetter {

	HttpClient get();
	
	public abstract class AbstractImpl implements HttpClientGetter,Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		public HttpClient get() {
			HttpClient.Builder builder = __getBuilder__();
			if(builder == null)
				return null;
			return builder.build();
		}
		
		protected HttpClient.Builder __getBuilder__() {
			return HttpClient.newBuilder().version(Version.HTTP_1_1);
		}		
	}
	
	/**/
	
	static HttpClientGetter getInstance() {
		return Helper.getInstance(HttpClientGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}