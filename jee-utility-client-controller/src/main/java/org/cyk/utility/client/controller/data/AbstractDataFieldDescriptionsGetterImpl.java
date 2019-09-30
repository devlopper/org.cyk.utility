package org.cyk.utility.client.controller.data;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.field.FieldDescription;
import org.cyk.utility.field.FieldDescriptions;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public abstract class AbstractDataFieldDescriptionsGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<FieldDescriptions> implements DataFieldDescriptionsGetter,Serializable {
	private static final long serialVersionUID = 1L;
	
	private SystemAction systemAction;
	
	@Override
	protected FieldDescriptions __execute__() throws Exception {
		SystemAction systemAction = ValueHelper.returnOrThrowIfBlank("data field desceiptions getter system action", getSystemAction());
		FieldDescriptions fieldDescriptions = null;
		Strings names = __getNames__(systemAction);
		if(CollectionHelper.isNotEmpty(names)) {
			fieldDescriptions = __inject__(FieldDescriptions.class);
			for(String index : names.get()) {
				FieldDescription fieldDescription = __inject__(FieldDescription.class);
				fieldDescription.setName(index);
				__process__(systemAction,fieldDescription);
				fieldDescriptions.add(fieldDescription);
			}
		}
		return fieldDescriptions;
	}
	
	protected void __process__(SystemAction systemAction,FieldDescription fieldDescription) {}
	
	protected Strings __getNames__(SystemAction systemAction) {
		return __inject__(DataFieldsNamesGetter.class).setSystemAction(systemAction).execute().getOutput();
	}
	
	@Override
	public SystemAction getSystemAction() {
		return systemAction;
	}

	@Override
	public DataFieldDescriptionsGetter setSystemAction(SystemAction systemAction) {
		this.systemAction = systemAction;
		return this;
	}

}
