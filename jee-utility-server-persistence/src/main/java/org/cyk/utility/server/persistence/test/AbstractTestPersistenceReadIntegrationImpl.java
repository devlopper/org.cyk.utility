package org.cyk.utility.server.persistence.test;

import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.server.persistence.Persistence;

public abstract class AbstractTestPersistenceReadIntegrationImpl extends AbstractTestPersistenceFunctionIntegrationImpl implements TestPersistenceReadIntegration {
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
		Boolean mustUnexist =  Boolean.TRUE.equals(CollectionHelper.contains(getUnexistingObjectIdentifiers(), object));
		ValueUsageType valueUsageType = getIdentifierValueUsageType();
		Object one = __inject__(Persistence.class).readByIdentifier(getObjectClass(),object,new Properties().setValueUsageType(valueUsageType));
		assertionHelper.assertEquals(getObjectClass()+" with "+valueUsageType+" identifier <"+object+">"+(mustUnexist ? "" : " not")+" found", !mustUnexist,one!=null);
		if(mustUnexist) {
			
		}else {
			assertionHelper.assertEquals(valueUsageType+" identitier do not match", object,FieldHelper.read(one, FieldName.IDENTIFIER, valueUsageType));
		}
		
	}

}
