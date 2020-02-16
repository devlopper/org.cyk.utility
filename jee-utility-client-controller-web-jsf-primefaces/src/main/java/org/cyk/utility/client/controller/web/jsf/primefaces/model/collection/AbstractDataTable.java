package org.cyk.utility.client.controller.web.jsf.primefaces.model.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class AbstractDataTable extends AbstractCollection implements Serializable {

	protected Column orderNumberColumn;
	protected Column menuColumn;
	protected Collection<Column> columnsAfterRowIndex;
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
	
	/**/
	
	public static abstract class AbstractConfiguratorImpl<DATATABLE extends AbstractDataTable> extends AbstractCollection.AbstractConfiguratorImpl<DATATABLE> implements Serializable {

		@Override
		public void configure(DATATABLE dataTable, Map<Object, Object> arguments) {
			super.configure(dataTable, arguments);
			if(dataTable.getOrderNumberColumn() == null) {
				Map<Object,Object> map = new HashMap<>(Map.of(Column.FIELD_HEADER,"#",Column.FIELD_WIDTH,"55"));
				if(StringHelper.isBlank(dataTable.getSelectionMode())) {
					
				}else {
					map.put(Column.FIELD_SELECTION_MODE, dataTable.getSelectionMode());
				}
				dataTable.setOrderNumberColumn(Builder.build(Column.class,map));
			}
			if(dataTable.areColumnsChoosable == null)
				dataTable.areColumnsChoosable = Boolean.TRUE;
			
			if(dataTable.isExportable == null)
				dataTable.isExportable = Boolean.TRUE;
		}
	}	
}