package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.PersistenceHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;

public interface NativeQueryStringBuilder {

	<T> String buildInsertOne(Class<T> klass,T object);
	<T> String buildInsertMany(Class<T> klass,Collection<T> collection);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements NativeQueryStringBuilder,Serializable {
		
		@Override
		public <T> String buildInsertOne(Class<T> klass, T object) {
			String tableName = PersistenceHelper.getTableName(klass);
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank(String.format("Table name of class %s",klass), tableName);
			Set<String> columnsNames = PersistenceHelper.getColumnsNames(klass);
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty(String.format("columns names of class %s", klass),columnsNames);			
			Collection<Object> values = PersistenceHelper.getColumnsValues(object);
			ThrowableHelper.throwIllegalArgumentExceptionIfEmpty(String.format("columns values of class %s", klass),values);
			return String.format("INSERT %s",getIntoValues(tableName, columnsNames, values));
		}
		
		protected String getIntoValues(String tableName,Set<String> columnsNames,Collection<Object> values) {
			return String.format("INTO %s (%s) VALUES (%s)",tableName,StringUtils.join(columnsNames,","),StringUtils.join(values,","));
		}
	}
}