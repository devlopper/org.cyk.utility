package org.cyk.utility.__kernel__.controller;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.__entities__.TestedEntityParent;
import org.cyk.utility.__kernel__.__entities__.TestedEntityParentData;
import org.cyk.utility.__kernel__.__entities__.TestedEntityParentDto;
import org.cyk.utility.__kernel__.persistence.EntityManagerFactoryGetterImpl;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.persistence.query.EntityCreator;
import org.cyk.utility.__kernel__.persistence.query.Query;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;
import org.cyk.utility.test.weld.AbstractWeldUnitTestBenchmark;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ControllerEntityReaderUnitTestBenchmark extends AbstractWeldUnitTestBenchmark {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		DependencyInjection.setQualifierClass(MessageRenderer.class, org.cyk.utility.__kernel__.annotation.Test.Class.class);
		EntityManagerFactoryGetterImpl.ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("pu");
		RepresentationEntityClassGetterImpl.MAP.put(TestedEntityParentData.class, TestedEntityParentDto.class);
		QueryHelper.getQueries().setIsRegisterableToEntityManager(Boolean.TRUE);
		QueryHelper.scan(List.of(getClass().getPackage()));	
		QueryHelper.addQueries(Query.build(TestedEntityParent.class,"read.full", "SELECT t FROM TestedEntityParent t"));
		QueryHelper.addQueries(Query.build(TestedEntityParent.class,"read.full.constructor", "SELECT new org.cyk.utility.__kernel__.__entities__.TestedEntityParent(t.identifier,t.code,t.name,t.lazy,t.eager) FROM TestedEntityParent t"));
		QueryHelper.addQueries(Query.build(TestedEntityParent.class,"read.portion", "SELECT new org.cyk.utility.__kernel__.__entities__.TestedEntityParent(t.identifier,t.code,t.name) FROM TestedEntityParent t"));
		Arguments.IS_REPRESENTATION_PROXYABLE = Boolean.FALSE;
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		QueryHelper.clear();
		EntityReader.INSTANCE.set(null);
		org.cyk.utility.__kernel__.representation.EntityReader.INSTANCE.set(null);
		org.cyk.utility.__kernel__.persistence.query.EntityReader.INSTANCE.set(null);
		EntityCreator.INSTANCE.set(null);
	}
	
	@Test
	public void readAll(){	
		Integer count = 10000;
		System.out.println("Persisting..."+count);
		Long t = System.currentTimeMillis();
		EntityManager entityManager = EntityManagerGetter.getInstance().get();
		entityManager.getTransaction().begin();
		for(Integer index = 0; index < count; index = index + 1) {
			entityManager.persist(new TestedEntityParent(""+index,""+index,""+index).setLazy("lazy").setEager("eager"));
			if(index % 1000 == 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
		entityManager.getTransaction().commit();		
		System.out.println("Persisted "+(System.currentTimeMillis()-t));
		
		for(Integer numberOfRounds : new Integer[] {10,100,1000/*,10000,100000,1000000*/}) {			
			__readAll__(numberOfRounds,Boolean.FALSE);
			__readAll__(numberOfRounds,Boolean.TRUE);
		}
	}
	
	/**/
	
	private void __readAll__(Integer numberOfRound,Boolean isResultCachable){
		execute(new Jobs().setName("read").setNumberOfRound(numberOfRound)
				.add("read.full | result is cachable = "+isResultCachable,new Runnable() {
				@Override
				public void run() {
					EntityReader.getInstance().readMany(TestedEntityParentData.class,new Arguments<TestedEntityParentData>()
							.setRepresentationArguments(new org.cyk.utility.__kernel__.representation.Arguments()
									.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier("TestedEntityParent.read.full"))));
				}
			}).add("read.full.constructor | result is cachable = ",new Runnable() {
				@Override
				public void run() {
					EntityReader.getInstance().readMany(TestedEntityParentData.class,new Arguments<TestedEntityParentData>()
							.setRepresentationArguments(new org.cyk.utility.__kernel__.representation.Arguments()
									.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier("TestedEntityParent.read.full.constructor"))));
				}
			}).add("read.portion | result is cachable = ",new Runnable() {
				@Override
				public void run() {
					EntityReader.getInstance().readMany(TestedEntityParentData.class,new Arguments<TestedEntityParentData>()
							.setRepresentationArguments(new org.cyk.utility.__kernel__.representation.Arguments()
									.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier("TestedEntityParent.read.portion"))));
				}
			})
		);
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @AllArgsConstructor
	public static class ReadJob extends Job implements Serializable {		
		private String queryIdentifier;
		private Boolean isResultCachable;	
		{
			setRunnable(new Runnable() {
				@Override
				public void run() {
					EntityReader.getInstance().readMany(TestedEntityParentData.class,new Arguments<TestedEntityParentData>()
							.setRepresentationArguments(new org.cyk.utility.__kernel__.representation.Arguments()
									.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier(queryIdentifier))));
				}
			});
		}		
	}
}