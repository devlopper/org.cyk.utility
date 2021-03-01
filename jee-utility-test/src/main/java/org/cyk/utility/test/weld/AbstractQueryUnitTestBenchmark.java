package org.cyk.utility.test.weld;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public abstract class AbstractQueryUnitTestBenchmark extends AbstractWeldUnitTestBenchmark {
	private static final long serialVersionUID = 1L;

	protected abstract String __getPersistenceUnitName__();
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		//EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory(__getPersistenceUnitName__());
		//QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		//QueryHelper.clear();
	}
	
	protected Integer __getNumberOfData__() {
		return 10000;
	}
	
	protected abstract Boolean __isResultCachable__();
	
	protected void __persistData__() {
		Integer count = __getNumberOfData__();
		System.out.println("Persisting "+count+" ...");
		Long t = System.currentTimeMillis();
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		for(Integer index = 0; index < count; index = index + 1) {
			entityManager.persist(__instantiateData__(index));
			if(index % 1000 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
		entityManager.getTransaction().commit();
		System.out.println("Persisted : "+(System.currentTimeMillis()-t));
	}
	
	protected EntityManager getEntityManager() {
		throw new RuntimeException("Get entity manager not yet implemented");
	}
	
	protected abstract Object __instantiateData__(Integer index);
	
	@Test
	public void readAll(){
		__persistData__();
		for(Integer numberOfRounds : new Integer[] {10,100,1000})
			__readAll__(numberOfRounds);
	}
	
	private void __readAll__(Integer numberOfRounds){
		__readAll__(__isResultCachable__(), numberOfRounds);
	}
	
	private void __readAll__(Boolean isResultCachable,Integer numberOfRounds){
		execute(new Jobs().setName(String.format("Read %s. Result cachable : %s",__getNumberOfData__(),isResultCachable)).setNumberOfRound(numberOfRounds)
			.add(__instantiateJob__("TestedEntityParent.read.full", isResultCachable))
			.add(__instantiateJob__("TestedEntityParent.read.constructor.full", isResultCachable))
			.add(__instantiateJob__("TestedEntityParent.read.constructor.portion", isResultCachable))
		);
	}
	
	protected abstract Class<? extends AbstractJob> __getJobClass__();
	
	protected AbstractJob __instantiateJob__(String queryIdentifier,Boolean isResultCachable) {
		return null;//ClassHelper.instanciate(__getJobClass__(), new Object[] {queryIdentifier,isResultCachable});
	}
	
	protected void execute(Job job) {
		if(Boolean.TRUE.equals(((AbstractJob)job).getIsResultCachable())) {
			//Cache cache = null;//EntityManagerFactoryGetter.getInstance().get().getCache();
			//cache.evictAll();
		}
		super.execute(job);
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static abstract class AbstractJob extends Job implements Serializable {		
		protected String queryIdentifier;
		protected Boolean isResultCachable;
		
		public AbstractJob(String queryIdentifier,Boolean isResultCachable) {
			setName(queryIdentifier);
			setQueryIdentifier(queryIdentifier);
			setIsResultCachable(isResultCachable);
			setRunnable(new Runnable() {
				@Override
				public void run() {
					__run__();
				}
			});
		}			
		protected abstract void __run__();
	}
}