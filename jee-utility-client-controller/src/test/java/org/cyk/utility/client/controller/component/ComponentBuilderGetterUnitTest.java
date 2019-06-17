package org.cyk.utility.client.controller.component;

import org.cyk.utility.client.controller.component.annotation.CommandableButton;
import org.cyk.utility.client.controller.component.annotation.InputStringLineMany;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.component.annotation.OutputStringText;
import org.cyk.utility.client.controller.component.input.InputStringLineOneBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ComponentBuilderGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = -2849775962912387317L;
	
	@Test
	public void getOutputStringTextBuilder_fromFieldNameStrings_123() {
		Model model = new Model();
		model.set__title__("123");
		OutputStringTextBuilder builder = (OutputStringTextBuilder) __inject__(ComponentBuilderGetter.class).setObject(model).addFieldNameStrings("__title__").execute().getOutput();
		assertionHelper.assertNotNull(builder);
		org.cyk.utility.client.controller.component.output.OutputStringText outputStringText = (org.cyk.utility.client.controller.component.output.OutputStringText)
				builder.execute().getOutput();
		assertionHelper.assertEquals("123", outputStringText.getValue());
	}
	
	@Test
	public void getInputStringLineOne_fromFieldNameStrings() {
		Model model = new Model();
		InputStringLineOneBuilder builder = (InputStringLineOneBuilder) __inject__(ComponentBuilderGetter.class).setObject(model).addFieldNameStrings("inputText").execute().getOutput();
		assertionHelper.assertNotNull(builder);
		org.cyk.utility.client.controller.component.input.InputStringLineOne inputStringLineOne = (org.cyk.utility.client.controller.component.input.InputStringLineOne)
				builder.execute().getOutput();
		assertionHelper.assertEquals(null, inputStringLineOne.getValue());
	}
	
	@Test
	public void getInputStringLineOne_fromFieldNameStrings_123() {
		Model model = new Model();
		model.setInputText("123");
		InputStringLineOneBuilder builder = (InputStringLineOneBuilder) __inject__(ComponentBuilderGetter.class).setObject(model).addFieldNameStrings("inputText").execute().getOutput();
		assertionHelper.assertNotNull(builder);
		org.cyk.utility.client.controller.component.input.InputStringLineOne inputStringLineOne = (org.cyk.utility.client.controller.component.input.InputStringLineOne)
				builder.execute().getOutput();
		assertionHelper.assertEquals("123", inputStringLineOne.getValue());
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Model {
		
		@OutputStringText private String __title__;
		@InputStringLineOne private String inputText;
		@InputStringLineMany private String inputTextArea;
		
		@CommandableButton
		public void submit() {
			
		}
		
	}
	
}
