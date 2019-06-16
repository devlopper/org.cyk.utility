package org.cyk.utility.__kernel__;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class DependencyInjectionUnitTest extends AbstractDependencyInjectionUnitTesting {
	private static final long serialVersionUID = 1L;

	@Inject private Class01 c01;
	
	//@Test
	public void c01Injected() {
		System.out.println("DependencyInjectionUnitTest.c01Injected() ::: "+c01);
	}
	
	@Test
	public void isClass01FirstVersionWhenClass01FirstVersionInjected() {
		assertThat(__inject__(Class01.class).getClass()).isEqualTo(Class01FirstVersion.class);
	}
	
	//@Test
	public void isMyCustomDefaultClassWhenMyClassInterfaceInjectedAndQualifierSet() {
		assertThat(__inject__(MyInterfaceWithPriority.class).getClass()).isEqualTo(MyInterfaceImplWithPriority02.class);
	}
	
}
