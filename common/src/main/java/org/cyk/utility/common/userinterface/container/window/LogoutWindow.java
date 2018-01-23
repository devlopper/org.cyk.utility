package org.cyk.utility.common.userinterface.container.window;

import java.io.Serializable;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.container.Form;

public class LogoutWindow extends Window implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String getPropertyTitleIdentifier() {
		return "userinterface.window.logout.title.label";
	}
	
	@Override
	protected Class<? extends Form.Master> getFormMasterClass() {
		return ClassHelper.getInstance().getMapping(FormMaster.class, Boolean.TRUE);
	}
	
	@Override
	protected Boolean getIsAutomaticallySetForm() {
		return Boolean.TRUE;
	}
	
	@Override
	protected void __setForm__() {
		getPropertiesMap().setAction(Constant.Action.LOGOUT);
		getPropertiesMap().setActionOnClass(Object.class);
		super.__setForm__();
	}
	
	/**/
	
	public static class FormMaster extends Form.Master implements Serializable {
		private static final long serialVersionUID = 1L;
	
	}
	
}
