package org.cyk.utility.__kernel__.report.jasper;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;

public interface RestClientConfigurationGetter {

	RestClientConfiguration get();
	
	public static abstract class AbstractImpl extends AbstractObject implements RestClientConfigurationGetter,Serializable {
		
		@Override
		public RestClientConfiguration get() {
			if(Boolean.TRUE.equals(VALUE.isHasBeenSet()))
				return (RestClientConfiguration) VALUE.get();
			VALUE.set(RestClientConfigurationBuilder.getInstance().build());
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