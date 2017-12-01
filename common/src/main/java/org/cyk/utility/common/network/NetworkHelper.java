package org.cyk.utility.common.network;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.AbstractHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton
public class NetworkHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static NetworkHelper INSTANCE;
	
	public static NetworkHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new NetworkHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Node extends AbstractBean implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String name;
		
		/**/
		
		@Getter @Setter @Accessors(chain=true)
		public static class Service extends AbstractBean implements Serializable {
			private static final long serialVersionUID = 1L;
			
			private String scheme;
			private Node node = new Node();
			private Long port;
			
			private String driver;
		
			public String computeUrl(){
				return scheme+"://"+node.getName()+":"+port;
			}
			
		}
	}
	
	
	
}
