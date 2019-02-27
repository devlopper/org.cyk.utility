package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.system.action.SystemAction;

public class DataGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Data> implements DataGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private SystemAction systemAction;
	
	@Override
	protected Data __execute__() throws Exception {
		Data data = null;
		SystemAction systemAction = getSystemAction();
		if(data == null) {
			if(systemAction!=null)
				data = (Data) systemAction.getEntities().getFirst();
		}
		return data;
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}

	@Override
	public DataGetter setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
	}

}
