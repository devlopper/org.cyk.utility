package org.cyk.utility.__kernel__.controller;

import java.io.Serializable;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.__entities__.TestedEntityParent;
import org.cyk.utility.__kernel__.__entities__.TestedEntityParentData;
import org.cyk.utility.__kernel__.__entities__.TestedEntityParentDto;
import org.cyk.utility.__kernel__.persistence.query.EntityCreator;
import org.cyk.utility.__kernel__.persistence.query.Query;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArgumentsDto;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;
import org.cyk.utility.__kernel__.test.weld.AbstractQueryUnitTestBenchmark;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public abstract class AbstractControllerQueryUnitTestBenchmark extends AbstractQueryUnitTestBenchmark {
	private static final long serialVersionUID = 1L;
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		Arguments.IS_REPRESENTATION_PROXYABLE = Boolean.FALSE;
		DependencyInjection.setQualifierClass(MessageRenderer.class, org.cyk.utility.__kernel__.annotation.Test.Class.class);
		RepresentationEntityClassGetterImpl.MAP.put(TestedEntityParentData.class, TestedEntityParentDto.class);
		QueryHelper.addQueries(Query.build(TestedEntityParent.class,"read.full", "SELECT t FROM TestedEntityParent t"));
		QueryHelper.addQueries(Query.build(TestedEntityParent.class,"read.constructor.full", "SELECT new org.cyk.utility.__kernel__.__entities__.TestedEntityParent(t.identifier,t.code,t.name) FROM TestedEntityParent t"));
		QueryHelper.addQueries(Query.build(TestedEntityParent.class,"read.constructor.portion", "SELECT new org.cyk.utility.__kernel__.__entities__.TestedEntityParent(t.identifier,t.code,t.name,t.lazy,t.eager) FROM TestedEntityParent t"));
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		EntityReader.INSTANCE.set(null);
		org.cyk.utility.__kernel__.representation.EntityReader.INSTANCE.set(null);
		org.cyk.utility.__kernel__.persistence.query.EntityReader.INSTANCE.set(null);
		EntityCreator.INSTANCE.set(null);
	}
	
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
			EntityReader.getInstance().readMany(TestedEntityParentData.class,new Arguments()
					.setRepresentationArguments(new org.cyk.utility.__kernel__.representation.Arguments()
							.setQueryExecutorArguments(new QueryExecutorArgumentsDto().setQueryIdentifier(queryIdentifier))));
		}
	}
}