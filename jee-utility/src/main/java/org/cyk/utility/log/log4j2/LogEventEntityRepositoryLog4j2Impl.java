package org.cyk.utility.log.log4j2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Alternative;
import javax.inject.Singleton;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.instance.InstanceRepository;
import org.cyk.utility.log.LogEventEntity;
import org.cyk.utility.log.LogEventEntityBuilder;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.repository.Repository;

@Plugin(name = "CustomAppender", category = "Core", elementType = "apender", printObject = true) @Alternative
@Singleton
public class LogEventEntityRepositoryLog4j2Impl extends AbstractAppender implements LogEventEntityRepositoryLog4j2, Serializable {
    private static final long serialVersionUID = 1L;
    
	private List<LogEventEntity> instances;

	@PostConstruct
	public void __listenPostConstruct__(){
		org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) org.apache.logging.log4j.LogManager.getRootLogger();
		((org.apache.logging.log4j.core.Appender)this).start();
		logger.getContext().getConfiguration().addLoggerAppender(logger, (org.apache.logging.log4j.core.Appender) this);	
	}
	
	public LogEventEntityRepositoryLog4j2Impl() {
		super(LogEventEntityRepositoryLog4j2Impl.class.getSimpleName()+DependencyInjection.inject(RandomHelper.class).getAlphanumeric(5), null, PatternLayout.createDefaultLayout());
	}
	
    @Override
    public void append(LogEvent event) {
    	if(event!=null && instances == null)
    		instances = new ArrayList<>();
        instances.add(DependencyInjection.inject(LogEventEntityBuilder.class).setEvent(event).execute().getOutput());
    }
    
    @Override
    public InstanceRepository<LogEventEntity> add(LogEventEntity event) {
    	//append(event);
    	return this;
    }
    
	@Override
	public Collection<LogEventEntity> readAll() {
		return instances;
	}
	
	@Override
	public Long countAll() {
		return DependencyInjection.inject(NumberHelper.class).getLong(DependencyInjection.inject(CollectionHelper.class).getSize(instances));
	}

	@Override
	public LogEventEntity getAt(Integer index) {
		return DependencyInjection.inject(CollectionHelper.class).getElementAt(instances, index);
	}

	@Override
	public LogEventEntity getFirst() {
		return DependencyInjection.inject(CollectionHelper.class).getFirst(instances);
	}

	@Override
	public LogEventEntity getLast() {
		return DependencyInjection.inject(CollectionHelper.class).getLast(instances);
	}

	@Override
	public Repository clear() {
		if(instances!=null)
			instances.clear();
		return null;
	}

	@Override
	public LogLevel getLastLevel() {
		return getLast().getLevel();
	}

	@Override
	public String getLastMessage() {
		return getLast().getMessage();
	}

	@Override
	public LogEventEntity getBySystemIdentifier(Object identifier) {
		return null;
	}
}