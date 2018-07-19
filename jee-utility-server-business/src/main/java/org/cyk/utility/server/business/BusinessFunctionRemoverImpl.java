package org.cyk.utility.server.business;

import java.io.Serializable;

import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.system.action.SystemAction;

public class BusinessFunctionRemoverImpl extends AbstractBusinessFunctionRemoverImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __execute__(SystemAction action) {
		__inject__(Persistence.class).delete(getEntity());
	}

}
