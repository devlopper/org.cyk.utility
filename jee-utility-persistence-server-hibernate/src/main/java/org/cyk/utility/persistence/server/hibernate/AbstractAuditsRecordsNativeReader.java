package org.cyk.utility.persistence.server.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.persistence.Query;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.marker.AuditableWhoDoneWhatWhen;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.time.TimeHelper;
import org.cyk.utility.persistence.PersistenceHelper;
import org.cyk.utility.persistence.query.Querier;
import org.cyk.utility.persistence.server.query.ArraysReaderByIdentifiers;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

public abstract class AbstractAuditsRecordsNativeReader<ENTITY> extends ArraysReaderByIdentifiers.AbstractImpl.DefaultImpl<ENTITY> implements Serializable {

	@Override
	protected String getQueryValue() {
		QueryStringBuilder.Arguments arguments = new QueryStringBuilder.Arguments();
		setProjections(arguments);
		setTuple(arguments);
		setPredicate(arguments);
		setOrder(arguments);
		return QueryStringBuilder.getInstance().build(arguments);
	}
	
	protected void setProjections(QueryStringBuilder.Arguments arguments) {
		arguments.getProjection(Boolean.TRUE).addFromTuple("t","IDENTIFIANT",getAuditFunctionalityColumnName(),getAuditActionColumnName(),getAuditActorColumnName()
				,getAuditDateColumnName());
		Collection<String> projections = getProjections();
		if(CollectionHelper.isNotEmpty(projections)) {
			for(String projection : projections) {
				if(StringHelper.isBlank(projection))
					continue;
				arguments.getProjection(Boolean.TRUE).addFromTuple("t",projection);
			}
		}
	}
	
	protected Collection<String> getProjections() {
		return null;
	}
	
	protected void setTuple(QueryStringBuilder.Arguments arguments) {
		arguments.getTuple(Boolean.TRUE).add(getAuditTableName()+" t");
	}
	
	protected void setPredicate(QueryStringBuilder.Arguments arguments) {
		arguments.getPredicate(Boolean.TRUE).add("t.identifiant IN :"+Querier.PARAMETER_NAME_IDENTIFIERS+" AND t.rev IN :numbers");
	}
	
	protected void setOrder(QueryStringBuilder.Arguments arguments) {
		arguments.getOrder(Boolean.TRUE).desc("t", getAuditDateColumnName());
	}
	
	@Override
	protected void setQueryParameters(Query query, Collection<String> identifiers, Map<String, Object> parameters) {
		super.setQueryParameters(query, identifiers, parameters);
		query.setParameter(PARAMETER_NAME_NUMBERS, MapHelper.readByKey(parameters, PARAMETER_NAME_NUMBERS));
	}
	
	@Override
	protected void __set__(ENTITY entity, Object[] array) {
		int i = 0;
		FieldHelper.writeSystemIdentifier(entity, array[i++]);
		if(entity instanceof AuditableWhoDoneWhatWhen) {
			AuditableWhoDoneWhatWhen record = (AuditableWhoDoneWhatWhen) entity;
			record.set__auditFunctionality__((String) array[i++]);
			record.set__auditWhat__((String) array[i++]);
			record.set__auditWho__((String) array[i++]);
			Object dateValue = array[i++];
			if(dateValue instanceof java.util.Date)
				record.set__auditWhen__(TimeHelper.parseLocalDateTimeFromDate((java.util.Date) dateValue));
			else if(dateValue instanceof String)
				record.set__auditWhen__(TimeHelper.parseLocalDateTimeFromString((String) dateValue));
		}
		__set__(entity, array, i);
	}
	
	protected abstract void __set__(ENTITY entity, Object[] array,Integer index);
	
	@Override
	protected Boolean getIsNativeQuery() {
		return Boolean.TRUE;
	}

	/**/
	
	protected String getAuditTableName() {
		return PersistenceHelper.getTableName(getEntityClass())+"_AUD";
	}
	
	protected String getAuditFunctionalityColumnName() {
		return readAuditColumnName(getEntityClass(), "COLUMN_AUDIT_FUNCTIONALITY", "AUDIT_FONCTIONNALITE");
	}
	
	protected String getAuditActionColumnName() {
		return readAuditColumnName(getEntityClass(), "COLUMN_AUDIT_ACTION", "AUDIT_ACTION");
	}
	
	protected String getAuditActorColumnName() {
		return readAuditColumnName(getEntityClass(), "COLUMN_AUDIT_ACTOR", "AUDIT_ACTEUR");
	}
	
	protected String getAuditDateColumnName() {
		return readAuditColumnName(getEntityClass(), "COLUMN_AUDIT_DATE", "AUDIT_DATE");
	}
	
	/**/
	
	private static String readAuditColumnName(Class<?> entityClass,String fieldName,String defaultValue) {
		String name = (String) FieldHelper.readStatic(entityClass, fieldName);
		if(StringHelper.isNotBlank(name))
			return name;		
		return defaultValue;
	}
	
	public static final String PARAMETER_NAME_NUMBERS = "numbers";
}