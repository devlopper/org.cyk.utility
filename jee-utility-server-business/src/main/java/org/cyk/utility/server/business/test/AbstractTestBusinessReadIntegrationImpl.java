package org.cyk.utility.server.business.test;

import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.server.business.Business;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractTestBusinessReadIntegrationImpl extends AbstractTestBusinessFunctionIntegrationImpl implements TestBusinessReadIntegration {
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
		Boolean mustUnexist =  Boolean.TRUE.equals(__inject__(CollectionHelper.class).contains(getUnexistingObjectIdentifiers(), object));
		ValueUsageType valueUsageType = getIdentifierValueUsageType();
		Object one = __inject__(Business.class).findOne(getObjectClass(),object,new Properties().setValueUsageType(valueUsageType));
		assertionHelper.assertEquals(getObjectClass()+" with "+valueUsageType+" identifier <"+object+">"+(mustUnexist ? "" : " not")+" found", !mustUnexist,one!=null);
		if(mustUnexist) {
			
		}else {
			assertionHelper.assertEquals(valueUsageType+" identitier do not match", object,__inject__(FieldValueGetter.class).execute(one, FieldName.IDENTIFIER, valueUsageType).getOutput());
		}
		
	}

}
