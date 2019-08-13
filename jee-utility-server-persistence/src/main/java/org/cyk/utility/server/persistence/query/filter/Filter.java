package org.cyk.utility.server.persistence.query.filter;

import java.util.Collection;

import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.value.ValueUsageType;

public interface Filter extends Objectable {

	Class<?> getKlass();
	Filter setKlass(Class<?> klass);
	
	Fields getFields();
	Fields getFields(Boolean injectIfNull);
	Filter setFields(Fields fields);
	Filter addFields(Collection<Field> fields);
	Filter addFields(Field...fields);
	Filter addField(String fieldName, Object fieldValue,ValueUsageType valueUsageType, ArithmeticOperator arithmeticOperator);
	Filter addField(String fieldName, Object fieldValue,ValueUsageType valueUsageType);
	Filter addField(String fieldName, Object fieldValue);
	
	/**
	 * Global filtering value
	 */
	String getValue();
	Filter setValue(String value);
	
	Boolean hasFieldWithPath(String...paths);
	
	Field getFieldByPath(String...paths);
	
	Object getFieldValueByPath(String...paths);
	
	/**/
	
	String PROPERTY_FIELDS = "fields";
}
