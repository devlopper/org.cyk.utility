package org.cyk.utility.__kernel__.configuration;


import io.smallrye.config.WithConverter;
import io.smallrye.config.WithDefault;

public interface Cron {
	
	@WithDefault("0 */30 * ? * *")
	@WithConverter(Configuration.StringConverter.class)
	String expression();
	
	Logging logging();
}