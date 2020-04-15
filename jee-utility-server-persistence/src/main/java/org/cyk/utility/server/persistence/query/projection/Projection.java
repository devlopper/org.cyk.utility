package org.cyk.utility.server.persistence.query.projection;

import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

@Deprecated
public interface Projection extends Objectable {

	Class<?> getKlass();
	Projection setKlass(Class<?> klass);
	
	Fields getFields();
	Fields getFields(Boolean injectIfNull);
	Projection setFields(Fields fields);
	Projection addFields(Collection<Field> fields);
	Projection addFields(Field...fields);
	Projection addField(String fieldName);
	
	Boolean hasFieldWithPath(String...paths);
	
	Field getFieldByPath(String...paths);
	
	/**/
	
	String PROPERTY_FIELDS = "fields";
}
