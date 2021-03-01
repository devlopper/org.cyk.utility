package org.cyk.utility.__kernel__;

import javax.inject.Inject;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class WeldUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Inject private MyClass01 myClass01;

	@Test
	public void isMyClass01AutomaticallyInjected() {
		org.assertj.core.api.Assertions.assertThat(myClass01).isNotNull();
	}
	
	@Test
	public void isMyClass01ProgramaticallyInjected(){
		org.assertj.core.api.Assertions.assertThat(__inject__(MyClass01.class)).isNotNull();
	}
	
}
