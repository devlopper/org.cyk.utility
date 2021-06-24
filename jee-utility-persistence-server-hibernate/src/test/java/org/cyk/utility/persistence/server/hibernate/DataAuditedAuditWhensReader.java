package org.cyk.utility.persistence.server.hibernate;
import java.time.LocalDateTime;

import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.persistence.query.Querier;
import org.cyk.utility.persistence.server.query.ArraysReaderByIdentifiers;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public class DataAuditedAuditWhensReader extends ArraysReaderByIdentifiers.AbstractImpl.DefaultImpl<DataAuditedAudit> {

	@Override
	protected String getQueryValue() {
		QueryStringBuilder.Arguments arguments = new QueryStringBuilder.Arguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",DataAuditedAudit.FIELD_IDENTIFIER,DataAuditedAudit.FIELD___WHEN__);
		arguments.getTuple(Boolean.TRUE).add("DataAuditedAudit t");
		arguments.getPredicate(Boolean.TRUE).add("t.identifier IN :"+Querier.PARAMETER_NAME_IDENTIFIERS);
		return QueryStringBuilder.getInstance().build(arguments);
	}
	
	@Override
	protected void __set__(DataAuditedAudit dataAuditedAudit, Object[] array) {
		int i = 1;
		dataAuditedAudit.set__whenAsString__(TimeHelper.formatLocalDateTimeTillSecond((LocalDateTime) array[i++]));		
	}
	
	@Override
	protected Boolean isEntityHasOnlyArray(DataAuditedAudit entity) {
		return Boolean.FALSE;
	}
	
	@Override
	protected Class<DataAuditedAudit> getEntityClass() {
		return DataAuditedAudit.class;
	}
}