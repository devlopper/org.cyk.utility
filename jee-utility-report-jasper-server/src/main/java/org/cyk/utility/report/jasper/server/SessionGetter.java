package org.cyk.utility.report.jasper.server;

import java.io.Serializable;

import javax.inject.Inject;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

public interface SessionGetter {

	Session get();
	
	Session get(String username,String password);
	
	void logout();
	
	public static abstract class AbstractImpl extends AbstractObject implements SessionGetter,Serializable {
		
		public static Session SESSION;
		
		@Inject private JasperserverRestClientGetter jasperserverRestClientGetter;
		
		@Override
		public Session get(String username, String password) {
			if(SESSION == null) {
				JasperserverRestClient client = jasperserverRestClientGetter.get();
				SESSION = client.authenticate(username, password);
				LogHelper.logInfo(String.format("Jasper server session has been instantiated with username %s ",username), getClass());
			}
			return SESSION;
		}
		
		@Override
		public Session get() {
			if(!ConfigurationHelper.is(VariableName.ENABLED)) {
				LogHelper.logWarning("Jasper reporter is not enabled", getClass());
				return null;
			}
			String username = ValueHelper.throwIfBlank("jasper report server user name", ConfigurationHelper.getValueAsString(VariableName.CREDENTIAL_USERNAME));
			String password = ValueHelper.throwIfBlank("jasper report server user password", ConfigurationHelper.getValueAsString(VariableName.CREDENTIAL_PASSWORD));
			return get(username,password);
		}
		
		@Override
		public void logout() {
			if(SESSION == null)
				return;
			SESSION.logout();
			LogHelper.logInfo("Jasper server session has been logged out", getClass());
		}
	}
	
	/**/
	
	static SessionGetter getInstance() {
		return Helper.getInstance(SessionGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}