package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.system.action.SystemActionFieldsNamesGetter;
import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.data.RowData;

public class WindowContainerManagedWindowBuilderListDataDefaultImpl extends AbstractWindowContainerManagedWindowBuilderListDataImpl implements WindowContainerManagedWindowBuilderListDataDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(GridBuilder gridBuilder) {
		Collection<String> fieldsNames = SystemActionFieldsNamesGetter.getInstance().get(getSystemAction());
		if(CollectionHelper.isEmpty(fieldsNames))
			return;
		for(String index : fieldsNames) {
			gridBuilder.addColumnsByFieldNames(FieldHelper.join(RowData.PROPERTY_DATA,index));				
		}
	}

}
