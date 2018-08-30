package org.cyk.utility.server.persistence.test;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractTestPersistenceServiceProviderReadImpl extends AbstractTestPersistenceServiceProviderFunctionImpl implements TestPersistenceServiceProviderRead {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setIdentifierValueUsageType(ValueUsageType.BUSINESS);
	}
	
	@Override
	protected Collection<Object> __getExecutionObjects__() throws Exception {
		return getObjectIdentifiers();
	}
	
	@Override
	protected void __perform__(Object object) throws Exception {
		ValueUsageType valueUsageType = getIdentifierValueUsageType();
		Object one = __inject__(Persistence.class).readOne(getObjectClass(),object,new Properties().setValueUsageType(valueUsageType));
		assertionHelper.assertNotNull(getObjectClass()+" with "+valueUsageType+" identifier <"+object+"> not found", one);
		assertionHelper.assertEquals(valueUsageType+" identitier do not match", object,__inject__(FieldValueGetter.class).execute(one, FieldName.IDENTIFIER, valueUsageType).getOutput());
	}

}