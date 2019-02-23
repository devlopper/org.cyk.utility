package org.cyk.utility.clazz;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface ClassInstance extends Objectable {

	Class<?> getClazz();
	ClassInstance setClazz(Class<?> clazz);
	
	Boolean getIsPersistable();
	ClassInstance setIsPersistable(Boolean isPersistable);
	
	Boolean getIsTransferable();
	ClassInstance setIsTransferable(Boolean isTransferable);
	
}
