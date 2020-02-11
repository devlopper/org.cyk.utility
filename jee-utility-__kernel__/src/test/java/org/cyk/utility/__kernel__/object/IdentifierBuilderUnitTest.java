package org.cyk.utility.__kernel__.object;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class IdentifierBuilderUnitTest extends AbstractWeldUnitTest {

	@Test
	public void build_runtime() {
		assertThat(IdentifierBuilder.getInstance().build(new MyObject("1"), IdentifierBuilder.Type.RUNTIME)).isEqualTo("1");
	}
	
	/**/
	
	@Getter @Setter @AllArgsConstructor
	public static class MyObject extends AbstractObject implements Serializable {
		
		private String f;
		
		@Override
		public String toString() {
			return f;
		}
		
	}

	
	
}
