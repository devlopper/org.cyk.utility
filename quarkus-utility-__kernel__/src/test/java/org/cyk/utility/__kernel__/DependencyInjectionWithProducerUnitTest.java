package org.cyk.utility.__kernel__;

import static org.assertj.core.api.Assertions.assertThat;

import javax.enterprise.inject.Produces;

import org.cyk.utility.__kernel__.annotation.Default;
import org.junit.Test;

public class DependencyInjectionWithProducerUnitTest extends AbstractDependencyInjectionUnitTesting {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void isClass01SecondVersionWhenClass01FirstVersionInjected() {
		assertThat(__inject__(Class01.class).getClass()).isEqualTo(Class01SecondVersion.class);
	}
	
	@Test
	public void isClass02FirstVersionWhenClass02FirstVersionInjected() {
		assertThat(__inject__(Class02.class).getClass()).isEqualTo(Class02FirstVersion.class);
	}
	
	@Test
	public void isV2ImplWhenVInjected() {
		assertThat(__inject__(V.class).getClass()).isEqualTo(V2Impl.class);
	}
	
	@Test
	public void isT2ImplWhenTInjected() {
		assertThat(__inject__(T.class).getClass()).isEqualTo(T2Impl.class);
	}
	
	@Test
	public void isMyDefaultClassWhenMyClassInterfaceInjected() {
		DependencyInjection.setQualifierClass(MyClassInterface.class, null);
		assertThat(__inject__(MyClassInterface.class).getClass()).isEqualTo(MyDefaultClass.class);
	}
	
	@Test
	public void isMyCustomDefaultClassWhenMyClassInterfaceInjectedAndQualifierSet() {
		DependencyInjection.setQualifierClass(MyClassInterface.class, Default.class);
		assertThat(__inject__(MyClassInterface.class).getClass()).isEqualTo(MyCustomDefaultClass.class);
		DependencyInjection.setQualifierClass(MyClassInterface.class, null);
	}
	
	public static class Producer {
		
		@Produces
		public Class01 getClass01() {
			return new Class01SecondVersion();
		}
		
		@Produces
		public V getV() {
			return DependencyInjection.injectByQualifiersClasses(V.class, V2.Qualifier.class);
		}
		
		@Produces
		public T getT() {
			return DependencyInjection.injectByQualifiersClasses(T.class, T2.class);
		}
		
	}
	
}
