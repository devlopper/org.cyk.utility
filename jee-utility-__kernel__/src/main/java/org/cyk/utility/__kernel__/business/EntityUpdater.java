package org.cyk.utility.__kernel__.business;

import java.io.Serializable;
import java.util.Collection;

import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityUpdater {

	@Transactional
	<T> void updateMany(Collection<Object> objects);
	
	@Transactional
	default <T> void updateMany(Object...objects) {
		if(ArrayHelper.isEmpty(objects))
			return;
		updateMany(CollectionHelper.listOf(objects));
	}
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements EntityUpdater,Serializable{
		public static Boolean IS_CONTAINER_MANAGED_TRANSACTION = Boolean.TRUE;
		
		@Transactional
		@Override
		public <T> void updateMany(Collection<Object> objects) {
			if(CollectionHelper.isEmpty(objects))
				return;
			QueryExecutorArguments queryExecutorArguments = new QueryExecutorArguments();
			queryExecutorArguments.setObjects(objects);
			queryExecutorArguments.setIsTransactional(!Boolean.TRUE.equals(IS_CONTAINER_MANAGED_TRANSACTION));
			__updateMany__(queryExecutorArguments);
		}
		
		protected void __updateMany__(QueryExecutorArguments queryExecutorArguments) {
			org.cyk.utility.__kernel__.persistence.query.EntityUpdater.getInstance().updateMany(queryExecutorArguments);
		}
	}
	/**/
	
	static EntityUpdater getInstance() {
		return Helper.getInstance(EntityUpdater.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}