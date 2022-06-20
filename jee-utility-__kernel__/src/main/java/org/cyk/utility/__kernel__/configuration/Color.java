package org.cyk.utility.__kernel__.configuration;

import org.cyk.utility.__kernel__.configuration.Configuration.StringConverter;

import io.smallrye.config.WithConverter;

public interface Color {
	
	@WithConverter(StringConverter.class)
	String hexadecimal();

}