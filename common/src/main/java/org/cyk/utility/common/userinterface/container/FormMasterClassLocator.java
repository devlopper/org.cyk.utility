package org.cyk.utility.common.userinterface.container;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.Constant;
import org.cyk.utility.common.helper.StringHelper;

public class FormMasterClassLocator extends org.cyk.utility.common.ClassLocator implements Serializable {

	private static final long serialVersionUID = -3187769614985951029L;

	private Constant.Action action ;
	
	public FormMasterClassLocator(Constant.Action action) {
		this.action = action;
		setClassType(this.action+"FormMaster");
		Listener listener = new Listener.Adapter(){
			private static final long serialVersionUID = -979036256355287919L;

			@Override
			public Boolean isLocatable(Class<?> basedClass) {
				return Boolean.TRUE;
			}
		};
		
		listener.setGetNameMethod(new GetOrgCykSystem(this));
		getClassLocatorListeners().add(listener);
	}
	
	@Override
	protected Class<?> getDefault(Class<?> aClass) {
		return Form.Master.class;
	}
	
	/**/
	
	public static class GetOrgCykSystem extends Listener.AbstractGetOrgCykSystem {
		private static final long serialVersionUID = 1L;

		private FormMasterClassLocator formMasterClassLocator;
		
		public GetOrgCykSystem(FormMasterClassLocator formMasterClassLocator) {
			this.formMasterClassLocator = formMasterClassLocator;
		}
		
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
			return new String[]{"FormMaster",StringHelper.getInstance().applyCaseType(formMasterClassLocator.action.name(), StringHelper.CaseType.FURL)
					+"Window$FormMaster"};
		}
		
		@Override
		protected String[] __execute__(Class<?> aClass) {
			String[] names =  super.__execute__(aClass);
			for(int index = 0 ; index < names.length ; index++)
				if(StringUtils.contains(names[index], "org.cyk.system.ui."))
					names[index] = StringUtils.replace(names[index],"org.cyk.system.ui.", "org.cyk.ui.");
			return names;
		}
	}
	
}