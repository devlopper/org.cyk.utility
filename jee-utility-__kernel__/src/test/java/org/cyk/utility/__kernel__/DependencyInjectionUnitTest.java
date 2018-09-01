package org.cyk.utility.__kernel__;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.test.arquillian.AbstractArquillianUnitTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class DependencyInjectionUnitTest extends AbstractDependencyInjectionUnitTesting {
	private static final long serialVersionUID = 1L;

	@Test
	public void isClass01FirstVersionWhenClass01FirstVersionInjected() {
		assertThat(__inject__(Class01.class).getClass()).isEqualTo(Class01FirstVersion.class);
	}
		
	/* Deployment*/
	
	@Deployment
	public static JavaArchive createDeployment() {
		return AbstractArquillianUnitTest.createJavaArchiveDeployment();
	}
	
}
