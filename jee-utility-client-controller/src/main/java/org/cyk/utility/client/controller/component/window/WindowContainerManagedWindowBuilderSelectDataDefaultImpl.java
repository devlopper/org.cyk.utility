package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.DataFieldsNamesGetter;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;

public class WindowContainerManagedWindowBuilderSelectDataDefaultImpl extends AbstractWindowContainerManagedWindowBuilderSelectDataImpl implements WindowContainerManagedWindowBuilderSelectDataDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(Form form, SystemAction systemAction, Data data, ViewBuilder viewBuilder) {
		Strings fieldNames = __inject__(DataFieldsNamesGetter.class).setSystemAction(systemAction).execute().getOutput();
		if(__injectCollectionHelper__().isNotEmpty(fieldNames)) {
			for(String index : fieldNames.get()) {
				if(__injectStringHelper__().isNotBlank(index)) {
					String[] strings = __inject__(FieldHelper.class).disjoin(index).get().toArray(new String[] {});
					if(__inject__(ArrayHelper.class).isNotEmpty(strings)) {
						viewBuilder.addInputBuilderByObjectByFieldNames(data, Boolean.TRUE, strings);
					}
				}		
			}
		}
	}

}
