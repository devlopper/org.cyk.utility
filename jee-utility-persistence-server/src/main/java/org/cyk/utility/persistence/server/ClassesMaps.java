package org.cyk.utility.persistence.server;

import java.util.HashMap;
import java.util.Map;

public interface ClassesMaps {

	Map<Class<?>,Class<?>> PERSISTENCE_ENTITY_TO_IMPL = new HashMap<>();

	static void clear() {
		PERSISTENCE_ENTITY_TO_IMPL.clear();
	}
}