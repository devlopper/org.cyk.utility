package org.cyk.utility.business.server;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.query.Querier;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.persistence.server.query.executor.DynamicManyExecutor;

public interface EntityReader {

	<T> Collection<T> readByIdentifiersForProcessing(Class<T> klass,Collection<Object> identifiers,String actionIdentifier);	
	<T> Collection<T> readByIdentifiersForProcessing(Class<T> klass,String actionIdentifier,Object...identifiers);

	<T> T readByIdentifierForProcessing(Class<T> klass,Object identifier,String actionIdentifier);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements EntityReader,Serializable{
		
		@Override
		public <T> Collection<T> readByIdentifiersForProcessing(Class<T> klass, Collection<Object> identifiers,String actionIdentifier) {
			if(klass == null || CollectionHelper.isEmpty(identifiers))
				return null;
			return __readByIdentifiersForProcessing__(klass, identifiers, actionIdentifier);
		}
		
		protected <T> Collection<T> __readByIdentifiersForProcessing__(Class<T> klass, Collection<Object> identifiers,String actionIdentifier) {
			return DynamicManyExecutor.getInstance().read(klass, getQueryExecutorArguments(klass, identifiers,actionIdentifier));
		}
		
		protected QueryExecutorArguments getQueryExecutorArguments(Class<?> klass,Collection<Object> identifiers,String actionIdentifier) {
			QueryExecutorArguments arguments = new QueryExecutorArguments();
			arguments.addProjectionsFromStrings("identifier");
			arguments.addFilterFieldsValues(Querier.PARAMETER_NAME_IDENTIFIERS,identifiers);
			return arguments;
		}
		
		@Override
		public <T> Collection<T> readByIdentifiersForProcessing(Class<T> klass, String actionIdentifier, Object... identifiers) {
			if(klass == null || ArrayHelper.isEmpty(identifiers))
				return null;
			return readByIdentifiersForProcessing(klass, CollectionHelper.listOf(identifiers), actionIdentifier);
		}
		
		@Override
		public <T> T readByIdentifierForProcessing(Class<T> klass, Object identifier, String actionIdentifier) {
			if(klass == null || identifier == null)
				return null;
			return CollectionHelper.getFirst(readByIdentifiersForProcessing(klass,List.of(identifier), actionIdentifier));
		}
	}
	
	/**/
	
	/**/
	
	static EntityReader getInstance() {
		return Helper.getInstance(EntityReader.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}