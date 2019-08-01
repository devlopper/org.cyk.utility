package org.cyk.utility.server.persistence.query.filter;

import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.field.FieldInstance;
import org.cyk.utility.value.ValueUsageType;

public interface Field extends Objectable {

	FieldInstance getInstance();
	Field setInstance(FieldInstance instance);
	
	Object getValue();
	Field setValue(Object value);
	
	ValueUsageType getValueUsageType();
	Field setValueUsageType(ValueUsageType valueUsageType);
	
	ArithmeticOperator getArithmeticOperator();
	Field setArithmeticOperator(ArithmeticOperator arithmeticOperator);
}
