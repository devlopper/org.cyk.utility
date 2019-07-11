package org.cyk.utility.server.business;

import java.io.Serializable;

import org.cyk.utility.log.LogLevel;
import org.cyk.utility.system.AbstractSystemFunctionServerImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerBusiness;

public abstract class AbstractBusinessFunctionImpl extends AbstractSystemFunctionServerImpl implements BusinessFunction, Serializable {
	private static final long serialVersionUID = 1L;
	
	public static LogLevel LOG_LEVEL = LogLevel.TRACE;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setLogLevel(LOG_LEVEL);
	}
	
	@Override
	public BusinessFunction setEntityIdentifier(Object identifier) {
		return (BusinessFunction) super.setEntityIdentifier(identifier);
	}
	
	@Override
	public BusinessFunction setAction(SystemAction action) {
		return (BusinessFunction) super.setAction(action);
	}
	
	@Override
	public BusinessFunction setEntityClass(Class<?> aClass) {
		return (BusinessFunction) super.setEntityClass(aClass);
	}
	
	@Override
	protected SystemLayer getSystemLayer() {
		return __inject__(SystemLayerBusiness.class);
	}
	
}
