package org.cyk.utility.sql.jpql.builder;

import org.cyk.utility.sql.jpql.JpqlQualifier;
import org.cyk.utility.sql.jpql.builder.QueryClauseStringBuilderSelectJpql;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class QueryClauseStringBuilderSelectJpqlUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void selectStart(){
		assertionHelper.assertEquals("SELECT tuple", JpqlQualifier.inject(QueryClauseStringBuilderSelectJpql.class).addTuplesByNames("Tuple").execute().getOutput());
	}
	
	@Test
	public void selectColumn1(){
		assertionHelper.assertEquals("SELECT column1", JpqlQualifier.inject(QueryClauseStringBuilderSelectJpql.class)
				.setIsAttributeNamePrefixedWithTuple(Boolean.FALSE).addTuplesByNames("Tuple").addAttributesByNames("column1").execute().getOutput());
	}
	
	@Test
	public void selectColumn1ColumnPrefixRequired(){
		assertionHelper.assertEquals("SELECT tuple.column1", JpqlQualifier.inject(QueryClauseStringBuilderSelectJpql.class).addTuplesByNames("Tuple")
				.addAttributesByNames("column1").execute().getOutput());
	}
	
	@Test
	public void selectColumn1Column2(){
		assertionHelper.assertEquals("SELECT column1,column2", JpqlQualifier.inject(QueryClauseStringBuilderSelectJpql.class).addTuplesByNames("Tuple")
				.setIsAttributeNamePrefixedWithTuple(Boolean.FALSE).addAttributesByNames("column1","column2").execute().getOutput());
	}
	
	@Test
	public void selectColumn1Column2ColumnPrefixRequired(){
		assertionHelper.assertEquals("SELECT tuple.column1,tuple.column2", JpqlQualifier.inject(QueryClauseStringBuilderSelectJpql.class)
				.addTuplesByNames("Tuple").addAttributesByNames("column1","column2").execute().getOutput());
	}
	
	@Test
	public void selectColumn2Column1Column2(){
		assertionHelper.assertEquals("SELECT column2,column1", JpqlQualifier.inject(QueryClauseStringBuilderSelectJpql.class).addTuplesByNames("Tuple")
				.setIsAttributeNamePrefixedWithTuple(Boolean.FALSE).addAttributesByNames("column2","column1","column2").execute().getOutput());
	}
	
	@Test
	public void selectColumn2Column1Column2ColumnPrefixRequired(){
		assertionHelper.assertEquals("SELECT tuple.column2,tuple.column1", JpqlQualifier.inject(QueryClauseStringBuilderSelectJpql.class)
				.addTuplesByNames("Tuple").addAttributesByNames("column2","column1","column2").execute().getOutput());
	}
}
