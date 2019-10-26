package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.data.DataHelper;
import org.cyk.utility.client.controller.data.DataIdentifiedByString;
import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndCoded;
import org.cyk.utility.client.controller.data.DataIdentifiedByStringAndLinkedByStringAndNamed;
import org.cyk.utility.client.controller.data.RowData;

public class WindowContainerManagedWindowBuilderListDataDefaultImpl extends AbstractWindowContainerManagedWindowBuilderListDataImpl implements WindowContainerManagedWindowBuilderListDataDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(GridBuilder gridBuilder) {
		Strings fieldNames = __inject__(DataHelper.class).getPropertiesFieldsNames(getSystemAction().getEntityClass());
		if(CollectionHelper.isEmpty(fieldNames))
			return;
		if(fieldNames.getSize() > 1 && CollectionHelper.contains(fieldNames, DataIdentifiedByStringAndCoded.PROPERTY_CODE))
			fieldNames.remove(DataIdentifiedByString.PROPERTY_IDENTIFIER);
		
		if(ClassHelper.isInstanceOf(getSystemAction().getEntityClass(), org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString.class)) {
			fieldNames.removeMany(org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString.PROPERTY_PARENTS
					,org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString.PROPERTY_NUMBER_OF_PARENTS
					,org.cyk.utility.client.controller.data.hierarchy.DataIdentifiedByString.PROPERTY_NUMBER_OF_CHILDREN);
		}else if(ClassHelper.isInstanceOf(getSystemAction().getEntityClass(), DataIdentifiedByStringAndLinkedByStringAndNamed.class)) {
			
		}
		for(String index : fieldNames.get()) {
			gridBuilder.addColumnsByFieldNames(FieldHelper.join(RowData.PROPERTY_DATA,index));				
		}
	}

}
