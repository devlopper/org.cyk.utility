package org.cyk.utility.client.controller.web.jsf.primefaces.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.Column;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractEntityListPageContainerManagedImpl<ENTITY> extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	protected DataTable dataTable;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		@SuppressWarnings("unchecked")
		Class<ENTITY> entityClass = (Class<ENTITY>) ClassHelper.getParameterAt(getClass(), 0);
		dataTable = __buildDataTable__(entityClass);		
	}
	
	
	protected DataTable __buildDataTable__(Class<ENTITY> entityClass) {
		DataTable dataTable = DataTable.build(DataTable.FIELD_LAZY,Boolean.TRUE,DataTable.FIELD_ELEMENT_CLASS,entityClass,DataTable.FIELD_SELECTION_MODE,"multiple");
		Collection<Column> columns = __getColumns__(entityClass);
		if(CollectionHelper.isEmpty(columns))
			return dataTable;
		dataTable.addColumnsAfterRowIndex(columns);		
		__addDataTableHeaderToolbarLeftCommandsByArguments__(dataTable);				
		__addDataTableRecordMenuItemByArguments__(dataTable);
		return dataTable;
	}
	
	protected void __addDataTableHeaderToolbarLeftCommandsByArguments__(DataTable dataTable) {
		dataTable.addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialogCreate();
		dataTable.addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialogRead();
		dataTable.addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialogUpdate();
	}
	
	protected void __addDataTableRecordMenuItemByArguments__(DataTable dataTable) {
		dataTable.addRecordMenuItemByArgumentsOpenViewInDialogRead();
		dataTable.addRecordMenuItemByArgumentsOpenViewInDialogUpdate();
		dataTable.addRecordMenuItemByArgumentsExecuteFunctionDelete();
	}
	
	protected Collection<Column> __getColumns__(Class<ENTITY> entityClass) {
		Collection<String> columnsFieldsNames = __getColumnsFieldsNames__(entityClass);
		if(CollectionHelper.isEmpty(columnsFieldsNames))
			return null;
		Collection<Column> columns = null;
		for(String fieldName : columnsFieldsNames) {
			Column column = __buildColumn__(fieldName);
			if(column == null)
				continue;
			if(columns == null)
				columns = new ArrayList<>();
			columns.add(column);
		}
		return columns;
	}
	
	protected Column __buildColumn__(String fieldName) {
		if(StringHelper.isBlank(fieldName))
			return null;
		Object[] arguments = __getColumnFieldNameArguments__(fieldName);
		if(ArrayHelper.isEmpty(arguments))
			return null;
		return Column.build(arguments);
	}
	
	protected Object[] __getColumnFieldNameArguments__(String fieldName) {
		return new Object[] {Column.FIELD_FIELD_NAME,fieldName};
	}
	
	protected abstract Collection<String> __getColumnsFieldsNames__(Class<ENTITY> entityClass);
	
	@Override
	protected String __getWindowTitleValue__() {
		if(dataTable == null || dataTable.getTitle() == null || StringHelper.isBlank(dataTable.getTitle().getValue()))
			return super.__getWindowTitleValue__();
		return dataTable.getTitle().getValue();
	}
}
