package org.cyk.utility.server.business;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.system.action.SystemAction;

public class BusinessFunctionCreatorImpl extends AbstractBusinessFunctionCreatorImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __execute__(SystemAction action) {
		if(getEntities()!=null)
			__inject__(Persistence.class).createMany((Collection<Object>) getEntities());
		else if(getEntity()!=null)
			__inject__(Persistence.class).create(getEntity());
		else
			//TODO log warning
			;
	}

}
