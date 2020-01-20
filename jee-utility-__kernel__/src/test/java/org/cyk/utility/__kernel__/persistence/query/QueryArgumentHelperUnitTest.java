package org.cyk.utility.__kernel__.persistence.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class QueryArgumentHelperUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void getLikes_null_null(){
		assertThat(QueryArgumentHelper.getLikes(null, null)).containsExactly("%%");
	}

	@Test
	public void getLikes_null_2(){
		assertThat(QueryArgumentHelper.getLikes(null, 2)).containsExactly("%%","%%");
	}
	
	@Test
	public void getLikes_abc_null(){
		assertThat(QueryArgumentHelper.getLikes("abc", null)).containsExactly("%abc%");
	}
	
	@Test
	public void getLikes_abc_3(){
		assertThat(QueryArgumentHelper.getLikes("abc", 3)).containsExactly("%abc%","%abc%","%%");
	}

	@Test
	public void getLikes_abc_123_null(){
		assertThat(QueryArgumentHelper.getLikes("abc 123", null)).containsExactly("%abc 123%");
	}
	
	@Test
	public void getLikes_abc_123_1(){
		assertThat(QueryArgumentHelper.getLikes("abc 123", 1)).containsExactly("%abc 123%");
	}
	
	@Test
	public void getLikes_abc_123_2(){
		assertThat(QueryArgumentHelper.getLikes("abc 123", 2)).containsExactly("%abc 123%","%abc%");
	}
	
	@Test
	public void getLikes_abc_123_3(){
		assertThat(QueryArgumentHelper.getLikes("abc 123", 3)).containsExactly("%abc 123%","%abc%","%123%");
	}
	
	@Test
	public void getLikes_abc_123_4(){
		assertThat(QueryArgumentHelper.getLikes("abc 123", 4)).containsExactly("%abc 123%","%abc%","%123%","%%");
	}
	
	@Test
	public void getLikes_abc_123_5(){
		assertThat(QueryArgumentHelper.getLikes("abc 123", 5)).containsExactly("%abc 123%","%abc%","%123%","%%","%%");
	}
	
	@Test
	public void getLikes_abc_123_xyz_456_2(){
		assertThat(QueryArgumentHelper.getLikes("abc 123 xyz 456", 2)).containsExactly("%abc 123 xyz 456%","%abc%");
	}
}
