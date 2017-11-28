package org.cyk.utility.common.userinterface.container.window;

import java.io.Serializable;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.container.Form;

public class LogoutWindow extends Window implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<? extends Form.Master> getFormMasterClass() {
		return ClassHelper.getInstance().getMapping(FormMaster.class, Boolean.TRUE);
	}
	
	/**/
	
	public static class FormMaster extends Form.Master implements Serializable {
		private static final long serialVersionUID = 1L;
	
		/**/
		
	}
	
}
