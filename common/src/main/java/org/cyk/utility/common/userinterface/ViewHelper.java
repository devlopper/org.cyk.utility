package org.cyk.utility.common.userinterface;

import java.io.Serializable;

import javax.inject.Singleton;

import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.AbstractHelper;
import org.cyk.utility.common.helper.ClassHelper;

@Singleton
public class ViewHelper extends AbstractHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private static ViewHelper INSTANCE;
	
	public static ViewHelper getInstance() {
		if(INSTANCE == null)
			INSTANCE = new ViewHelper();
		return INSTANCE;
	}
	
	@Override
	protected void initialisation() {
		INSTANCE = this;
		super.initialisation();
	}
	
	public Object getParameter(String name){
		Listener listener = ClassHelper.getInstance().instanciateOne(Listener.Adapter.Default.class);
		return listener.getParameter(name);
	}
	
	/**/
	
	public static interface Listener {
		
		Object getParameter(String name);
		
		public static class Adapter extends AbstractBean implements Listener,Serializable {
			private static final long serialVersionUID = 1L;
			
			@Override
			public Object getParameter(String name) {
				return null;
			}
			
			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
												
			}
		}
	}
	
}
