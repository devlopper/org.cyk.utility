package org.cyk.utility.server.persistence;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.architecture.system.SystemAction;
import org.cyk.utility.field.FieldGetName;
import org.cyk.utility.field.FieldGetValue;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.value.ValueUsageType;

public class PersistenceFunctionReadImpl extends AbstractPersistenceFunctionImpl implements PersistenceFunctionRead, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(SystemAction action) {
		Class<?> aClass = getEntityClass();
		Object entityIdentifier = getEntityIdentifier();
		Object entity = getEntityManager().find(aClass,entityIdentifier);
		getProperties().setEntity(entity);
		
		if(entity == null){
			//TODO log warning
		}else{
			String systemIdentifierFieldName = __inject__(FieldGetName.class).execute(entity.getClass(), FieldName.IDENTIFIER,ValueUsageType.SYSTEM).getOutput();
			String businessIdentifierFieldName = __inject__(FieldGetName.class).execute(entity.getClass(), FieldName.IDENTIFIER,ValueUsageType.BUSINESS).getOutput();
			
			injectLog(action,entity).setLevel(LogLevel.INFO).getMessageBuilder(Boolean.TRUE)
				.addParameter(systemIdentifierFieldName, __inject__(FieldGetValue.class).setObject(entity).setField(systemIdentifierFieldName).execute().getOutput())
				.addParameter(businessIdentifierFieldName, __inject__(FieldGetValue.class).setObject(entity).setField(businessIdentifierFieldName).execute().getOutput())
					.getParent().execute();	
		}
	}

	@Override
	public PersistenceFunctionRead setEntityClass(Class<?> aClass) {
		getProperties().setFromPath(new Object[]{Properties.ENTITY,Properties.CLASS}, aClass);
		return this;
	}

	@Override
	public Class<?> getEntityClass() {
		return (Class<?>) getProperties().getFromPath(Properties.ENTITY,Properties.CLASS);
	}

	@Override
	public PersistenceFunctionRead setEntityIdentifier(Object identifier) {
		getProperties().setFromPath(new Object[]{Properties.ENTITY,Properties.IDENTIFIER}, identifier);
		return this;
	}

	@Override
	public Object getEntityIdentifier() {
		return getProperties().getFromPath(Properties.ENTITY,Properties.IDENTIFIER);
	}

	@Override
	public PersistenceFunctionRead setAction(SystemAction action) {
		return (PersistenceFunctionRead) super.setAction(action);
	}
}
