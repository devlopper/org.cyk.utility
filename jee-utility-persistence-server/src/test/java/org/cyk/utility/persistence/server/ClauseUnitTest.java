package org.cyk.utility.persistence.server;

import static org.assertj.core.api.Assertions.assertThat;
import org.cyk.utility.persistence.server.query.Clause;
import org.cyk.utility.test.AbstractUnitTest;
import org.junit.jupiter.api.Test;

public class ClauseUnitTest extends AbstractUnitTest {

	@Test
	public void select() {
		assertThat(Clause.SELECT.getValue()).isEqualTo("SELECT"); 
	}
	
	@Test
	public void from() {
		assertThat(Clause.FROM.getValue()).isEqualTo("FROM"); 
	}
	
	@Test
	public void where() {
		assertThat(Clause.WHERE.getValue()).isEqualTo("WHERE"); 
	}
	
	@Test
	public void orderBy_value() {
		assertThat(Clause.ORDER_BY.getValue()).isEqualTo("ORDER BY"); 
	}
	
	@Test
	public void orderBy_format() {
		assertThat(Clause.ORDER_BY.format("code ASC")).isEqualTo("ORDER BY code ASC"); 
	}
	
	@Test
	public void having() {
		assertThat(Clause.HAVING.getValue()).isEqualTo("HAVING"); 
	}
}
