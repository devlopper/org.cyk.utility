package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.client.controller.component.ComponentBuilder;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.DataFieldDescriptionsGetter;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.field.FieldDescription;
import org.cyk.utility.field.FieldDescriptions;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionAdd;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionUpdate;

public class WindowContainerManagedWindowBuilderEditDataDefaultImpl extends AbstractWindowContainerManagedWindowBuilderEditDataImpl implements WindowContainerManagedWindowBuilderEditDataDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(Form form, SystemAction systemAction, Data data, ViewBuilder viewBuilder) {
		Boolean isEditable = systemAction instanceof SystemActionCreate || systemAction instanceof SystemActionUpdate || systemAction instanceof SystemActionAdd|| data.getIdentifier() == null;
		FieldDescriptions fieldDescriptions = __inject__(DataFieldDescriptionsGetter.class).setSystemAction(systemAction).execute().getOutput();
		if(__injectCollectionHelper__().isNotEmpty(fieldDescriptions)) {
			for(FieldDescription index : fieldDescriptions.get()) {
				if(__injectStringHelper__().isNotBlank(index.getName())) {
					String[] strings = __inject__(FieldHelper.class).disjoin(index.getName()).get().toArray(new String[] {});
					if(__inject__(ArrayHelper.class).isNotEmpty(strings)) {
						ComponentBuilder<?> component = viewBuilder.addInputBuilderByObjectByFieldNames(data, isEditable, strings);
						if(component instanceof InputBuilder<?, ?>) {
							InputBuilder<?, ?> input = (InputBuilder<?, ?>) component;
							input.setIsNullable(index.getIsNullable());	
							String name = index.getName();
							if(__inject__(StringHelper.class).isNotBlank(name))
								input.getLabel(Boolean.TRUE).setValue(name);
						}						
					}
				}		
			}
		}
	}

}
