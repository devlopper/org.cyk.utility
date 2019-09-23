package org.cyk.utility.log.log4j2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.instance.InstanceRepository;
import org.cyk.utility.log.LogEventEntity;
import org.cyk.utility.log.LogEventEntityBuilder;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.repository.Repository;

@Plugin(name = "CustomAppender", category = "Core", elementType = "apender", printObject = true) @Alternative
@ApplicationScoped
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
		super(LogEventEntityRepositoryLog4j2Impl.class.getSimpleName()+DependencyInjection.inject(RandomHelper.class).getAlphanumeric(5), null, PatternLayout.createDefaultLayout(),false,null);
	}
	
    @Override
    public void append(LogEvent event) {
    	if(event!=null && instances == null)
    		instances = new ArrayList<>();
        instances.add(DependencyInjection.inject(LogEventEntityBuilder.class).setEvent(event).execute().getOutput());
    }
    
    @Override
	public InstanceRepository<LogEventEntity> add(Collection<LogEventEntity> instances) {
    	//append(event);
		return null;
	}
    
    @Override
    public InstanceRepository<LogEventEntity> add(LogEventEntity...events) {
    	//append(event);
    	return this;
    }
    
	@Override
	public Collection<LogEventEntity> readAll() {
		return instances;
	}
	
	@Override
	public Long countAll() {
		return NumberHelper.getLong(CollectionHelper.getSize(instances));
	}

	@Override
	public LogEventEntity getAt(Integer index) {
		return CollectionHelper.getElementAt(instances, index);
	}

	@Override
	public LogEventEntity getFirst() {
		return CollectionHelper.getFirst(instances);
	}

	@Override
	public LogEventEntity getLast() {
		return CollectionHelper.getLast(instances);
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
		//no operation
		return null;
	}

	@Override
	public LogEventEntity getBySystemIdentifier(Object identifier, Boolean logIfResultIsNull) {
		//no operation
		return null;
	}

	@Override
	public InstanceRepository<LogEventEntity> setInstanceClass(Class<LogEventEntity> aClass) {
		//no operation
		return this;
	}

	@Override
	public Class<LogEventEntity> getInstanceClass() {
		return LogEventEntity.class;
	}

	@Override
	public Properties getProperties() {
		return null;
	}

	@Override
	public Objectable setProperties(Properties properties) {
		return null;
	}

	@Override
	public Object getParent() {
		return null;
	}

	@Override
	public Objectable setParent(Object parent, Boolean executeAddChild) {
		return null;
	}

	@Override
	public Objectable setParent(Object parent) {
		return null;
	}

	@Override
	public Object getIdentifier() {
		return null;
	}

	@Override
	public Objectable setIdentifier(Object identifier) {
		return null;
	}

	@Override
	public Objectable setChildren(Collection<Object> children) {
		return null;
	}

	@Override
	public Collection<Object> getChildren() {
		return null;
	}

	@Override
	public Objectable addChildren(Collection<Object> children) {
		return null;
	}

	@Override
	public Objectable addChild(Object... child) {
		return null;
	}

	@Override
	public <I> I getParentAs(Class<I> aClass) {
		return null;
	}
	
	@Override
	public Object getChildAt(Integer index) {
		return null;
	}

	@Override
	public Object getProperty(Object key) {
		return null;
	}

	@Override
	public Objectable setProperty(Object key, Object value) {
		return null;
	}

	@Override
	public Object getOrderNumber() {
		return null;
	}

	@Override
	public Objectable setOrderNumber(Object orderNumber) {
		return null;
	}

	@Override
	public String getRepresentationAsString() {
		return null;
	}

	@Override
	public Object getLastChild() {
		return null;
	}

	@Override
	public Objectable copyProperty(Object key, Properties properties) {
		return null;
	}

	@Override
	public Collection<Object> getPropertiesWhereKeyIsInstanceOf(Class<?> clazz) {
		return null;
	}

	@Override
	public Objectable setPropertyIfNull(Object key, Object value) {
		return null;
	}

	@Override
	public Boolean isHasChildren() {
		return null;
	}

	@Override
	public Boolean isHasChildrenInstanceOf(Class<?> aClass) {
		return null;
	}

	@Override
	public <T> Collection<T> getChildrenInstanceOf(Class<T> aClass) {
		return null;
	}

}