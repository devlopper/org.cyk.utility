package org.cyk.utility.server.representation;

import java.io.Serializable;

import org.cyk.utility.server.business.Business;
import org.cyk.utility.system.action.SystemAction;

public class RepresentationFunctionCreatorImpl extends AbstractRepresentationFunctionCreatorImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __execute__(SystemAction action) {
		__inject__(Business.class).create(getEntity());
	}

}