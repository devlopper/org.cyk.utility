package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.clazz.AbstractClassFunctionImpl;
import org.cyk.utility.client.controller.ControllerLayer;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionList;
import org.cyk.utility.system.action.SystemActionSelect;

@SuppressWarnings("rawtypes")
public class RowClassGetterImpl extends AbstractClassFunctionImpl implements RowClassGetter,Serializable {
	private static final long serialVersionUID = 1L;

	private SystemAction systemAction;
	
	@Override
	protected Class __execute__() throws Exception {
		Class clazz = null;
		SystemAction systemAction = getSystemAction();
		clazz = __inject__(ControllerLayer.class).getRowClass(systemAction);
		if(clazz == null) {
			if(systemAction instanceof SystemActionSelect) {
				Class<?> entityClass = null;
				if(systemAction.getEntities()!=null)
					entityClass = systemAction.getEntities().getElementClass();
				systemAction = __inject__(SystemActionList.class).setEntityClass(entityClass);
				clazz = __inject__(ControllerLayer.class).getRowClass(systemAction);
			}
		}
		return clazz;
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}

	@Override
	public RowClassGetter setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
	}

}
