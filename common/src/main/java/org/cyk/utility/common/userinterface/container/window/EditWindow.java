package org.cyk.utility.common.userinterface.container.window;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.container.Form;

public class EditWindow extends Window implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected Class<Form.Master> getFormMasterClass() {
		return (Class<Form.Master>) EditWindow.FormMaster.ClassLocator.getInstance().locate(actionOnClass);
	}
	
	/**/
	
	public static class FormMaster extends Form.Master implements Serializable {
		private static final long serialVersionUID = 1L;
	
		/**/
		
		public static class ClassLocator extends org.cyk.utility.common.ClassLocator implements Serializable {

			private static final long serialVersionUID = -3187769614985951029L;

			private static ClassLocator INSTANCE;
			
			public ClassLocator() {
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
					protected String[] getSystemIdentifiers(Class<?> aClass) {
						return ArrayUtils.addAll(super.getSystemIdentifiers(aClass), "") ;
					}
					
					@Override
					protected String[] getModulePrefixes() {
						return new String[]{"ui.web.primefaces.page"};
					}
					
					@Override
					protected String[] getModuleSuffixes() {
						return new String[]{"FormMaster","EditWindow$FormMaster"};
					}
					
					@Override
					protected String[] __execute__(Class<?> aClass) {
						String[] names =  super.__execute__(aClass);
						for(int index = 0 ; index < names.length ; index++)
							if(StringUtils.contains(names[index], "org.cyk.system.ui."))
								names[index] = StringUtils.replace(names[index],"org.cyk.system.ui.", "org.cyk.ui.");
						return names;
					}
					
				});
				getClassLocatorListeners().add(listener);
			}
			
			@Override
			protected Class<?> getDefault(Class<?> aClass) {
				return Form.Master.class;
			}
			
			public static ClassLocator getInstance() {
				if(INSTANCE == null){
					INSTANCE = ClassHelper.getInstance().instanciateOne(ClassLocator.class);
				}
				return INSTANCE;
			}
		}
		
	}
	
	

}
