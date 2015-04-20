package org.cyk.utility.common.model.table;

import java.lang.reflect.Field;

public interface Column<DATA,CELL extends Cell<VALUE>, VALUE> extends Dimension<DATA, CELL, VALUE> {

	//void setObject(Object anObject);
	//Object getObject();
	
	void setField(Field aField);
	Field getField();
	
	void setFooter(String footer);
	String getFooter();
	
}
