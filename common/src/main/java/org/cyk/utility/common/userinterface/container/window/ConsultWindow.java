package org.cyk.utility.common.userinterface.container.window;

import java.io.Serializable;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.container.Form;

public class ConsultWindow extends Window implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends Form.Master> getFormMasterClass() {
		return (Class<Form.Master>) ConsultWindow.FormMaster.ClassLocator.getInstance().locate((Class<?>)getPropertiesMap().getActionOnClass());
	}
	
	/**/
	
	public static class FormMaster extends Form.Master implements Serializable {
		private static final long serialVersionUID = 1L;
	
		/**/
		
		public static class ClassLocator extends Form.Master.ClassLocator implements Serializable {
			private static final long serialVersionUID = 1L;

			private static ClassLocator INSTANCE;
			
			public ClassLocator() {
				super(Constant.Action.CONSULT);
			}
			
			@Override
			public Class<?> locate(Class<?> basedClass) {
				Class<?> clazz = super.locate(basedClass);
				if(clazz.equals(Form.Master.class))
					return EditWindow.FormMaster.ClassLocator.getInstance().locate(basedClass);
				return clazz;
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
