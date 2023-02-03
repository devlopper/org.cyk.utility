package org.cyk.utility.__kernel__.configuration;


import io.smallrye.config.WithDefault;

public interface Logging {
	@WithDefault("false")
	Boolean enabled();
	
	@WithDefault("INFO")
	String level();
}