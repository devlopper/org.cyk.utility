package org.cyk.utility.mapping;

import java.util.Collection;

public interface Mapper<SOURCE,DESTINATION> {

	DESTINATION mapSourceToDestination(SOURCE source);
	Collection<DESTINATION> mapSourcesToDestinations(Collection<SOURCE> sources);
	
	SOURCE mapDestinationToSource(DESTINATION destination);
	Collection<SOURCE> mapDestinationsToSources(Collection<DESTINATION> destinations);
}