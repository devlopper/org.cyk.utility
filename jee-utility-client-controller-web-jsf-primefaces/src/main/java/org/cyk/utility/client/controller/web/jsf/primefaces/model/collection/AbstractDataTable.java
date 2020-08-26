package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.data.structure.grid.Grid;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.internationalization.InternationalizationHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.__static__.controller.AbstractDataIdentifiableSystemStringIdentifiableBusinessStringImpl;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceAction;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.client.controller.web.jsf.primefaces.PrimefacesHelper;
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
	protected Boolean stickyHeader,areColumnsChoosable,isRowAddable,isColumnAddable,isLastColumnRemovable;
	protected CommandButton addRowCommandButton,removeRowCommandButton,addColumnCommandButton,removeLastColumnCommandButton;
	protected String stickyTopAt,columnFieldNameFormat,rowTooltipStyleClass;
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
		addRowCommandButton = CommandButton.build(CommandButton.FIELD_TITLE,title,CommandButton.FIELD_ICON,"fa fa-plus",CommandButton.FIELD___COLLECTION__,this
				,CommandButton.ConfiguratorImpl.FIELD_COLLECTION_UPDATABLE,Boolean.TRUE,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.EXECUTE_FUNCTION
				,CommandButton.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE
				,CommandButton.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
			@SuppressWarnings("unchecked")
			@Override
			protected Object __runExecuteFunction__(AbstractAction action) {
				if(dataGrid == null) {
					if(value != null && !(value instanceof Collection))
						throw new RuntimeException("Cannot add instance into value of type "+value.getClass());
					if(value == null)
						setValue(value = new ArrayList<>());
					Object element = ClassHelper.instanciate(elementClass);				
					((Collection<Object>)value).add(element);
					//if(listener != null)
					//	((Listener)listener).listenAddRow(AbstractDataTable.this, element);
				}else
					dataGrid.addRow();
				return "row added";
			}
		});
		
		removeRowCommandButton = CommandButton.build(CommandButton.FIELD_TITLE,"Retirer",CommandButton.FIELD_ICON,"fa fa-minus",CommandButton.FIELD___COLLECTION__,this
				,CommandButton.ConfiguratorImpl.FIELD_COLLECTION_UPDATABLE,Boolean.TRUE,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.EXECUTE_FUNCTION
				,CommandButton.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE
				,CommandButton.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
			@Override
			protected Object __runExecuteFunction__(AbstractAction action) {
				if(dataGrid == null) {
					
				}else {
					if(action.get__argument__() instanceof Grid.Row)
						dataGrid.removeRow((Grid.Row) action.get__argument__());
				}
				return "row removed";
			}
		});
		menuColumn.setRendered(Boolean.TRUE);
		recordMenu = null;
		return this;
	}
	
	public AbstractDataTable enableCommandButtonAddColumn() {
		enableCommandButtonAddColumn(null, null,null);
		enableCommandButtonRemoveLastColumn();
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
		addColumnCommandButton = CommandButton.build(CommandButton.FIELD_VALUE,value,CommandButton.FIELD_ICON,icon,CommandButton.FIELD___COLLECTION__,this
				,CommandButton.ConfiguratorImpl.FIELD_COLLECTION_UPDATABLE,Boolean.TRUE,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.EXECUTE_FUNCTION
				,CommandButton.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE
				,CommandButton.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
			@Override
			protected Object __runExecuteFunction__(AbstractAction action) {
				String fieldName;
				if(dataGrid == null) {
					fieldName = null;
				}else
					fieldName = (String) dataGrid.formatNextColumnKey();
				Map<Object,Object> arguments = null;
				arguments = ((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).getColumnArguments(AbstractDataTable.this,fieldName);
				Column column = Column.build(arguments);
				addColumnsAfterRowIndex(column);
				//((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).addColumn(AbstractDataTable.this, column);
				if(dataGrid == null) {
					
				}else
					dataGrid.addColumn(fieldName);
				return "column added";
			}
		});
		return this;
	}
	
	public AbstractDataTable enableCommandButtonRemoveLastColumn(String value,String icon) {
		setIsLastColumnRemovable(Boolean.TRUE);
		if(value == null)
			value = "Retirer la dernière colonne";
		if(icon == null)
			icon = "fa fa-minus";
		removeLastColumnCommandButton = CommandButton.build(CommandButton.FIELD_VALUE,value,CommandButton.FIELD_ICON,icon,CommandButton.FIELD___COLLECTION__,this
				,CommandButton.ConfiguratorImpl.FIELD_COLLECTION_UPDATABLE,Boolean.TRUE,CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.EXECUTE_FUNCTION
				,CommandButton.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_NULLABLE,Boolean.TRUE
				,CommandButton.FIELD_LISTENER,new AbstractAction.Listener.AbstractImpl() {
			@Override
			protected Object __runExecuteFunction__(AbstractAction action) {
				if(!((List<Column>)columnsAfterRowIndex).isEmpty()) {
					Column column = ((List<Column>)columnsAfterRowIndex).get(((List<Column>)columnsAfterRowIndex).size()-1);
					columnsAfterRowIndex.remove(column);
					if(dataGrid == null) {
						
					}else
						dataGrid.removeColumn(column.getFieldName());	
				}				
				return "column removed";
			}
		});
		return this;
	}
	
	public AbstractDataTable enableCommandButtonRemoveLastColumn() {
		return enableCommandButtonRemoveLastColumn(null, null);
	}
	
	public AbstractDataTable enableAjaxCellEdit() {
		setEditable(Boolean.TRUE);
		setEditMode("cell");
		getAjaxes().get("cellEdit").setDisabled(Boolean.FALSE);
		getAjaxes().get("cellEdit").setUserInterfaceAction(UserInterfaceAction.EXECUTE_FUNCTION);
		getAjaxes().get("cellEdit").setListener(new AbstractAction.Listener.AbstractImpl() {
			@Override
			protected Object __runExecuteFunction__(AbstractAction action) {
				CellEditEvent event = (CellEditEvent) action.get__argument__();
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
		});
		return this;
	}
	
	public Object getCellValueByRecordByColumn(Object record,Integer recordIndex,Column column,Integer columnIndex) {
		if(record == null || recordIndex == null || column == null || columnIndex == null)
			return null;
		return ((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).getCellValueByRecordByColumn(record, recordIndex, column, columnIndex);
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
		return ((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).getStyleClassByRecord(record, recordIndex);
	}
	
	public String getStyleClassByRecordByColumn(Object record,Integer recordIndex,Column column,Integer columnIndex) {
		return ((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).getStyleClassByRecordByColumn(record, recordIndex, column, columnIndex);
	}
	
	public String getTooltipByRecord(Object record,Integer recordIndex) {
		return ((Listener)(listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : listener)).getTooltipByRecord(record, recordIndex);
	}
	
	@Override
	public AbstractCollection addRecordMenuItems(Collection<MenuItem> menuItems) {		
		if(CollectionHelper.isEmpty(menuItems))
			return this;
		super.addRecordMenuItems(menuItems);
		menuColumn.setRendered(Boolean.TRUE);
		return this;
	}
	
	public AbstractDataTable setColumnsFootersValuesFromMaster(Object master) {
		if(CollectionHelper.isEmpty(columnsAfterRowIndex))
			return this;
		columnsAfterRowIndex.stream().forEach(column -> {
			column.setFooterValueFromMaster(master);	
		});
		return this;
	}
	
	public AbstractDataTable updateColumnsFooters() {
		PrimefacesHelper.updateDataTableFooters(this);
		return this;
	}
	
	/**/
	
	public static final String FIELD_DATA_GRID = "dataGrid";
	public static final String FIELD_STICKY_HEADER = "stickyHeader";
	public static final String FIELD_STICKY_TOP_AT = "stickyTopAt";
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<DATATABLE extends AbstractDataTable> extends AbstractCollection.AbstractConfiguratorImpl<DATATABLE> implements Serializable {

		@Override
		protected String __getTemplate__(DATATABLE datatable, Map<Object, Object> arguments) {
			return Boolean.TRUE.equals(datatable.editable) ? "/collection/datatable/editable.xhtml" : "/collection/datatable/default.xhtml";
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public void configure(DATATABLE dataTable, Map<Object, Object> arguments) {
			Listener listener = (Listener) MapHelper.readByKey(arguments, DataTable.FIELD_LISTENER);
			if(listener == null)
				listener = Listener.AbstractImpl.DefaultImpl.INSTANCE;
			Listener __listener__ = listener;
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
				Map<Object,Object> map = new HashMap<>(Map.of(Column.FIELD_HEADER_TEXT,"",Column.FIELD_WIDTH,"40",Column.ConfiguratorImpl.FIELD_FILTERABLE,Boolean.FALSE
						,Column.FIELD_RENDERED,Boolean.FALSE));
				dataTable.setMenuColumn(Builder.build(Column.class,map));
			}
			if(dataTable.areColumnsChoosable == null)
				dataTable.areColumnsChoosable = Boolean.FALSE;
			
			if(dataTable.isExportable == null)
				dataTable.isExportable = Boolean.FALSE;
			
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
			
			if(dataTable.getDataGrid() == null) {				
				Collection<String> columnsFieldsNames = (Collection<String>) MapHelper.readByKey(arguments, FIELD_COLUMNS_FIELDS_NAMES);
				if(columnsFieldsNames == null && Boolean.TRUE.equals(MapHelper.readByKey(arguments, FIELD_COLUMNS_FIELDS_NAMES_COMPUTABLE))) {
					columnsFieldsNames = FieldHelper.getNames(FieldHelper.getByAnnotationClass(dataTable.elementClass, org.cyk.utility.__kernel__.object.__static__.controller.annotation.Column.class));
					if(CollectionHelper.getSize(columnsFieldsNames) > 2) {
						if(columnsFieldsNames.contains(AbstractDataIdentifiableSystemStringIdentifiableBusinessStringImpl.FIELD_IDENTIFIER)
								&& columnsFieldsNames.contains(AbstractDataIdentifiableSystemStringIdentifiableBusinessStringImpl.FIELD_CODE))
							columnsFieldsNames.remove(AbstractDataIdentifiableSystemStringIdentifiableBusinessStringImpl.FIELD_IDENTIFIER);
					}
				}
				if(CollectionHelper.isNotEmpty(columnsFieldsNames)) {
					columnsFieldsNames.forEach(fieldName -> {
						Map<Object,Object> columnArguments = __listener__.getColumnArguments(dataTable, fieldName);
						dataTable.addColumnsAfterRowIndex(Column.build(columnArguments));
					});
				}				
			}else {
				if(CollectionHelper.isNotEmpty(dataTable.dataGrid.getColumnsKeys())) {
					dataTable.dataGrid.getColumnsKeys().forEach(key -> {
						if(key != null) {
							Map<Object,Object> columnArguments = __listener__.getColumnArguments(dataTable, key.toString());
							dataTable.addColumnsAfterRowIndex(Column.build(columnArguments));	
						}						
					});
				}
			}
			
			Collection<Action> actions = (Collection<Action>) MapHelper.readByKey(arguments, FIELD_ACTIONS);
			if(CollectionHelper.isNotEmpty(actions))
				listener.addHeaderToolbarLeftCommands(dataTable, actions);
			
			actions = (Collection<Action>) MapHelper.readByKey(arguments, FIELD_RECORD_ACTIONS);
			if(CollectionHelper.isNotEmpty(actions))
				listener.addRecordMenuItems(dataTable, actions);
			
			dataTable.addStyleClasses("cyk-datatable");
			
			if(StringHelper.isBlank(dataTable.stickyTopAt) && Boolean.TRUE.equals(dataTable.stickyHeader)) {
				//dataTable.stickyTopAt = ".layout-topbar";
			}
			
			if(StringHelper.isBlank(dataTable.rowTooltipStyleClass))
				dataTable.rowTooltipStyleClass = dataTable.identifier+"_row_tooltip";			
			dataTable.addStyleClasses(dataTable.rowTooltipStyleClass);
		}
		
		/*public static interface Listener {
			
		}*/
		
		public static final String FIELD_COLUMNS_FIELDS_NAMES = "columnsFieldsNames";
		public static final String FIELD_COLUMNS_FIELDS_NAMES_COMPUTABLE = "columnsFieldsNamesComputable";
		
		public static final String FIELD_USABLE_AS_SELECTION_ONLY = "usableAsSelectionOnly";
	}
	
	/**/
	
	public static interface Listener extends AbstractCollection.Listener {
		Object getCellValueByRecordByColumn(Object record,Integer recordIndex,Column column,Integer columnIndex);
		String getStyleClassByRecord(Object record,Integer recordIndex);
		String getStyleClassByRecordByColumn(Object record,Integer recordIndex,Column column,Integer columnIndex);
		String getTooltipByRecord(Object record,Integer recordIndex);
		void addRow(AbstractDataTable dataTable,Object element);
		void removeRow(AbstractDataTable dataTable,Object element);
		//void addColumn(AbstractDataTable dataTable);
		void removeColumn(AbstractDataTable dataTable,Column column);
		Map<Object,Object> getColumnArguments(AbstractDataTable dataTable,String fieldName);
		Map<Object,Object> getHeaderToolbarLeftCommandArguments(AbstractDataTable dataTable,Action action);
		void addHeaderToolbarLeftCommands(AbstractDataTable dataTable,Collection<Action> actions);
		Map<Object,Object> getRecordMenuItemArguments(AbstractDataTable dataTable,Action action);
		void addRecordMenuItems(AbstractDataTable dataTable,Collection<Action> actions);
		/**/
		public static abstract class AbstractImpl extends AbstractCollection.Listener.AbstractImpl implements Listener,Serializable {
			@Override
			public Object getCellValueByRecordByColumn(Object record, Integer recordIndex, Column column,Integer columnIndex) {
				if(record == null || column == null || StringHelper.isBlank(column.getFieldName()))
					return null;
				Object value = FieldHelper.read(record, column.getFieldName());
				if(Value.Type.CURRENCY.equals(column.getValueType())) {
					if(value instanceof Number)
						value = NumberHelper.format((Number) value);
				}
				return value;
			}
			
			@Override
			public String getStyleClassByRecord(Object record, Integer recordIndex) {
				return null;
			}
			
			@Override
			public String getStyleClassByRecordByColumn(Object record, Integer recordIndex, Column column,Integer columnIndex) {
				if(record == null || column == null || StringHelper.isBlank(column.getFieldName()))
					return null;
				if(Value.Type.CURRENCY.equals(column.getValueType()))
					return "cyk-text-align-right";
				return null;
			}
			
			@Override
			public String getTooltipByRecord(Object record, Integer recordIndex) {
				return null;
			}
			
			@Override
			public void addRow(AbstractDataTable dataTable, Object element) {}
			
			@Override
			public void removeRow(AbstractDataTable dataTable, Object element) {}
			/*
			@Override
			public void addColumn(AbstractDataTable dataTable) {
				String fieldName;
				if(dataTable.dataGrid == null) {
					fieldName = null;
				}else
					fieldName = (String) dataTable.dataGrid.formatNextColumnKey();
				Map<Object,Object> arguments = null;
				arguments = ((Listener)(dataTable.listener == null ? Listener.AbstractImpl.DefaultImpl.INSTANCE : dataTable.listener)).getColumnArguments(dataTable,fieldName);
				Column column = Column.build(arguments);
				dataTable.addColumnsAfterRowIndex(column);
				if(dataTable.dataGrid == null) {
					
				}else
					dataTable.dataGrid.addColumn(fieldName);
			}
			*/
			@Override
			public void removeColumn(AbstractDataTable dataTable, Column column) {}
			
			@Override
			public Map<Object, Object> getColumnArguments(AbstractDataTable dataTable,String fieldName) {
				if(StringHelper.isBlank(fieldName))
					fieldName = String.format(dataTable.columnFieldNameFormat, CollectionHelper.getSize(dataTable.columnsAfterRowIndex));
				String headerText = InternationalizationHelper.buildString(InternationalizationHelper.buildKey(fieldName),null,null,Case.FIRST_CHARACTER_UPPER_REMAINDER_LOWER);
				return MapHelper.instantiate(Column.FIELD_HEADER_TEXT,headerText,Column.FIELD_FIELD_NAME,fieldName
						,Column.ConfiguratorImpl.FIELD_EDITABLE,dataTable.editable,Column.FIELD_REMOVE_COMMAND_BUTTON);
			}
			
			@Override
			public Map<Object, Object> getHeaderToolbarLeftCommandArguments(AbstractDataTable dataTable,Action action) {
				return null;
			}
			
			@Override
			public Map<Object, Object> getRecordMenuItemArguments(AbstractDataTable dataTable, Action action) {
				return null;
			}

			@Override
			public void addHeaderToolbarLeftCommands(AbstractDataTable dataTable, Collection<Action> actions) {
				if(CollectionHelper.isEmpty(actions))
					return;
				actions.forEach(action -> {
					Map<Object, Object> arguments = getHeaderToolbarLeftCommandArguments(dataTable, action);
					if(MapHelper.isEmpty(arguments)) {
						if(Action.CREATE.equals(action))
							dataTable.addHeaderToolbarLeftCommandsByArgumentsOpenViewInDialogCreate();
					}else
						dataTable.addHeaderToolbarLeftCommands(CommandButton.build(arguments));
				});
			}
			
			@Override
			public void addRecordMenuItems(AbstractDataTable dataTable, Collection<Action> actions) {
				if(CollectionHelper.isEmpty(actions))
					return;
				actions.forEach(action -> {
					Map<Object, Object> arguments = getRecordMenuItemArguments(dataTable, action);
					if(MapHelper.isEmpty(arguments)) {
						if(Action.READ.equals(action))
							dataTable.addRecordMenuItemByArgumentsOpenViewInDialogRead();
						else if(Action.UPDATE.equals(action))
							dataTable.addRecordMenuItemByArgumentsOpenViewInDialogUpdate();
						else if(Action.DELETE.equals(action))
							dataTable.addRecordMenuItemByArgumentsExecuteFunctionDelete();
					}else
						dataTable.addRecordCommands(MenuItem.build(arguments));
				});
			}
			
			/**/
			
			public static class DefaultImpl extends Listener.AbstractImpl implements Serializable {
				public static final Listener INSTANCE = new DefaultImpl();
			}
		}	
	}
	
	public static class AbstractAddRowCommandButtonActionListenerImpl extends AbstractAction.Listener.AbstractImpl implements Serializable {
		
		private AbstractDataTable dataTable;
		
		public AbstractAddRowCommandButtonActionListenerImpl(AbstractDataTable dataTable) {
			this.dataTable = dataTable;
		}
		
		@SuppressWarnings("unchecked")
		protected Object __executeFunction__(Object argument) {
			if(dataTable.dataGrid == null) {
				if(dataTable.value != null && !(dataTable.value instanceof Collection))
					throw new RuntimeException("Cannot add instance into value of type "+dataTable.value.getClass());
				if(dataTable.value == null)
					dataTable.value = new ArrayList<>();
				Object element = ClassHelper.instanciate(dataTable.elementClass);				
				((Collection<Object>)dataTable.value).add(element);
				//if(dataTable.listener != null)
				//	((Listener)dataTable.listener).listenAddRow(dataTable, element);
			}else
				dataTable.dataGrid.addRow();
			return "row added";
		}
	}
}