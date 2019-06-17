package org.cyk.utility.__kernel__;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class DependencyInjectionUnitTest extends AbstractDependencyInjectionUnitTesting {
	private static final long serialVersionUID = 1L;

	@Test
	public void isClass01FirstVersionWhenClass01FirstVersionInjected() {
		assertThat(__inject__(Class01.class).getClass()).isEqualTo(Class01FirstVersion.class);
	}
	
	//@Test
	public void isMyCustomDefaultClassWhenMyClassInterfaceInjectedAndQualifierSet() {
		assertThat(__inject__(MyInterfaceWithPriority.class).getClass()).isEqualTo(MyInterfaceImplWithPriority02.class);
	}
	
}
