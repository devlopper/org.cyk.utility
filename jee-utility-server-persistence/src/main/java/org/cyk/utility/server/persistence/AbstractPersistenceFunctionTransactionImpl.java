package org.cyk.utility.server.persistence;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractPersistenceFunctionTransactionImpl extends AbstractPersistenceFunctionImpl implements PersistenceFunctionTransaction, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Boolean __executeGetIsExecutable__(Boolean value) {
		return Boolean.TRUE;
	}
	
	@Override
	protected void __executeQuery__(SystemAction action) {
		if(Boolean.TRUE.equals(__injectCollectionHelper__().isNotEmpty(__entities__))) {
			__executeQueryWithEntities__(__entities__);
			//TODO integration with distributed stream
			/*if(__en == null)
				entitiesIdentifiers = new ArrayList<Object>();
			for(Object index : entities) {
				entitiesIdentifiers.add(__injectFieldHelper__().getFieldValueSystemIdentifier(index));
			}	*/
		}
	}
	
	protected void __executeQueryWithEntities__(Collection<Object> entities) {
		Integer count = 0;
		for(Object index : entities) {
			__executeWithEntity__(index);
			if(__batchSize__ != null) {
				count++;
				if(count % __batchSize__ == 0) {
					__flush__();
					__clear__();
				}
			}
		}
	}
	
	protected abstract void __executeWithEntity__(Object object);
	protected abstract void __flush__();
	protected abstract void __clear__();
	
	/*@Override
	protected Map<String, String> __getProduceFunctionOutputs__() {
		return __injectMapHelper__().instanciateKeyAsStringValueAsString(
				"entities.identifiers",entitiesIdentifiers
				);
	}*/
	
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
