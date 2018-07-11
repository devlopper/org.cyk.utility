package org.cyk.utility.sql.jpql;

import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class QueryClauseStringBuilderSelectJpqlUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void selectStart(){
		assertionHelper.assertEquals("SELECT tuple", __injectByQualifiersClasses__(QueryClauseStringBuilderSelectJpql.class,JpqlQualifier.class).addTuplesByNames("Tuple").execute().getOutput());
	}
	
	@Test
	public void selectColumn1(){
		assertionHelper.assertEquals("SELECT column1", __injectByQualifiersClasses__(QueryClauseStringBuilderSelectJpql.class,JpqlQualifier.class).addTuplesByNames("Tuple").addColumnsByNames("column1").execute().getOutput());
	}
	
	@Test
	public void selectColumn1ColumnPrefixRequired(){
		assertionHelper.assertEquals("SELECT tuple.column1", __injectByQualifiersClasses__(QueryClauseStringBuilderSelectJpql.class,JpqlQualifier.class).addTuplesByNames("Tuple")
				.setIsPrefixColumnWithTupleRequired(Boolean.TRUE).addColumnsByNames("column1").execute().getOutput());
	}
	
	@Test
	public void selectColumn1Column2(){
		assertionHelper.assertEquals("SELECT column1,column2", __injectByQualifiersClasses__(QueryClauseStringBuilderSelectJpql.class,JpqlQualifier.class).addTuplesByNames("Tuple").addColumnsByNames("column1","column2").execute().getOutput());
	}
	
	@Test
	public void selectColumn1Column2ColumnPrefixRequired(){
		assertionHelper.assertEquals("SELECT tuple.column1,tuple.column2", __injectByQualifiersClasses__(QueryClauseStringBuilderSelectJpql.class,JpqlQualifier.class)
				.addTuplesByNames("Tuple").setIsPrefixColumnWithTupleRequired(Boolean.TRUE).addColumnsByNames("column1","column2").execute().getOutput());
	}
	
	@Test
	public void selectColumn2Column1Column2(){
		assertionHelper.assertEquals("SELECT column2,column1", __injectByQualifiersClasses__(QueryClauseStringBuilderSelectJpql.class,JpqlQualifier.class).addTuplesByNames("Tuple").addColumnsByNames("column2","column1","column2").execute().getOutput());
	}
	
	@Test
	public void selectColumn2Column1Column2ColumnPrefixRequired(){
		assertionHelper.assertEquals("SELECT tuple.column2,tuple.column1", __injectByQualifiersClasses__(QueryClauseStringBuilderSelectJpql.class,JpqlQualifier.class)
				.addTuplesByNames("Tuple").setIsPrefixColumnWithTupleRequired(Boolean.TRUE).addColumnsByNames("column2","column1","column2").execute().getOutput());
	}
}
