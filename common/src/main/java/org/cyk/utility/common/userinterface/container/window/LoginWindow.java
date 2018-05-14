package org.cyk.utility.common.userinterface.container.window;

import java.io.Serializable;

import org.cyk.utility.common.Constant;
import org.cyk.utility.common.Properties;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.security.SecurityHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.Layout;
import org.cyk.utility.common.userinterface.container.form.FormDetail;
import org.cyk.utility.common.userinterface.container.form.Form;

public class LoginWindow extends Window implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Properties instanciateProperties() {
		return super.instanciateProperties().setLayoutCardinalPointNorthRendered(Boolean.FALSE)
				.setLayoutCardinalPointEastRendered(Boolean.FALSE)
				.setLayoutCardinalPointSouthRendered(Boolean.FALSE)
				.setLayoutCardinalPointWestRendered(Boolean.FALSE)
				.setLayoutCardinalPointCenterRendered(Boolean.TRUE);
	}
	
	@Override
	protected String getPropertyTitleIdentifier() {
		return "userinterface.window.login.title.label";
	}
	
	@Override
	protected Class<? extends Form> getFormMasterClass() {
		return ClassHelper.getInstance().getMapping(FormMaster.class, Boolean.TRUE);
	}
	
	@Override
	protected Boolean getIsAutomaticallySetForm() {
		return Boolean.TRUE;
	}
	
	@Override
	protected void __setForm__() {
		getPropertiesMap().setAction(Constant.Action.LOGIN);
		getPropertiesMap().setActionOnClass(SecurityHelper.Credentials.class);
		super.__setForm__();
	}
	
	/**/
	
	public static class FormMaster extends Form implements Serializable {
		private static final long serialVersionUID = 1L;
	
		/**/
		
		@Override
		public Component prepare() {
			super.prepare();
			//controls
			//inputs
			FormDetail detail = getDetail();
			detail.getLayout().setType(Layout.Type.ADAPTIVE);
			detail.add(SecurityHelper.Credentials.FIELD_USERNAME).addBreak();
			detail.add(SecurityHelper.Credentials.FIELD_PASSWORD).addBreak();
			
			//commands
			setSubmitCommandActionAdapterClass(ClassHelper.getInstance().getMapping(SubmitCommandActionAdapter.class));
			return this;
		}
		
		/**/
		
		public static class SubmitCommandActionAdapter extends Form.SubmitCommandActionAdapter implements Serializable {
			private static final long serialVersionUID = 1L;
			
			
			
		}
		
	}
	
}
