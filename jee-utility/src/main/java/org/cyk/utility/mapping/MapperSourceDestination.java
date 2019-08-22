package org.cyk.utility.mapping;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;

public interface MapperSourceDestination<SOURCE,DESTINATION> extends Mapper {

	DESTINATION getDestination(SOURCE source);
	Collection<DESTINATION> getDestinations(Collection<SOURCE> sources);
	
	SOURCE getSource(DESTINATION destination);
	SOURCE getSource(DESTINATION destination,Properties properties);
	Collection<SOURCE> getSources(Collection<DESTINATION> destinations);
	Collection<SOURCE> getSources(Collection<DESTINATION> destinations,Properties properties);
	
}
