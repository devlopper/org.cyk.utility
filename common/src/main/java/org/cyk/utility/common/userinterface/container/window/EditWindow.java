package org.cyk.utility.common.userinterface.container.window;

import java.io.Serializable;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.container.Form;

public class EditWindow extends Window implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected Class<? extends Form.Master> getFormMasterClass() {
		return (Class<Form.Master>) EditWindow.FormMaster.ClassLocator.getInstance().locate(actionOnClass);
	}
	
	/**/
	
	public static class FormMaster extends Form.Master implements Serializable {
		private static final long serialVersionUID = 1L;
	
		/**/
		
		public static class ClassLocator extends Form.Master.ClassLocator implements Serializable {
			private static final long serialVersionUID = 1L;

			private static ClassLocator INSTANCE;
			
			public ClassLocator() {
				super("Edit");
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
