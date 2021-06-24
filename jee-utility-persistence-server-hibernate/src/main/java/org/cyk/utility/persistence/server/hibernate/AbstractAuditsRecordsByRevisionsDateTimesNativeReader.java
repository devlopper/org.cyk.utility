package org.cyk.utility.persistence.server.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.persistence.Query;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public abstract class AbstractAuditsRecordsByRevisionsDateTimesNativeReader<ENTITY> extends AbstractAuditsRecordsNativeReader<ENTITY> implements Serializable {

	protected void setPredicate(QueryStringBuilder.Arguments arguments) {
		//arguments.getPredicate(Boolean.TRUE).add("t."+getAuditIdentifierColumnName()+" IN :"+Querier.PARAMETER_NAME_IDENTIFIERS+" AND t.rev IN :"+PARAMETER_NAME_NUMBERS);
	}
	
	@Override
	protected void setQueryParameters(Query query, Collection<String> identifiers, Map<String, Object> parameters) {
		super.setQueryParameters(query, identifiers, parameters);
		query.setParameter(PARAMETER_NAME_FROM_DATE_TIME, MapHelper.readByKey(parameters, PARAMETER_NAME_FROM_DATE_TIME));
		query.setParameter(PARAMETER_NAME_TO_DATE_TIME, MapHelper.readByKey(parameters, PARAMETER_NAME_TO_DATE_TIME));
	}
	
	public static final String PARAMETER_NAME_FROM_DATE_TIME = "fromDateTime";
	public static final String PARAMETER_NAME_TO_DATE_TIME = "toDateTime";
}