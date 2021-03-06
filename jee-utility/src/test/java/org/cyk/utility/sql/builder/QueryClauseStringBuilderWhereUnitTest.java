package org.cyk.utility.sql.builder;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.criteria.Criteria;
import org.cyk.utility.filter.Filter;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class QueryClauseStringBuilderWhereUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	private Tuple tuple;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		tuple = new Tuple().setName("Tuple");
	}
	
	@Test
	public void build(){
		QueryWherePredicateStringBuilder predicateBuilder = (QueryWherePredicateStringBuilder) __inject__(QueryWherePredicateStringBuilderEqual.class).addFormatArgumentObjects("column1",
				ComparisonOperator.EQ.getSymbol(),"1");
		assertionHelper.assertEquals("WHERE column1=1", __inject__(QueryClauseStringBuilderWhere.class).setPredicateBuilder(predicateBuilder).execute().getOutput());
	}
	
	@Test
	public void buildWithAnd(){
		QueryWherePredicateStringBuilder predicateBuilderA = (QueryWherePredicateStringBuilder) __inject__(QueryWherePredicateStringBuilderEqual.class)
				.addFormatArgumentObjects("tuple.f1","=","@f1");
		QueryWherePredicateStringBuilder predicateBuilderB = (QueryWherePredicateStringBuilder) __inject__(QueryWherePredicateStringBuilderEqual.class)
				.addFormatArgumentObjects("tuple.f2","=","@f2");
		QueryWherePredicateStringBuilder predicateBuilder = (QueryWherePredicateStringBuilder) __inject__(QueryWherePredicateStringBuilderEqual.class)
				.addChild(predicateBuilderA,LogicalOperator.AND,predicateBuilderB);
		assertionHelper.assertEquals("WHERE tuple.f1=@f1 AND tuple.f2=@f2", __inject__(QueryClauseStringBuilderWhere.class).setPredicateBuilder(predicateBuilder).execute().getOutput());
	}
	
	@Test
	public void buildUsingFilter(){
		Criteria criteria = __inject__(Criteria.class).setClassName(tuple.getName()).setFieldNameAsString("name");
		Filter filter = __inject__(Filter.class).addChild(criteria);
		assertionHelper.assertEquals("WHERE tuple.name=@name", __inject__(QueryClauseStringBuilderWhere.class).setFilter(filter).execute().getOutput());
	}
	
	@Test
	public void buildUsingFilterAnd(){
		Criteria criteriaA = __inject__(Criteria.class).setClassName("Person").setFieldNameAsString("firstname");
		Criteria criteriaB = __inject__(Criteria.class).setClassName("Person").setFieldNameAsString("lastnames");
		Filter filter = __inject__(Filter.class).addChild(criteriaA,LogicalOperator.AND,criteriaB);
		assertionHelper.assertEquals("WHERE person.firstname=@firstname AND person.lastnames=@lastnames", __inject__(QueryClauseStringBuilderWhere.class).setFilter(filter).execute().getOutput());
	}
	
	/*@Test
	public void execute_filterWithOneChildWithThreeChildWhichAreNestedPredicateANDPredicate_nestedPredicateWithParenthesisAndPredicate(){
		Filter filter = __inject__(Filter.class).addChild(criteriaA,LogicalOperator.AND,criteriaB);
		assertionHelper.assertEquals("(tuple.code=@code OR tuple.firstname=@firstname) AND tuple.lastname=@lastname", 
				__inject__(QueryWherePredicateStringBuilderEqual.class).addChild(
						__inject__(QueryWherePredicateStringBuilderEqual.class)
						.surroundedWithParentheses()
						.addChild(__inject__(QueryWherePredicateStringBuilderEqual.class).addOperandBuilderByAttribute("code", tuple))
						.or()
						.addChild(__inject__(QueryWherePredicateStringBuilderEqual.class).addOperandBuilderByAttribute("firstname", tuple))
						
						,LogicalOperator.AND
						,__inject__(QueryWherePredicateStringBuilderEqual.class).addOperandBuilderByAttribute("lastname", tuple)
					).execute().getOutput());
		assertionHelper.assertEquals("WHERE (tuple.code=@code OR tuple.firstname=@firstname) AND tuple.lastname=@lastname"
				, __inject__(QueryClauseStringBuilderWhere.class).setFilter(filter).execute().getOutput());
	}*/
}
