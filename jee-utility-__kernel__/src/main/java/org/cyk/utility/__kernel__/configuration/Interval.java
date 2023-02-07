package org.cyk.utility.__kernel__.configuration;


import io.smallrye.config.WithDefault;

public interface Interval {
	@WithDefault("0")
	Long lower();
	
	@WithDefault("0")
	Long upper();
}