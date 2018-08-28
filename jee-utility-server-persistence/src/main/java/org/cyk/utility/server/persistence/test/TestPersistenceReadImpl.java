package org.cyk.utility.server.persistence.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.value.ValueUsageType;

public class TestPersistenceReadImpl extends AbstractTestPersistenceServiceProviderReadImpl implements TestPersistenceRead {
	private static final long serialVersionUID = 1L;

	@Override
	protected void ____execute____() throws Exception{
		Class<?> aClass = getObjectClass();
		Collection<Object> objectIdentifiers = new ArrayList<Object>();
		__inject__(CollectionHelper.class).add(objectIdentifiers, Boolean.TRUE, getObjectIdentifiers());
		
		Integer executionCount = getExecutionCount();
		if(executionCount == null)
			executionCount = 1;
		for(Integer index = 0 ; index < executionCount ; index++){
			for(Object indexObject : objectIdentifiers){
				getUserTransaction().begin();
				__inject__(Persistence.class).readOne(aClass,indexObject,new Properties().setValueUsageType(getIdentifierValueUsageType()));
				getUserTransaction().commit();
				assertThat(__inject__(FieldValueGetter.class).execute(indexObject,FieldName.IDENTIFIER,ValueUsageType.SYSTEM).getOutput()).isNotNull();
				assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Create "+indexObject.getClass().getSimpleName())
					.assertContainsLastLogEventMessage("code="+__inject__(FieldValueGetter.class).execute(indexObject,FieldName.IDENTIFIER,ValueUsageType.BUSINESS).getOutput());
			}
		}
	}

}
