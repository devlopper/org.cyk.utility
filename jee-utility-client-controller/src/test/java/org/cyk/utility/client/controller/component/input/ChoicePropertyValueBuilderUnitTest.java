package org.cyk.utility.client.controller.component.input;

import org.cyk.utility.client.controller.component.input.choice.ChoicePropertyValueBuilder;
import org.cyk.utility.string.StringConstant;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ChoicePropertyValueBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void isNull_propertyIsNull_objectIsNull() {
		assertionHelper.assertNull(__inject__(ChoicePropertyValueBuilder.class).execute().getOutput());
	}
	
	@Test
	public void isEmpty_propertyIsNull_objectIsNotNull_allFieldsAreNull() {
		assertionHelper.assertEquals(StringConstant.EMPTY,__inject__(ChoicePropertyValueBuilder.class).setObject(new MyClass()).execute().getOutput());
	}
	
	@Test
	public void isEmpty_propertyIsNull_objectIsNotNull_identifierIs1() {
		assertionHelper.assertEquals(StringConstant.EMPTY,__inject__(ChoicePropertyValueBuilder.class).setObject(new MyClass().setIdentifier("1")).execute().getOutput());
	}
	
	@Test
	public void is1_propertyIsIdentifier_objectIsNotNull_identifierIs1() {
		assertionHelper.assertEquals("1",__inject__(ChoicePropertyValueBuilder.class).setPropertyName("identifier").setObject(new MyClass().setIdentifier("1")).execute().getOutput());
	}
	
	@Test
	public void isEmpty_propertyIsNull_objectIsNotNull_codeIsC1() {
		assertionHelper.assertEquals(StringConstant.EMPTY,__inject__(ChoicePropertyValueBuilder.class).setObject(new MyClass().setCode("C1")).execute().getOutput());
	}
	
	@Test
	public void isC1_propertyIsNull_objectIsNotNull_codeIsC1() {
		assertionHelper.assertEquals("C1",__inject__(ChoicePropertyValueBuilder.class).setPropertyName("code").setObject(new MyClass().setCode("C1")).execute().getOutput());
	}
	
	@Test
	public void isN1_propertyIsNull_objectIsNotNull_nameIsN1() {
		assertionHelper.assertEquals("N1",__inject__(ChoicePropertyValueBuilder.class).setObject(new MyClass().setName("N1")).execute().getOutput());
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class MyClass {
		
		private String identifier;
		private String code;
		private String name;
		
	}
}
