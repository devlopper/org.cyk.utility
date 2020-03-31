package org.cyk.utility.__kernel__.persistence;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.value.Value;

public interface EntityManagerGetter {

	EntityManager get();
	
	/**/
	
	public abstract class AbstractImpl extends AbstractObject implements EntityManagerGetter,Serializable {

		@Override
		public EntityManager get() {
			EntityManagerFactory entityManagerFactory = EntityManagerFactoryGetter.getInstance().get();
			if(entityManagerFactory == null)
				throw new RuntimeException("Entity manager factory cannot bet got.");
			return entityManagerFactory.createEntityManager();
		}		
	}
	
	/**/
	
	static EntityManagerGetter getInstance() {
		return Helper.getInstance(EntityManagerGetter.class, INSTANCE);
	}
	
	Value INSTANCE = DependencyInjection.inject(Value.class);	
}