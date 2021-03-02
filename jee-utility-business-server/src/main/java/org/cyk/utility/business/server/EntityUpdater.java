package org.cyk.utility.business.server;

import java.io.Serializable;
import java.util.Collection;

import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityUpdater {

	@Transactional
	void update(QueryExecutorArguments queryExecutorArguments);
	
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
		public void update(QueryExecutorArguments queryExecutorArguments) {
			if(queryExecutorArguments == null)
				return;
			queryExecutorArguments.setIsTransactional(!Boolean.TRUE.equals(IS_CONTAINER_MANAGED_TRANSACTION));
			__update__(queryExecutorArguments);
		}
		
		@Transactional
		@Override
		public <T> void updateMany(Collection<Object> objects) {
			if(CollectionHelper.isEmpty(objects))
				return;
			QueryExecutorArguments queryExecutorArguments = new QueryExecutorArguments();
			queryExecutorArguments.setObjects(objects);
			queryExecutorArguments.setIsTransactional(!Boolean.TRUE.equals(IS_CONTAINER_MANAGED_TRANSACTION));
			__update__(queryExecutorArguments);
		}
		
		protected void __update__(QueryExecutorArguments queryExecutorArguments) {
			org.cyk.utility.persistence.query.EntityUpdater.getInstance().updateMany(queryExecutorArguments);
		}
	}
	/**/
	
	static EntityUpdater getInstance() {
		return Helper.getInstance(EntityUpdater.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}