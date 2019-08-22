package org.cyk.utility.mapping;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.helper.Helper;

public interface MappingHelper extends Helper {

	<SOURCE,DESTINATION> Collection<DESTINATION> getDestinations(Collection<SOURCE> sources,Class<DESTINATION> destinationClass,Properties properties);
	<SOURCE,DESTINATION> Collection<DESTINATION> getDestinations(Collection<SOURCE> sources,Class<DESTINATION> destinationClass);
	<SOURCE,DESTINATION> DESTINATION getDestination(SOURCE source,Class<DESTINATION> destinationClass,Properties properties);
	<SOURCE,DESTINATION> DESTINATION getDestination(SOURCE source,Class<DESTINATION> destinationClass);
	
	<SOURCE,DESTINATION> Collection<SOURCE> getSources(Collection<DESTINATION> destinations,Class<SOURCE> sourceClass,Properties properties);
	<SOURCE,DESTINATION> Collection<SOURCE> getSources(Collection<DESTINATION> destinations,Class<SOURCE> sourceClass);
	<SOURCE,DESTINATION> SOURCE getSource(DESTINATION destination,Class<SOURCE> sourceClass,Properties properties);
	<SOURCE,DESTINATION> SOURCE getSource(DESTINATION destination,Class<SOURCE> sourceClass);
}
