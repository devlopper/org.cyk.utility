package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.computation.LogicalOperator;
import org.cyk.utility.__kernel__.persistence.query.Language.Argument;
import org.cyk.utility.__kernel__.persistence.query.Language.Order;
import org.cyk.utility.__kernel__.persistence.query.Language.Select;
import org.cyk.utility.__kernel__.persistence.query.Language.Where;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;
public class LanguageUnitTest extends AbstractWeldUnitTest {

	@Test
	public void formatVariable(){
		assertThat(Language.formatVariable("p")).isEqualTo("p");
	}
	
	@Test
	public void formatParameter(){
		assertThat(Language.formatParameter("p")).isEqualTo(":p");
	}
	
	@Test
	public void select_of(){
		assertThat(Select.of("p1")).isEqualTo("SELECT p1");
		assertThat(Select.of("p1","p2")).isEqualTo("SELECT p1,p2");		
	}
	
	@Test
	public void where_join(){
		assertThat(Where.and("p1")).isEqualTo("p1");
		assertThat(Where.and("p1","p2")).isEqualTo("p1 AND p2");
		assertThat(Where.or("p1","p2")).isEqualTo("p1 OR p2");
		assertThat(Where.or("","p1","","","p2","")).isEqualTo("p1 OR p2");
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
	public void where_like_f1_like_p(){
		assertThat(Where.like("t","f1","p")).isEqualTo("LOWER(t.f1) LIKE LOWER(:p)");
	}
	
	@Test
	public void where_like_f1_like_p_or_f1_like_p0(){
		assertThat(Where.like("t","f1","p",LogicalOperator.OR,1)).isEqualTo("(LOWER(t.f1) LIKE LOWER(:p) OR LOWER(t.f1) LIKE LOWER(:p0))");
	}
	
	@Test
	public void where_like_f1_like_p0_and_f1_like_p1(){
		assertThat(Where.like("t","f1","p",LogicalOperator.AND,1)).isEqualTo("(LOWER(t.f1) LIKE LOWER(:p) AND LOWER(t.f1) LIKE LOWER(:p0))");
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
	
	/**/
	
	@Test
	public void argument_like_startsWith(){
		assertThat(Argument.Like.startsWith(null)).isEqualTo("%");
		assertThat(Argument.Like.startsWith("")).isEqualTo("%");
		assertThat(Argument.Like.startsWith(" ")).isEqualTo(" %");
		assertThat(Argument.Like.startsWith("a")).isEqualTo("a%");
	}
	
	@Test
	public void argument_like_endsWith(){
		assertThat(Argument.Like.endsWith("a")).isEqualTo("%a");
	}
	
	@Test
	public void argument_like_contains(){
		assertThat(Argument.Like.contains("a")).isEqualTo("%a%");
	}
	
	@Test
	public void argument_like_containsWords(){
		assertThat(Argument.Like.containsWords("a",null)).isEqualTo(null);
		assertThat(Argument.Like.containsWords(null,3)).containsExactly("%%","%%","%%");
		assertThat(Argument.Like.containsWords("a",1)).containsExactly("%a%");
		assertThat(Argument.Like.containsWords("a",2)).containsExactly("%a%","%%");
		assertThat(Argument.Like.containsWords("a",3)).containsExactly("%a%","%%","%%");
		
		assertThat(Argument.Like.containsWords("a b",1)).containsExactly("%a%");
		assertThat(Argument.Like.containsWords("a b",2)).containsExactly("%a%","%b%");
		assertThat(Argument.Like.containsWords("a b",3)).containsExactly("%a%","%b%","%%");
		assertThat(Argument.Like.containsWords("a b",4)).containsExactly("%a%","%b%","%%","%%");
	}
	
	@Test
	public void argument_like_containsStringOrWords(){
		assertThat(Argument.Like.containsStringOrWords("a",null)).isEqualTo(null);
		assertThat(Argument.Like.containsStringOrWords(null,3)).containsExactly("%%","%%","%%","%%");
		assertThat(Argument.Like.containsStringOrWords("",3)).containsExactly("%%","%%","%%","%%");
		assertThat(Argument.Like.containsStringOrWords("a",1)).containsExactly("%a%","%a%");
		assertThat(Argument.Like.containsStringOrWords("a",2)).containsExactly("%a%","%a%","%%");
		assertThat(Argument.Like.containsStringOrWords("a",3)).containsExactly("%a%","%a%","%%","%%");
		
		assertThat(Argument.Like.containsStringOrWords("a b",1)).containsExactly("%a b%","%a%");
		assertThat(Argument.Like.containsStringOrWords("a b",2)).containsExactly("%a b%","%a%","%b%");
		assertThat(Argument.Like.containsStringOrWords("a b",3)).containsExactly("%a b%","%a%","%b%","%%");
		assertThat(Argument.Like.containsStringOrWords("a b",4)).containsExactly("%a b%","%a%","%b%","%%","%%");
	}
}