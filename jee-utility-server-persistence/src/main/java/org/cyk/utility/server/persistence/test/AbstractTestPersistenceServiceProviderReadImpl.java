package org.cyk.utility.server.persistence.test;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.Persistence;

public abstract class AbstractTestPersistenceServiceProviderReadImpl extends AbstractTestPersistenceServiceProviderFunctionImpl implements TestPersistenceServiceProviderRead {
	private static final long serialVersionUID = 1L;

	@Override
	protected Collection<Object> __getExecutionObjects__() throws Exception {
		return getObjectIdentifiers();
	}
	
	@Override
	protected void __perform__(Object object) throws Exception {
		__inject__(Persistence.class).readOne(getObjectClass(),object,new Properties().setValueUsageType(getIdentifierValueUsageType()));
	}
	
}
