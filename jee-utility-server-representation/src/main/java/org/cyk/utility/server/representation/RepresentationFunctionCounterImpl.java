package org.cyk.utility.server.representation;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.query.filter.Filter;
import org.cyk.utility.server.persistence.query.filter.FilterDto;

@Dependent
public class RepresentationFunctionCounterImpl extends AbstractRepresentationFunctionCounterImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long count;
	
	@Override
	protected void __executeBusiness__() {
		Properties properties = new Properties();
		FilterDto filterDto = (FilterDto) getProperty(Properties.QUERY_FILTERS);
		if(filterDto != null) {
			Filter filter = MappingHelper.getDestination(filterDto, Filter.class).normalize(__persistenceEntityClass__);
			properties.setQueryFilters(filter);
		}
		count = __injectBusiness__().count(__persistenceEntityClass__, properties);
	}
	
	@Override
	protected Object __computeResponseEntity__() {
		return count;
	}

}
