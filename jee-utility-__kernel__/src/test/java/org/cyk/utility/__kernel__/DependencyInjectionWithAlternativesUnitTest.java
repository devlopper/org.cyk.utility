package org.cyk.utility.__kernel__;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class DependencyInjectionWithAlternativesUnitTest extends AbstractDependencyInjectionUnitTesting {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void isClass01SecondVersionWhenClass01FirstVersionInjected() {
		assertThat(__inject__(Class01.class).getClass()).isEqualTo(Class01SecondVersion.class);
	}
	
	@Test
	public void isClass02FirstVersionWhenClass02FirstVersionInjected() {
		assertThat(__inject__(Class02.class).getClass()).isEqualTo(Class02FirstVersion.class);
	}
	
	/* Deployment*/
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static org.jboss.shrinkwrap.api.spec.JavaArchive createDeployment() {
		return new org.cyk.utility.__kernel__.test.arquillian.archive.builder.JavaArchiveBuilder()
				.setBeanXml("org/cyk/utility/__kernel__/beans-with-alternatices.xml")
				.addClass(Class01.class)
				.addClass(Class02.class)
				.addClass(Class01SecondVersion.class)
				.addClass(Class02FirstVersion.class)
				.addClass(MySingleton.class)
				.execute();
	}
	
}
