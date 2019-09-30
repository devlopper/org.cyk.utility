package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationKeyStringType;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.DataFieldsNamesGetter;
import org.cyk.utility.client.controller.data.DataSelect;
import org.cyk.utility.client.controller.data.Form;

public class WindowContainerManagedWindowBuilderSelectDataDefaultImpl extends AbstractWindowContainerManagedWindowBuilderSelectDataImpl implements WindowContainerManagedWindowBuilderSelectDataDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(Form form, SystemAction systemAction, Data data, ViewBuilder viewBuilder) {
		Strings fieldNames = __inject__(DataFieldsNamesGetter.class).setSystemAction(systemAction).execute().getOutput();
		if(CollectionHelper.isNotEmpty(fieldNames)) {
			fieldNames.remove(DataSelect.PROPERTY_MANY);
			for(String index : fieldNames.get()) {
				if(StringHelper.isNotBlank(index)) {
					String[] strings = FieldHelper.disjoin(index).toArray(new String[] {});
					if(__inject__(ArrayHelper.class).isNotEmpty(strings)) {
						InputBuilder<?, ?> input = (InputBuilder<?, ?>) viewBuilder.addInputBuilderByObjectByFieldNames(data, Boolean.TRUE, strings);
						if(Arrays.equals(strings, new String[] {DataSelect.PROPERTY_ONE}) || Arrays.equals(strings, new String[] {DataSelect.PROPERTY_MANY})) {							
							input.setLabelValue(__buildInternationalizationString__(StringUtils.substringBefore(data.getClass().getSimpleName()
									,"Select"), Arrays.equals(strings, new String[] {DataSelect.PROPERTY_ONE}) ? null
											: InternationalizationKeyStringType.PLURAL, null));
						}
					}
				}		
			}
		}
	}
}
