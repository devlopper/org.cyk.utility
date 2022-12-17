package org.cyk.utility.test.persistence.server;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.persistence.EntityManagerGetter;

import lombok.Getter;

public interface Transaction {

	void run();

	EntityManager getEntityManager();
	Transaction setEntityManager(EntityManager entityManager);
	
	public static abstract class AbstractImpl extends AbstractObject implements Transaction,Serializable {

		@Getter
		protected EntityManager entityManager;
		
		public Transaction setEntityManager(EntityManager entityManager) {
			this.entityManager = entityManager;
			return this;
		}
		
		@Override
		public void run() {
			if(entityManager == null)
				entityManager = EntityManagerGetter.getInstance().get();
			entityManager.getTransaction().begin();
			__run__(entityManager);
			entityManager.getTransaction().commit();
		}
		
		protected abstract void __run__(EntityManager entityManager);
	}	
}