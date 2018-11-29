package org.cyk.utility.client.controller;

import org.cyk.utility.__kernel__.properties.Properties;

public interface ControllerEntityPersistedInCollection<ENTITY> extends ControllerEntity<ENTITY> {

	@Override ControllerEntityPersistedInCollection<ENTITY> update(ENTITY object, Properties properties);
	
	@Override ControllerEntityPersistedInCollection<ENTITY> delete(ENTITY object, Properties properties);
	
}
