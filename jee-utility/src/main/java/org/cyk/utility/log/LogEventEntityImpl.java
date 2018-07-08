package org.cyk.utility.log;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class LogEventEntityImpl extends AbstractObject implements LogEventEntity,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public LogLevel getLevel() {
		return (LogLevel) getProperties().getLevel();
	}

	@Override
	public LogEventEntity setLevel(LogLevel logLevel) {
		getProperties().setLevel(logLevel);
		return this;
	}

	@Override
	public String getMessage() {
		return (String) getProperties().getMessage();
	}

	@Override
	public LogEventEntity setMessage(String message) {
		getProperties().setMessage(message);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<String> getMarkers() {
		return (Collection<String>) getProperties().getMarker();
	}

	@Override
	public LogEventEntity setMarkers(Collection<String> markers) {
		getProperties().setMarkers(markers);
		return this;
	}

	@Override
	public Object getMarker() {
		return getProperties().getMarker();
	}
	
	@Override
	public LogEventEntity setMarker(Object marker) {
		getProperties().setMarker(marker);
		return this;
	}
}
