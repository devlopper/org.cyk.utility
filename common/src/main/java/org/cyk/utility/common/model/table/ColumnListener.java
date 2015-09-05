package org.cyk.utility.common.model.table;

import java.lang.reflect.Field;
import java.util.List;


public interface ColumnListener<DIMENSION extends Column<DATA, CELL, VALUE>,DATA,CELL extends Cell<VALUE>,VALUE> extends DimensionListener<DIMENSION,DATA,CELL,VALUE> {
	
	Boolean isColumn(Field field);
	void populateFromDataClass(Class<?> aClass,List<Field> fields);
	void sort(List<Field> fields);
}
