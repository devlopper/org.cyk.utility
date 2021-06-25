package org.cyk.utility.persistence.server.hibernate;
import java.time.LocalDateTime;

import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.persistence.PersistenceHelper;
import org.cyk.utility.persistence.query.Querier;
import org.cyk.utility.persistence.server.hibernate.entity.AbstractAudit;
import org.cyk.utility.persistence.server.hibernate.entity.AbstractAuditIdentifiedByString;
import org.cyk.utility.persistence.server.query.ArraysReaderByIdentifiers;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public abstract class AbstractAuditIdentifiedByStringWhensReader<ENTITY extends AbstractAuditIdentifiedByString> extends ArraysReaderByIdentifiers.AbstractImpl.DefaultImpl<ENTITY> {

	@Override
	protected String getQueryValue() {
		QueryStringBuilder.Arguments arguments = new QueryStringBuilder.Arguments();
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",AbstractAuditIdentifiedByString.FIELD_IDENTIFIER,AbstractAudit.FIELD___AUDIT_WHEN__);
		arguments.getTuple(Boolean.TRUE).add(PersistenceHelper.getEntityName(getEntityClass())+" t");
		arguments.getPredicate(Boolean.TRUE).add("t.identifier IN :"+Querier.PARAMETER_NAME_IDENTIFIERS);
		return QueryStringBuilder.getInstance().build(arguments);
	}
	
	@Override
	protected void __set__(ENTITY entity, Object[] array) {
		int i = 1;
		entity.set__auditWhenAsString__(TimeHelper.formatLocalDateTimeTillSecond((LocalDateTime) array[i++]));		
	}
	
	@Override
	protected Boolean isEntityHasOnlyArray(ENTITY entity) {
		return Boolean.FALSE;
	}
}