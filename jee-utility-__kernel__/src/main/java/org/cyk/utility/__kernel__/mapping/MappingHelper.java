package org.cyk.utility.__kernel__.mapping;

import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.properties.Properties;

public interface MappingHelper {

	static <SOURCE, DESTINATION> Collection<DESTINATION> getDestinations(Collection<SOURCE> sources,Class<DESTINATION> destinationClass,Properties properties) {
		if(CollectionHelper.isEmpty(sources))
			return null;
		@SuppressWarnings("unchecked")
		Class<SOURCE> sourceClass = (Class<SOURCE>) sources.iterator().next().getClass();
		return MapperGetter.getInstance().getBySourceClassByDestinationClass(sourceClass, destinationClass).getDestinations(sources);			
	}	
	
	static <SOURCE, DESTINATION> Collection<DESTINATION> getDestinations(Collection<SOURCE> sources,Class<DESTINATION> destinationClass) {
		if(CollectionHelper.isEmpty(sources))
			return null;
		return getDestinations(sources, destinationClass, null);
	}
	
	static <SOURCE, DESTINATION> Collection<SOURCE> getSources(Collection<DESTINATION> destinations,Class<SOURCE> sourceClass,Properties properties) {
		if(CollectionHelper.isEmpty(destinations))
			return null;
		@SuppressWarnings("unchecked")
		Class<DESTINATION> destinationClass = (Class<DESTINATION>) destinations.iterator().next().getClass();
		return MapperGetter.getInstance().getBySourceClassByDestinationClass(sourceClass,destinationClass).getSources(destinations,properties);
	}
	
	static <SOURCE, DESTINATION> Collection<SOURCE> getSources(Collection<DESTINATION> destinations,Class<SOURCE> sourceClass) {
		if(CollectionHelper.isEmpty(destinations))
			return null;
		return getSources(destinations, sourceClass, null);
	}
	
	static <SOURCE, DESTINATION> DESTINATION getDestination(SOURCE source, Class<DESTINATION> destinationClass,Properties properties) {
		if(source == null)
			return null;		
		return MapperGetter.getInstance().getBySourceByDestinationClass(source,destinationClass).getDestination(source);	
	}
	
	static <SOURCE, DESTINATION> DESTINATION getDestination(SOURCE source, Class<DESTINATION> destinationClass) {
		if(source == null)
			return null;
		return getDestination(source, destinationClass, null);
	}
	
	static <SOURCE, DESTINATION> SOURCE getSource(DESTINATION destination, Class<SOURCE> sourceClass,Properties properties) {
		if(destination == null)
			return null;
		@SuppressWarnings("unchecked")
		Class<DESTINATION> destinationClass = (Class<DESTINATION>) destination.getClass();
		return MapperGetter.getInstance().getBySourceClassByDestinationClass(sourceClass,destinationClass).getSource(destination,properties);	
	}
	
	static <SOURCE, DESTINATION> SOURCE getSource(DESTINATION destination, Class<SOURCE> sourceClass) {
		if(destination == null)
			return null;
		return getSource(destination, sourceClass, null);
	}
	
}
