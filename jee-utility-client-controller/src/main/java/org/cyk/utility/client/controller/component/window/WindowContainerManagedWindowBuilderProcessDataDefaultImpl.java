package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.DataFieldDescriptionsGetter;
import org.cyk.utility.client.controller.data.DataFieldsNamesGetter;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.field.FieldDescription;
import org.cyk.utility.field.FieldDescriptions;

public class WindowContainerManagedWindowBuilderProcessDataDefaultImpl extends AbstractWindowContainerManagedWindowBuilderProcessDataImpl implements WindowContainerManagedWindowBuilderProcessDataDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(Form form, SystemAction systemAction, Data data, ViewBuilder viewBuilder) {
		Strings fieldNames = __inject__(DataFieldsNamesGetter.class).setSystemAction(systemAction).execute().getOutput();
		if(CollectionHelper.isNotEmpty(fieldNames)) {
			for(String index : fieldNames.get()) {
				if(StringHelper.isNotBlank(index)) {
					String[] strings = FieldHelper.disjoin(index).toArray(new String[] {});
					if(__inject__(ArrayHelper.class).isNotEmpty(strings)) {
						viewBuilder.addInputBuilderByObjectByFieldNames(data, Boolean.TRUE, strings);
					}
				}		
			}
		}
		
		FieldDescriptions fieldDescriptions = __inject__(DataFieldDescriptionsGetter.class).setSystemAction(systemAction).execute().getOutput();
		if(CollectionHelper.isNotEmpty(fieldDescriptions)) {
			for(FieldDescription index : fieldDescriptions.get()) {
				if(StringHelper.isNotBlank(index.getName())) {
					String[] strings = FieldHelper.disjoin(index.getName()).toArray(new String[] {});
					if(__inject__(ArrayHelper.class).isNotEmpty(strings)) {
						InputBuilder<?, ?> input = (InputBuilder<?, ?>) viewBuilder.addInputBuilderByObjectByFieldNames(data, ValueHelper
								.defaultToIfNull(index.getIsEditable(),Boolean.TRUE), strings);
						input.setIsNullable(index.getIsNullable());
					}
				}		
			}
		}
	}

}
