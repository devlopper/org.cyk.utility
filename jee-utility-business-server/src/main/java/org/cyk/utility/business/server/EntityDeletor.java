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

public interface EntityDeletor {

	@Transactional
	void delete(QueryExecutorArguments arguments);
	
	@Transactional
	<T> void deleteMany(Collection<Object> objects);
	
	@Transactional
	default <T> void deleteMany(Object...objects) {
		if(ArrayHelper.isEmpty(objects))
			return;
		deleteMany(CollectionHelper.listOf(objects));
	}
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements EntityDeletor,Serializable{
		public static Boolean IS_CONTAINER_MANAGED_TRANSACTION = Boolean.TRUE;
		
		@Override @Transactional
		public void delete(QueryExecutorArguments queryExecutorArguments) {
			if(queryExecutorArguments == null)
				return;
			queryExecutorArguments.setIsTransactional(!Boolean.TRUE.equals(IS_CONTAINER_MANAGED_TRANSACTION));
			__deleteMany__(queryExecutorArguments);
		}
		
		@Transactional
		@Override
		public <T> void deleteMany(Collection<Object> objects) {
			if(CollectionHelper.isEmpty(objects))
				return;
			QueryExecutorArguments queryExecutorArguments = new QueryExecutorArguments();
			queryExecutorArguments.setObjects(objects);
			queryExecutorArguments.setIsTransactional(!Boolean.TRUE.equals(IS_CONTAINER_MANAGED_TRANSACTION));
			__deleteMany__(queryExecutorArguments);
		}
		
		protected void __deleteMany__(QueryExecutorArguments queryExecutorArguments) {
			org.cyk.utility.persistence.query.EntityDeletor.getInstance().deleteMany(queryExecutorArguments);
		}
	}
	/**/
	
	static EntityDeletor getInstance() {
		return Helper.getInstance(EntityDeletor.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
}