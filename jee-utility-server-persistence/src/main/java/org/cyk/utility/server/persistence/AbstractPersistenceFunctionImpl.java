package org.cyk.utility.server.persistence;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.server.persistence.query.PersistenceQueryRepository;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.system.AbstractSystemFunctionServerImpl;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerPersistence;
import org.cyk.utility.throwable.ThrowableHelper;

public abstract class AbstractPersistenceFunctionImpl extends AbstractSystemFunctionServerImpl implements PersistenceFunction, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected final void __execute__(SystemAction action) {
		String queryIdentifier = (String) getQueryIdentifier();
		String queryValue = getQueryValue();
		if(__inject__(StringHelper.class).isBlank(queryIdentifier) && __inject__(StringHelper.class).isBlank(queryValue)){
			__executeQuery__(action);		
		}else {
			PersistenceQuery persistenceQuery = __inject__(PersistenceQueryRepository.class).getBySystemIdentifier(queryIdentifier);
			if(persistenceQuery == null){
				if(__inject__(StringHelper.class).isBlank(queryValue))
					__injectThrowableHelper__().throwRuntimeException("persistence query with identifier "+queryIdentifier+" not found.");	
				persistenceQuery = new PersistenceQuery().setValue(queryValue).setResultClass(getQueryResultClass());	
			}
			__executeQuery__(action, persistenceQuery);
		}
	}
	
	protected void __executeQuery__(SystemAction action){
		__inject__(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented();
	}
	
	protected void __executeQuery__(SystemAction action,PersistenceQuery persistenceQuery){
		__inject__(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented();
	}
	
	@Override
	public PersistenceFunction setAction(SystemAction action) {
		return (PersistenceFunction) super.setAction(action);
	}
	
	@Override
	public PersistenceFunction setEntityClass(Class<?> aClass) {
		return (PersistenceFunction) super.setEntityClass(aClass);
	}
	
	@Override
	protected SystemLayer getSystemLayer() {
		return __inject__(SystemLayerPersistence.class);
	}
	
	@Override
	public PersistenceFunction setQueryIdentifier(Object identifier) {
		getProperties().setFromPath(new Object[]{Properties.QUERY,Properties.IDENTIFIER}, identifier);
		if(getEntityClass() == null){
			PersistenceQuery persistenceQuery = __inject__(PersistenceQueryRepository.class).getBySystemIdentifier(identifier,Boolean.TRUE);
			if(persistenceQuery!=null){
				setEntityClass(persistenceQuery.getResultClass());
				setQueryResultClass(persistenceQuery.getResultClass());
			}
		}
		return this;
	}
	
	@Override
	public Object getQueryIdentifier() {
		return getProperties().getFromPath(Properties.QUERY,Properties.IDENTIFIER);
	}
	
	@Override
	public PersistenceFunction setQueryValue(String value) {
		getProperties().setFromPath(new Object[]{Properties.QUERY,Properties.VALUE}, value);
		/*if(getEntityClass() == null){
			PersistenceQuery persistenceQuery = __inject__(PersistenceQueryRepository.class).getBySystemIdentifier(identifier,Boolean.TRUE);
			if(persistenceQuery!=null)
				setEntityClass(persistenceQuery.getResultClass());
		}*/
		return this;
	}
	
	@Override
	public String getQueryValue() {
		return (String) getProperties().getFromPath(Properties.QUERY,Properties.VALUE);
	}

	@Override
	public PersistenceFunction setQueryParameters(Properties parameters) {
		getProperties().setParameters(parameters);
		return this;
	}
	
	@Override
	public Properties getQueryParameters() {
		return (Properties) getProperties().getParameters();
	}
	
	@Override
	public PersistenceFunction setQueryParameter(String name, Object value) {
		Properties parameters = getQueryParameters();
		if(parameters == null)
			setQueryParameters(parameters = new Properties());
		parameters.set(name, value);
		return this;
	}
	
	@Override
	public Class<?> getQueryResultClass() {
		return (Class<?>) getProperties().getFromPath(Properties.QUERY,Properties.RESULT,Properties.CLASS);
	}
	
	@Override
	public PersistenceFunction setQueryResultClass(Class<?> aClass) {
		getProperties().setFromPath(new Object[]{Properties.QUERY,Properties.RESULT,Properties.CLASS}, aClass);
		return this;
	}
}
