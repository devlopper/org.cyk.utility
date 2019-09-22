package org.cyk.utility.sql.builder;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.criteria.Criteria;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.junit.jupiter.api.Test;

public class QueryWherePredicateStringBuilderEqualUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	private Tuple tuple;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		tuple = new Tuple().setName("Tuple");
	}
	
	@Test
	public void isColumn1Equal1ByFormat(){
		assertionHelper.assertEquals("column1=1", __inject__(QueryWherePredicateStringBuilderEqual.class).addFormatArgumentObjects("column1","=","1").execute().getOutput());
	}
	
	@Test
	public void isColumn1Equal1ByChild(){
		assertionHelper.assertEquals("column1 = 1", __inject__(QueryWherePredicateStringBuilderEqual.class).addChild("column1","=","1").execute().getOutput());
	}
	
	@Test
	public void execute_predicateWithOneChildWhichIsLogicalOperatorAND_AND(){
		assertionHelper.assertEquals("AND", 
				__inject__(QueryWherePredicateStringBuilderEqual.class).addChild(
						LogicalOperator.AND
					).execute().getOutput());
	}
	
	@Test
	public void execute_predicateWithOneChildWhichIsPredicate_predicate(){
		assertionHelper.assertEquals("tuple.code=@code", 
				__inject__(QueryWherePredicateStringBuilderEqual.class).addChild(
						__inject__(QueryWherePredicateStringBuilderEqual.class).addOperandBuilderByAttribute("code",ComparisonOperator.EQ, tuple)
					).execute().getOutput());
	}
	
	@Test
	public void execute_predicateWithThreeChildWhichArePredicateANDPredicate_predicateAndPredicate(){
		assertionHelper.assertEquals("tuple.code=@code AND tuple.name=@name", 
				__inject__(QueryWherePredicateStringBuilderEqual.class).addChild(
						__inject__(QueryWherePredicateStringBuilderEqual.class).addOperandBuilderByAttribute("code",ComparisonOperator.EQ, tuple)
						,LogicalOperator.AND
						,__inject__(QueryWherePredicateStringBuilderEqual.class).addOperandBuilderByAttribute("name",ComparisonOperator.EQ, tuple)
					).execute().getOutput());
	}
	
	@Test
	public void execute_predicateWithThreeChildWhichAreNestedPredicateANDPredicate_nestedPredicateWithParenthesisAndPredicate(){
		assertionHelper.assertEquals("(tuple.code=@code OR tuple.firstname=@firstname) AND tuple.lastname=@lastname", 
				__inject__(QueryWherePredicateStringBuilderEqual.class).addChild(
						__inject__(QueryWherePredicateStringBuilderEqual.class)
						.surroundedWithParentheses()
						.addChild(__inject__(QueryWherePredicateStringBuilderEqual.class).addOperandBuilderByAttribute("code",ComparisonOperator.EQ, tuple))
						.or()
						.addChild(__inject__(QueryWherePredicateStringBuilderEqual.class).addOperandBuilderByAttribute("firstname",ComparisonOperator.EQ, tuple))
						
						,LogicalOperator.AND
						,__inject__(QueryWherePredicateStringBuilderEqual.class).addOperandBuilderByAttribute("lastname",ComparisonOperator.EQ, tuple)
					).execute().getOutput());
	}
	
	@Test
	public void isColumn1Equal1ByOperands(){
		Tuple tuple = new Tuple().setName("Tuple").addAttributes(new Attribute().setName("code"));
		QueryOperandStringBuilder operand1 = __inject__(QueryOperandStringBuilder.class).setAttributeNameBuilder("code",tuple);
		QueryOperandStringBuilder operand2 = __inject__(QueryOperandStringBuilder.class).setParameterNameBuilder("myparam");
		assertionHelper.assertEquals("tuple.code=@myparam", __inject__(QueryWherePredicateStringBuilderEqual.class).addFormatArgumentObjects(operand1,"=",operand2)
				.execute().getOutput());
	}
	
	@Test
	public void isColumn1Equal1ByOperandsShortcut(){
		Tuple tuple = new Tuple().setName("Tuple").addAttributes(new Attribute().setName("code"));
		assertionHelper.assertEquals("tuple.code=@myparam", __inject__(QueryWherePredicateStringBuilderEqual.class)
				.addOperandBuilderByAttributeByParameter("code",ComparisonOperator.EQ, tuple,"myparam").execute().getOutput());
	}
	
	@Test
	public void buildByCriteriaNotDerived(){
		Criteria criteria = __inject__(Criteria.class).setClassName("Tuple").setFieldNameAsString("code");
		assertionHelper.assertEquals("tuple.code=@code", __inject__(QueryWherePredicateStringBuilderEqual.class)
				.setCriteria(criteria).execute().getOutput());
	}
	
	@Test
	public void buildByCriteriaDerived(){
		Criteria criteria = __inject__(Criteria.class).setClazz(MyTuple.class);
		assertionHelper.assertEquals("myTuple.code=@code", __inject__(QueryWherePredicateStringBuilderEqual.class)
				.setCriteria(criteria).execute().getOutput());
	}
	
	@Test
	public void buildByCriteriaWithSystemIdentifierFieldName(){
		Criteria criteria = __inject__(Criteria.class).setClazz(MyTuple.class).setFieldValueUsageType(ValueUsageType.SYSTEM);
		assertionHelper.assertEquals("myTuple.identifier=@identifier", __inject__(QueryWherePredicateStringBuilderEqual.class)
				.setCriteria(criteria).execute().getOutput());
	}
	
	@Test
	public void buildByCriteriaWithSystemIdentifierFieldNameDerived(){
		Criteria criteria = __inject__(Criteria.class).setClazz(MyTuple.class).setFieldValueUsageType(ValueUsageType.SYSTEM);
		assertionHelper.assertEquals("myTuple.identifier=@identifier", __inject__(QueryWherePredicateStringBuilderEqual.class)
				.setCriteria(criteria).execute().getOutput());
	}
	
	/*@Test
	public void buildByCriteriaWithSystemIdentifierFieldNameDerived02(){
		Criteria criteria01 = __inject__(Criteria.class).setClazz(MyTuple.class).setFieldValueUsageType(ValueUsageType.SYSTEM);
		Criteria criteria02 = __inject__(Criteria.class).setClazz(MyTuple.class).setFieldValueUsageType(ValueUsageType.BUSINESS);
		Criteria criteria = __inject__(Criteria.class).addChild(criteria01,LogicalOperator.AND,criteria02);
		assertionHelper.assertEquals("myTuple.identifier=@identifier", __inject__(QueryWherePredicateStringBuilderEqual.class)
				.setCriteria(criteria).execute().getOutput());
	}*/
	
	@Test
	public void isColumn1Equala(){
		assertionHelper.assertEquals("column1=a", __inject__(QueryWherePredicateStringBuilderEqual.class).addFormatArgumentObjects("column1","=","a").execute().getOutput());
	}
	
	@Test
	public void isColumn1EqualParameterMarker(){
		assertionHelper.assertEquals("column1=?", __inject__(QueryWherePredicateStringBuilderEqual.class).addFormatArgumentObjects("column1","=","?").execute().getOutput());
	}
	
	@Test
	public void isColumn1Equal1AndColumn2Equal2(){
		QueryWherePredicateStringBuilder predicate1 = (QueryWherePredicateStringBuilder) __inject__(QueryWherePredicateStringBuilderEqual.class).addFormatArgumentObjects("column1","=","1");
		QueryWherePredicateStringBuilder predicate2 = (QueryWherePredicateStringBuilder) __inject__(QueryWherePredicateStringBuilderEqual.class).addFormatArgumentObjects("column2","=","2");
		assertionHelper.assertEquals("column1=1 AND column2=2", __inject__(QueryWherePredicateStringBuilderEqual.class)
				.addChild(predicate1,LogicalOperator.AND,predicate2).execute().getOutput());
	}
}
