package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.system.action.SystemAction;

public class BusinessFunctionCreatorImpl extends AbstractBusinessFunctionCreatorImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __execute__(SystemAction action) {
		Collection<Object> entities = new ArrayList<>();
		if(getEntities()!=null)
			entities.addAll(getEntities());
		if(getEntity()!=null)
			entities.add(getEntity());
		__inject__(Persistence.class).createMany(entities);
	}

}
