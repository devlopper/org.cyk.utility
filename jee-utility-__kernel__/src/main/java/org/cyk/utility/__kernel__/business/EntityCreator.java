package org.cyk.utility.__kernel__.business;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityCreator {

	@Transactional
	void create(QueryExecutorArguments arguments);
	
	@Transactional
	void createMany(Collection<Object> objects);
	
	@Transactional
	default void createMany(Object...objects) {
		if(ArrayHelper.isEmpty(objects))
			return;
		createMany(CollectionHelper.listOf(objects));
	}
	
	<T> void createByBatch(List<T> list,Integer batchSize);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements EntityCreator,Serializable{
		public static Boolean IS_CONTAINER_MANAGED_TRANSACTION = Boolean.TRUE;
		
		@Override @Transactional
		public void create(QueryExecutorArguments queryExecutorArguments) {
			if(queryExecutorArguments == null)
				return;
			queryExecutorArguments.setIsTransactional(!Boolean.TRUE.equals(IS_CONTAINER_MANAGED_TRANSACTION));
			__createMany__(queryExecutorArguments);
		}
		
		@Transactional
		@Override
		public void createMany(Collection<Object> objects) {
			if(CollectionHelper.isEmpty(objects))
				return;
			QueryExecutorArguments queryExecutorArguments = new QueryExecutorArguments();
			queryExecutorArguments.setObjects(objects);
			create(queryExecutorArguments);
		}
		
		protected void __createMany__(QueryExecutorArguments queryExecutorArguments) {
			org.cyk.utility.__kernel__.persistence.query.EntityCreator.getInstance().createMany(queryExecutorArguments);
		}
	
		@Override
		public <T> void createByBatch(List<T> list,Integer batchSize) {
			List<List<T>> lists = CollectionHelper.getBatches(list, batchSize);	
			if(CollectionHelper.isEmpty(lists))
				return;
			for(List<T> index : lists) {
				createMany(CollectionHelper.cast(Object.class, index));
				LogHelper.logInfo(String.format("%s batch created", index.size()), getClass());
			}
		}
		
	}
	
	/**/
	
	/**/
	
	static EntityCreator getInstance() {
		return Helper.getInstance(EntityCreator.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}