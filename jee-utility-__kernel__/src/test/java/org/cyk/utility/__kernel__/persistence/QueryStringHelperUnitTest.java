package org.cyk.utility.__kernel__.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class QueryStringHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void formatTupleFieldLike(){
		assertThat(QueryStringHelper.formatTupleFieldLike("t", "name", "query", null, null)).isEqualTo("LOWER(t.name) LIKE LOWER(:query)");
	}
	
	@Test
	public void formatTupleFieldLike_many_and(){
		assertThat(QueryStringHelper.formatTupleFieldLike("t", "name", "query", 2, LogicalOperator.AND)).isEqualTo("LOWER(t.name) LIKE LOWER(:query1) AND LOWER(t.name) LIKE LOWER(:query2)");
	}
	
	@Test
	public void formatTupleFieldLike_many_or(){
		assertThat(QueryStringHelper.formatTupleFieldLike("t", "name", "query", 2, LogicalOperator.OR)).isEqualTo("LOWER(t.name) LIKE LOWER(:query1) OR LOWER(t.name) LIKE LOWER(:query2)");
	}
}
