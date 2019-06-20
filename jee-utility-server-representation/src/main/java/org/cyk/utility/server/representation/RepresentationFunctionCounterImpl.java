package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.properties.Properties;

@Dependent
public class RepresentationFunctionCounterImpl extends AbstractRepresentationFunctionCounterImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long count;
	
	@Override
	protected void __executeBusiness__() {
		Properties properties = new Properties();
		properties.copyFrom(getProperties(),Properties.QUERY_FILTERS);
		count = __injectBusiness__().count(getPersistenceEntityClass(), properties);
	}
	
	@Override
	protected Object __computeResponseEntity__() {
		return count;
	}

}
