package org.cyk.utility.__kernel__;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class AbstractDependencyInjectionUnitTesting extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void injectNullIsNull(){
		Assertions.assertThrows(RuntimeException.class, () -> {
			__inject__(null);
		  });
	}
	
	@Test
	public void injectMyClassIsNotNull(){
		org.assertj.core.api.Assertions.assertThat(__inject__(Class01.class)).isNotNull();
	}
	
	@Test
	public void injectManySingletonInstanceAreSame(){
		MySingleton singleton = __inject__(MySingleton.class);
		for(Integer index = 0; index < 500; index++)
			org.assertj.core.api.Assertions.assertThat(__inject__(MySingleton.class)).isEqualTo(singleton);
	}
	
}
