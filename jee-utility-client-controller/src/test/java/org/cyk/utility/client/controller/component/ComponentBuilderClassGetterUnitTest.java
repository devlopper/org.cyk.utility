package org.cyk.utility.client.controller.component;

import java.lang.annotation.Annotation;

import org.cyk.utility.client.controller.component.annotation.CommandableButton;
import org.cyk.utility.client.controller.component.annotation.InputStringLineMany;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.component.command.CommandableButtonBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineManyBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineOneBuilder;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ComponentBuilderClassGetterUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = -2849775962912387317L;

	@Test
	public void get() {
		assertionHelper.assertEquals(null, __inject__(ComponentBuilderClassGetter.class).execute().getOutput());
	}
	
	@Test
	public void getInputStringLineOneBuilder_fromAnnotation() {
		assertionHelper.assertEquals(InputStringLineOneBuilder.class, __inject__(ComponentBuilderClassGetter.class).addAnnotations(new InputStringLineOne() {
			@Override
			public Class<? extends Annotation> annotationType() {
				return InputStringLineOne.class;
			}
		}).execute().getOutput());
	}
	
	@Test
	public void getInputStringLineManyBuilder_fromAnnotation() {
		assertionHelper.assertEquals(InputStringLineManyBuilder.class, __inject__(ComponentBuilderClassGetter.class).addAnnotations(new InputStringLineMany() {
			@Override
			public Class<? extends Annotation> annotationType() {
				return InputStringLineMany.class;
			}
		}).execute().getOutput());
	}
	
	@Test
	public void getCommandableButtonBuilder_fromAnnotation() {
		assertionHelper.assertEquals(CommandableButtonBuilder.class, __inject__(ComponentBuilderClassGetter.class).addAnnotations(new CommandableButton() {
			@Override
			public Class<? extends Annotation> annotationType() {
				return CommandableButton.class;
			}
		}).execute().getOutput());
	}
	
	@Test
	public void getInputStringLineOneBuilder_fromFieldNameStrings() {
		assertionHelper.assertEquals(InputStringLineOneBuilder.class, __inject__(ComponentBuilderClassGetter.class).setClazz(Model.class).addFieldNameStrings("inputText").execute().getOutput());
	}
	
	@Test
	public void getInputStringLineManyBuilder_fromFieldNameStrings() {
		assertionHelper.assertEquals(InputStringLineManyBuilder.class, __inject__(ComponentBuilderClassGetter.class).setClazz(Model.class).addFieldNameStrings("inputTextArea").execute().getOutput());
	}
	
	@Test
	public void getCommandableButtonBuilder_fromMethodName() {
		assertionHelper.assertEquals(CommandableButtonBuilder.class, __inject__(ComponentBuilderClassGetter.class).setClazz(Model.class).setMethodName("submit").execute().getOutput());
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Model {
		
		@InputStringLineOne private String inputText;
		@InputStringLineMany private String inputTextArea;
		
		@CommandableButton
		public void submit() {
			
		}
		
	}
	
}
