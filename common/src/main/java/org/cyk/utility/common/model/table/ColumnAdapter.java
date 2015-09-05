package org.cyk.utility.common.model.table;

import java.lang.reflect.Field;
import java.util.List;


public class ColumnAdapter<DIMENSION extends Column<DATA, CELL, VALUE>,DATA,CELL extends Cell<VALUE>,VALUE> extends DimensionAdapter<DIMENSION, DATA, CELL, VALUE> 
	implements ColumnListener<DIMENSION,DATA,CELL,VALUE> {

	@Override
	public Boolean isColumn(Field field) {
		return null;
	}

	@Override
	public void populateFromDataClass(Class<?> aClass, List<Field> fields) {}

	@Override
	public void sort(List<Field> fields) {}


}
