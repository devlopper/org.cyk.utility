package org.cyk.utility.common.security;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.AbstractHelper;
import org.cyk.utility.common.helper.ClassHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Singleton
public class SecurityHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(Listener.class, Shiro.Adapter.class,Boolean.FALSE);
	}
	
	private static SecurityHelper INSTANCE;
	
	public static SecurityHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new SecurityHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public void initialize(){
		ClassHelper.getInstance().instanciateOne(Listener.class).initialize();
	}
	
	public void login(Credentials credentials){
		ClassHelper.getInstance().instanciateOne(Listener.class).login(credentials);
	}
	
	public void logout(){
		ClassHelper.getInstance().instanciateOne(Listener.class).logout();
	}
	
	public Boolean getIsAuthenticated(){
		return ClassHelper.getInstance().instanciateOne(Listener.class).getIsAuthenticated();
	}
	
	public Object getPrincipal(){
		return ClassHelper.getInstance().instanciateOne(Listener.class).getPrincipal();
	}
	
	public Boolean getHasRole(String role){
		return ClassHelper.getInstance().instanciateOne(Listener.class).getHasRole(role);
	}
	
	public static interface Listener{
		
		void initialize();
		void login(Credentials credentials);
		void login(String username,String password);
		void logout();
		Boolean getIsAuthenticated();
		Object getPrincipal();
		Boolean getHasRole(String role);
		
		public static class Adapter extends AbstractBean implements Listener , Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public void login(String username, String password) {
					login(new Credentials().setUsername(username).setPassword(password));
				}
				
			}
			
			@Override
			public void initialize() {}
			
			@Override
			public void login(Credentials credentials) {}
			
			@Override
			public void login(String username, String password) {}
			
			@Override
			public void logout() {}
			
			@Override
			public Boolean getIsAuthenticated() {
				return null;
			}
			
			@Override
			public Object getPrincipal() {
				return null;
			}
			
			@Override
			public Boolean getHasRole(String role) {
				return null;
			}
			
		}
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Credentials implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String username;
		private String password;
		
		public static final String FIELD_USERNAME = "username";
		public static final String FIELD_PASSWORD = "password";
	}
	
}
