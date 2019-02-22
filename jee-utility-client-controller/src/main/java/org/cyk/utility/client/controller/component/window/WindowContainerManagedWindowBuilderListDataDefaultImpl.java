package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.data.DataHelper;
import org.cyk.utility.client.controller.data.RowData;
import org.cyk.utility.string.Strings;

public class WindowContainerManagedWindowBuilderListDataDefaultImpl extends AbstractWindowContainerManagedWindowBuilderListDataImpl implements WindowContainerManagedWindowBuilderListDataDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(GridBuilder gridBuilder) {
		Strings fieldNames = __inject__(DataHelper.class).getPropertiesFieldsNames(getSystemAction().getEntityClass());
		if(__injectCollectionHelper__().isNotEmpty(fieldNames))
			for(String index : fieldNames.get()) {
				gridBuilder.addColumnsByFieldNames(__injectFieldHelper__().concatenate(RowData.PROPERTY_DATA,index));
			}
	}

}
