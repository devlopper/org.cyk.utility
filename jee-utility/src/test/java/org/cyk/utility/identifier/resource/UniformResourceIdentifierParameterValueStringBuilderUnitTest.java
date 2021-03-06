package org.cyk.utility.identifier.resource;

import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionList;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class UniformResourceIdentifierParameterValueStringBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void parameter_value_null(){
		assertionHelper.assertEquals(null, __inject__(UniformResourceIdentifierParameterValueStringBuilder.class).execute().getOutput());
	}
	
	@Test
	public void parameter_value_class(){
		assertionHelper.assertEquals("class", __inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(Class.class).execute().getOutput());
	}
	
	@Test
	public void parameter_value_class_system_action_create(){
		assertionHelper.assertEquals("create", __inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(SystemActionCreate.class)
				.execute().getOutput());
	}
	
	@Test
	public void parameter_value_system_action_create_create(){
		assertionHelper.assertEquals("create", __inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(__inject__(SystemActionCreate.class))
				.execute().getOutput());
	}
	
	@Test
	public void parameter_value_system_action_create_custom(){
		assertionHelper.assertEquals("custom", __inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(__inject__(SystemActionCreate.class).setIdentifier("custom"))
				.execute().getOutput());
	}
	
	@Test
	public void parameter_value_system_action_list_list(){
		assertionHelper.assertEquals("list", __inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(__inject__(SystemActionList.class))
				.execute().getOutput());
	}
	
}
