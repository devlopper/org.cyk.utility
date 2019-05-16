package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response.Status;

import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.server.business.Business;
import org.cyk.utility.system.action.SystemActionCreate;

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
			__injectFieldHelper__().setFieldValueSystemIdentifier(representation, __injectFieldHelper__().getFieldValueSystemIdentifier(persistence));
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
