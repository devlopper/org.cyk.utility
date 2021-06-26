package org.cyk.utility.persistence.server.hibernate;

import org.cyk.utility.__kernel__.annotation.Test;
import org.cyk.utility.persistence.server.hibernate.entity.AbstractAuditIdentifiedByString;

@Test
public class TransientFieldsProcessorImplTest extends org.cyk.utility.persistence.server.hibernate.TransientFieldsProcessorImpl {

	@SuppressWarnings("unchecked")
	@Override
	protected <T extends AbstractAuditIdentifiedByString> AbstractAuditIdentifiedByStringReader<T> getAuditsRecordsReader(Class<T> klass) {
		if(DataAuditedAudit.class.equals(klass))
			return (AbstractAuditIdentifiedByStringReader<T>) new DataAuditedAuditReader();
		return super.getAuditsRecordsReader(klass);
	}
}