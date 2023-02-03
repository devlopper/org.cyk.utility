package org.cyk.utility.__kernel__.configuration;

import io.smallrye.config.WithDefault;

public interface Batch {
	@WithDefault("2000")
	Integer size();
	
	Pause pauseBefore();
	
	Pause pauseAfter();	
}