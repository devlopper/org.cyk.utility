package org.cyk.utility.__kernel__.configuration.processing;

import org.cyk.utility.__kernel__.configuration.Batch;
import org.cyk.utility.__kernel__.configuration.Cron;
import org.cyk.utility.__kernel__.configuration.Executor;
import org.cyk.utility.__kernel__.configuration.Logging;
import org.cyk.utility.__kernel__.configuration.PersistenceOperation;

import io.smallrye.config.WithDefault;

public interface Processing {

	@WithDefault("true")
	Boolean enabled();
	
	Cron cron();
	
	@WithDefault("true")
	Boolean sequential();
	
	Executor executor();
	
	Batch batch();
	
	Persistence persistence();
	
	Logging logging();
	
	/* Persistence */
	
	public interface Persistence {
		
		PersistenceOperation read();
		
	}
}