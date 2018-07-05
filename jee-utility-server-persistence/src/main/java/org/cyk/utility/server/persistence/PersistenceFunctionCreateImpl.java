package org.cyk.utility.server.persistence;

import java.io.Serializable;

import org.cyk.utility.architecture.system.SystemAction;
import org.cyk.utility.field.FieldGetName;
import org.cyk.utility.field.FieldGetValue;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.log.LogLevel;
import org.cyk.utility.value.ValueUsageType;

public class PersistenceFunctionCreateImpl extends AbstractPersistenceFunctionTransactionImpl implements PersistenceFunctionCreate, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(SystemAction action,Object entity) {
		getEntityManager().persist(entity);
		
		String systemIdentifierFieldName = __inject__(FieldGetName.class).execute(entity.getClass(), FieldName.IDENTIFIER,ValueUsageType.SYSTEM).getOutput();
		String businessIdentifierFieldName = __inject__(FieldGetName.class).execute(entity.getClass(), FieldName.IDENTIFIER,ValueUsageType.BUSINESS).getOutput();
		
		injectLog(action,entity).setLevel(LogLevel.INFO).getMessageBuilder(Boolean.TRUE)
			.addParameter(systemIdentifierFieldName, __inject__(FieldGetValue.class).setObject(entity).setField(systemIdentifierFieldName).execute().getOutput())
			.addParameter(businessIdentifierFieldName, __inject__(FieldGetValue.class).setObject(entity).setField(businessIdentifierFieldName).execute().getOutput())
				.getParent().execute();
		
	}

}
