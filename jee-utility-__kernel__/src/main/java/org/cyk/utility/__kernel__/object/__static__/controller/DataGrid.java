package org.cyk.utility.__kernel__.object.__static__.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @Deprecated
public class DataGrid extends AbstractObjectImpl implements Serializable {

	private List<Row> rows;
	private String columnKeyFormat = Row.COLUMN_KEY_FORMAT;
	private Set<java.lang.Object> defaultColumnsKeys = new LinkedHashSet<>();
	private Listener listener;
	
	public List<Row> getRows(Boolean injectIfNull) {
		if(rows == null && Boolean.TRUE.equals(injectIfNull))
			rows = new ArrayList<>();
		return rows;
	}
	
	public DataGrid addRow() {
		Row row = ClassHelper.instanciate(Row.class);
		getRows(Boolean.TRUE).add(row);
		if(CollectionHelper.isNotEmpty(defaultColumnsKeys))
			defaultColumnsKeys.forEach(key -> {row.addColumn(key);});
		if(listener != null)
			listener.listenAddRow(this, row);
		return this;
	}
	
	public DataGrid removeRow(Row row) {
		if(row == null || CollectionHelper.isEmpty(rows))
			return this;
		rows.remove(row);
		if(listener != null)
			listener.listenRemoveRow(this, row);
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
	
	public DataGrid removeColumn(String key) {
		if(StringHelper.isBlank(key) || CollectionHelper.isEmpty(rows))
			return this;
		rows.forEach(row -> {
			row.removeColumn(key);
			if(listener != null)
				listener.listenRemoveColumn(this, row,key);
			}
		);		
		return this;
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
	
	public java.lang.Object getValue(Row row,java.lang.Object key) {
		if(row == null || key == null)
			return null;
		return row.getValue(key);
	}
	
	public java.lang.Object formatValue(Row row,java.lang.Object key) {
		if(row == null || key == null)
			return null;
		java.lang.Object value = getValue(row, key);
		if(listener != null)
			value = listener.listenFormatValue(this, row, key, value);
		return value;
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
	
	@Getter @Setter @Deprecated
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
		
		public Row removeColumn(java.lang.Object key) {
			if(MapHelper.isEmpty(values))
				return this;
			values.remove(key);
			return this;
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
		
		public java.lang.Object getValue(java.lang.Object key) {
			if(key == null || MapHelper.isEmpty(values))
				return null;
			return values.get(key);
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
	@Deprecated
	public static interface Listener {
		void listenAddRow(DataGrid grid,Row row);
		void listenRemoveRow(DataGrid grid,Row row);
		void listenAddColumn(DataGrid grid,Row row,java.lang.Object columnKey);
		void listenRemoveColumn(DataGrid grid,Row row,java.lang.Object columnKey);
		java.lang.Object listenFormatValue(DataGrid grid,Row row,java.lang.Object columnKey,java.lang.Object formatted);
		/**/
		
		public static abstract class AbstractImpl extends AbstractObject implements Listener,Serializable{
			@Override
			public void listenAddRow(DataGrid grid, Row row) {}
			@Override
			public void listenRemoveRow(DataGrid grid, Row row) {}
			@Override
			public void listenAddColumn(DataGrid grid, Row row, java.lang.Object columnKey) {}
			@Override
			public void listenRemoveColumn(DataGrid grid, Row row, java.lang.Object columnKey) {}
			
			@Override
			public java.lang.Object listenFormatValue(DataGrid grid, Row row, java.lang.Object columnKey,java.lang.Object formatted) {
				return formatted;
			}
		}
	}
}