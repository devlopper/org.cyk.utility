package org.cyk.utility.__kernel__;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.ws.rs.core.GenericType;

public interface TypeHelper {

	static Type instantiateParameterizedType(Type rawType, Type ownerType,Type[] arguments) {
		return new ParameterizedType() {
			@Override
			public Type getRawType() {
				return rawType;
			}
			
			@Override
			public Type getOwnerType() {
				return ownerType;
			}
			
			@Override
			public Type[] getActualTypeArguments() {
				return arguments;
			}
		};
	}
	
	static Type instantiateCollectionParameterizedType(Type collectionType, Type elementType) {
		return instantiateParameterizedType(collectionType, collectionType, new Type[] {elementType});
	}
	
	static <C extends Type,E extends Type> GenericType<Type> instantiateGenericCollectionParameterizedTypeForJaxrs(Type collectionType, E elementType) {
		return new GenericType<Type>(instantiateCollectionParameterizedType(collectionType,elementType));
	}
}