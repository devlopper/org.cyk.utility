package org.cyk.utility.__kernel__.method;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class MethodHelperUnitTest extends AbstractWeldUnitTest{

	@Test
	public void executeStatic(){
		assertThat(MethodHelper.executeStatic(AClass.class, "sayHi")).isEqualTo("hi!");
	}
	
	/**/
	
	public static class AClass {
		public static String sayHi() {
			return "hi!";
		}
	}
}
