package org.cyk.utility.identifier.resource;

import org.cyk.utility.field.FieldName;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class UniformResourceIdentifierParameterNameStringBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void parameter_name_null(){
		assertionHelper.assertEquals(null, __inject__(UniformResourceIdentifierParameterNameStringBuilder.class).execute().getOutput());
	}
	
	@Test
	public void parameter_name_class(){
		assertionHelper.assertEquals("class", __inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setName(Class.class).execute().getOutput());
	}
	
	@Test
	public void parameter_name_class_entity(){
		assertionHelper.assertEquals("entityclass", __inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsEntityClass().execute().getOutput());
	}
	
	@Test
	public void parameter_name_class_entity_instance(){
		assertionHelper.assertEquals("entityidentifier", __inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsEntityIdentifier().execute().getOutput());
	}
	
	@Test
	public void parameter_name_class_systemAction(){
		assertionHelper.assertEquals("actionclass", __inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsActionClass().execute().getOutput());
	}
	
	@Test
	public void parameter_name_class_systemAction_instance(){
		assertionHelper.assertEquals("actionidentifier", __inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsActionIdentifier().execute().getOutput());
	}
	
	@Test
	public void parameter_name_class_nextSystemAction(){
		assertionHelper.assertEquals("nextactionclass", __inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsNextActionClass().execute().getOutput());
	}
	
	@Test
	public void parameter_name_class_nextSystemAction_instance(){
		assertionHelper.assertEquals("nextactionidentifier", __inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsNextActionIdentifier().execute().getOutput());
	}
	
	@Test
	public void parameter_name_class_windowRenderType_instance(){
		assertionHelper.assertEquals("windowrendertypeclass", __inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setNameAsWindowRenderTypeClass().execute().getOutput());
	}
	
	@Test
	public void parameter_name_field_identifier(){
		assertionHelper.assertEquals("identifier", __inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setName(FieldName.IDENTIFIER).execute().getOutput());
	}
	
	/**/
	
}
