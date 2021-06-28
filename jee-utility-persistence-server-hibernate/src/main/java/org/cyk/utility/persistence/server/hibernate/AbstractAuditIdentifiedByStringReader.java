package org.cyk.utility.persistence.server.hibernate;
import java.time.LocalDateTime;

import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.persistence.PersistenceHelper;
import org.cyk.utility.persistence.query.Querier;
import org.cyk.utility.persistence.server.hibernate.entity.AbstractAudit;
import org.cyk.utility.persistence.server.hibernate.entity.AbstractAuditIdentifiedByString;
import org.cyk.utility.persistence.server.query.ArraysReaderByIdentifiers;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public abstract class AbstractAuditIdentifiedByStringReader<ENTITY extends AbstractAuditIdentifiedByString> extends ArraysReaderByIdentifiers.AbstractImpl.DefaultImpl<ENTITY> {

	@Override
	protected String getQueryValue() {
		QueryStringBuilder.Arguments arguments = new QueryStringBuilder.Arguments();
		setProjections(arguments);
		setTuple(arguments);
		setPredicates(arguments);
		setOrder(arguments);
		return QueryStringBuilder.getInstance().build(arguments);
	}
	
	protected void setProjections(QueryStringBuilder.Arguments arguments) {
		arguments.getProjection(Boolean.TRUE).addFromTuple("t",AbstractAuditIdentifiedByString.FIELD_IDENTIFIER,AbstractAudit.FIELD___AUDIT_REVISION__
				,AbstractAudit.FIELD___AUDIT_WHEN__);
	}
	
	protected void setTuple(QueryStringBuilder.Arguments arguments) {
		arguments.getTuple(Boolean.TRUE).add(PersistenceHelper.getEntityName(getEntityClass())+" t");
	}
	
	protected void setPredicates(QueryStringBuilder.Arguments arguments) {
		arguments.getPredicate(Boolean.TRUE).add("t.identifier IN :"+Querier.PARAMETER_NAME_IDENTIFIERS);
	}
	
	protected void setOrder(QueryStringBuilder.Arguments arguments) {
		
	}
	
	@Override
	protected void __set__(ENTITY entity, Object[] array) {
		int i = 2;
		entity.set__auditWhenAsString__(TimeHelper.formatLocalDateTimeTillMillisecond((LocalDateTime) array[i]));
		__set__(entity, array, i);
	}
	
	protected void __set__(ENTITY entity, Object[] array,Integer index) {
		
	}
	
	@Override
	protected Boolean isEntityArray(ENTITY entity, Object[] array) {
		return Boolean.TRUE.equals(super.isEntityArray(entity, array)) && entity.get__auditRevision__().equals(array[1]);
	}
}