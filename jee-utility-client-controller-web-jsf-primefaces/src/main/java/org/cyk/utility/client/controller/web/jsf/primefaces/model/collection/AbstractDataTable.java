package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.MenuItem;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractDataTable extends AbstractCollection implements Serializable {

	protected Column orderNumberColumn;
	protected Column menuColumn;
	protected Collection<Column> columnsAfterRowIndex,selectedColumnsAfterRowIndex;
	protected Boolean areColumnsChoosable;
	
	/**/
	
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
	
	public static abstract class AbstractConfiguratorImpl<DATATABLE extends AbstractDataTable> extends AbstractCollection.AbstractConfiguratorImpl<DATATABLE> implements Serializable {

		@Override
		public void configure(DATATABLE dataTable, Map<Object, Object> arguments) {
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
		}
	}
	
	/**/
	
	public static interface Listener {
		
		String listenGetStyleClassByRecord(Object record,Integer recordIndex);
		
		String listenGetStyleClassByRecordByColumn(Object record,Integer recordIndex,Column column,Integer columnIndex);
		
		/**/
		
		public static abstract class AbstractImpl extends AbstractObject implements Listener,Serializable {
			
			@Override
			public String listenGetStyleClassByRecord(Object record, Integer recordIndex) {
				return null;
			}
			
			@Override
			public String listenGetStyleClassByRecordByColumn(Object record, Integer recordIndex, Column column,Integer columnIndex) {
				return null;
			}
			
		}
		
	}
}