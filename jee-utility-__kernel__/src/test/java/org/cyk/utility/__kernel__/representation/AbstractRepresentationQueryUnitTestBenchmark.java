package org.cyk.utility.__kernel__.representation;

import java.io.Serializable;

import org.cyk.utility.__kernel__.__entities__.TestedEntityParent;
import org.cyk.utility.__kernel__.__entities__.TestedEntityParentDto;
import org.cyk.utility.__kernel__.__entities__.TestedEntityParentDtoMapper;
import org.cyk.utility.__kernel__.mapping.MapperClassGetterImpl;
import org.cyk.utility.__kernel__.persistence.query.Query;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutorArguments;
import org.cyk.utility.__kernel__.persistence.query.QueryHelper;
import org.cyk.utility.test.weld.AbstractQueryUnitTestBenchmark;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public abstract class AbstractRepresentationQueryUnitTestBenchmark extends AbstractQueryUnitTestBenchmark {
	private static final long serialVersionUID = 1L;
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		MapperClassGetterImpl.MAP.put(TestedEntityParentDto.class, TestedEntityParentDtoMapper.class);
		QueryHelper.addQueries(Query.build(TestedEntityParent.class,"read.full", "SELECT t FROM TestedEntityParent t"));
		QueryHelper.addQueries(Query.build(TestedEntityParent.class,"read.constructor.full", "SELECT new org.cyk.utility.__kernel__.__entities__.TestedEntityParent(t.identifier,t.code,t.name) FROM TestedEntityParent t"));
		QueryHelper.addQueries(Query.build(TestedEntityParent.class,"read.constructor.portion", "SELECT new org.cyk.utility.__kernel__.__entities__.TestedEntityParent(t.identifier,t.code,t.name,t.lazy,t.eager) FROM TestedEntityParent t"));
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		EntityReader.INSTANCE.set(null);
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
			EntityReader.getInstance().read(new Arguments().setRepresentationEntityClass(TestedEntityParentDto.class)
					.setQueryExecutorArguments(new QueryExecutorArguments.Dto().setQueryIdentifier(queryIdentifier)));
		}
	}
}