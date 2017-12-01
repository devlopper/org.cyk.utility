package org.cyk.utility.common.network;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.AbstractHelper;
import org.cyk.utility.common.security.SecurityHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton
public class DatasourceHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static DatasourceHelper INSTANCE;
	
	public static DatasourceHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new DatasourceHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Datasource extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String databaseName,urlFormat="%s/%s";
		
		private SecurityHelper.Credentials credentials = new SecurityHelper.Credentials();
		private NetworkHelper.Node.Service service = new NetworkHelper.Node.Service();
		
		public String computeUrl(){
			return String.format(urlFormat,service.computeUrl(), databaseName);
		}
	}
}
