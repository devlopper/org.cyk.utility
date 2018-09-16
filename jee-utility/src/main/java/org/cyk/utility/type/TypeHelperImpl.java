package org.cyk.utility.type;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.inject.Singleton;
import javax.ws.rs.core.GenericType;

import org.cyk.utility.helper.AbstractHelper;

@Singleton
public class TypeHelperImpl extends AbstractHelper implements TypeHelper, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Type instanciateParameterizedType(Type rawType, Type ownerType,Type[] arguments) {
		return __instanciateParameterizedType__(rawType, ownerType, arguments);
	}
	
	@Override
	public Type instanciateCollectionParameterizedType(Type collectionType, Type elementType) {
		return __instanciateCollectionParameterizedType__(collectionType, elementType);
	}
	
	@Override
	public <C extends Type,E extends Type> GenericType<Type> instanciateGenericCollectionParameterizedTypeForJaxrs(Type collectionType, E elementType) {
		return __instanciateGenericCollectionParameterizedTypeForJaxrs__(collectionType, elementType);
	}
	
	/**/
	
	public static Type __instanciateParameterizedType__(Type rawType, Type ownerType,Type[] arguments) {
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
	
	public static Type __instanciateCollectionParameterizedType__(Type collectionType, Type elementType) {
		return __instanciateParameterizedType__(collectionType, collectionType, new Type[] {elementType});
	}
	
	public static <C extends Type,E extends Type> GenericType<Type> __instanciateGenericCollectionParameterizedTypeForJaxrs__(Type collectionType, E elementType) {
		return new GenericType<Type>(TypeHelperImpl.__instanciateCollectionParameterizedType__(collectionType,elementType));
	}
	
}
