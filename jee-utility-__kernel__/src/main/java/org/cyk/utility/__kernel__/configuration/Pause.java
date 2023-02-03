package org.cyk.utility.__kernel__.configuration;

import io.smallrye.config.WithDefault;

public interface Pause {
	@WithDefault("false")
	Boolean enabled();
	
	Duration duration();
}