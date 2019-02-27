package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.string.AbstractStringsFunctionImpl;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionRead;

public class DataMethodsNamesGetterImpl extends AbstractStringsFunctionImpl implements DataMethodsNamesGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private SystemAction systemAction;
	
	@Override
	protected Strings __execute__() throws Exception {
		Strings names = null;
		SystemAction systemAction = getSystemAction();
		if(names == null) {
			if(systemAction!=null) {
				Data data = null;
				if(__injectCollectionHelper__().isNotEmpty(systemAction.getEntities()))
					data = (Data) systemAction.getEntities().getFirst();
				Object dataIdentifier = __injectFieldHelper__().getFieldValueSystemOrBusinessIdentifier(data);
				if(dataIdentifier!=null && Boolean.TRUE.equals(__injectClassHelper__().isInstanceOfOne(systemAction.getClass(), SystemActionRead.class))) {
					
				}else {
					names = __inject__(Strings.class).add(Form.METHOD_SUBMIT);	
				}
			}
		}
		return names;
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}

	@Override
	public DataMethodsNamesGetter setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
	}

}
