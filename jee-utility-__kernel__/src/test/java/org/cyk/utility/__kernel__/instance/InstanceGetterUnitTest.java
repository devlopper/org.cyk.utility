package org.cyk.utility.__kernel__.instance;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class InstanceGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		InstanceGetterImpl.clear();
	}
	
	@Test
	public void getBySystemIdentifier_(){
		InstanceGetterImpl.add(new Klass().setIdentifier("123").setCode("abc"));
		Klass instance = InstanceHelper.getBySystemIdentifier(Klass.class, "123");
		assertThat(instance.getIdentifier()).isEqualTo("123");
		assertThat(instance.getCode()).isEqualTo("abc");
	}
	
	@Test
	public void getByBusinessIdentifier_(){
		InstanceGetterImpl.add(new Klass().setIdentifier("123").setCode("abc"));
		Klass instance = InstanceHelper.getByBusinessIdentifier(Klass.class, "abc");
		assertThat(instance.getIdentifier()).isEqualTo("123");
		assertThat(instance.getCode()).isEqualTo("abc");
	}
	
	/**/
	
	/* Persistence */
	
	@Getter @Setter @Accessors(chain=true)
	public static class Klass {		
		private String identifier,code;
	}
	
}
