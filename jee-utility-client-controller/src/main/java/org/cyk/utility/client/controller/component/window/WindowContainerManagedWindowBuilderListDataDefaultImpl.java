package org.cyk.utility.client.controller.component.window;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.client.controller.component.grid.GridBuilder;
import org.cyk.utility.client.controller.component.grid.column.ColumnBuilder;
import org.cyk.utility.client.controller.data.Data;
import org.cyk.utility.client.controller.data.DataHelper;
import org.cyk.utility.client.controller.data.RowData;
import org.cyk.utility.field.FieldGetter;
import org.cyk.utility.field.FieldTypeGetter;
import org.cyk.utility.internationalization.InternalizationStringBuilder;
import org.cyk.utility.string.StringLocation;
import org.cyk.utility.string.Strings;

public class WindowContainerManagedWindowBuilderListDataDefaultImpl extends AbstractWindowContainerManagedWindowBuilderListDataImpl implements WindowContainerManagedWindowBuilderListDataDefault,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(GridBuilder gridBuilder) {
		Strings fieldNames = __inject__(DataHelper.class).getPropertiesFieldsNames(getSystemAction().getEntityClass());
		if(__injectCollectionHelper__().isNotEmpty(fieldNames))
			for(String index : fieldNames.get()) {
				ColumnBuilder column = __inject__(ColumnBuilder.class); 
				column.addFieldNameStrings(RowData.PROPERTY_DATA,index);
				//which kind of field index is
				//TODO build column using ColumnBuilder in order to well set title and field value path
				Field field = __inject__(FieldGetter.class).setToken(index).setTokenLocation(StringLocation.EXAT)
						.setClazz(__inject__(getSystemAction().getEntityClass()).getClass()).execute().getOutput().getFirst();
				Class<?> fieldType = __inject__(FieldTypeGetter.class).execute(field).getOutput();
				column.setHeaderTextValue(__inject__(InternalizationStringBuilder.class).setKeyValue(index).execute().getOutput());
				if(__injectClassHelper__().isInstanceOf(fieldType, Data.class)) {
					//TODO according to a list pick up the first matching
					//index = index + ".name";//TODO get name first else code
					//index = index + ".code";
					column.addFieldNameStrings(Data.PROPERTY_CODE);
					//System.out.println("WindowContainerManagedWindowBuilderListDataDefaultImpl.__execute__() : "+fieldType);
				}else {
					
				}
				//column.addFieldNameStrings(RowData.PROPERTY_DATA,index);
				//gridBuilder.addColumnsByFieldNames(__injectFieldHelper__().concatenate(RowData.PROPERTY_DATA,index));
				gridBuilder.addColumns(column);
			}
	}

}
