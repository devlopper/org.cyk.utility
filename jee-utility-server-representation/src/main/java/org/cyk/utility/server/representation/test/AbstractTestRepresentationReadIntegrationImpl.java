package org.cyk.utility.server.representation.test;

import java.util.Collection;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.RepresentationLayer;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractTestRepresentationReadIntegrationImpl extends AbstractTestRepresentationFunctionIntegrationImpl implements TestRepresentationReadIntegration {
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
	protected void ____perform____(Object object) throws Exception {
		Boolean mustUnexist =  Boolean.TRUE.equals(__inject__(CollectionHelper.class).contains(getUnexistingObjectIdentifiers(), object));
		ValueUsageType valueUsageType = getIdentifierValueUsageType();
		@SuppressWarnings("rawtypes")
		RepresentationEntity representation = __inject__(RepresentationLayer.class).injectInterfaceClassFromEntityClass(getObjectClass());
		__response__ = representation.getOne(object.toString(), valueUsageType.name());
		
		Object one = __response__.getEntity();
		
		assertionHelper.assertEquals(getObjectClass()+" with "+valueUsageType+" identifier <"+object+">"+(mustUnexist ? "" : " not")+" found", !mustUnexist,one!=null);
		if(mustUnexist) {
			
		}else {
			assertionHelper.assertEquals(valueUsageType+" identitier do not match", object,__inject__(FieldValueGetter.class).execute(one, FieldName.IDENTIFIER, valueUsageType).getOutput());
		}
		
	}

}
