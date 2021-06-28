package org.cyk.utility.persistence.query;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityCounter {

	Long count(Class<?> tupleClass,QueryExecutorArguments arguments);	
	Long count(Class<?> tupleClass,String queryIdentifier,Object...filterFieldsValues);
	Long count(Class<?> tupleClass);		
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements EntityCounter,Serializable {
		private static final long serialVersionUID = 1L;		
		
	}
	
	/**/
	
	static EntityCounter getInstance() {
		return Helper.getInstance(EntityCounter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}