package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.data.DataHelper;
import org.cyk.utility.client.controller.data.DataIdentifiedByString;
import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndCoded;
import org.cyk.utility.client.controller.data.RowData;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.string.Strings;

public class WindowContainerManagedWindowBuilderListDataDefaultImpl extends AbstractWindowContainerManagedWindowBuilderListDataImpl implements WindowContainerManagedWindowBuilderListDataDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(GridBuilder gridBuilder) {
		Strings fieldNames = __inject__(DataHelper.class).getPropertiesFieldsNames(getSystemAction().getEntityClass());
		if(__injectCollectionHelper__().isNotEmpty(fieldNames)) {
			if(fieldNames.getSize() > 1) {
				if(__inject__(CollectionHelper.class).contains(fieldNames, DataIdentifiedByStringAndCoded.PROPERTY_CODE))
					fieldNames.remove(DataIdentifiedByString.PROPERTY_IDENTIFIER);
			}
			if(__injectClassHelper__().isInstanceOf(getSystemAction().getEntityClass(), org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString.class)) {
				fieldNames.removeMany(org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString.PROPERTY_PARENTS
						,org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString.PROPERTY_NUMBER_OF_PARENTS
						,org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString.PROPERTY_NUMBER_OF_CHILDREN);
			}
			for(String index : fieldNames.get()) {
				gridBuilder.addColumnsByFieldNames(__injectFieldHelper__().join(RowData.PROPERTY_DATA,index));				
			}
		}
	}

}
