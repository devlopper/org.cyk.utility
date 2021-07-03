package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.server.query.string.LikeStringBuilder;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class LikeStringBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void like(){
		assertThat(LikeStringBuilder.getInstance().build("t","name","name")).isEqualTo("LOWER(t.name) LIKE LOWER(:name)");
	}
	
	@Test
	public void like_1(){
		assertThat(LikeStringBuilder.getInstance().build("t","name","name",1)).isEqualTo("(LOWER(t.name) LIKE LOWER(:name) OR LOWER(t.name) LIKE LOWER(:name0))");
	}
	
	@Test
	public void like_2(){
		assertThat(LikeStringBuilder.getInstance().build("t","name","name",2)).isEqualTo("(LOWER(t.name) LIKE LOWER(:name) OR (LOWER(t.name) LIKE LOWER(:name0) AND LOWER(t.name) LIKE LOWER(:name1)))");
	}
	
	@Test
	public void like_negate(){
		assertThat(LikeStringBuilder.getInstance().build(new LikeStringBuilder.Arguments().setTupleName("t").setFieldName("name").setParameterName("name")
				.setNegate(Boolean.TRUE))).isEqualTo("LOWER(t.name) NOT LIKE LOWER(:name)");
	}
}