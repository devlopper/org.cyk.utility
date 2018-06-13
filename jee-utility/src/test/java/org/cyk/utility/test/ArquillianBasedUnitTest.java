package org.cyk.utility.test;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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
	
	/* Deployment*/
	
	@Deployment
	public static JavaArchive createDeployment() {
		return AbstractArquillianUnitTest.createDeployment().addClass(MyClass01.class);
	}
	
}
