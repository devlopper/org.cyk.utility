package org.cyk.utility.__kernel__.data.structure.grid;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.data.structure.AbstractObjectImpl;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class Grid extends AbstractObjectImpl implements Serializable {

	private List<Row> rows;
	private String columnKeyFormat = Row.COLUMN_KEY_FORMAT;
	private Set<java.lang.Object> columnsKeys;
	private Listener listener;
	
	public Grid(Collection<?> rowsObjects,String...columnsKeys) {
		addColumnsKeys(columnsKeys);
		addRows(rowsObjects);
	}
	
	public Grid writeValuesToObjects() {
		if(CollectionHelper.isEmpty(rows))
			return this;
		rows.forEach(row -> {
			row.writeValuesToObjects();
		});
		return this;
	}
	
	public Set<java.lang.Object> getColumnsKeys(Boolean injectIfNull) {
		if(columnsKeys == null && Boolean.TRUE.equals(injectIfNull))
			columnsKeys = new LinkedHashSet<Object>();
		return columnsKeys;
	}
	
	public Grid addColumnsKeys(Set<java.lang.Object> columnsKeys) {
		if(CollectionHelper.isEmpty(columnsKeys))
			return this;
		getColumnsKeys(Boolean.TRUE).addAll(columnsKeys);
		return this;
	}
	
	public Grid addColumnsKeys(String...columnsKeys) {
		if(ArrayHelper.isEmpty(columnsKeys))
			return this;
		addColumnsKeys((Set<Object>) CollectionHelper.cast(Object.class, CollectionHelper.setOf(columnsKeys)));
		return this;
	}
	
	public List<Row> getRows(Boolean injectIfNull) {
		if(rows == null && Boolean.TRUE.equals(injectIfNull))
			rows = new ArrayList<>();
		return rows;
	}
	
	public Grid addRows(Collection<?> rowsObjects) {
		if(CollectionHelper.isEmpty(rowsObjects))
			return this;
		rowsObjects.forEach(object -> {
			addRow(object);
		});
		return this;
	}
	
	public Grid addRow(Object object) {
		Row row = ClassHelper.instanciate(Row.class);
		row.setGrid(this);
		row.setObject(object);
		getRows(Boolean.TRUE).add(row);
		if(CollectionHelper.isNotEmpty(columnsKeys))
			columnsKeys.forEach(key -> {
				Object value = null;
				if(object!= null && key != null)
					value = listener == null ? Listener.AbstractImpl.__getColumnValueFromRowObject__(this, object, key) 
							: listener.getColumnValueFromRowObject(this, object, key);
				row.addColumn(key,value);
			});
		if(listener != null)
			listener.listenAddRow(this, row);
		return this;
	}
	
	public Grid addRow() {
		return addRow(null);
	}
	
	public Grid removeRow(Row row) {
		if(row == null || CollectionHelper.isEmpty(rows))
			return this;
		rows.remove(row);
		if(listener != null)
			listener.listenRemoveRow(this, row);
		return this;
	}
	
	public Object formatNextColumnKey() {
		if(listener == null) {
			if(StringHelper.isBlank(columnKeyFormat))
				return "row"+getNumberOfColumns();
			return String.format(columnKeyFormat, getNumberOfColumns());	
		}
		return listener.formatNextColumnKey(this);
	}
	
	public Grid addColumn(String key) {
		if(CollectionHelper.isEmpty(rows)) {
			LogHelper.logInfo("We cannot add column because there is no row", getClass());
			return this;
		}
		if(StringHelper.isBlank(key) && StringHelper.isNotBlank(columnKeyFormat))
			key = (String) formatNextColumnKey();
		if(StringHelper.isBlank(key))
			key = "row"+getNumberOfColumns();
		for(Row row : rows) {
			row.addColumn(key);
			
		}
		return this;
	}
	
	public Grid addColumn() {
		return addColumn(null);
	}
	
	public Grid removeColumn(String key) {
		if(StringHelper.isBlank(key) || CollectionHelper.isEmpty(rows))
			return this;
		if(CollectionHelper.isNotEmpty(columnsKeys)) {
			columnsKeys.remove(key);
			rows.forEach(row -> {
				row.removeColumn(key);
				if(listener != null)
					listener.listenRemoveColumn(this, row,key);
				}
			);		
		}
		return this;
	}
	
	public Integer getNumberOfRows() {
		return CollectionHelper.getSize(rows);
	}
	
	public Integer getNumberOfColumns() {
		return CollectionHelper.isEmpty(rows) ? 0 : rows.iterator().next().getnumberOfColumns();
	}
	
	public Grid setValue(Row row,java.lang.Object key,java.lang.Object value) {
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
		if(listener == null)
			Listener.AbstractImpl.__formatValue__(this, row, key);
		return listener.formatValue(this, row, key);
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
	
	@Getter @Setter @NoArgsConstructor
	public static class Row extends AbstractObject implements Serializable {
		
		private Grid grid;
		private Object object;
		private Map<java.lang.Object,java.lang.Object> values;
		
		public Row writeValuesToObjects() {
			if(MapHelper.isEmpty(values))
				return this;
			values.forEach((key,value) -> {
				if(grid == null || grid.listener == null)
					Listener.AbstractImpl.__setColumnValueToRowObject__(grid, this, key, value);
				else
					grid.listener.setColumnValueToRowObject(grid, this, key, value);
			});
			return this;
		}
		
		public Map<java.lang.Object,java.lang.Object> getValues(Boolean injectIfNull) {
			if(values == null && Boolean.TRUE.equals(injectIfNull))
				values = new LinkedHashMap<>();
			return values;
		}
		
		public Row addColumn(java.lang.Object key,java.lang.Object value) {
			getValues(Boolean.TRUE).put(key, value);
			if(grid.listener != null)
				grid.listener.listenAddColumn(grid, this,key);
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
		
		public java.lang.Object formatValue(java.lang.Object key) {
			if(key == null)
				return null;
			if(grid == null || grid.listener == null)
				Listener.AbstractImpl.__formatValue__(grid, this, key);
			return grid.listener.formatValue(grid, this, key);
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
		void listenAddRow(Grid grid,Row row);
		void listenRemoveRow(Grid grid,Row row);
		void listenAddColumn(Grid grid,Row row,java.lang.Object columnKey);
		void listenRemoveColumn(Grid grid,Row row,java.lang.Object columnKey);
		java.lang.Object getColumnValueFromRowObject(Grid grid,Object rowObject,java.lang.Object columnKey);
		void setColumnValueToRowObject(Grid grid, Row row, Object columnKey,Object value);
		java.lang.Object formatValue(Grid grid,Row row,java.lang.Object columnKey);
		Object formatNextColumnKey(Grid grid);
		/**/
		
		public static abstract class AbstractImpl extends AbstractObject implements Listener,Serializable{
			@Override
			public void listenAddRow(Grid grid, Row row) {}
			@Override
			public void listenRemoveRow(Grid grid, Row row) {}
			@Override
			public void listenAddColumn(Grid grid, Row row, java.lang.Object columnKey) {}
			@Override
			public void listenRemoveColumn(Grid grid, Row row, java.lang.Object columnKey) {}
			
			@Override
			public Object getColumnValueFromRowObject(Grid grid, Object rowObject, Object columnKey) {
				return __getColumnValueFromRowObject__(grid, rowObject, columnKey);
			}
			
			@Override
			public void setColumnValueToRowObject(Grid grid, Row row, Object columnKey, Object value) {
				__setColumnValueToRowObject__(grid, row, columnKey, value);
			}
			
			@Override
			public java.lang.Object formatValue(Grid grid, Row row, java.lang.Object columnKey) {
				if(grid == null || row == null || columnKey == null)
					return null;
				return formatValue(grid, row, columnKey,row.getValue(columnKey));
			}
			
			protected java.lang.Object formatValue(Grid grid, Row row, java.lang.Object columnKey,Object value) {
				return value;
			}
			
			@Override
			public Object formatNextColumnKey(Grid grid) {
				return __formatNextColumnKey__(grid);
			}
			
			/**/
			
			public static Object __getColumnValueFromRowObject__(Grid grid, Object rowObject, Object columnKey) {
				return FieldHelper.read(rowObject, columnKey.toString());
			}
			
			public static void __setColumnValueToRowObject__(Grid grid, Row row, Object columnKey,Object value) {
				FieldHelper.write(row.getObject(), columnKey.toString(), value);
			}
			
			public static java.lang.Object __formatValue__(Grid grid, Row row, java.lang.Object columnKey) {
				return row.getValue(columnKey);
			}
			
			public static Object __formatNextColumnKey__(Grid grid) {
				if(grid == null)
					return null;
				if(StringHelper.isBlank(grid.columnKeyFormat))
					return "row"+grid.getNumberOfColumns();
				return String.format(grid.columnKeyFormat, grid.getNumberOfColumns());
			}
		}
	}
}