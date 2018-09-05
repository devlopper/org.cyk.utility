package org.cyk.utility.log.jul;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.inject.Singleton;

import org.cyk.utility.log.AbstractLogEventPropertyAccessorImpl;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.string.StringConstant;

@Singleton
public class LogEventPropertyAccessorJulImpl extends AbstractLogEventPropertyAccessorImpl implements LogEventPropertyAccessorJul, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage(Object bean) {
		if(bean == null)
			return null;
		if(((LogRecord)bean).getMessage() == null)
			return StringConstant.EMPTY;
		return MessageFormat.format(((LogRecord)bean).getMessage(), ((LogRecord)bean).getParameters());
	}

	@Override
	public LogLevel getLevel(Object bean) {
		if(bean == null)
			return null;
		Level level = ((LogRecord)bean).getLevel();
		//TODO adapt to best practices about logging
		if(Level.WARNING.equals(level))
			return LogLevel.WARN;
		if(Level.SEVERE.equals(level))
			return LogLevel.ERROR;
		if(Level.FINE.equals(level))
			return LogLevel.DEBUG;
		if(Level.FINER.equals(level))
			return LogLevel.DEBUG;
		if(Level.FINEST.equals(level))
			return LogLevel.TRACE;
		return LogLevel.valueOf(level.getName());
	}

	@Override
	public Object getMarker(Object bean) {
		if(bean == null)
			return null;
		//TODO how to make it support marker by our own
		return null;
	}

}
