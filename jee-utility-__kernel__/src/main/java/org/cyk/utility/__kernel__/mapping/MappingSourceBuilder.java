package org.cyk.utility.__kernel__.mapping;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination.Arguments;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

public interface MappingSourceBuilder {

	<SOURCE, DESTINATION> Collection<SOURCE> build(Collection<DESTINATION> destinations,Class<SOURCE> sourceClass,MapperSourceDestination.Arguments arguments);
	
	<SOURCE, DESTINATION> SOURCE build(DESTINATION destination,Class<SOURCE> sourceClass,MapperSourceDestination.Arguments arguments);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements MappingSourceBuilder,Serializable {
		
		public <SOURCE, DESTINATION> Collection<SOURCE> build(Collection<DESTINATION> destinations,Class<SOURCE> sourceClass,MapperSourceDestination.Arguments arguments) {
			if(CollectionHelper.isEmpty(destinations))
				return null;
			Class<DESTINATION> destinationClass = (Class<DESTINATION>) destinations.iterator().next().getClass();
			MapperSourceDestination<SOURCE, DESTINATION> mapper = MapperGetter.getInstance().getBySourceClassByDestinationClass(sourceClass,destinationClass);
			if(mapper == null)
				return null;
			return mapper.getSources(destinations,arguments);
		}
		
		@Override
		public <SOURCE, DESTINATION> SOURCE build(DESTINATION destination, Class<SOURCE> sourceClass,Arguments arguments) {
			if(destination == null)
				return null;
			Class<DESTINATION> destinationClass = (Class<DESTINATION>) destination.getClass();
			MapperSourceDestination<SOURCE, DESTINATION> mapper = MapperGetter.getInstance().getBySourceClassByDestinationClass(sourceClass,destinationClass);
			if(mapper == null)
				return null;
			return mapper.getSource(destination,arguments);
		}
	}
	
	/**/
	
	static MappingSourceBuilder getInstance() {
		return Helper.getInstance(MappingSourceBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);	
}