package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.client.controller.component.input.InputBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.DataFieldsNamesGetter;
import org.cyk.utility.client.controller.data.DataSelect;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.internationalization.InternationalizationKeyStringType;
import org.cyk.utility.internationalization.InternalizationStringBuilder;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;

public class WindowContainerManagedWindowBuilderSelectDataDefaultImpl extends AbstractWindowContainerManagedWindowBuilderSelectDataImpl implements WindowContainerManagedWindowBuilderSelectDataDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(Form form, SystemAction systemAction, Data data, ViewBuilder viewBuilder) {
		Strings fieldNames = __inject__(DataFieldsNamesGetter.class).setSystemAction(systemAction).execute().getOutput();
		if(__injectCollectionHelper__().isNotEmpty(fieldNames)) {
			fieldNames.remove(DataSelect.PROPERTY_MANY);
			for(String index : fieldNames.get()) {
				if(__injectStringHelper__().isNotBlank(index)) {
					String[] strings = __inject__(FieldHelper.class).disjoin(index).get().toArray(new String[] {});
					if(__inject__(ArrayHelper.class).isNotEmpty(strings)) {
						InputBuilder<?, ?> input = (InputBuilder<?, ?>) viewBuilder.addInputBuilderByObjectByFieldNames(data, Boolean.TRUE, strings);
						if(Arrays.equals(strings, new String[] {DataSelect.PROPERTY_ONE}) || Arrays.equals(strings, new String[] {DataSelect.PROPERTY_MANY})) {							
							input.setLabelValue(__inject__(InternalizationStringBuilder.class).setKeyValue(StringUtils.substringBefore(data.getClass().getSimpleName()
									,"Select")).setKeyType(Arrays.equals(strings, new String[] {DataSelect.PROPERTY_ONE}) ? null
											: InternationalizationKeyStringType.PLURAL).execute().getOutput());
						}
					}
				}		
			}
		}
	}

}
