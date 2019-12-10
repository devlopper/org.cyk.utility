package org.cyk.utility.__kernel__.mapping;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface MapperGetter {

	<S,D> MapperSourceDestination<S,D> getBySourceClassByDestinationClass(Class<S> sourceClass,Class<D> destinationClass,Boolean isThrowExceptionIfNull);
	
	default <S,D> MapperSourceDestination<S,D> getBySourceClassByDestinationClass(Class<S> sourceClass,Class<D> destinationClass) {
		return getBySourceClassByDestinationClass(sourceClass, destinationClass,Boolean.TRUE);
	}
	
	@SuppressWarnings("unchecked")
	default <S,D> MapperSourceDestination<S,D> getBySourceByDestinationClass(S source,Class<D> destinationClass,Boolean isThrowExceptionIfNull) {
		if(source == null || destinationClass == null)
			return null;
		return (MapperSourceDestination<S, D>) getBySourceClassByDestinationClass(source.getClass(), destinationClass,isThrowExceptionIfNull);
	}
	
	@SuppressWarnings("unchecked")
	default <S,D> MapperSourceDestination<S,D> getBySourceByDestinationClass(S source,Class<D> destinationClass) {
		if(source == null || destinationClass == null)
			return null;
		return (MapperSourceDestination<S, D>) getBySourceClassByDestinationClass(source.getClass(), destinationClass,Boolean.TRUE);
	}
	
	/**/
	
	static MapperGetter getInstance() {
		MapperGetter instance = (MapperGetter) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(MapperGetter.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", MapperGetter.class);
		return instance;
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}
