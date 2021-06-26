package org.cyk.utility.persistence.server.hibernate;

import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.persistence.server.hibernate.annotation.Hibernate;
import org.cyk.utility.persistence.server.hibernate.entity.AbstractAudit;
import org.cyk.utility.persistence.server.hibernate.entity.AbstractAuditIdentifiedByString;

@Hibernate
public class TransientFieldsProcessorImpl extends org.cyk.utility.persistence.server.TransientFieldsProcessorImpl {

	@SuppressWarnings("unchecked")
	@Override
	protected void __process__(Class<?> klass,Collection<?> objects,Filter filter, Collection<String> fieldsNames) {
		if(Boolean.TRUE.equals(ClassHelper.isInstanceOf(klass, AbstractAuditIdentifiedByString.class)))
			processAuditsRecords((Class<AbstractAuditIdentifiedByString>) klass, objects, filter, fieldsNames);
		else
			super.__process__(klass,objects,filter, fieldsNames);
	}

	public void processAuditsRecords(Class<AbstractAuditIdentifiedByString> klass,Collection<?> auditsRecords,Filter filter, Collection<String> fieldsNames) {
		for(String fieldName : fieldsNames) {
			if(AbstractAudit.FIELDS___AUDITED__.equals(fieldName)) {
				AbstractAuditIdentifiedByStringReader<AbstractAuditIdentifiedByString> reader = getAuditsRecordsReader(klass);
				if(reader == null)
					throw new RuntimeException("No audits records reader defined for "+klass);
				reader.readThenSet(CollectionHelper.cast(AbstractAuditIdentifiedByString.class, auditsRecords), null);
			}
		}
	}
	
	protected <T extends AbstractAuditIdentifiedByString> AbstractAuditIdentifiedByStringReader<T> getAuditsRecordsReader(Class<T> klass) {
		return null;
	}
}