package org.cyk.utility.client.controller.navigation;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang.StringUtils;
import org.cyk.utility.string.AbstractStringFunctionImpl;
import org.cyk.utility.string.Case;
import org.cyk.utility.string.StringFormat;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionUpdate;

@Dependent @Deprecated
public class NavigationIdentifierStringBuilderImpl extends AbstractStringFunctionImpl implements NavigationIdentifierStringBuilder,Serializable {
	private static final long serialVersionUID = 1L;

	private SystemAction systemAction;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		getFormat(Boolean.TRUE).setValue(FORMAT);
	}
	
	@Override
	protected StringFormat __getFormat__(StringFormat format) {
		SystemAction systemAction = getSystemAction();
		if(systemAction != null) {
			if(systemAction.getEntities()!=null && systemAction.getEntities().getElementClass()!=null)
				format.setArguments( FORMAT_ARGUMENT_ENTITY,__injectStringHelper__().applyCase(systemAction.getEntities().getElementClass().getSimpleName(), Case.FIRST_CHARACTER_LOWER));
			else
				format.setArguments( FORMAT_ARGUMENT_ENTITY,__ENTITY__);
			
			if(systemAction instanceof SystemActionCreate || systemAction instanceof SystemActionUpdate || systemAction instanceof SystemActionDelete)
				format.setArguments( FORMAT_ARGUMENT_ACTION,EDIT);
			else {
				String action = StringUtils.substringBetween(systemAction.getClass().getSimpleName(), SystemAction.class.getSimpleName(), "Impl") ;
				format.setArguments( FORMAT_ARGUMENT_ACTION,action);
			}
		}
		return format;
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}

	@Override
	public NavigationIdentifierStringBuilder setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
	}
	
	/**/
	
	private static final String EDIT = "Edit";
	
	private static final String __ENTITY__ = "__entity__";
}
