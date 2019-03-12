package org.cyk.utility.__kernel__;

import static org.assertj.core.api.Assertions.assertThat;

import javax.enterprise.inject.Produces;

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
	
	/* Deployment*/
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static org.jboss.shrinkwrap.api.spec.JavaArchive createDeployment() {
		return new org.cyk.utility.__kernel__.test.arquillian.archive.builder.JavaArchiveBuilder()
				.addClass(Producer.class)
				.addClass(Class01.class)
				.addClass(Class02.class)
				.addClass(Class01SecondVersion.class)
				.addClass(Class02FirstVersion.class)
				.addClass(MySingleton.class)
				.addClass(V.class)
				.addClass(V1.class)
				.addClass(V1Impl.class)
				.addClass(V2.class)
				.addClass(V2Impl.class)
				.addClass(T.class)
				.addClass(T1.class)
				.addClass(T1Impl.class)
				.addClass(T2.class)
				.addClass(T2Impl.class)
				.execute();
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
