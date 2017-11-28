package org.cyk.utility.common.userinterface.container.window;

import java.io.Serializable;
import java.util.ArrayList;

import javax.validation.constraints.NotNull;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.Layout;
import org.cyk.utility.common.userinterface.container.Form;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class LoginWindow extends Window implements Serializable {
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
		actionOnClassInstances = new ArrayList<Object>();
		actionOnClassInstances.add(new Credentials());
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
			setEditable(Boolean.TRUE);
			Form.Detail detail = getDetail();
			detail.getLayout().setType(Layout.Type.ADAPTIVE);
			detail.add(Credentials.FIELD_USERNAME).addBreak();
			detail.add(Credentials.FIELD_PASSWORD).addBreak();
			return this;
		}
		
	}
	
	
	@Getter @Setter @Accessors(chain=true)
	public static class Credentials implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@NotNull private String username;
		@NotNull private String password;
		
		public static final String FIELD_USERNAME = "username";
		public static final String FIELD_PASSWORD = "password";
	}
}
