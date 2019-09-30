package org.cyk.utility.client.controller.test;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.client.controller.Controller;

public class TestControllerReadImpl extends AbstractTestControllerFunctionIntegrationImpl implements TestControllerRead {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __perform__(Object object) throws Exception {
		Boolean mustUnexist =  Boolean.TRUE.equals(CollectionHelper.contains(getUnexistingObjectIdentifiers(), object));
		ValueUsageType valueUsageType = getIdentifierValueUsageType();
		Object one = __inject__(Controller.class).readByIdentifier(getObjectClass(),object,valueUsageType);
		assertionHelper.assertEquals(getObjectClass()+" with "+valueUsageType+" identifier <"+object+">"+(mustUnexist ? "" : " not")+" found", !mustUnexist,one!=null);
		if(mustUnexist) {
			
		}else {
			assertionHelper.assertEquals(valueUsageType+" identitier do not match", object,FieldHelper.read(one, FieldName.IDENTIFIER, valueUsageType));
		}
	}
	
}
