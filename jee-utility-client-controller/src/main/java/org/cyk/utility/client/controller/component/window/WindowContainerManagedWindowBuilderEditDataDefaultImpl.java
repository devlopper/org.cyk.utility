package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.DataHelper;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.client.controller.data.Row;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionAdd;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionUpdate;

public class WindowContainerManagedWindowBuilderEditDataDefaultImpl extends AbstractWindowContainerManagedWindowBuilderEditDataImpl implements WindowContainerManagedWindowBuilderEditDataDefault,Serializable {
	private static final long serialVersionUID = 1L;

	private Boolean isDataHasBeenInstanciatedLocally;
	
	@Override
	protected Data __getData__(WindowBuilder window, SystemAction systemAction, Class<? extends Form> formClass,Class<? extends Row> rowClass) {
		if(systemAction instanceof SystemActionRead && __injectCollectionHelper__().isEmpty(systemAction.getEntities())) {
			Data data = (Data) __inject__(systemAction.getEntityClass());
			systemAction.getEntities(Boolean.TRUE).add(data);
			isDataHasBeenInstanciatedLocally = Boolean.TRUE;
			return data;
		}
		return super.__getData__(window, systemAction, formClass, rowClass);
	}
	
	@Override
	protected Boolean __isAddCommandable__(WindowBuilder window, SystemAction systemAction,Class<? extends Form> formClass, Class<? extends Row> rowClass) {
		if(systemAction instanceof SystemActionRead)
			return Boolean.TRUE.equals(isDataHasBeenInstanciatedLocally);
		return super.__isAddCommandable__(window, systemAction, formClass, rowClass);
	}
	
	@Override
	protected void __execute__(Form form, SystemAction systemAction, Data data, ViewBuilder viewBuilder) {
		Strings fieldNames = null;
		if(systemAction instanceof SystemActionRead && Boolean.TRUE.equals(isDataHasBeenInstanciatedLocally)) {
			fieldNames = __inject__(Strings.class).add("code");
		}
		
		if(fieldNames == null)
			fieldNames = __inject__(DataHelper.class).getPropertiesFieldsNames(getSystemAction().getEntityClass());
		
		if(__injectCollectionHelper__().isNotEmpty(fieldNames))
			for(String index : fieldNames.get())
				viewBuilder.addInputBuilderByObjectByFieldNames(data, systemAction instanceof SystemActionCreate || systemAction instanceof SystemActionUpdate || systemAction instanceof SystemActionAdd
						|| Boolean.TRUE.equals(isDataHasBeenInstanciatedLocally), index);
	}

}
