package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response.Status;

import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.server.business.Business;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractRepresentationFunctionCreatorImpl extends AbstractRepresentationFunctionTransactionImpl implements RepresentationFunctionCreator, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		setAction(__inject__(SystemActionCreate.class));
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void __executeBusiness__() {
		if(getEntities()!=null)
			__inject__(Business.class).createMany((Collection<Object>) __inject__(InstanceHelper.class).buildMany(getPersistenceEntityClass(),getEntities()));
		else if(getEntity()!=null) {
			Object representation = getEntity();
			Object persistence = __inject__(InstanceHelper.class).buildOne(getPersistenceEntityClass(),representation);
			__inject__(Business.class).create(persistence);
			Object identifierSystem = __injectFieldHelper__().getFieldValueSystemIdentifier(persistence);
			__responseBuilder__.header("entity-identifier-system", identifierSystem);
			__injectFieldHelper__().setFieldValueSystemIdentifier(representation,identifierSystem);
			
			if(__inject__(FieldHelper.class).getField(persistence.getClass(), FieldName.IDENTIFIER, ValueUsageType.BUSINESS)!=null && 
					__inject__(FieldHelper.class).getField(representation.getClass(), FieldName.IDENTIFIER, ValueUsageType.BUSINESS)!=null) {
				Object identifierBusiness = __injectFieldHelper__().getFieldValueBusinessIdentifier(persistence);
				__responseBuilder__.header("entity-identifier-business", identifierBusiness);
				__injectFieldHelper__().setFieldValueBusinessIdentifier(representation,identifierBusiness);
			}
			
		}else {
			__injectThrowableHelper__().throwRuntimeException("No entity to "+getAction());
		}	
	}

	@Override
	protected Status __computeResponseStatus__() {
		if(__throwable__==null)
			return javax.ws.rs.core.Response.Status.CREATED;
		return super.__computeResponseStatus__();
	}
}
