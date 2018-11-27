package org.cyk.utility.client.controller.navigation;

import java.io.Serializable;

import org.cyk.utility.string.AbstractStringFunctionImpl;
import org.cyk.utility.string.Case;
import org.cyk.utility.string.StringFormat;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionList;
import org.cyk.utility.system.action.SystemActionProcess;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionSelect;
import org.cyk.utility.system.action.SystemActionUpdate;

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
			else if(systemAction instanceof SystemActionRead)
				format.setArguments( FORMAT_ARGUMENT_ACTION,READ);
			else if(systemAction instanceof SystemActionList)
				format.setArguments( FORMAT_ARGUMENT_ACTION,LIST);
			else if(systemAction instanceof SystemActionSelect)
				format.setArguments( FORMAT_ARGUMENT_ACTION,SELECT);
			else if(systemAction instanceof SystemActionProcess)
				format.setArguments( FORMAT_ARGUMENT_ACTION,PROCESS);
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
	private static final String READ = "Read";
	private static final String LIST = "List";
	private static final String SELECT = "Select";
	private static final String PROCESS = "Process";
	private static final String __ENTITY__ = "__entity__";
}
