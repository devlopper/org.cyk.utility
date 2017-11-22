package org.cyk.utility.common.userinterface.container.window;

import java.io.Serializable;

import org.cyk.utility.common.ClassLocator;
import org.cyk.utility.common.userinterface.container.Form;

public class EditWindow extends Window implements Serializable {
	private static final long serialVersionUID = 1L;

	/**/
	
	public static class FormMasterClassLocator extends ClassLocator implements Serializable {

		private static final long serialVersionUID = -3187769614985951029L;

		private static FormMasterClassLocator INSTANCE;
		
		@Override
		protected void initialisation() {
			INSTANCE = this;
			super.initialisation();
			setClassType("EditFormMaster");
			Listener listener = new Listener.Adapter(){
				private static final long serialVersionUID = -979036256355287919L;

				@Override
				public Boolean isLocatable(Class<?> basedClass) {
					return Boolean.TRUE;
				}
			};
			
			listener.setGetNameMethod(new Listener.AbstractGetOrgCykSystem() {
				private static final long serialVersionUID = -7213562588417233353L;
				@Override
				protected String getBaseClassPackageName() {
					return "model";
				}
				
				@Override
				protected String getModulePrefix() {
					return "ui.web.primefaces.page";
				}
				
				@Override
				protected String[] getModuleSuffixes() {
					return new String[]{"FormMaster","EditWindow$FormMaster"};
				}
				
			});
			getClassLocatorListeners().add(listener);
		}
		
		@Override
		protected Class<?> getDefault(Class<?> aClass) {
			return Form.Master.class;
		}
		
		public static FormMasterClassLocator getInstance() {
			if(INSTANCE == null){
				INSTANCE = new FormMasterClassLocator();
				INSTANCE.postConstruct();
			}
			return INSTANCE;
		}
	}

}
