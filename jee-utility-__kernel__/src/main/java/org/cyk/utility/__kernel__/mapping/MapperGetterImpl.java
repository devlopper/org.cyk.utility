package org.cyk.utility.__kernel__.mapping;

import java.io.Serializable;

import org.cyk.utility.__kernel__.DependencyInjection;

public class MapperGetterImpl extends AbstractMapperGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public <S,D> MapperSourceDestination<S, D> getBySourceClassByDestinationClass(Class<S> sourceClass, Class<D> destinationClass,Boolean isThrowExceptionIfNull) {
		if(sourceClass == null || destinationClass == null)
			return null;
		Class<?> klass = MapperClassGetter.getInstance().get(sourceClass);
		if(klass == null && Boolean.TRUE.equals(isThrowExceptionIfNull))
			throw new RuntimeException(String.format("mapper of %s and %s not found", sourceClass,destinationClass));
		return (MapperSourceDestination<S,D>) DependencyInjection.inject(klass);
	}

}
