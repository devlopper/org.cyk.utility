package org.cyk.utility.__kernel__.report.jasper;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.__kernel__.variable.VariableName;

import com.jaspersoft.jasperserver.jaxrs.client.core.JasperserverRestClient;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

public interface SessionGetter {

	Session get();
	
	Session get(String username,String password);
	
	void logout();
	
	public static abstract class AbstractImpl extends AbstractObject implements SessionGetter,Serializable {
		
		private static Session SESSION;
		
		@Override
		public Session get(String username, String password) {
			if(SESSION == null) {
				JasperserverRestClient client = JasperserverRestClientGetter.getInstance().get();
				SESSION = client.authenticate(username, password);
				LogHelper.logInfo("Jasper server session has been instantiated", getClass());
			}
			return SESSION;
		}
		
		@Override
		public Session get() {
			if(!ConfigurationHelper.is(VariableName.JASPER_ENABLED)) {
				LogHelper.logWarning("Jasper reporter is not enabled", getClass());
				return null;
			}
			String username = ValueHelper.throwIfBlank("jasper report server user name", ConfigurationHelper.getValueAsString(VariableName.JASPER_SERVER_CREDENTIAL_USERNAME));
			String password = ValueHelper.throwIfBlank("jasper report server user password", ConfigurationHelper.getValueAsString(VariableName.JASPER_SERVER_CREDENTIAL_PASSWORD));
			return get(username,password);
		}
		
		@Override
		public void logout() {
			if(SESSION == null)
				return;
			SESSION.logout();
		}
	}
	
	/**/
	
	static SessionGetter getInstance() {
		return Helper.getInstance(SessionGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}