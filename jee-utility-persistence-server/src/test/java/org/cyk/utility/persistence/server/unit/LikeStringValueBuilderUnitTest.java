package org.cyk.utility.persistence.server.unit;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.persistence.server.query.string.LikeStringValueBuilder;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class LikeStringValueBuilderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void like(){
		assertThat(LikeStringValueBuilder.getInstance().build("v",null,null)).isEqualTo("%v%");
		assertThat(LikeStringValueBuilder.getInstance().build("v",null,false)).isEqualTo("%v");
		assertThat(LikeStringValueBuilder.getInstance().build("v",null,true)).isEqualTo("%v%");
		assertThat(LikeStringValueBuilder.getInstance().build("v",false,false)).isEqualTo("v");
		assertThat(LikeStringValueBuilder.getInstance().build("v",false,true)).isEqualTo("v%");
		assertThat(LikeStringValueBuilder.getInstance().build("v",true,false)).isEqualTo("%v");
		assertThat(LikeStringValueBuilder.getInstance().build("v",true,true)).isEqualTo("%v%");
	}
}