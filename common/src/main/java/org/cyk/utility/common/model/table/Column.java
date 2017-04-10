package org.cyk.utility.common.model.table;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.AbstractFieldSorter.FieldSorter;

public interface Column<DATA,CELL extends Cell<VALUE>, VALUE> extends Dimension<DATA, CELL, VALUE> {

	//void setObject(Object anObject);
	//Object getObject();
	
	void setField(Field aField);
	Field getField();
	
	void setFooter(String footer);
	String getFooter();
	
	/**/
	
	public interface Listener<DIMENSION extends Column<DATA, CELL, VALUE>,DATA,CELL extends Cell<VALUE>,VALUE> extends DimensionListener<DIMENSION,DATA,CELL,VALUE> {
		
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
			implements Listener<DIMENSION,DATA,CELL,VALUE>,Serializable {
		
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
			
			/**/
			
			@Getter @Setter
			public static class Default<DIMENSION extends Column<DATA, CELL, VALUE>,DATA,CELL extends Cell<VALUE>,VALUE> extends Listener.Adapter<DIMENSION, DATA, CELL, VALUE> 
				implements Listener<DIMENSION,DATA,CELL,VALUE>,Serializable {
				
				private static final long serialVersionUID = 1L;
				
			}
		}

	}

}
