package org.cyk.utility.persistence.server.hibernate;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.annotation.Test;

@Test
public class AuditReaderImplTest extends org.cyk.utility.persistence.server.hibernate.AuditReaderImpl implements Serializable {

	@Override
	protected <T> void __process__(Class<T> klass, T identifiable,Collection<T> auditsRecords) {
		super.__process__(klass, identifiable,auditsRecords);
		if(DataAudited.class.equals(klass)) {
			
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T> AbstractAuditsRecordsByRevisionsNumbersNativeReader<T> getAuditsRecordsByRevisionsNumbersNativeReader(Class<T> klass, Arguments<T> arguments, Object identifier, Collection<Number> numbers) {
		if(DataAudited.class.equals(klass))
			return (AbstractAuditsRecordsByRevisionsNumbersNativeReader<T>) new DataAuditedAuditsRecordsByRevisionsNumbersNativeReader();
		return super.getAuditsRecordsByRevisionsNumbersNativeReader(klass, arguments, identifier, numbers);
	}
	
	
}