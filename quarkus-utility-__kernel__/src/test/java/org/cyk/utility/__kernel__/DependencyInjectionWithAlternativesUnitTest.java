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
	
}
