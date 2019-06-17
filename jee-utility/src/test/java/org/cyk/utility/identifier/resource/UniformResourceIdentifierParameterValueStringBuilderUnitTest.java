package org.cyk.utility.identifier.resource;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionList;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public class UniformResourceIdentifierParameterValueStringBuilderUnitTest extends AbstractWeldUnitTest {
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
	
	@Test
	public void parameter_value_identifier_MyId(){
		assertionHelper.assertEquals("MyId", __inject__(UniformResourceIdentifierParameterValueStringBuilder.class).setValue(__inject__(Klass.class).setIdentifier("MyId"))
				.execute().getOutput());
	}
	
	/**/
	
	public static class Klass extends AbstractObject {
		private static final long serialVersionUID = 1L;
		
		
		
	}
}
