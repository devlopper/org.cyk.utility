package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.DataHelper;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;

public class WindowContainerManagedWindowBuilderEditDataDefaultImpl extends AbstractWindowContainerManagedWindowBuilderEditDataImpl implements WindowContainerManagedWindowBuilderEditDataDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(Form form, SystemAction systemAction, Data data, ViewBuilder viewBuilder) {
		Strings fieldNames = __inject__(DataHelper.class).getPropertiesFieldsNames(getSystemAction().getEntityClass());
		if(__injectCollectionHelper__().isNotEmpty(fieldNames))
			for(String index : fieldNames.get())
				viewBuilder.addInputBuilderByObjectByFieldNames(data,systemAction, index);
	}

}
