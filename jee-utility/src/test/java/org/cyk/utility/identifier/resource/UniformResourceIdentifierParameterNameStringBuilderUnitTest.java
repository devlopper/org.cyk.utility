package org.cyk.utility.identifier.resource;

import org.cyk.utility.field.FieldName;
import org.cyk.utility.system.action.SystemAction;
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
	public void parameter_name_systemAction(){
		assertionHelper.assertEquals("action", __inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setName(SystemAction.class).execute().getOutput());
	}
	
	@Test
	public void parameter_name_identifier(){
		assertionHelper.assertEquals("identifier", __inject__(UniformResourceIdentifierParameterNameStringBuilder.class).setName(FieldName.IDENTIFIER).execute().getOutput());
	}
	
}
