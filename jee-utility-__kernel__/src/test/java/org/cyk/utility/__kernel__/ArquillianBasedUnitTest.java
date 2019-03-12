package org.cyk.utility.__kernel__;

import javax.inject.Inject;

import org.cyk.utility.__kernel__.test.arquillian.AbstractArquillianUnitTest;
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
	
	/* Deployments */
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static org.jboss.shrinkwrap.api.spec.JavaArchive createDeployment() {
		return new org.cyk.utility.__kernel__.test.arquillian.archive.builder.JavaArchiveBuilder()
				.addPackage("org.cyk.utility.__kernel__")
				.execute();
	}
}
