package org.cyk.utility.sql.builder;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class QueryClauseStringBuilderFromUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void selectTuple(){
		assertionHelper.assertEquals("FROM Tuple tuple", __inject__(QueryClauseStringBuilderFrom.class).addTuplesByNames("Tuple").execute().getOutput());
	}
	
	@Test
	public void selectTuple1Tuple2(){
		assertionHelper.assertEquals("FROM Tuple1 tuple1,Tuple2 tuple2", __inject__(QueryClauseStringBuilderFrom.class).addTuplesByNames("Tuple1").addTuplesByNames("Tuple2")
				.execute().getOutput());
	}
	
	@Test
	public void selectTuple1AliasATuple1AliasB(){
		assertionHelper.assertEquals("FROM Tuple1 a,Tuple1 b", __inject__(QueryClauseStringBuilderFrom.class)
				.addTuples(new Tuple().setName("Tuple1").setAlias("a"),new Tuple().setName("Tuple1").setAlias("b"))
				.execute().getOutput());
	}
	
}
