package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.persistence.query.Language.Order;
import org.cyk.utility.__kernel__.persistence.query.Language.Where;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;
public class LanguageUnitTest extends AbstractWeldUnitTest {

	@Test
	public void where_join(){
		assertThat(Where.and("p1")).isEqualTo("p1");
		assertThat(Where.and("p1","p2")).isEqualTo("p1 AND p2");
		assertThat(Where.or("p1","p2")).isEqualTo("p1 OR p2");
	}
	
	@Test
	public void where_equals(){
		assertThat(Where.equals("t","a1","p")).isEqualTo("t.a1 = :p");
	}
	
	@Test
	public void where_in(){
		assertThat(Where.in("t","a1","p")).isEqualTo("t.a1 IN :p");
	}
	
	@Test
	public void order_asc(){
		assertThat(Order.ascending("t","a1")).isEqualTo("t.a1 ASC");
	}
	
	@Test
	public void order_desc(){
		assertThat(Order.descending("t","a1")).isEqualTo("t.a1 DESC");
	}
	
	@Test
	public void order_join(){
		assertThat(Order.join(Order.ascending("t","a1"),Order.descending("t","a1"))).isEqualTo("t.a1 ASC,t.a1 DESC");
	}
}
