package org.cyk.utility.test.persistence.server;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.persistence.EntityManagerGetter;

public interface Transaction {

	void run();
	
	public static abstract class AbstractImpl extends AbstractObject implements Transaction,Serializable {

		@Override
		public void run() {
			EntityManager entityManager = EntityManagerGetter.getInstance().get();
			entityManager.getTransaction().begin();
			__run__(entityManager);
			entityManager.getTransaction().commit();
		}
		
		protected abstract void __run__(EntityManager entityManager);
	}	
}