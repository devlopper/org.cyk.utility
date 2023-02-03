package org.cyk.utility.__kernel__.configuration;


import io.smallrye.config.WithDefault;

public interface Application {
	@WithDefault("SYSTEME")
	String identifier();
}