package org.cyk.utility.common.model.table;

import java.lang.reflect.Field;
import java.util.List;

import org.cyk.utility.common.AbstractFieldSorter.FieldSorter;

import lombok.Getter;
import lombok.Setter;


public interface ColumnListener<DIMENSION extends Column<DATA, CELL, VALUE>,DATA,CELL extends Cell<VALUE>,VALUE> extends DimensionListener<DIMENSION,DATA,CELL,VALUE> {
	
	Boolean isColumn(Field field);
	void populateFromDataClass(Class<?> aClass,List<Field> fields);
	void sort(List<Field> fields);
	Boolean getSortable();
	void setSortable(Boolean sortable);
	//void sort(List<Column<?, ?, ?>> columns);
	
	List<String> getExpectedFieldNames();
	void setExpectedFieldNames(List<String> collection);
	
	//public Integer getOrderIndex(Column<?, ?, ?> column);
	
	FieldSorter getFieldSorter();
	
	/**/
	
	@Getter @Setter
	public static class Adapter<DIMENSION extends Column<DATA, CELL, VALUE>,DATA,CELL extends Cell<VALUE>,VALUE> extends DimensionListener.Adapter<DIMENSION, DATA, CELL, VALUE> 
		implements ColumnListener<DIMENSION,DATA,CELL,VALUE> {
	
		private static final long serialVersionUID = 1L;

		private Boolean sortable;
		
		@Override
		public Boolean isColumn(Field field) {
			return null;
		}
	
		@Override
		public void populateFromDataClass(Class<?> aClass, List<Field> fields) {}
	
		@Override public void sort(List<Field> fields) {}
		
		@Override
		public List<String> getExpectedFieldNames() {
			return null;
		}
		
		@Override
		public void setExpectedFieldNames(List<String> collection) {}

		@Override
		public FieldSorter getFieldSorter() {
			return null;
		}
		
	}

}
