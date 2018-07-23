package org.cyk.utility.server.persistence;

import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInput;

public interface PersistenceQueryIdentifierFieldGetter extends FunctionWithPropertiesAsInput<Collection<Field>> {

	PersistenceQueryIdentifierFieldGetter setClazz(Class<?> aClass);
	Class<?> getClazz();
}
