package org.cyk.utility.server.representation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.instance.InstanceHelper;
import org.cyk.utility.server.business.Business;
import org.cyk.utility.system.action.SystemAction;

public class RepresentationFunctionCreatorImpl extends AbstractRepresentationFunctionCreatorImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __execute__(SystemAction action) {
		Collection<Object> entities = new ArrayList<>();
		if(getEntities()!=null)
			entities.addAll(getEntities());
		if(getEntity()!=null)
			entities.add(getEntity());
		entities = (Collection<Object>) __inject__(InstanceHelper.class).buildMany(getPersistenceEntityClass(), entities);
		__inject__(Business.class).createMany(entities);
		setResponse(Response.status(Response.Status.CREATED).build());
	}

}
