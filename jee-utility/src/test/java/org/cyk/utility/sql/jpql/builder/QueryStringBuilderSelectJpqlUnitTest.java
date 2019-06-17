package org.cyk.utility.sql.jpql.builder;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.sql.builder.QueryStringBuilderSelect;
import org.cyk.utility.sql.builder.Tuple;
import org.cyk.utility.sql.jpql.JpqlQualifier;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class QueryStringBuilderSelectJpqlUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void selectFromTuple(){
		Tuple tuple = new Tuple().setName("Tuple");
		QueryStringBuilderSelectJpql queryBuilder = JpqlQualifier.inject(QueryStringBuilderSelectJpql.class).from(tuple);
		assertionHelper.assertEquals("SELECT tuple FROM Tuple tuple", queryBuilder.execute().getOutput());
	}
	
	@Test
	public void selectFromTupleOrderBy(){
		Tuple tuple = new Tuple().setName("Tuple");
		QueryStringBuilderSelectJpql queryBuilder = JpqlQualifier.inject(QueryStringBuilderSelectJpql.class).from(tuple).orderBy("code","name");
		assertionHelper.assertEquals("SELECT tuple FROM Tuple tuple ORDER BY tuple.code ASC,tuple.name ASC", queryBuilder.execute().getOutput());
	}
	
	@Test
	public void selectFromTupleWhereColumn1Equal1(){
		Tuple tuple = new Tuple().setName("Tuple");
		
		QueryStringBuilderSelect queryBuilder = JpqlQualifier.inject(QueryStringBuilderSelectJpql.class)
				.from(tuple).getWherePredicateBuilderAsEqual().addOperandBuilderByAttribute("code",ComparisonOperator.EQ)
				.getParentAsWhereClause().getParentAs(QueryStringBuilderSelect.class);
		
		assertionHelper.assertEquals("SELECT tuple FROM Tuple tuple WHERE tuple.code=:code", queryBuilder.execute().getOutput());
	}
	
	@Test
	public void selectFromTupleWhereP1AndP2(){
		QueryStringBuilderSelect queryBuilder = JpqlQualifier.inject(QueryStringBuilderSelectJpql.class)
				.from(new Tuple().setName("Tuple")).getWherePredicateBuilderAsGroup().addOperandBuilderByAttribute("a1",ComparisonOperator.EQ).and()
				.addOperandBuilderByAttribute("a2",ComparisonOperator.EQ)
				.getParentAsWhereClause().getParentAs(QueryStringBuilderSelect.class);
		
		assertionHelper.assertEquals("SELECT tuple FROM Tuple tuple WHERE tuple.a1=:a1 AND tuple.a2=:a2", queryBuilder.execute().getOutput());
	}
	
}
