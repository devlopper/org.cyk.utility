package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.data.structure.grid.Grid;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.MenuItem;
import org.primefaces.component.api.DynamicColumn;
import org.primefaces.event.CellEditEvent;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public abstract class AbstractDataTable extends AbstractCollection implements Serializable {

	protected Column orderNumberColumn;
	protected Column menuColumn;
	protected Collection<Column> columnsAfterRowIndex,selectedColumnsAfterRowIndex;
	protected Boolean areColumnsChoosable,isRowAddable,isColumnAddable;
	protected CommandButton addRowCommandButton,removeRowCommandButton,addColumnCommandButton;
	protected String columnFieldNameFormat;
	protected Grid dataGrid;
	
	/**/
	
	public AbstractDataTable enableCommandButtonAddRow() {
		enableCommandButtonAddRow(null);
		return this;
	}
	
	public AbstractDataTable enableCommandButtonAddRow(String title) {
		setIsRowAddable(Boolean.TRUE);
		if(title == null)
			title = "Ajouter une ligne";
		addRowCommandButton = CommandButton.build(CommandButton.FIELD_TITLE,title,CommandButton.FIELD_ICON,"fa fa-plus",CommandButton.ConfiguratorImpl.FIELD_COLLECTION,this
				,CommandButton.ConfiguratorImpl.FIELD_COLLECTION_UPDATABLE,Boolean.TRUE
				,CommandButton.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE
				,CommandButton.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
			@SuppressWarnings("unchecked")
			protected Object __executeFunction__(Object argument) {
				if(dataGrid == null) {
					if(value != null && !(value instanceof Collection))
						throw new RuntimeException("Cannot add instance into value of type "+value.getClass());
					if(value == null)
						setValue(value = new ArrayList<>());
					Object element = ClassHelper.instanciate(elementClass);				
					((Collection<Object>)value).add(element);
					if(listener != null)
						((Listener)listener).listenAddRow(AbstractDataTable.this, element);
				}else
					dataGrid.addRow();
				return "row added";
			}
		}.setAction(AbstractAction.Listener.Action.EXECUTE_FUNCTION));
		
		removeRowCommandButton = CommandButton.build(CommandButton.FIELD_TITLE,"Retirer",CommandButton.FIELD_ICON,"fa fa-minus",CommandButton.ConfiguratorImpl.FIELD_COLLECTION,this
				,CommandButton.ConfiguratorImpl.FIELD_COLLECTION_UPDATABLE,Boolean.TRUE
				,CommandButton.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE
				,CommandButton.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
			protected Object __executeFunction__(Object argument) {
				if(dataGrid == null) {
					
				}else {
					if(argument instanceof Grid.Row)
						dataGrid.removeRow((Grid.Row) argument);
				}
				return "row removed";
			}
		}.setAction(AbstractAction.Listener.Action.EXECUTE_FUNCTION));
		menuColumn.setRendered(Boolean.TRUE);
		recordMenu = null;
		return this;
	}
	
	public AbstractDataTable enableCommandButtonAddColumn() {
		enableCommandButtonAddColumn(null, null,null);
		return this;
	}
	
	public AbstractDataTable enableCommandButtonAddColumn(String value,String icon,String variableFormat) {
		setIsColumnAddable(Boolean.TRUE);
		if(value == null)
			value = "Ajouter une colonne";
		if(icon == null)
			icon = "fa fa-plus";
		if(StringHelper.isBlank(variableFormat) && dataGrid != null)
			variableFormat = dataGrid.getColumnKeyFormat();
		if(StringHelper.isBlank(variableFormat))
			variableFormat = "value%s";
		columnFieldNameFormat = variableFormat;
		addColumnCommandButton = CommandButton.build(CommandButton.FIELD_VALUE,value,CommandButton.FIELD_ICON,icon,CommandButton.ConfiguratorImpl.FIELD_COLLECTION,this
				,CommandButton.ConfiguratorImpl.FIELD_COLLECTION_UPDATABLE,Boolean.TRUE
				,CommandButton.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE
				,CommandButton.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
			protected Object __executeFunction__(Object argument) {
				String fieldName;
				if(dataGrid == null) {
					fieldName = null;
				}else
					fieldName = (String) dataGrid.formatNextColumnKey();
				System.out.println(
						"AbstractDataTable.enableCommandButtonAddColumn(...).new AbstractImpl() {...}.__executeFunction__() : "+fieldName);
				Map<Object,Object> arguments = null;
				if(listener == null)
					arguments = Listener.AbstractImpl.__getColumnArguments__(AbstractDataTable.this,fieldName);
				else
					arguments = ((Listener)listener).listenAddColumnGetArguments(AbstractDataTable.this,fieldName);
				Column column = Column.build(arguments);
				addColumnsAfterRowIndex(column);
				if(listener == null)
					;
				else
					((Listener)listener).listenAddColumn(AbstractDataTable.this,column);
				if(dataGrid == null) {
					
				}else
					dataGrid.addColumn(fieldName);
				return "column added";
			}
		}.setAction(AbstractAction.Listener.Action.EXECUTE_FUNCTION));
		return this;
	}
	
	public AbstractDataTable enableAjaxCellEdit() {
		setEditable(Boolean.TRUE);
		setEditMode("cell");
		getAjaxes().get("cellEdit").setDisabled(Boolean.FALSE);
		getAjaxes().get("cellEdit").setListener(new AbstractAction.Listener.AbstractImpl() {
			protected Object __executeFunction__(Object argument) {
				CellEditEvent event = (CellEditEvent) argument;
				DynamicColumn dynamicColumn = (DynamicColumn) event.getColumn();
				Object record = getValueAt(event.getRowIndex());
				String fieldName = CollectionHelper.getElementAt(getColumnsAfterRowIndex(), dynamicColumn.getIndex()).getFieldName();
				if(dataGrid == null && controllerEntity == null)
					throw new RuntimeException("Data grid or Controller is required to handle cell edit");				
				if(dataGrid != null) {
					//dataGrid.setValue((DataGrid.Row)record, fieldName, event.getNewValue());
				}else if(controllerEntity != null)
					controllerEntity.update(record,new Properties().setFields(fieldName));
				return null;
			}
		}.setAction(AbstractAction.Listener.Action.EXECUTE_FUNCTION));
		return this;
	}
	
	public Object getCellValueByRecordByColumn(Object record,Integer recordIndex,Column column,Integer columnIndex) {
		if(record == null || recordIndex == null || column == null || columnIndex == null)
			return null;
		if(listener == null)
			return FieldHelper.read(record, column.getFieldName());
		return ((Listener)listener).listenGetCellValueByRecordByColumn(record, recordIndex, column, columnIndex);
	}
	
	public Collection<Column> getColumnsAfterRowIndex(Boolean injectIfNull) {
		if(columnsAfterRowIndex == null && Boolean.TRUE.equals(injectIfNull))
			columnsAfterRowIndex = new ArrayList<>();
		return columnsAfterRowIndex;
	}
	
	public AbstractDataTable addColumnsAfterRowIndex(Collection<Column> columnsAfterRowIndex) {
		if(CollectionHelper.isEmpty(columnsAfterRowIndex))
			return this;
		getColumnsAfterRowIndex(Boolean.TRUE).addAll(columnsAfterRowIndex);
		return this;
	}
	
	public AbstractDataTable addColumnsAfterRowIndex(Column...columnsAfterRowIndex) {
		if(ArrayHelper.isEmpty(columnsAfterRowIndex))
			return this;
		return addColumnsAfterRowIndex(CollectionHelper.listOf(columnsAfterRowIndex));
	}
	
	public Collection<Column> getSelectedColumnsAfterRowIndex(Boolean injectIfNull) {
		if(selectedColumnsAfterRowIndex == null && Boolean.TRUE.equals(injectIfNull))
			selectedColumnsAfterRowIndex = new ArrayList<>();
		return selectedColumnsAfterRowIndex;
	}
	
	public AbstractDataTable addSelectedColumnsAfterRowIndex(Collection<Column> selectedColumnsAfterRowIndex) {
		if(CollectionHelper.isEmpty(selectedColumnsAfterRowIndex))
			return this;
		getSelectedColumnsAfterRowIndex(Boolean.TRUE).addAll(selectedColumnsAfterRowIndex);
		return this;
	}
	
	public AbstractDataTable addSelectedColumnsAfterRowIndex(Column...selectedColumnsAfterRowIndex) {
		if(ArrayHelper.isEmpty(selectedColumnsAfterRowIndex))
			return this;
		return addSelectedColumnsAfterRowIndex(CollectionHelper.listOf(selectedColumnsAfterRowIndex));
	}
	
	public String getStyleClassByRecord(Object record,Integer recordIndex) {
		if(listener instanceof AbstractDataTable.Listener)
			return ((AbstractDataTable.Listener)listener).listenGetStyleClassByRecord(record,recordIndex);
		return null;
	}
	
	public String getStyleClassByRecordByColumn(Object record,Integer recordIndex,Column column,Integer columnIndex) {
		if(listener instanceof AbstractDataTable.Listener)
			return ((AbstractDataTable.Listener)listener).listenGetStyleClassByRecordByColumn(record, recordIndex,column,columnIndex);
		return null;
	}
	
	@Override
	public AbstractCollection addRecordMenuItems(Collection<MenuItem> menuItems) {		
		if(CollectionHelper.isEmpty(menuItems))
			return this;
		super.addRecordMenuItems(menuItems);
		menuColumn.setRendered(Boolean.TRUE);
		return this;
	}
	
	/**/
	
	public static final String FIELD_DATA_GRID = "dataGrid";
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<DATATABLE extends AbstractDataTable> extends AbstractCollection.AbstractConfiguratorImpl<DATATABLE> implements Serializable {

		@Override
		protected String __getTemplate__(DATATABLE datatable, Map<Object, Object> arguments) {
			return Boolean.TRUE.equals(datatable.editable) ? "/collection/datatable/editable.xhtml" : "/collection/datatable/default.xhtml";
		}
		
		@Override
		public void configure(DATATABLE dataTable, Map<Object, Object> arguments) {
			Listener listener = (Listener) MapHelper.readByKey(arguments, DataTable.FIELD_LISTENER);
			if(Grid.Row.class.equals(MapHelper.readByKey(arguments, DataTable.FIELD_ELEMENT_CLASS))) {
				dataTable.setDataGrid((Grid) MapHelper.readByKey(arguments, DataTable.FIELD_DATA_GRID));
				if(dataTable.getDataGrid() == null)
					dataTable.setDataGrid(new Grid());
				if(dataTable.getDataGrid().getRows() == null)
					dataTable.getDataGrid().setRows(new ArrayList<Grid.Row>());
				arguments.put(DataTable.FIELD_DATA_GRID, null);
			}
			if(dataTable.getDataGrid() != null) {
				if(MapHelper.readByKey(arguments, DataTable.FIELD_VALUE) == null)
					dataTable.setValue(dataTable.getDataGrid().getRows());
				if(StringHelper.isBlank(dataTable.getColumnFieldNameFormat()))
					dataTable.setColumnFieldNameFormat(dataTable.getDataGrid().getColumnKeyFormat());
				if(Boolean.TRUE.equals(MapHelper.readByKey(arguments, DataTable.FIELD_EDITABLE))) {
					if(arguments == null)
						arguments = new HashMap<>();
					arguments.put(FIELD_EDITABLE_CELL, Boolean.TRUE);
				}
			}
			super.configure(dataTable, arguments);
			if(dataTable.getOrderNumberColumn() == null) {
				Map<Object,Object> map = new HashMap<>(Map.of(Column.FIELD_HEADER_TEXT,"#",Column.FIELD_WIDTH,"55"));
				if(StringHelper.isBlank(dataTable.getSelectionMode())) {
					
				}else {
					map.put(Column.FIELD_SELECTION_MODE, dataTable.getSelectionMode());
				}
				dataTable.setOrderNumberColumn(Builder.build(Column.class,map));
			}
			if(dataTable.getMenuColumn() == null) {
				Map<Object,Object> map = new HashMap<>(Map.of(Column.FIELD_HEADER_TEXT,"",Column.FIELD_WIDTH,"50",Column.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.FALSE
						,Column.FIELD_RENDERED,Boolean.FALSE));
				dataTable.setMenuColumn(Builder.build(Column.class,map));
			}
			if(dataTable.areColumnsChoosable == null)
				dataTable.areColumnsChoosable = Boolean.TRUE;
			
			if(dataTable.isExportable == null)
				dataTable.isExportable = Boolean.TRUE;
			
			if(dataTable.getRecordMenu() != null && CollectionHelper.isNotEmpty(dataTable.getRecordMenu().getItems()))
				dataTable.menuColumn.setRendered(Boolean.TRUE);
			
			if(Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_EDITABLE_CELL))) {
				dataTable.enableAjaxCellEdit();
			}
			
			if(Boolean.TRUE.equals(dataTable.editable)) {
				dataTable.areColumnsChoosable = Boolean.FALSE;
				if(dataTable.dataGrid == null) {
					
				}else {
					dataTable.enableCommandButtonAddRow().enableCommandButtonAddColumn().enableCommandButtonSave();	
				}
			}
			
			if(dataTable.getDataGrid() != null) {
				if(CollectionHelper.isNotEmpty(dataTable.dataGrid.getColumnsKeys())) {
					dataTable.dataGrid.getColumnsKeys().forEach(key -> {
						if(key != null) {
							Map<Object,Object> columnArguments = listener == null ? Listener.AbstractImpl.__getColumnArguments__(dataTable, key.toString())
									: listener.listenAddColumnGetArguments(dataTable, key.toString());
							dataTable.addColumnsAfterRowIndex(Column.build(columnArguments));	
						}						
					});
				}
			}
		}
	}
	
	/**/
	
	public static interface Listener extends AbstractCollection.Listener {
		
		Object listenGetCellValueByRecordByColumn(Object record,Integer recordIndex,Column column,Integer columnIndex);
		
		String listenGetStyleClassByRecord(Object record,Integer recordIndex);
		
		String listenGetStyleClassByRecordByColumn(Object record,Integer recordIndex,Column column,Integer columnIndex);
		
		void listenAddRow(AbstractDataTable dataTable,Object element);
		
		void listenRemoveRow(AbstractDataTable dataTable,Object element);
		
		void listenAddColumn(AbstractDataTable dataTable,Column column);
		
		Map<Object,Object> listenAddColumnGetArguments(AbstractDataTable dataTable,String fieldName);
		
		void listenRemoveColumn(AbstractDataTable dataTable,Column column);
		
		/**/
		
		public static abstract class AbstractImpl extends AbstractCollection.Listener.AbstractImpl implements Listener,Serializable {
			
			@Override
			public Object listenGetCellValueByRecordByColumn(Object record, Integer recordIndex, Column column,Integer columnIndex) {
				return null;
			}
			
			@Override
			public String listenGetStyleClassByRecord(Object record, Integer recordIndex) {
				return null;
			}
			
			@Override
			public String listenGetStyleClassByRecordByColumn(Object record, Integer recordIndex, Column column,Integer columnIndex) {
				return null;
			}
			
			@Override
			public void listenAddRow(AbstractDataTable dataTable, Object element) {}
			
			@Override
			public void listenRemoveRow(AbstractDataTable dataTable, Object element) {}
			
			@Override
			public void listenAddColumn(AbstractDataTable dataTable, Column column) {}
						
			@Override
			public Map<Object, Object> listenAddColumnGetArguments(AbstractDataTable dataTable,String fieldName) {
				return __getColumnArguments__(dataTable,fieldName);
			}
			
			@Override
			public void listenRemoveColumn(AbstractDataTable dataTable, Column column) {}
			
			/**/
			
			public static Map<Object, Object> __getColumnArguments__(AbstractDataTable dataTable,String fieldName) {
				if(StringHelper.isBlank(fieldName))
					fieldName = String.format(dataTable.columnFieldNameFormat, CollectionHelper.getSize(dataTable.columnsAfterRowIndex));
				return MapHelper.instantiate(Column.FIELD_HEADER_TEXT,fieldName,Column.FIELD_FIELD_NAME,fieldName
						,Column.ConfiguratorImpl.FIELD_EDITABLE,dataTable.editable,Column.FIELD_REMOVE_COMMAND_BUTTON
						,CommandButton.build(CommandButton.FIELD_TITLE,"Retirer",CommandButton.FIELD_ICON,"fa fa-minus"
								,CommandButton.ConfiguratorImpl.FIELD_COLLECTION,dataTable
								,CommandButton.ConfiguratorImpl.FIELD_COLLECTION_UPDATABLE,Boolean.TRUE
								,CommandButton.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE
								,CommandButton.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
							
							protected Object __executeFunction__(Object argument) {
								if(!(argument instanceof Column) || CollectionHelper.isEmpty(dataTable.columnsAfterRowIndex))
									return null;
								Column column = (Column) argument;
								dataTable.columnsAfterRowIndex.remove(column);
								if(dataTable.getDataGrid() == null) {
									
								}else {
									dataTable.getDataGrid().removeColumn(column.getFieldName());
								}
								return "column removed";
							}
						}.setAction(AbstractAction.Listener.Action.EXECUTE_FUNCTION)));
			}
		}
		
	}
}