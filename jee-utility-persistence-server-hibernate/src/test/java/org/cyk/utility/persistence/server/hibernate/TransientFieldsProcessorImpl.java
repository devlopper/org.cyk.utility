package org.cyk.utility.persistence.server.hibernate;

import java.util.Collection;

import org.cyk.utility.__kernel__.annotation.Test;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.persistence.query.Filter;

@Test
public class TransientFieldsProcessorImpl extends org.cyk.utility.persistence.server.TransientFieldsProcessorImpl {

	@Override
	protected void __process__(Class<?> klass,Collection<?> objects,Filter filter, Collection<String> fieldsNames) {
		if(DataAuditedAudit.class.equals(klass))
			processDataAuditedAudits(CollectionHelper.cast(DataAuditedAudit.class, objects),fieldsNames);
		else
			super.__process__(klass,objects,filter, fieldsNames);
	}

	public void processDataAuditedAudits(Collection<DataAuditedAudit> dataAuditedAudits,Collection<String> fieldsNames) {
		for(String fieldName : fieldsNames) {
			if(DataAuditedAudit.FIELD___WHEN_AS_STRING__.equals(fieldName)) {
				DataAuditedAuditWhensReader dataAuditedAuditWhensReader = new DataAuditedAuditWhensReader();
				dataAuditedAuditWhensReader.readThenSet(dataAuditedAudits, null);
			}
		}
	}
}