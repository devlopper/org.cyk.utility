package org.cyk.utility.__kernel__.configuration;

import java.util.concurrent.TimeUnit;

import io.smallrye.config.WithDefault;

public interface Duration {
	@WithDefault("5")
	Long value();
	
	@WithDefault("MINUTES")
	TimeUnit unit();
}