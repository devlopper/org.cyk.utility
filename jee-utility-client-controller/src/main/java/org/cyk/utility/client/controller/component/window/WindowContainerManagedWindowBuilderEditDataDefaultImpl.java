package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.codehaus.plexus.util.StringUtils;
import org.cyk.utility.character.CharacterConstant;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.DataFieldsNamesGetter;
import org.cyk.utility.client.controller.data.Form;
import org.cyk.utility.string.Strings;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionAdd;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionUpdate;

public class WindowContainerManagedWindowBuilderEditDataDefaultImpl extends AbstractWindowContainerManagedWindowBuilderEditDataImpl implements WindowContainerManagedWindowBuilderEditDataDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(Form form, SystemAction systemAction, Data data, ViewBuilder viewBuilder) {
		Strings fieldNames = __inject__(DataFieldsNamesGetter.class).setSystemAction(systemAction).execute().getOutput();
		if(__injectCollectionHelper__().isNotEmpty(fieldNames))
			for(String index : fieldNames.get())
				viewBuilder.addInputBuilderByObjectByFieldNames(data, systemAction instanceof SystemActionCreate || systemAction instanceof SystemActionUpdate || systemAction instanceof SystemActionAdd
						|| data.getIdentifier() == null, StringUtils.split(index,CharacterConstant.DOT.toString()));
		
	}

}
