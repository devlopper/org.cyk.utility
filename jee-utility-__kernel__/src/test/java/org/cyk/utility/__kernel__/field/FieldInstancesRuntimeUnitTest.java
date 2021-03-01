package org.cyk.utility.__kernel__.field;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class FieldInstancesRuntimeUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		FieldHelper.CLASSES_FIELDNAMES_MAP.clear();
	}
	
	@Test
	public void get_identifier(){
		FieldInstance fieldInstance = __inject__(FieldInstancesRuntime.class).get(Class.class, "identifier");
		assertThat(fieldInstance).isNotNull();
	}	
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Class implements Serializable {
		private static final long serialVersionUID = 1L;
		private String identifier;
	}
}
