package org.cyk.utility.server.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.business.Business;
import org.cyk.utility.system.action.SystemAction;

public class RepresentationFunctionReaderImpl extends AbstractRepresentationFunctionReaderImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __execute__(SystemAction action) {
		Object object = __inject__(Business.class).findOne(getEntityClass(),getEntityIdentifier(),new Properties().setValueUsageType(getEntityIdentifierValueUsageType()));
		getProperties().setEntity(object);
	}

}
