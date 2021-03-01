package org.cyk.utility.client.controller.component.output;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class OutputStringTextUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void isStyleNull() {
		OutputStringTextBuilder outputStringTextBuilder = __inject__(OutputStringTextBuilder.class);
		OutputStringText outputStringText = outputStringTextBuilder.execute().getOutput();
		//assertionHelper.assertNull(outputStringText.getStyle());
	}
	
	@Test
	public void isStyleNotNull() {
		OutputStringTextBuilder outputStringTextBuilder = __inject__(OutputStringTextBuilder.class);
		outputStringTextBuilder.addStyleClasses("c1");
		OutputStringText outputStringText = outputStringTextBuilder.execute().getOutput();
		//assertionHelper.assertNotNull(outputStringText.getStyle());
	}
	
	@Test
	public void isStyleClassEqualsC1() {
		OutputStringTextBuilder outputStringTextBuilder = __inject__(OutputStringTextBuilder.class);
		outputStringTextBuilder.addStyleClasses("c1");
		OutputStringText outputStringText = outputStringTextBuilder.execute().getOutput();
		//assertionHelper.assertNotNull(outputStringText.getStyle());
		//assertionHelper.assertEquals("c1",outputStringText.getStyle().getClassesAsString());
	}
	
	@Test
	public void isStyleValueEqualsC1() {
		OutputStringTextBuilder outputStringTextBuilder = __inject__(OutputStringTextBuilder.class);
		outputStringTextBuilder.addStyleValues("c1");
		OutputStringText outputStringText = outputStringTextBuilder.execute().getOutput();
		//assertionHelper.assertNotNull(outputStringText.getStyle());
		//assertionHelper.assertEquals("c1",outputStringText.getStyle().getValuesAsString());
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass {
		
		private String identifier;
		private String code;
		private String name;
		
	}
}
