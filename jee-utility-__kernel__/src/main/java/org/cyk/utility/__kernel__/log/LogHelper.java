package org.cyk.utility.__kernel__.log;

import java.util.logging.Level;

public interface LogHelper {

	static void log(String message,Level level,Class<?> klass) {
		if(message == null || message.isBlank())
			return;
		if(level == null)
			level = Level.INFO;
		if(klass == null)
			klass = LogHelper.class;
		java.util.logging.Logger.getLogger(klass.getName()).log(level, message);
	}
	
	static void log(String message,Level level) {
		log(message, level, LogHelper.class);
	}
	
	static void log(String message) {
		log(message, Level.INFO, LogHelper.class);
	}
	
	static void logFinest(String message,Class<?> klass) {
		log(message, Level.FINEST,klass);
	}
	
	static void logFiner(String message,Class<?> klass) {
		log(message, Level.FINER,klass);
	}
	
	static void logFine(String message,Class<?> klass) {
		log(message, Level.FINE,klass);
	}

	static void logInfo(String message,Class<?> klass) {
		log(message, Level.INFO,klass);
	}
	
	static void logWarning(String message,Class<?> klass) {
		log(message, Level.WARNING,klass);
	}
	
	static void logSevere(String message,Class<?> klass) {
		log(message, Level.SEVERE,klass);
	}
	
	static void logAll(String message,Class<?> klass) {
		log(message, Level.ALL,klass);
	}
	
	static void logConfig(String message,Class<?> klass) {
		log(message, Level.CONFIG,klass);
	}
	
	static void logOff(String message,Class<?> klass) {
		log(message, Level.OFF,klass);
	}
	
	static void log(Throwable throwable,Class<?> klass) {
		logSevere(throwable.toString(),klass);
	}

	
}
