package org.cyk.utility.__kernel__.report.jasper;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface JasperserverRestClientGetter {
	
	JasperserverRestClient get(Arguments arguments);
	
	default JasperserverRestClient get() {
		return get(null);
	}
	
	public static abstract class AbstractImpl extends AbstractObject implements JasperserverRestClientGetter,Serializable {
		
		private static JasperserverRestClient CLIENT;
		
		@Override
		public JasperserverRestClient get(Arguments arguments) {
			if(CLIENT == null) {
				CLIENT = new JasperserverRestClient(RestClientConfigurationGetter.getInstance().get());
				LogHelper.logInfo("Jasper server rest client has been instantiated", getClass());
			}
			return CLIENT;
		}
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments implements Serializable {
		private String username,password;
	}
	
	/**/
	
	static JasperserverRestClientGetter getInstance() {
		return Helper.getInstance(JasperserverRestClientGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}