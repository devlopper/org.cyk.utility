package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface QueryCreateExecutor {

	<T> void create(Class<T> tupleClass,Arguments<T> arguments);
	
	/**/
	
	public abstract class AbstractQueryCreateExecutorImpl extends AbstractObject implements QueryCreateExecutor,Serializable {

		@Override
		public <T> void create(Class<T> tupleClass,Arguments<T> arguments) {
			if(tupleClass == null)
				throw new RuntimeException("tuple class is required");
			if(arguments == null)
				throw new RuntimeException("arguments are required");
			if(CollectionHelper.isEmpty(arguments.getCollection()))
				return;
			EntityManager entityManager = arguments.entityManager;
			if(entityManager == null)
				entityManager = EntityManagerGetter.getInstance().get();
			for(T tuple : arguments.getCollection())
				__create__(tupleClass, tuple, entityManager);
		}
		
		protected <T> void __create__(Class<T> tupleClass,T tuple,EntityManager entityManager) {
			entityManager.persist(tuple);
		}
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Arguments<T> implements Serializable {
		private Collection<T> collection;
		private EntityManager entityManager;
	}
	
	/**/
	
	static QueryCreateExecutor getInstance() {
		return Helper.getInstance(QueryCreateExecutor.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);
}