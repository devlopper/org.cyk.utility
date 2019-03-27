package org.cyk.utility.client.controller.component;

import java.lang.annotation.Annotation;

import org.cyk.utility.client.controller.component.annotation.Commandable;
import org.cyk.utility.client.controller.component.annotation.InputChoiceOneCombo;
import org.cyk.utility.client.controller.component.annotation.InputFile;
import org.cyk.utility.client.controller.component.annotation.InputStringLineMany;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.component.annotation.OutputStringText;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.input.InputFileBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineManyBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineOneBuilder;
import org.cyk.utility.client.controller.component.input.choice.InputChoiceOneComboBuilder;
import org.cyk.utility.client.controller.component.output.OutputBuilder;
import org.cyk.utility.client.controller.component.output.OutputFileBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.file.File;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class ComponentBuilderClassGetterUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = -2849775962912387317L;

	@Test
	public void isNull_whenAnnotaionsAreEmpty() {
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
	public void getOutputStringTextBuilder_fromAnnotation() {
		assertionHelper.assertEquals(OutputStringTextBuilder.class, __inject__(ComponentBuilderClassGetter.class).addAnnotations(new OutputStringText() {
			@Override
			public Class<? extends Annotation> annotationType() {
				return OutputStringText.class;
			}
		}).execute().getOutput());
	}
	
	@Test
	public void getCommandableButtonBuilder_fromAnnotation() {
		assertionHelper.assertEquals(CommandableBuilder.class, __inject__(ComponentBuilderClassGetter.class).addAnnotations(new org.cyk.utility.client.controller.component.annotation.Commandable() {
			@Override
			public Class<? extends Annotation> annotationType() {
				return org.cyk.utility.client.controller.component.annotation.Commandable.class;
			}
			
			@Override
			public Class<? extends SystemAction> systemActionClass() {
				return null;
			}
		}).execute().getOutput());
	}
	
	@Test
	public void getInputStringLineOneBuilder_fromFieldNameStrings() {
		assertionHelper.assertEquals(InputStringLineOneBuilder.class, __inject__(ComponentBuilderClassGetter.class).setClazz(Model.class).addFieldNameStrings("inputText").execute().getOutput());
	}
	
	@Test
	public void getOutputStringTextBuilder_fromFieldNameStrings_inputText() {
		assertionHelper.assertEquals(OutputStringTextBuilder.class, __inject__(ComponentBuilderClassGetter.class).setClazz(Model.class).addFieldNameStrings("inputText")
				.setBaseClass(OutputStringTextBuilder.class).execute().getOutput());
	}
	
	@Test
	public void getOutputStringTextBuilder_fromFieldNameStrings_subModel_subInputText() {
		assertionHelper.assertEquals(OutputStringTextBuilder.class, __inject__(ComponentBuilderClassGetter.class).setClazz(Model.class).addFieldNameStrings("subModel","subInputText")
				.setBaseClass(OutputStringTextBuilder.class).execute().getOutput());
	}
	
	@Test
	public void getInputStringLineManyBuilder_fromFieldNameStrings() {
		assertionHelper.assertEquals(InputStringLineManyBuilder.class, __inject__(ComponentBuilderClassGetter.class).setClazz(Model.class).addFieldNameStrings("inputTextArea").execute().getOutput());
	}
	
	@Test
	public void getInputChoiceOneComboBuilder_fromFieldNameStrings() {
		assertionHelper.assertEquals(InputChoiceOneComboBuilder.class, __inject__(ComponentBuilderClassGetter.class).setClazz(Model.class).addFieldNameStrings("inputCombo").execute().getOutput());
	}
	
	@Test
	public void getOutputStringTextBuilder_fromFieldNameStrings() {
		assertionHelper.assertEquals(OutputStringTextBuilder.class, __inject__(ComponentBuilderClassGetter.class).setClazz(Model.class).addFieldNameStrings("__title__").execute().getOutput());
	}
	
	@Test
	public void getCommandableButtonBuilder_fromMethodName() {
		assertionHelper.assertEquals(CommandableBuilder.class, __inject__(ComponentBuilderClassGetter.class).setClazz(Model.class).setMethodName("submit").execute().getOutput());
	}
	
	@Test
	public void getInputFileBuilder_fromFieldNameStrings() {
		assertionHelper.assertEquals(InputFileBuilder.class, __inject__(ComponentBuilderClassGetter.class).setClazz(Model.class).addFieldNameStrings("inputFile").execute().getOutput());
	}
	
	@Test
	public void getOutputFileBuilder_fromFieldNameStrings_inputText() {
		assertionHelper.assertEquals(OutputFileBuilder.class, __inject__(ComponentBuilderClassGetter.class).setClazz(Model.class).addFieldNameStrings("inputFile")
				.setBaseClass(OutputBuilder.class).execute().getOutput());
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class Model {
		
		@OutputStringText private String __title__;
		@InputStringLineOne private String inputText;
		@InputStringLineMany private String inputTextArea;
		@InputChoiceOneCombo private String inputCombo;
		@InputFile private File inputFile;
		private SubModel subModel;
		
		@Commandable(systemActionClass=SystemAction.class)
		public void submit() {
			
		}
		
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class SubModel {
		
		@InputStringLineOne private String subInputText;
		
	}
	
}
