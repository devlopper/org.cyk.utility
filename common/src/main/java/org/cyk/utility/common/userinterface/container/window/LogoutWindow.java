package org.cyk.utility.common.userinterface.container.window;

import java.io.Serializable;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.container.Form;

public class LogoutWindow extends Window implements Serializable {
	private static final long serialVersionUID = 1L;

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
		action = Constant.Action.LOGOUT;
		actionOnClass = Object.class;
		super.__setForm__();
	}
	
	/**/
	
	public static class FormMaster extends Form.Master implements Serializable {
		private static final long serialVersionUID = 1L;
	
		/**/
		
		@Override
		public Component prepare() {
			super.prepare();
			//controls
			//inputs
			
			//commands
			setSubmitCommandActionAdapterClass(ClassHelper.getInstance().getMapping(SubmitCommandActionAdapter.class));
			return this;
		}
		
		/**/
		
		public static class SubmitCommandActionAdapter extends Form.Master.SubmitCommandActionAdapter implements Serializable {
			private static final long serialVersionUID = 1L;
			
			
		}
		
	}
	
}
