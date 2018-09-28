package org.cyk.utility.server.representation;

import java.io.Serializable;

public class RepresentationFunctionCounterImpl extends AbstractRepresentationFunctionCounterImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long count;
	
	@Override
	protected void __executeBusiness__() {
		count = __injectBusiness__().count(getPersistenceEntityClass()/*, properties*/);
	}
	
	@Override
	protected Object __computeResponseEntity__() {
		return count;
	}

}
