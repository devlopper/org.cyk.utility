package org.cyk.utility.server.business;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.system.action.SystemAction;

@Dependent
public class BusinessFunctionModifierImpl extends AbstractBusinessFunctionModifierImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __execute__(SystemAction action) {
		__inject__(Persistence.class).update(getEntity());
	}

}
