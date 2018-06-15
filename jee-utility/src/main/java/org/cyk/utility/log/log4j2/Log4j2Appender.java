package org.cyk.utility.log.log4j2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.random.RandomHelper;

@Plugin(name = "Appender", category = "Core", elementType = "apender", printObject = true)
public class Log4j2Appender extends AbstractAppender implements LogEventRepositoryLog4j, Serializable {
    private static final long serialVersionUID = 1L;
    
	private List<LogEvent> logEvents;

	public Log4j2Appender() {
		super(Log4j2Appender.class.getSimpleName()+DependencyInjection.inject(RandomHelper.class).getAlphanumeric(5), null, PatternLayout.createDefaultLayout());
	}
	
    @Override
    public void append(LogEvent event) {
    	if(event!=null && logEvents == null)
    		logEvents = new ArrayList<>();
        logEvents.add(event);
    }
    
	@Override
	public Collection<LogEvent> readAll() {
		return logEvents;
	}
	
	@Override
	public Long countAll() {
		return DependencyInjection.inject(NumberHelper.class).getLong(DependencyInjection.inject(CollectionHelper.class).getSize(logEvents));
	}

	@Override
	public LogEvent getAt(Integer index) {
		return DependencyInjection.inject(CollectionHelper.class).getElementAt(logEvents, index);
	}

	@Override
	public LogEvent getFirst() {
		return DependencyInjection.inject(CollectionHelper.class).getFirst(logEvents);
	}

	@Override
	public LogEvent getLast() {
		return DependencyInjection.inject(CollectionHelper.class).getLast(logEvents);
	}

}