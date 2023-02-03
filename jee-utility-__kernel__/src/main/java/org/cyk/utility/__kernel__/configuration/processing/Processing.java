package org.cyk.utility.__kernel__.configuration.processing;

import org.cyk.utility.__kernel__.configuration.Batch;
import org.cyk.utility.__kernel__.configuration.Cron;
import org.cyk.utility.__kernel__.configuration.Executor;
import org.cyk.utility.__kernel__.configuration.Logging;

import io.smallrye.config.WithDefault;

public interface Processing {

	Cron cron();
	
	@WithDefault("true")
	Boolean sequential();
	
	Executor executor();
	
	Batch batch();
	
	Logging logging();
}