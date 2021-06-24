package org.cyk.utility.persistence.server.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class DataAuditedAuditsRecordsByRevisionsNumbersNativeReader extends AbstractAuditsRecordsByRevisionsNumbersNativeReader<DataAudited> implements Serializable {
	
	@Override
	protected void __set__(DataAudited data, Object[] array, Integer i) {
		data.setCode(getAsString(array, i++));
		data.setName(getAsString(array, i++));
	}

	@Override
	protected Collection<String> getProjections() {
		return List.of(DataAudited.COLUMN_CODE,DataAudited.COLUMN_NAME);
	}
	
	@Override
	protected Class<DataAudited> getEntityClass() {
		return DataAudited.class;
	}
}