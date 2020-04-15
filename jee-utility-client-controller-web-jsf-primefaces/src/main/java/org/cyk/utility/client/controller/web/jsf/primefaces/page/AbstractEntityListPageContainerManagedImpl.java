package org.cyk.utility.client.controller.web.jsf.primefaces.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.Column;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.collection.DataTable;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractEntityListPageContainerManagedImpl<ENTITY> extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<ENTITY> entityClass;
	protected DataTable dataTable;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		entityClass = (Class<ENTITY>) ClassHelper.getParameterAt(getClass(), 0);
		dataTable = __buildDataTable__();		
	}
	
	protected Map<Object,Object> __getDataTableArguments__() {
		return MapHelper.instantiate(DataTable.FIELD_LAZY,Boolean.TRUE,DataTable.FIELD_ELEMENT_CLASS,entityClass
				,DataTable.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.FALSE);
	}
	
	protected DataTable __buildDataTable__() {
		Map<Object,Object> arguments = __getDataTableArguments__();
		if(MapHelper.isEmpty(arguments))
			return null;
		DataTable dataTable = DataTable.build(arguments);
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
		//dataTable.addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialogRead();
		//dataTable.addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialogUpdate();
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
		Map<Object,Object> arguments = __getColumnArguments__(fieldName);
		if(MapHelper.isEmpty(arguments))
			return null;
		return Column.build(arguments);
	}
	
	protected Map<Object,Object> __getColumnArguments__(String fieldName) {
		return MapHelper.instantiate(Column.FIELD_FIELD_NAME,fieldName);
	}
	
	protected abstract Collection<String> __getColumnsFieldsNames__(Class<ENTITY> entityClass);
	
	@Override
	protected String __getWindowTitleValue__() {
		if(dataTable == null || dataTable.getTitle() == null || StringHelper.isBlank(dataTable.getTitle().getValue()))
			return super.__getWindowTitleValue__();
		return dataTable.getTitle().getValue();
	}
}