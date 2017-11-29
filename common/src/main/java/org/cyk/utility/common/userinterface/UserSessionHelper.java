package org.cyk.utility.common.userinterface;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.AbstractHelper;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;

@Singleton
public class UserSessionHelper extends AbstractHelper implements Serializable {
	private static final long serialVersionUID = 1L;

	static {
		ClassHelper.getInstance().map(Listener.class, Listener.Adapter.Default.class,Boolean.FALSE);
	}
	
	private static UserSessionHelper INSTANCE;
	
	public static UserSessionHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new UserSessionHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public String getUniformResourceLocator(UniformResourceLocatorHelper.Identifier identifier,Object userSession){
		Listener listener = ClassHelper.getInstance().instanciateOne(Listener.class);
		return listener.getUniformResourceLocator(identifier, userSession);
	}
	
	public String getUniformResourceLocator(UniformResourceLocatorHelper.Identifier identifier){
		Listener listener = ClassHelper.getInstance().instanciateOne(Listener.class);
		return listener.getUniformResourceLocator(identifier);
	}
	
	/**/
	
	public static interface Listener {
		
		Object get();
		String getUniformResourceLocator(UniformResourceLocatorHelper.Identifier identifier,Object userSession);
		String getUniformResourceLocator(UniformResourceLocatorHelper.Identifier identifier);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@Override
				public String getUniformResourceLocator(UniformResourceLocatorHelper.Identifier identifier, Object userSession) {
					if(UniformResourceLocatorHelper.Identifier.HOME.equals(identifier))
						return UniformResourceLocatorHelper.getInstance().stringify(UniformResourceLocatorHelper.PathStringifier.Adapter.Default.IDENTIFIER_HOME);
					return super.getUniformResourceLocator(identifier, userSession);
				}
				
				@Override
				public String getUniformResourceLocator(UniformResourceLocatorHelper.Identifier identifier) {
					return getUniformResourceLocator(identifier,get());
				}
				
			}
			
			@Override
			public Object get() {
				return null;
			}
			
			@Override
			public String getUniformResourceLocator(UniformResourceLocatorHelper.Identifier identifier) {
				return null;
			}
			
			@Override
			public String getUniformResourceLocator(UniformResourceLocatorHelper.Identifier identifier, Object userSession) {
				return null;
			}
		}
	}
	
}
