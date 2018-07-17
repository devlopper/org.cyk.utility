package org.cyk.utility.criteria;

import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.value.ValueUsageType;

/**
 * a principle or standard by which something may be judged or decided
 * @author Christian
 *
 */
public interface Criteria extends Objectable {

	Class<?> getClazz();
	Criteria setClazz(Class<?> aClass);
	
	String getClassName();
	Criteria setClassName(String name);
	
	FieldName getFieldName();
	Criteria setFieldName(FieldName fieldName);
	
	String getFieldNameAsString();
	Criteria setFieldNameAsString(String fieldName);
	
	ValueUsageType getFieldValueUsageType();
	Criteria setFieldValueUsageType(ValueUsageType valueUsageType);
	
	Boolean getIsMulptipleValues();
	Criteria setIsMulptipleValues(Boolean value);

	Boolean getIsAscendingOrder();
	Criteria setAscendingOrder(Boolean value);

	Collection<Object> getValuesMatch();
	Criteria setValuesMatch(Collection<Object> values);

	Collection<Object> getValuesRequired();
	Criteria setValuesRequired(Collection<Object> values);

	Collection<Object> getValuesToExclude();
	Criteria setValuesToExclude(Collection<Object> values);

	Object getNullValue();
	Criteria setNullValue(Object nullValue);
	
	Criteria addChild(Object... child);

}