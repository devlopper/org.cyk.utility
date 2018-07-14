package org.cyk.utility.sql.builder;

import org.cyk.utility.sql.builder.QueryClauseStringBuilderSelect;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class QueryClauseStringBuilderSelectUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void selectStart(){
		assertionHelper.assertEquals("SELECT *", __inject__(QueryClauseStringBuilderSelect.class).addTuplesByNames("Tuple").execute().getOutput());
	}
	
	@Test
	public void selectColumn1(){
		assertionHelper.assertEquals("SELECT column1", __inject__(QueryClauseStringBuilderSelect.class).addTuplesByNames("Tuple").addAttributesByNames("column1")
				.setIsAttributeNamePrefixedWithTuple(Boolean.FALSE).execute().getOutput());
	}
	
	@Test
	public void selectColumn1ColumnPrefixRequired(){
		assertionHelper.assertEquals("SELECT tuple.column1", __inject__(QueryClauseStringBuilderSelect.class).addTuplesByNames("Tuple")
				.addAttributesByNames("column1").execute().getOutput());
	}
	
	@Test
	public void selectColumn1Column2(){
		assertionHelper.assertEquals("SELECT column1,column2", __inject__(QueryClauseStringBuilderSelect.class).addTuplesByNames("Tuple")
				.addAttributesByNames("column1","column2").setIsAttributeNamePrefixedWithTuple(Boolean.FALSE).execute().getOutput());
	}
	
	@Test
	public void selectColumn1Column2ColumnPrefixRequired(){
		assertionHelper.assertEquals("SELECT tuple.column1,tuple.column2", __inject__(QueryClauseStringBuilderSelect.class).addTuplesByNames("Tuple")
				.addAttributesByNames("column1","column2").execute().getOutput());
	}
	
	@Test
	public void selectColumn2Column1Column2(){
		assertionHelper.assertEquals("SELECT column2,column1", __inject__(QueryClauseStringBuilderSelect.class).addTuplesByNames("Tuple")
				.addAttributesByNames("column2","column1","column2").setIsAttributeNamePrefixedWithTuple(Boolean.FALSE).execute().getOutput());
	}
	
	@Test
	public void selectColumn2Column1Column2ColumnPrefixRequired(){
		assertionHelper.assertEquals("SELECT tuple.column2,tuple.column1", __inject__(QueryClauseStringBuilderSelect.class).addTuplesByNames("Tuple")
				.addAttributesByNames("column2","column1","column2").execute().getOutput());
	}
}
