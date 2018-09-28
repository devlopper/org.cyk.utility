package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response.Status;

import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.server.business.Business;

public class RepresentationFunctionCreatorImpl extends AbstractRepresentationFunctionCreatorImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __executeBusiness__() {
		if(getEntities()!=null)
			__inject__(Business.class).createMany((Collection<Object>) __inject__(InstanceHelper.class).buildMany(getPersistenceEntityClass(),getEntities()));
		else if(getEntity()!=null)
			__inject__(Business.class).create(__inject__(InstanceHelper.class).buildOne(getPersistenceEntityClass(),getEntity()));
		else {
			__injectThrowableHelper__().throwRuntimeException("No entity to "+getAction());
		}	
	}

	@Override
	protected Status __computeResponseStatus__() {
		return javax.ws.rs.core.Response.Status.CREATED;
	}
	
	@Override
	protected Object __computeResponseEntity__() {
		MessageDto message = new MessageDto().setValue(getPersistenceEntityClass().getSimpleName()+" a été créé avec succès.");
		return message;
	}
	
	@Override
	protected Object __computeResponseEntity__(Throwable throwable) {
		throwable = (Throwable) super.__computeResponseEntity__(throwable);
		MessageDto message = new MessageDto().setValue("Une erreur est survenue lors de la création de "+getPersistenceEntityClass()+". "+throwable.getMessage());
		return message;
	}
}
