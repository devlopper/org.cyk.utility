package org.cyk.utility.sql.builder;

import org.cyk.utility.__kernel__.computation.SortOrder;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class QueryClauseStringBuilderOrderByUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void orderByColumn1___addFormatArgumentObjects_column1(){
		assertionHelper.assertEquals("ORDER BY column1 ASC", __inject__(QueryClauseStringBuilderOrderBy.class).addFormatArgumentObjects("column1 ASC").execute().getOutput());
	}
	
	@Test
	public void orderBy_tuple_dot_code___addFormatArgumentObjects_tupleDOTcode(){
		assertionHelper.assertEquals("ORDER BY tuple.code ASC", __inject__(QueryClauseStringBuilderOrderBy.class).addFormatArgumentObjects("tuple.code ASC").execute().getOutput());
	}
	
	@Test
	public void orderBy_code___addAttribute_code(){
		assertionHelper.assertEquals("ORDER BY code ASC", __inject__(QueryClauseStringBuilderOrderBy.class)
				.addTuples(new Tuple().setName("Tuple")).addAttributesByNames("code").setIsAttributeNamePrefixedWithTuple(Boolean.FALSE).execute().getOutput());
	}
	
	@Test
	public void orderBy_tuple_dot_code___addAttribute_tupleDOTcode(){
		assertionHelper.assertEquals("ORDER BY tuple.code ASC", __inject__(QueryClauseStringBuilderOrderBy.class).addTuples(new Tuple().setName("Tuple"))
				.addAttributesByNames("code").execute().getOutput());
	}
	
	@Test
	public void orderBy_tuple_dot_code_desc___addAttribute_tupleDOTcode(){
		assertionHelper.assertEquals("ORDER BY tuple.code DESC", __inject__(QueryClauseStringBuilderOrderBy.class).addTuples(new Tuple().setName("Tuple"))
				.addAttributes(new Attribute().setName("code").setSortOrder(SortOrder.DESCENDING).setTuple(new Tuple().setName("Tuple"))).execute().getOutput());
	}
	
	@Test
	public void orderBy_tuple_dot_code_desc_name_asc___addAttribute_tupleDOTcode(){
		assertionHelper.assertEquals("ORDER BY tuple.code DESC,tuple.name ASC", __inject__(QueryClauseStringBuilderOrderBy.class).addTuples(new Tuple().setName("Tuple"))
				.addAttributes(new Attribute().setName("code").setSortOrder(SortOrder.DESCENDING).setTuple(new Tuple().setName("Tuple")))
				.addAttributes(new Attribute().setName("name"))
				.execute().getOutput());
	}
}
