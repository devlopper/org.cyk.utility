package org.cyk.utility.__kernel__.mapping;

import java.io.Serializable;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface MapperGetter {

	default <S,D> MapperSourceDestination<S, D> getBySourceClassByDestinationClass(Class<S> sourceClass, Class<D> destinationClass,Boolean isThrowExceptionIfNull) {
		if(sourceClass == null || destinationClass == null)
			return null;
		Class<?> klass = MapperClassGetter.getInstance().get(sourceClass);
		if(klass == null && Boolean.TRUE.equals(isThrowExceptionIfNull))
			throw new RuntimeException(String.format("mapper of %s and %s not found", sourceClass,destinationClass));
		return (MapperSourceDestination<S,D>) DependencyInjection.inject(klass);
	}
	
	default <S,D> MapperSourceDestination<S,D> getBySourceClassByDestinationClass(Class<S> sourceClass,Class<D> destinationClass) {
		return getBySourceClassByDestinationClass(sourceClass, destinationClass,Boolean.TRUE);
	}
	
	default <S,D> MapperSourceDestination<S,D> getBySourceByDestinationClass(S source,Class<D> destinationClass,Boolean isThrowExceptionIfNull) {
		if(source == null || destinationClass == null)
			return null;
		return (MapperSourceDestination<S, D>) getBySourceClassByDestinationClass(source.getClass(), destinationClass,isThrowExceptionIfNull);
	}
	
	default <S,D> MapperSourceDestination<S,D> getBySourceByDestinationClass(S source,Class<D> destinationClass) {
		if(source == null || destinationClass == null)
			return null;
		return (MapperSourceDestination<S, D>) getBySourceClassByDestinationClass(source.getClass(), destinationClass,Boolean.TRUE);
	}
	
	/**/
	
	public abstract class AbstractImpl implements MapperGetter,Serializable {
		private static final long serialVersionUID = 1L;

	}

	/**/
	
	static MapperGetter getInstance() {
		return Helper.getInstance(MapperGetter.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}