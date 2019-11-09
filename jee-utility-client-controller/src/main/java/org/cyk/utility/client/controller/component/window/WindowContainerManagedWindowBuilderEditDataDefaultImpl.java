package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.system.action.SystemActionAdd;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.__kernel__.system.action.SystemActionFieldsNamesGetter;
import org.cyk.utility.__kernel__.system.action.SystemActionUpdate;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.DataIdentifiedByString;
import org.cyk.utility.client.controller.data.Form;

public class WindowContainerManagedWindowBuilderEditDataDefaultImpl extends AbstractWindowContainerManagedWindowBuilderEditDataImpl implements WindowContainerManagedWindowBuilderEditDataDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(Form form, SystemAction systemAction, Data data, ViewBuilder viewBuilder) {
		Boolean isEditable = systemAction instanceof SystemActionCreate || systemAction instanceof SystemActionUpdate || systemAction instanceof SystemActionAdd|| (data instanceof DataIdentifiedByString &&  ((DataIdentifiedByString)data).getIdentifier() == null);
		/*
		FieldDescriptions fieldDescriptions = __inject__(DataFieldDescriptionsGetter.class).setSystemAction(systemAction).execute().getOutput();
		if(CollectionHelper.isNotEmpty(fieldDescriptions)) {
			for(FieldDescription index : fieldDescriptions.get()) {
				if(StringHelper.isNotBlank(index.getName())) {
					String[] strings = FieldHelper.disjoin(index.getName()).toArray(new String[] {});
					if(__inject__(ArrayHelper.class).isNotEmpty(strings)) {
						ComponentBuilder<?> component = viewBuilder.addInputBuilderByObjectByFieldNames(data, isEditable, strings);
						if(component instanceof InputBuilder<?, ?>) {
							InputBuilder<?, ?> input = (InputBuilder<?, ?>) component;
							input.setIsNullable(index.getIsNullable());	
							String name = index.getName();
							if(StringHelper.isNotBlank(name))
								input.getLabel(Boolean.TRUE).setValue(__buildInternationalizationString__(name,Case.FIRST_CHARACTER_UPPER_REMAINDER_LOWER));
						}
					}
				}		
			}
		}
		*/
		//
		
		Collection<String> fieldsNames = SystemActionFieldsNamesGetter.getInstance().get(systemAction);
		if(CollectionHelper.isEmpty(fieldsNames))
			return;
		for(String index : fieldsNames) {
			viewBuilder.addInputBuilderByObjectByFieldNames(data, isEditable, FieldHelper.disjoin(index).toArray(new String[] {}));				
		}
	}

}
