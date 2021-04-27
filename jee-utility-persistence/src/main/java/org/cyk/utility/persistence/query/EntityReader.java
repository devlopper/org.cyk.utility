package org.cyk.utility.persistence.query;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.persistence.query.QueryExecutorArguments;

public interface EntityReader {

	<T> Collection<T> readMany(Class<T> tupleClass,QueryExecutorArguments arguments);	
	<T> Collection<T> readMany(Class<T> tupleClass,String queryIdentifier,Object...filterFieldsValues);	
	<T> Collection<T> readMany(Class<T> tupleClass);
	
	<T> Collection<T> readManyDynamically(Class<T> tupleClass,QueryExecutorArguments arguments);	
	<T> Collection<T> readManyDynamically(Class<T> tupleClass,Object...filterFieldsValues);	
	
	<T> T readOne(Class<T> tupleClass,QueryExecutorArguments arguments);	
	<T> T readOneWhereFieldEquals(Class<T> tupleClass,String fieldName,Object fieldValue);
	<T> T readOneWhereFieldIn(Class<T> tupleClass,String queryName,String fieldName,Object fieldValue);	
	<T> T readOneBySystemIdentifier(Class<T> tupleClass,Object systemIdentifier);	
	<T> T readOneByBusinessIdentifier(Class<T> tupleClass,Object businessIdentifier);
	
	<T> T readOneDynamically(Class<T> tupleClass,QueryExecutorArguments arguments);	
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements EntityReader,Serializable {
		private static final long serialVersionUID = 1L;		
		
	}
	
	/**/
	
	static EntityReader getInstance() {
		return Helper.getInstance(EntityReader.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}