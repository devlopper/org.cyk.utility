package org.cyk.utility.clazz;

import java.lang.reflect.Field;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.field.Fields;

public interface ClassInstance extends Objectable {

	Class<?> getClazz();
	ClassInstance setClazz(Class<?> clazz);
	
	Fields getFields();
	Fields getFields(Boolean injectIfNull);
	ClassInstance setFields(Fields fields);
	
	Field getSystemIdentifierField();
	ClassInstance setSystemIdentifierField(Field systemIdentifierField);
	
	Field getBusinessIdentifierField();
	ClassInstance setBusinessIdentifierField(Field businessIdentifierField);
	
	Boolean getIsPersistable();
	ClassInstance setIsPersistable(Boolean isPersistable);
	
	Boolean getIsTransferable();
	ClassInstance setIsTransferable(Boolean isTransferable);
	
	String getTupleName();
	ClassInstance setTupleName(String tutpleName);
	
}
