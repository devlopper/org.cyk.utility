package org.cyk.utility.log.log4j2;

import java.io.Serializable;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.cyk.utility.log.AbstractLogEventPropertyAccessorImpl;
import org.cyk.utility.log.LogLevel;

public class LogEventPropertyAccessorLog4jImpl extends AbstractLogEventPropertyAccessorImpl implements LogEventPropertyAccessorLog4j, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public String getMessage(Object bean) {
		if(bean == null)
			return null;
		return ((LogEvent)bean).getMessage().getFormattedMessage();
	}

	@Override
	public LogLevel getLevel(Object bean) {
		if(bean == null)
			return null;
		Level level = ((LogEvent)bean).getLevel();
		return LogLevel.valueOf(level.name());
	}

	@Override
	public Object getMarker(Object bean) {
		if(bean == null)
			return null;
		Marker marker = ((LogEvent)bean).getMarker();
		return marker == null ? null : marker.getName();
	}

}
