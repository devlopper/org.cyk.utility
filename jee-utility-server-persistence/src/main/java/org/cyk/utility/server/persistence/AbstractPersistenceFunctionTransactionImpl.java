package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractPersistenceFunctionTransactionImpl extends AbstractPersistenceFunctionImpl implements PersistenceFunctionTransaction, Serializable {
	private static final long serialVersionUID = 1L;

	private Collection<Object> entitiesIdentifiers;
	
	@Override
	protected Boolean __executeGetIsExecutable__(Boolean value) {
		/*Object queryIdentifier = getQueryIdentifier();
		return queryIdentifier == null ? getEntity() != null : Boolean.TRUE;
		*/
		
		//return getQueryIdentifier()!=null || getEntities()!=null || getEntity()!=null;
		return Boolean.TRUE;
	}
	
	@Override
	protected void __executeQuery__(SystemAction action) {
		Collection<Object> entities = __getEntities__();
		Integer batchSize = __getBatchSize__();
		__execute__(entities,batchSize);
		if(entitiesIdentifiers == null)
			entitiesIdentifiers = new ArrayList<Object>();
		for(Object index : entities) {
			entitiesIdentifiers.add(__injectFieldHelper__().getFieldValueSystemIdentifier(index));
		}
	}
	
	protected abstract void __execute__(Collection<Object> entities,Integer batchSize);
	
	protected Collection<Object> __getEntities__() {
		Collection<Object> entities = new ArrayList<>();
		if(getEntities()!=null)
			entities.addAll(getEntities());
		if(getEntity()!=null)
			entities.add(getEntity());
		addLogMessageBuilderParameter("count", entities.size());
		return entities;
	}
	
	protected Integer __getBatchSize__() {
		Integer size = __injectNumberHelper__().getInteger(getProperty(Properties.BATCH_SIZE), null);
		if(size == null)
			size = 30;
		return size;
	}
	
	@Override
	protected Map<String, String> __getProduceFunctionOutputs__() {
		return __injectMapHelper__().instanciateKeyAsStringValueAsString(
				"entities.identifiers",entitiesIdentifiers
				);
	}
	
	@Override
	public PersistenceFunctionTransaction setEntity(Object entity) {
		return (PersistenceFunctionTransaction) super.setEntity(entity);
	}
	
	@Override
	public PersistenceFunctionTransaction setEntities(Collection<?> entities) {
		return (PersistenceFunctionTransaction) super.setEntities(entities);
	}
	
	@Override
	public PersistenceFunctionTransaction setAction(SystemAction action) {
		return (PersistenceFunctionTransaction) super.setAction(action);
	}
	
	@Override
	public PersistenceFunctionTransaction setEntityClass(Class<?> aClass) {
		return (PersistenceFunctionTransaction) super.setEntityClass(aClass);
	}
	
	@Override
	public PersistenceFunctionTransaction setEntityIdentifier(Object identifier) {
		return (PersistenceFunctionTransaction) super.setEntityIdentifier(identifier);
	}

	@Override
	public PersistenceFunctionTransaction setEntityIdentifierValueUsageType(ValueUsageType valueUsageType) {
		return (PersistenceFunctionTransaction) super.setEntityIdentifierValueUsageType(valueUsageType);
	}
	
	@Override
	public PersistenceFunctionTransaction setQueryIdentifier(Object identifier) {
		return (PersistenceFunctionTransaction) super.setQueryIdentifier(identifier);
	}
	
	@Override
	public PersistenceFunctionTransaction setQueryParameter(String name, Object value) {
		return (PersistenceFunctionTransaction) super.setQueryParameter(name, value);
	}
	
	@Override
	public PersistenceFunctionTransaction setQueryParameters(Properties parameters) {
		return (PersistenceFunctionTransaction) super.setQueryParameters(parameters);
	}
}
