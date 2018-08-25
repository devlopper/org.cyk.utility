package org.cyk.utility.log;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public abstract class AbstractLogEventEntityBuilderImpl<BEAN> extends AbstractFunctionWithPropertiesAsInputImpl<LogEventEntity> implements LogEventEntityBuilder, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected LogEventEntity __execute__() {
		@SuppressWarnings("unchecked")
		BEAN bean = (BEAN) getEvent();
		LogEventEntity entity = null;
		if(bean == null){
			//TODO log warning
		}else {
			entity = __inject__(LogEventEntity.class);
			__execute__(bean,entity);
		}
		return entity;
	}
	
	protected void __execute__(BEAN bean, LogEventEntity entity) {
		LogEventPropertyAccessor logEventPropertyAccessor = __inject__(LogEventPropertyAccessor.class);
		entity.setLevel(logEventPropertyAccessor.getLevel(bean));
		entity.setMessage(logEventPropertyAccessor.getMessage(bean));
		entity.setMarker(logEventPropertyAccessor.getMarker(bean));
	}
	
	@Override
	public LogEventEntityBuilder execute(Object event) {
		return (LogEventEntityBuilder) setEvent(event).execute();
	}
	
	@Override
	public Object getEvent() {
		return getProperties().getEvent();
	}
	
	@Override
	public LogEventEntityBuilder setEvent(Object event) {
		getProperties().setEvent(event);
		return this;
	}
}
