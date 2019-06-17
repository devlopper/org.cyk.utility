package org.cyk.utility.sql.builder;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.computation.SortOrder;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class QueryStringBuilderSelectUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void select(){
		Tuple tuple = new Tuple().setName("Tuple");
	
		QueryStringBuilderSelect queryBuilder = __inject__(QueryStringBuilderSelect.class).select(tuple);
		
		assertionHelper.assertEquals("SELECT *", queryBuilder.execute().getOutput());
	}
	
	@Test
	public void selectAllAttributeFromTuple(){
		Tuple tuple = new Tuple().setName("Tuple");
	
		QueryStringBuilderSelect queryBuilder = __inject__(QueryStringBuilderSelect.class).from(tuple);
		
		assertionHelper.assertEquals("SELECT * FROM Tuple tuple", queryBuilder.execute().getOutput());
	}
	
	@Test
	public void selectAllAttributeFromTupleWhereColumn1Equal1(){
		Tuple tuple = new Tuple().setName("Tuple");
		QueryWherePredicateStringBuilder predicateBuilder = (QueryWherePredicateStringBuilder) __inject__(QueryWherePredicateStringBuilderEqual.class)
				.addOperandBuilderByAttributeByParameter("column1",ComparisonOperator.EQ, tuple,"myparam");
		
		QueryStringBuilderSelect queryBuilder = __inject__(QueryStringBuilderSelect.class).from(tuple).where(predicateBuilder);
		
		assertionHelper.assertEquals("SELECT * FROM Tuple tuple WHERE tuple.column1=@myparam", queryBuilder.execute().getOutput());
	}
	
	@Test
	public void selectAllAttributeFromTupleWhereColumn1Equal1OrderByIndexAsc(){
		Tuple tuple = new Tuple().setName("Tuple");
		QueryWherePredicateStringBuilder predicateBuilder = (QueryWherePredicateStringBuilder) __inject__(QueryWherePredicateStringBuilderEqual.class)
				.addOperandBuilderByAttributeByParameter("column1",ComparisonOperator.EQ, tuple,"myparam");
		
		QueryStringBuilderSelect queryBuilder = __inject__(QueryStringBuilderSelect.class).from(tuple).where(predicateBuilder);
		queryBuilder.orderBy("index",SortOrder.ASCENDING);
		
		assertionHelper.assertEquals("SELECT * FROM Tuple tuple WHERE tuple.column1=@myparam ORDER BY tuple.index ASC", queryBuilder.execute().getOutput());
	}
	
	@Test
	public void selectAllAttributeFromTupleWhereColumn1Equal1OrderByIndexDesc(){
		Tuple tuple = new Tuple().setName("Tuple");
		QueryWherePredicateStringBuilder predicateBuilder = (QueryWherePredicateStringBuilder) __inject__(QueryWherePredicateStringBuilderEqual.class)
				.addOperandBuilderByAttributeByParameter("column1",ComparisonOperator.EQ, tuple,"myparam");
		
		QueryStringBuilderSelect queryBuilder = __inject__(QueryStringBuilderSelect.class).from(tuple).where(predicateBuilder);
		queryBuilder.orderBy("index",SortOrder.DESCENDING);
		
		assertionHelper.assertEquals("SELECT * FROM Tuple tuple WHERE tuple.column1=@myparam ORDER BY tuple.index DESC", queryBuilder.execute().getOutput());
	}
	
	@Test
	public void selectAllAttributeFromTupleWhereColumn1Equal1OrderByIndexDescIndex2Asc(){
		Tuple tuple = new Tuple().setName("Tuple");
		QueryWherePredicateStringBuilder predicateBuilder = (QueryWherePredicateStringBuilder) __inject__(QueryWherePredicateStringBuilderEqual.class)
				.addOperandBuilderByAttributeByParameter("column1",ComparisonOperator.EQ, tuple,"myparam");
		
		QueryStringBuilderSelect queryBuilder = __inject__(QueryStringBuilderSelect.class).from(tuple).where(predicateBuilder);
		queryBuilder.orderBy("index",SortOrder.DESCENDING);
		queryBuilder.orderBy("index2",SortOrder.ASCENDING);
		
		assertionHelper.assertEquals("SELECT * FROM Tuple tuple WHERE tuple.column1=@myparam ORDER BY tuple.index DESC,tuple.index2 ASC", queryBuilder.execute().getOutput());
	}
}
