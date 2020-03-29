package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.cyk.utility.__kernel__.__entities__.TestedEntityParent;
import org.cyk.utility.__kernel__.persistence.EntityManagerGetter;
import org.cyk.utility.__kernel__.test.weld.AbstractQueryUnitTestBenchmark;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public abstract class AbstractEntityManagerUnitTestBenchmark extends AbstractQueryUnitTestBenchmark {
	private static final long serialVersionUID = 1L;
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		QueryHelper.addQueries(Query.build(TestedEntityParent.class,"read.full", "SELECT t FROM TestedEntityParent t"));
		QueryHelper.addQueries(Query.build(TestedEntityParent.class,"read.constructor.full", "SELECT new org.cyk.utility.__kernel__.__entities__.TestedEntityParent(t.identifier,t.code,t.name) FROM TestedEntityParent t"));
		QueryHelper.addQueries(Query.build(TestedEntityParent.class,"read.constructor.portion", "SELECT new org.cyk.utility.__kernel__.__entities__.TestedEntityParent(t.identifier,t.code,t.name,t.lazy,t.eager) FROM TestedEntityParent t"));
	}
	
	protected abstract Boolean __isResultCachable__();
	
	@Override
	protected Object __instantiateData__(Integer index) {
		return new TestedEntityParent(""+index,""+index,""+index).setLazy("lazy").setEager("eager");
	}

	@Override
	protected Class<? extends AbstractJob> __getJobClass__() {
		return ReadAllJob.class;
	}
	
	@Override
	protected AbstractJob __instantiateJob__(String queryIdentifier, Boolean isResultCachable) {
		return new ReadAllJob(queryIdentifier, isResultCachable);
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class ReadAllJob extends AbstractJob implements Serializable {		
		
		public ReadAllJob(String queryIdentifier, Boolean isResultCachable) {
			super(queryIdentifier, isResultCachable);
		}

		@Override
		protected void __run__() {
			EntityManager entityManager = EntityManagerGetter.getInstance().get();
			entityManager.createNamedQuery(queryIdentifier, TestedEntityParent.class).setHint("org.hibernate.cacheable", isResultCachable).getResultList();
			entityManager.close();	
		}
	}
}