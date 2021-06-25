package org.cyk.utility.persistence.server.hibernate;

import org.cyk.utility.__kernel__.annotation.Test;
import org.cyk.utility.persistence.server.hibernate.entity.AbstractAuditIdentifiedByString;

@Test
public class TransientFieldsProcessorImplTest extends org.cyk.utility.persistence.server.hibernate.TransientFieldsProcessorImpl {

	@SuppressWarnings("unchecked")
	@Override
	protected <T extends AbstractAuditIdentifiedByString> AbstractAuditIdentifiedByStringWhensReader<T> getAuditsRecordsWhensReader(Class<T> klass) {
		if(DataAuditedAudit.class.equals(klass))
			return (AbstractAuditIdentifiedByStringWhensReader<T>) new DataAuditedAuditWhensReader();
		return super.getAuditsRecordsWhensReader(klass);
	}
}