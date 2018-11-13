package org.cyk.utility.client.controller.navigation;

import java.io.Serializable;

import org.cyk.utility.string.AbstractStringFunctionImpl;
import org.cyk.utility.string.Case;
import org.cyk.utility.string.StringFormat;
import org.cyk.utility.system.action.SystemAction;

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
			format.setArguments( FORMAT_ARGUMENT_ACTION,"Edit"/*systemAction.getIdentifier()*/);
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
	
}
