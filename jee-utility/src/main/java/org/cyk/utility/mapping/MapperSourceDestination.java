package org.cyk.utility.mapping;

import java.util.Collection;

public interface MapperSourceDestination<SOURCE,DESTINATION> extends Mapper {

	DESTINATION getDestination(SOURCE source);
	Collection<DESTINATION> getDestinations(Collection<SOURCE> sources);
	
	SOURCE getSource(DESTINATION destination);
	Collection<SOURCE> getSources(Collection<DESTINATION> destinations);
	
}
