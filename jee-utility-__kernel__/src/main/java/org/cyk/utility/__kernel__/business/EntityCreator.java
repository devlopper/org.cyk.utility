package org.cyk.utility.__kernel__.business;

import java.io.Serializable;
import java.util.Collection;

import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityCreator {

	@Transactional
	<T> void createMany(Collection<Object> objects);
	
	@Transactional
	default <T> void createMany(Object...objects) {
		if(ArrayHelper.isEmpty(objects))
			return;
		createMany(CollectionHelper.listOf(objects));
	}
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements EntityCreator,Serializable{
		public static Boolean IS_CONTAINER_MANAGED_TRANSACTION = Boolean.TRUE;
		
		@Transactional
		@Override
		public <T> void createMany(Collection<Object> objects) {
			if(CollectionHelper.isEmpty(objects))
				return;
			QueryExecutorArguments queryExecutorArguments = new QueryExecutorArguments();
			queryExecutorArguments.setObjects(objects);
			queryExecutorArguments.setIsTransactional(!Boolean.TRUE.equals(IS_CONTAINER_MANAGED_TRANSACTION));
			__createMany__(queryExecutorArguments);
		}
		
		protected void __createMany__(QueryExecutorArguments queryExecutorArguments) {
			org.cyk.utility.__kernel__.persistence.query.EntityCreator.getInstance().createMany(queryExecutorArguments);
		}
	}
	/**/
	
	static EntityCreator getInstance() {
		return Helper.getInstance(EntityCreator.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}