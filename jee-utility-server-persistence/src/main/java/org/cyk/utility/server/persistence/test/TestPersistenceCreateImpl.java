package org.cyk.utility.server.persistence.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.value.ValueUsageType;

public class TestPersistenceCreateImpl extends AbstractTestPersistenceServiceProviderCreateImpl implements TestPersistenceCreate {
	private static final long serialVersionUID = 1L;

	@Override
	protected void ____execute____() throws Exception{
		Collection<Object> objects = new ArrayList<Object>();
		__inject__(CollectionHelper.class).add(objects, Boolean.TRUE, getObjects());
		if(getObject()!=null)
			objects.add(getObject());
		
		Integer executionCount = getExecutionCount();
		if(executionCount == null)
			executionCount = 1;
		for(Integer index = 0 ; index < executionCount ; index++){
			for(Object indexObject : objects){
				getUserTransaction().begin();
				__inject__(Persistence.class).create(indexObject);
				getUserTransaction().commit();
				assertThat(__inject__(FieldValueGetter.class).execute(indexObject,FieldName.IDENTIFIER,ValueUsageType.SYSTEM).getOutput()).isNotNull();
				assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Create "+indexObject.getClass().getSimpleName())
					.assertContainsLastLogEventMessage("code="+__inject__(FieldValueGetter.class).execute(indexObject,FieldName.IDENTIFIER,ValueUsageType.BUSINESS).getOutput());
			}
		}
	}

}
