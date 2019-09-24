package org.cyk.utility.__kernel__;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.builder.ToStringBuilder;
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
	
	//@Test
	public void injectBean02() {
		Bean02 bean02 = __inject__(Bean02.class);
		assertThat(bean02).isNotNull();
		assertThat(bean02.isBean01Null()).isTrue();
	}
	
	/**/
	
	public static class Bean01 {
		
	}
	
	public static class Bean02 {
		
		@Inject private Bean01 bean01;
		
		@PostConstruct
		public void postConstruct() {
			System.out.println("DependencyInjectionUnitTest.Bean02.postConstruct()");
			System.out.println(ToStringBuilder.reflectionToString(this));
		}
		
		/*public Bean01 getBean01() {
			return bean01;
		}
		
		public void setBean01(Bean01 bean01) {
			this.bean01 = bean01;
		}*/
		
		public Boolean isBean01Null() {
			return bean01 == null;
		}
	}
}
