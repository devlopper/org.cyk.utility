package org.cyk.utility.persistence.server.hibernate;

import java.io.Serializable;

import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.query.Querier;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.hibernate.annotation.Hibernate;
import org.cyk.utility.persistence.server.hibernate.entity.AbstractAudit;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder.Arguments;
import org.cyk.utility.persistence.server.query.string.RuntimeQueryStringBuilder;
import org.cyk.utility.persistence.server.query.string.WhereStringBuilder.Predicate;

@Hibernate
public class RuntimeQueryStringBuilderImpl extends RuntimeQueryStringBuilder.AbstractImpl implements Serializable {

	@Override
	protected void populatePredicate(QueryExecutorArguments arguments, Arguments builderArguments, Predicate predicate,Filter filter) {
		super.populatePredicate(arguments, builderArguments, predicate, filter);
		if(Boolean.TRUE.equals(ClassHelper.isInstanceOf(arguments.getQuery().getTupleClass(),AbstractAudit.class)) 
				&& arguments.getQuery().isIdentifierEqualsDynamic(arguments.getQuery().getTupleClass())) {
			
			if(arguments.getFilterFieldValue(Querier.PARAMETER_NAME_FROM_DATE) != null) {
				predicate.add("t."+AbstractAudit.FIELD___AUDIT_WHEN__+" >= :"+Querier.PARAMETER_NAME_FROM_DATE);
				filter.addField(Querier.PARAMETER_NAME_FROM_DATE, arguments.getFilterFieldValue(Querier.PARAMETER_NAME_FROM_DATE));
			}
			
			if(arguments.getFilterFieldValue(Querier.PARAMETER_NAME_TO_DATE) != null) {
				predicate.add("t."+AbstractAudit.FIELD___AUDIT_WHEN__+" <= :"+Querier.PARAMETER_NAME_TO_DATE);
				filter.addField(Querier.PARAMETER_NAME_TO_DATE, arguments.getFilterFieldValue(Querier.PARAMETER_NAME_TO_DATE));
			}
		}
	}
	
	/*@Override
	protected void setOrder(QueryExecutorArguments arguments, Arguments builderArguments) {
		super.setOrder(arguments, builderArguments);
		if(arguments.getQuery().getIdentifier().equals(DataAuditedQuerier.QUERY_IDENTIFIER_READ_DYNAMIC)) {
			builderArguments.getOrder(Boolean.TRUE).asc("t", DataAudited.FIELD_CODE);
		}
	}*/	
}