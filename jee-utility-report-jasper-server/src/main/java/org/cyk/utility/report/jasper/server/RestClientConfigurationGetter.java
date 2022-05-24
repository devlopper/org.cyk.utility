package org.cyk.utility.report.jasper.server;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;

public interface RestClientConfigurationGetter {

	RestClientConfiguration get();
	
	public static abstract class AbstractImpl extends AbstractObject implements RestClientConfigurationGetter,Serializable {
		
		@Inject private RestClientConfigurationBuilder restClientConfigurationBuilder;
		
		@Override
		public RestClientConfiguration get() {
			if(Boolean.TRUE.equals(VALUE.isHasBeenSet()))
				return (RestClientConfiguration) VALUE.get();
			VALUE.set(restClientConfigurationBuilder.build());
			return (RestClientConfiguration) VALUE.get();
		}
	}
	
	/**/
	
	static RestClientConfigurationGetter getInstance() {
		return Helper.getInstance(RestClientConfigurationGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	Value VALUE = new Value();
}