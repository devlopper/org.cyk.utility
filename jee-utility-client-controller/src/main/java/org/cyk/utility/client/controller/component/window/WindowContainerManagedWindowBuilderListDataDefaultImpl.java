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
			for(String index : fieldNames.get()) {
				/*ColumnBuilder column = __inject__(ColumnBuilder.class); 
				column.addFieldNameStrings(RowData.PROPERTY_DATA,index);
				//which kind of field index is
				//TODO build column using ColumnBuilder in order to well set title and field value path
				Field field = __inject__(FieldGetter.class).setToken(index).setTokenLocation(StringLocation.EXAT)
						.setClazz(__inject__(getSystemAction().getEntityClass()).getClass()).execute().getOutput().getFirst();
				Class<?> fieldType = __inject__(FieldTypeGetter.class).execute(field).getOutput();
				column.setHeaderTextValue(__inject__(InternalizationStringBuilder.class).setKeyValue(index).execute().getOutput());
				if(!__injectClassHelper__().isBelongsToJavaPackages(fieldType)) {
					Fields fields = __inject__(FieldGetter.class).setClazz(__inject__(fieldType).getClass()).addNameToken("name").addNameToken(Data.PROPERTY_CODE).execute().getOutput();
					if(__injectCollectionHelper__().isNotEmpty(fields)) {
						//TODO according to a list pick up the first matching
						//index = index + ".name";//TODO get name first else code
						//index = index + ".code";
						column.addFieldNameStrings(__injectCollectionHelper__().getFirst(fields).getName());
					}else {
						
					}	
				}
				gridBuilder.addColumns(column);
				*/
				//column.addFieldNameStrings(RowData.PROPERTY_DATA,index);
				gridBuilder.addColumnsByFieldNames(__injectFieldHelper__().join(RowData.PROPERTY_DATA,index));
				
			}
		}
	}

}
