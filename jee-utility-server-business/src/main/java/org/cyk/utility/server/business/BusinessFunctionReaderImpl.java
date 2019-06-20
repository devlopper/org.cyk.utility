package org.cyk.utility.server.business;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.system.action.SystemAction;

@Dependent
public class BusinessFunctionReaderImpl extends AbstractBusinessFunctionReaderImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __execute__(SystemAction action) {
		Object object = __inject__(Persistence.class).readOne(getEntityClass(),getEntityIdentifier(),new Properties().setValueUsageType(getEntityIdentifierValueUsageType()));
		getProperties().setEntity(object);
	}

}
