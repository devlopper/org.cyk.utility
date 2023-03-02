package org.cyk.utility.__kernel__.configuration.processing;

import java.util.List;

import io.smallrye.config.WithDefault;

public interface Publishing {
	
	Processing processing();
	
	@WithDefault("MESSAGING,API,STORED_PROCEDURE")
	List<Mode> modes();
	
	public static enum Mode {

		MESSAGING
		,API
		,STORED_PROCEDURE
		
	}
}