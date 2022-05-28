package org.cyk.utility.report.jasper.server;
import org.cyk.utility.__kernel__.configuration.Credentials;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "cyk.jasper.server")
public interface Configuration {

	@WithDefault("pdf")
	String defaultFileType();
	
	@WithDefault("false")
	Boolean defaultIsContentInline();
	
	Credentials credentials();
	
}