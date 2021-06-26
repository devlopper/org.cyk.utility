package org.cyk.utility.persistence.server.hibernate;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.instance.InstanceCopier;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.persistence.EntityManagerGetter;
import org.cyk.utility.persistence.query.Querier;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.audit.AuditReader;
import org.cyk.utility.persistence.server.hibernate.annotation.Hibernate;
import org.cyk.utility.persistence.server.hibernate.entity.AbstractAudit;
import org.cyk.utility.persistence.server.query.executor.DynamicManyExecutor;
import org.hibernate.envers.AuditReaderFactory;

@Hibernate
public class AuditReaderImpl extends AuditReader.AbstractImpl implements Serializable {

	@Override
	protected <T> Collection<T> __readByIdentifiers__(Class<T> klass, Arguments<T> arguments,Collection<Object> identifiers) {
		Collection<T> collection = null;
		org.hibernate.envers.AuditReader reader = AuditReaderFactory.get(EntityManagerGetter.getInstance().get());
		for(Object identifier : identifiers) {
			Collection<T> revisions = getRevisions(klass, arguments, reader, identifier);
			if(CollectionHelper.isEmpty(revisions))
				continue;
			if(collection == null)
				collection = new ArrayList<>();
			collection.addAll(revisions);		
		}
		return collection;
	}
	
	protected <T> Collection<T> getRevisions(Class<T> klass, Arguments<T> arguments,org.hibernate.envers.AuditReader reader,Object identifier) {
		//Get all revisions numbers by identifier
		List<Number> numbers = reader.getRevisions(klass, identifier);
		if(CollectionHelper.isEmpty(numbers))
			return null;
		Collection<T> collection = getInstanceByIdentifierByRevision(klass, arguments, reader, identifier, numbers);		
		return collection;
	}
	
	protected <T> Collection<T> getInstanceByIdentifierByRevision(Class<T> klass, Arguments<T> arguments,org.hibernate.envers.AuditReader reader,Object identifier
			,Collection<Number> numbers) {
		AbstractAuditsRecordsByRevisionsNumbersNativeReader<T> nativeReader = getAuditsRecordsByRevisionsNumbersNativeReader(klass, arguments, identifier, numbers);
		Collection<T> collection = null;
		if(nativeReader == null) {
			for(Number number : numbers) {
				//Get instance by identifier and revision number
				T instance = reader.find(klass, identifier, number);
				if(instance == null)
					continue;
				if(collection == null)
					collection = new ArrayList<>();
				collection.add(instance);
			}
		}else {
			collection = (Collection<T>) nativeReader.readByIdentifiersThenInstantiate(List.of(identifier.toString())
					, Map.of(AbstractAuditsRecordsByRevisionsNumbersNativeReader.PARAMETER_NAME_NUMBERS,numbers));
		}
		return collection;
	}
	
	protected <T> AbstractAuditsRecordsByRevisionsNumbersNativeReader<T> getAuditsRecordsByRevisionsNumbersNativeReader(Class<T> klass, Arguments<T> arguments,Object identifier,Collection<Number> numbers) {
		return null;
	}
	
	/**/
	
	@Override
	protected <T> Collection<T> __readByDates__(Class<T> klass, Arguments<T> arguments, LocalDateTime fromDate,LocalDateTime toDate) {
		@SuppressWarnings("unchecked")
		Class<T> auditClass = (Class<T>) getAuditClass(klass);
		if(auditClass == null)
			return null;
		QueryExecutorArguments queryExecutorArguments = arguments.getQueryExecutorArguments();
		if(queryExecutorArguments == null)
			queryExecutorArguments = new QueryExecutorArguments();
		queryExecutorArguments.addProcessableTransientFieldsNames(AbstractAudit.FIELDS___AUDITED__);
		if(fromDate != null)
			queryExecutorArguments.addFilterField(Querier.PARAMETER_NAME_FROM_DATE,fromDate);
		if(toDate != null)
			queryExecutorArguments.addFilterField(Querier.PARAMETER_NAME_TO_DATE,toDate);
		Collection<?> __collection__ = DynamicManyExecutor.getInstance().read(auditClass, queryExecutorArguments);
		if(CollectionHelper.isEmpty(__collection__))
			return null;
		return convert(klass, queryExecutorArguments, __collection__);
	}
	
	protected <T> Collection<T> convert(Class<T> klass,QueryExecutorArguments queryExecutorArguments,Collection<?> audits) {
		Collection<T> collection = new ArrayList<>();
		Collection<String> fieldsNames = getConvertableFieldsNames(klass, queryExecutorArguments, audits);
		if(CollectionHelper.isEmpty(fieldsNames))
			return null;
		for(Object audit : audits) {
			T entity = ClassHelper.instanciate(klass);
			copyAuditToEntity(klass, audit, entity,fieldsNames);			
			collection.add(entity);
		}
		return collection;
	}
	
	protected void copyAuditToEntity(Class<?> entityClass,Object audit,Object entity,Collection<String> fieldsNames) {
		InstanceCopier.getInstance().copy(audit, entity,fieldsNames);
	}
	
	private <T> Collection<String> getConvertableFieldsNames(Class<T> klass,QueryExecutorArguments queryExecutorArguments,Collection<?> __collection__) {
		Collection<String> fieldsNames = new HashSet<>();
		if(CollectionHelper.isNotEmpty(queryExecutorArguments.getProjections()))
			fieldsNames.addAll(queryExecutorArguments.getProjections().stream().map(p -> p.getFieldName()).collect(Collectors.toList()));
		if(CollectionHelper.isNotEmpty(queryExecutorArguments.getProcessableTransientFieldsNames()))
			fieldsNames.addAll(queryExecutorArguments.getProcessableTransientFieldsNames());
		fieldsNames.remove(AbstractAudit.FIELDS___AUDITED__);
		/*fieldsNames.addAll(List.of(AbstractAudit.FIELD___AUDIT_WHO__,AbstractAudit.FIELD___AUDIT_FUNCTIONALITY__,AbstractAudit.FIELD___AUDIT_WHAT__
				,AbstractAudit.FIELD___AUDIT_WHEN_AS_STRING__));*/
		return fieldsNames;
	}
}