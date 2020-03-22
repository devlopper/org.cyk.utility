package org.cyk.utility.__kernel__.object.__static__.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class DataGrid extends AbstractObjectImpl implements Serializable {

	private List<Row> rows;
	private String columnKeyFormat = Row.COLUMN_KEY_FORMAT;
	private Listener listener;
	
	public List<Row> getRows(Boolean injectIfNull) {
		if(rows == null && Boolean.TRUE.equals(injectIfNull))
			rows = new ArrayList<>();
		return rows;
	}
	
	public DataGrid addRow() {
		Row row = ClassHelper.instanciate(Row.class);
		if(listener != null)
			listener.listenAddRow(this, row);
		getRows(Boolean.TRUE).add(row);
		return this;
	}
	
	public DataGrid addColumn(String key) {
		if(CollectionHelper.isEmpty(rows)) {
			LogHelper.logInfo("We cannot add column because there is no row", getClass());
			return this;
		}
		if(StringHelper.isBlank(key) && StringHelper.isNotBlank(columnKeyFormat))
			key = String.format(columnKeyFormat, getNumberOfColumns());
		if(StringHelper.isBlank(key))
			key = "row"+getNumberOfColumns();
		for(Row row : rows) {
			row.addColumn(key);
			if(listener != null)
				listener.listenAddColumn(this, row,key);
		}
		return this;
	}
	
	public DataGrid addColumn() {
		return addColumn(null);
	}
	
	public Integer getNumberOfRows() {
		return CollectionHelper.getSize(rows);
	}
	
	public Integer getNumberOfColumns() {
		return CollectionHelper.isEmpty(rows) ? 0 : rows.iterator().next().getnumberOfColumns();
	}
	
	public DataGrid setValue(Row row,java.lang.Object key,java.lang.Object value) {
		if(row == null || key == null)
			return this;
		row.setValue(key,value);
		return this;
	}
	
	@Override
	public String toString() {
		if(CollectionHelper.isEmpty(rows))
			return super.toString();
		Collection<String> strings = new ArrayList<>();
		for(Row row : rows)
			strings.add(row.toString());
		return StringHelper.concatenate(strings, "\n\r");			
	}
	
	/**/
	
	@Getter @Setter
	public static class Row extends AbstractObject implements Serializable {
		
		private Object identifiable;
		private Map<java.lang.Object,java.lang.Object> values;
		
		public Map<java.lang.Object,java.lang.Object> getValues(Boolean injectIfNull) {
			if(values == null && Boolean.TRUE.equals(injectIfNull))
				values = new LinkedHashMap<>();
			return values;
		}
		
		public Row addColumn(java.lang.Object key,java.lang.Object value) {
			getValues(Boolean.TRUE).put(key, value);
			return this;
		}
		
		public Row addColumn(java.lang.Object key) {
			return addColumn(key, null);
		}
		
		public Integer getnumberOfColumns() {
			return MapHelper.isEmpty(values) ? 0 : values.size();
		}
		
		public Row setValue(java.lang.Object key,java.lang.Object value) {
			if(key == null)
				return this;
			getValues(Boolean.TRUE).put(key, value);
			return this;
		}
		
		@Override
		public String toString() {
			return values ==  null ? null : values.toString();
		}
		
		/**
		 * value index by its order number
		 */
		public static final String COLUMN_KEY_FORMAT = "value%s";
	}
	
	public static interface Listener {
		void listenAddRow(DataGrid grid,Row row);
		void listenAddColumn(DataGrid grid,Row row,java.lang.Object columnKey);
		/**/
		
		public static abstract class AbstractImpl extends AbstractObject implements Listener,Serializable{
			@Override
			public void listenAddRow(DataGrid grid, Row row) {}
			@Override
			public void listenAddColumn(DataGrid grid, Row row, java.lang.Object columnKey) {}
		}
	}
}