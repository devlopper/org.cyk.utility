package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.string.AbstractStringsFunctionImpl;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;

public class FieldsNamesGetterImpl extends AbstractStringsFunctionImpl implements FieldsNamesGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private SystemAction systemAction;
	
	@Override
	protected Strings __execute__() throws Exception {
		Strings names = null;
		SystemAction systemAction = getSystemAction();
		if(names == null) {
			if(systemAction!=null)
				names = __inject__(DataHelper.class).getPropertiesFieldsNames(systemAction.getEntityClass());
		}
		return names;
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}

	@Override
	public FieldsNamesGetter setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
	}

}
