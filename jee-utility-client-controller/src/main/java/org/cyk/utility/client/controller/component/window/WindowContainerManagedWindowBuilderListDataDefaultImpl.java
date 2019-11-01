package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.system.action.SystemActionFieldsGetter;
import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.data.RowData;

public class WindowContainerManagedWindowBuilderListDataDefaultImpl extends AbstractWindowContainerManagedWindowBuilderListDataImpl implements WindowContainerManagedWindowBuilderListDataDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(GridBuilder gridBuilder) {
		Collection<Field> fields = SystemActionFieldsGetter.getInstance().get(getSystemAction());
		if(CollectionHelper.isEmpty(fields))
			return;
		for(Field index : fields) {
			gridBuilder.addColumnsByFieldNames(FieldHelper.join(RowData.PROPERTY_DATA,index.getName()));				
		}
	}

}
