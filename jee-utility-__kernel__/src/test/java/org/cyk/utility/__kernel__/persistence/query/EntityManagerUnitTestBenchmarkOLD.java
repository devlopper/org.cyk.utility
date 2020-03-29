package org.cyk.utility.__kernel__.persistence.query;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.__entities__.TestedEntityParent;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTestBenchmark;
import org.junit.jupiter.api.Test;

public class EntityManagerUnitTestBenchmarkOLD extends AbstractWeldUnitTestBenchmark {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("pu");
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
		QueryHelper.addQueries(Query.build(TestedEntityParent.class,"readBySystemIdentifier", "SELECT t FROM TestedEntityParent t WHERE t.identifier = :identifier"));
		QueryHelper.addQueries(Query.build(TestedEntityParent.class,"readBySystemIdentifier.1", "SELECT new org.cyk.utility.__kernel__.__entities__.TestedEntityParent(t.identifier,t.code,t.name) FROM TestedEntityParent t WHERE t.identifier = :identifier"));
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
	}
	
	@Test
	public void get(){		
		for(Integer numberOfRounds : new Integer[] {10,100,1000,10000,100000,1000000}) {
			EntityManager entityManager = EntityManagerGetter.getInstance().get();
			entityManager.getTransaction().begin();
			for(Integer index = 0; index < 1000; index = index + 1) {
				entityManager.persist(new TestedEntityParent(""+index,""+index,""+index).setLazy("lazy").setEager("eager"));
				if(index % 1000 == 0) {
					entityManager.flush();
					entityManager.clear();
				}
			}
			entityManager.getTransaction().commit();			
			__get__(entityManager,numberOfRounds);
			
			entityManager = EntityManagerGetter.getInstance().get();
			entityManager.getTransaction().begin();
			entityManager.createQuery("DELETE FROM TestedEntityParent").executeUpdate();
			entityManager.getTransaction().commit();	
		}
	}
	
	/**/
	
	private void __get__(EntityManager entityManager,Integer numberOfRound){
		__get__(entityManager,Boolean.FALSE, numberOfRound);
		__get__(entityManager,Boolean.TRUE, numberOfRound);
	}
	
	private void __get__(EntityManager entityManager,Boolean isEntityManagerClosable,Integer numberOfRound){
		if(Boolean.TRUE.equals(isEntityManagerClosable))
			entityManager.close();		
		execute(new Jobs().setName("Get from "+(isEntityManagerClosable ? "new" : "current")+" entity manager").setNumberOfRound(numberOfRound)
				.add("EntityManager.getReference",new Runnable() {
				@Override
				public void run() {
					EntityManager __entityManager__ = Boolean.TRUE.equals(isEntityManagerClosable) ? EntityManagerGetter.getInstance().get() : entityManager;
					__entityManager__.getReference(TestedEntityParent.class, "1");
					if(Boolean.TRUE.equals(isEntityManagerClosable))
						__entityManager__.close();
				}
			}).add("EntityManager.find",new Runnable() {
				@Override
				public void run() {
					EntityManager __entityManager__ = Boolean.TRUE.equals(isEntityManagerClosable) ? EntityManagerGetter.getInstance().get() : entityManager;
					__entityManager__.find(TestedEntityParent.class, "1");
					if(Boolean.TRUE.equals(isEntityManagerClosable))
						__entityManager__.close();
				}
			}).add("query.full", new Runnable() {
				@Override
				public void run() {
					EntityManager __entityManager__ = Boolean.TRUE.equals(isEntityManagerClosable) ? EntityManagerGetter.getInstance().get() : entityManager;
					__entityManager__.createQuery("SELECT t FROM TestedEntityParent t WHERE t.identifier = :identifier", TestedEntityParent.class).setParameter("identifier", "1").getSingleResult();
					if(Boolean.TRUE.equals(isEntityManagerClosable))
						__entityManager__.close();
				}
			}).add("query.portion", new Runnable() {
				@Override
				public void run() {
					EntityManager __entityManager__ = Boolean.TRUE.equals(isEntityManagerClosable) ? EntityManagerGetter.getInstance().get() : entityManager;
					__entityManager__.createQuery("SELECT new org.cyk.utility.__kernel__.__entities__.TestedEntityParent(t.identifier,t.code,t.name) FROM TestedEntityParent t WHERE t.identifier = :identifier", TestedEntityParent.class)
					.setParameter("identifier", "1").getSingleResult();
					if(Boolean.TRUE.equals(isEntityManagerClosable))
						__entityManager__.close();
				}
			}).add("query.named.full", new Runnable() {
				@Override
				public void run() {
					EntityManager __entityManager__ = Boolean.TRUE.equals(isEntityManagerClosable) ? EntityManagerGetter.getInstance().get() : entityManager;
					__entityManager__.createNamedQuery("TestedEntityParent.readBySystemIdentifier", TestedEntityParent.class).setParameter("identifier", "1").getSingleResult();
					if(Boolean.TRUE.equals(isEntityManagerClosable))
						__entityManager__.close();
				}
			}).add("query.named.portion", new Runnable() {
				@Override
				public void run() {
					EntityManager __entityManager__ = Boolean.TRUE.equals(isEntityManagerClosable) ? EntityManagerGetter.getInstance().get() : entityManager;
					__entityManager__.createNamedQuery("TestedEntityParent.readBySystemIdentifier.1", TestedEntityParent.class)
					.setParameter("identifier", "1").getSingleResult();
					if(Boolean.TRUE.equals(isEntityManagerClosable))
						__entityManager__.close();
				}
			})
		);
	}
}