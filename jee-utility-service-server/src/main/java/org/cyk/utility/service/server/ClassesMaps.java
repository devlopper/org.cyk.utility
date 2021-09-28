package org.cyk.utility.service.server;

import java.util.HashMap;
import java.util.Map;

public interface ClassesMaps {

	Map<Class<?>,Class<?>> SERVICE_ENTITY_TO_IMPL = new HashMap<>();
	Map<Class<?>,Class<?>> SERVICE_ENTITY_TO_PERSISTENCE_ENTITY = new HashMap<>();
	
	static void clear() {
		SERVICE_ENTITY_TO_IMPL.clear();
		SERVICE_ENTITY_TO_PERSISTENCE_ENTITY.clear();
	}
}