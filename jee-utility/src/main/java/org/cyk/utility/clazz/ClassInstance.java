package org.cyk.utility.clazz;

import java.lang.reflect.Field;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface ClassInstance extends Objectable {

	Class<?> getClazz();
	ClassInstance setClazz(Class<?> clazz);
	
	Field getSystemIdentifierField();
	ClassInstance setSystemIdentifierField(Field systemIdentifierField);
	
	Boolean getIsPersistable();
	ClassInstance setIsPersistable(Boolean isPersistable);
	
	Boolean getIsTransferable();
	ClassInstance setIsTransferable(Boolean isTransferable);
	
}
