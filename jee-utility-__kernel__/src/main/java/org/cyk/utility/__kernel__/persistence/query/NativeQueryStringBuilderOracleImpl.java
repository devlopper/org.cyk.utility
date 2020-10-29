package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.annotation.Oracle;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.persistence.PersistenceHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;

@Oracle
public class NativeQueryStringBuilderOracleImpl extends NativeQueryStringBuilder.AbstractImpl implements Serializable {

	@Override
	public <T> String buildInsertMany(Class<T> klass, Collection<T> collection) {
		if(klass == null || CollectionHelper.isEmpty(collection))
			return null;
		Collection<String> intos = new ArrayList<String>();
		String tableName = PersistenceHelper.getTableName(klass);
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank(String.format("Table name of class %s",klass), tableName);
		Set<String> columnsNames = PersistenceHelper.getColumnsNames(klass);
		ThrowableHelper.throwIllegalArgumentExceptionIfEmpty(String.format("columns names of class %s", klass),columnsNames);			
		for(Object object : collection) {
			Collection<Object> values = PersistenceHelper.getColumnsValues(object);
			if(CollectionHelper.isEmpty(values))
				continue;
			if(intos == null)
				intos = new ArrayList<>();
			intos.add(getIntoValues(tableName, columnsNames, values));
		}
		return String.format("INSERT %s ALL %s SELECT * FROM dual",String.format(HINTS_FORMAT, String.format(PARALLEL_FORMAT, tableName,4)), StringUtils.join(intos," "));
	}
	
	private static final String PARALLEL_FORMAT = "PARALLEL(%s,%s)";
	private static final String HINTS_FORMAT = "/*+ %s */";
}