package org.cyk.utility.mapping;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.helper.AbstractHelper;

@ApplicationScoped
public class MappingHelperImpl extends AbstractHelper implements MappingHelper,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public <SOURCE, DESTINATION> Collection<DESTINATION> getDestinations(Collection<SOURCE> sources,Class<DESTINATION> destinationClass,Properties properties) {
		Collection<DESTINATION> destinations = null;
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(sources))) {
			Class<SOURCE> sourceClass = (Class<SOURCE>) sources.iterator().next().getClass();
			destinations = __inject__(MapperSourceDestinationGetter.class).setSourceClass(sourceClass).setDestinationClass(destinationClass)
					.execute().getOutput().getDestinations(sources);	
		}
		return destinations;
	}	
	
	@Override
	public <SOURCE, DESTINATION> Collection<DESTINATION> getDestinations(Collection<SOURCE> sources,Class<DESTINATION> destinationClass) {
		return getDestinations(sources, destinationClass, null);
	}
	
	@Override
	public <SOURCE, DESTINATION> Collection<SOURCE> getSources(Collection<DESTINATION> destinations,Class<SOURCE> sourceClass,Properties properties) {
		Collection<SOURCE> sources = null;
		if(Boolean.TRUE.equals(CollectionHelper.isNotEmpty(destinations))) {
			Class<DESTINATION> destinationClass = (Class<DESTINATION>) destinations.iterator().next().getClass();
			sources = __inject__(MapperSourceDestinationGetter.class).setSourceClass(sourceClass).setDestinationClass(destinationClass)
					.execute().getOutput().getSources(destinations,properties);
		}
		return sources;
	}
	
	@Override
	public <SOURCE, DESTINATION> Collection<SOURCE> getSources(Collection<DESTINATION> destinations,Class<SOURCE> sourceClass) {
		return getSources(destinations, sourceClass, null);
	}
	
	@Override
	public <SOURCE, DESTINATION> DESTINATION getDestination(SOURCE source, Class<DESTINATION> destinationClass,Properties properties) {
		DESTINATION destination = null;
		if(source != null) {
			Class<SOURCE> sourceClass = (Class<SOURCE>) source.getClass();
			destination = (DESTINATION) __inject__(MapperSourceDestinationGetter.class).setSourceClass(sourceClass).setDestinationClass(destinationClass)
					.execute().getOutput().getDestination(source);	
		}
		return destination;
	}
	
	@Override
	public <SOURCE, DESTINATION> DESTINATION getDestination(SOURCE source, Class<DESTINATION> destinationClass) {
		return getDestination(source, destinationClass, null);
	}
	
	@Override
	public <SOURCE, DESTINATION> SOURCE getSource(DESTINATION destination, Class<SOURCE> sourceClass,Properties properties) {
		SOURCE source = null;
		if(destination != null) {
			Class<DESTINATION> destinationClass = (Class<DESTINATION>) destination.getClass();
			source = (SOURCE) __inject__(MapperSourceDestinationGetter.class).setSourceClass(sourceClass).setDestinationClass(destinationClass)
					.execute().getOutput().getSource(destination,properties);	
		}
		return source;
	}
	
	@Override
	public <SOURCE, DESTINATION> SOURCE getSource(DESTINATION destination, Class<SOURCE> sourceClass) {
		return getSource(destination, sourceClass, null);
	}
}
