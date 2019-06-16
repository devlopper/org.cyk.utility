package org.cyk.utility.__kernel__;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;

public class ArquillianBasedUnitTest extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	@Inject private MyClass01 myClass01;

	@Test
	public void isMyClass01AutomaticallyInjected() {
		Assert.assertNotNull(myClass01);
	}
	
	@Test
	public void isMyClass01ProgramaticallyInjected(){
		Assert.assertNotNull(__inject__(MyClass01.class));
	}
	
}
