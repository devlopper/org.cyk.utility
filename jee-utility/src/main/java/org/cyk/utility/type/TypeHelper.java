package org.cyk.utility.type;

import java.lang.reflect.Type;

import javax.ws.rs.core.GenericType;

import org.cyk.utility.helper.Helper;

public interface TypeHelper extends Helper {

	Type instanciateParameterizedType(Type rawType, Type ownerType, Type[] arguments);

	Type instanciateCollectionParameterizedType(Type collectionType, Type elementType);

	<C extends Type,E extends Type> GenericType<Type> instanciateGenericCollectionParameterizedTypeForJaxrs(Type collectionType, E elementType);

}
