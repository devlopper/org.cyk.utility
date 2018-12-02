package org.cyk.utility.internationalization;

import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionList;
import org.cyk.utility.system.action.SystemActionProcess;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionSelect;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class InternalizationKeyStringBuilderUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void none(){
		assertionHelper.assertEquals(null, __inject__(InternalizationKeyStringBuilder.class).execute().getOutput());
	}
	
	@Test
	public void empty(){
		assertionHelper.assertEquals(null, __inject__(InternalizationKeyStringBuilder.class).setValue("").execute().getOutput());
	}
	
	@Test
	public void hi(){
		assertionHelper.assertEquals("hi", __inject__(InternalizationKeyStringBuilder.class).setValue("hi").execute().getOutput());
	}
	
	@Test
	public void _hi(){
		assertionHelper.assertEquals("_.hi", __inject__(InternalizationKeyStringBuilder.class).setValue("_hi").execute().getOutput());
	}
	
	@Test
	public void hi_(){
		assertionHelper.assertEquals("hi._", __inject__(InternalizationKeyStringBuilder.class).setValue("hi_").execute().getOutput());
	}
	
	@Test
	public void camelCase(){
		assertionHelper.assertEquals("camel.case", __inject__(InternalizationKeyStringBuilder.class).setValue("camelCase").execute().getOutput());
	}
	
	@Test
	public void camelCase01(){
		assertionHelper.assertEquals("camel.case.01", __inject__(InternalizationKeyStringBuilder.class).setValue("camelCase01").execute().getOutput());
	}
	
	@Test
	public void camelCase01ABC(){
		assertionHelper.assertEquals("camel.case.01.abc", __inject__(InternalizationKeyStringBuilder.class).setValue("camelCase01ABC").execute().getOutput());
	}
	
	@Test
	public void camelCase01ABCOk(){
		assertionHelper.assertEquals("camel.case.01.abc.ok", __inject__(InternalizationKeyStringBuilder.class).setValue("camelCase01ABCOk").execute().getOutput());
	}
	
	@Test
	public void camel_dot_case(){
		assertionHelper.assertEquals("camel.case", __inject__(InternalizationKeyStringBuilder.class).setValue("camel.case").execute().getOutput());
	}
	
	@Test
	public void systemActionCreate(){
		assertionHelper.assertEquals("create", __inject__(InternalizationKeyStringBuilder.class).setValue(SystemActionCreate.class).execute().getOutput());
		assertionHelper.assertEquals("create", __inject__(InternalizationKeyStringBuilder.class).setValue(__inject__(SystemActionCreate.class)).execute().getOutput());
		assertionHelper.assertEquals("create.__noun__", __inject__(InternalizationKeyStringBuilder.class).setValue(SystemActionCreate.class).setType(InternalizationKeyStringType.NOUN).execute().getOutput());
		assertionHelper.assertEquals("create.__verb__", __inject__(InternalizationKeyStringBuilder.class).setValue(SystemActionCreate.class).setType(InternalizationKeyStringType.VERB).execute().getOutput());
	}
	
	@Test
	public void systemActionRead(){
		assertionHelper.assertEquals("read", __inject__(InternalizationKeyStringBuilder.class).setValue(SystemActionRead.class).execute().getOutput());
		assertionHelper.assertEquals("read", __inject__(InternalizationKeyStringBuilder.class).setValue(__inject__(SystemActionRead.class)).execute().getOutput());
	}
	
	@Test
	public void systemActionUpdate(){
		assertionHelper.assertEquals("update", __inject__(InternalizationKeyStringBuilder.class).setValue(SystemActionUpdate.class).execute().getOutput());
		assertionHelper.assertEquals("update", __inject__(InternalizationKeyStringBuilder.class).setValue(__inject__(SystemActionUpdate.class)).execute().getOutput());
	}
	
	@Test
	public void systemActionDelete(){
		assertionHelper.assertEquals("delete", __inject__(InternalizationKeyStringBuilder.class).setValue(SystemActionDelete.class).execute().getOutput());
		assertionHelper.assertEquals("delete", __inject__(InternalizationKeyStringBuilder.class).setValue(__inject__(SystemActionDelete.class)).execute().getOutput());
	}
	
	@Test
	public void systemActionList(){
		assertionHelper.assertEquals("list", __inject__(InternalizationKeyStringBuilder.class).setValue(SystemActionList.class).execute().getOutput());
		assertionHelper.assertEquals("list", __inject__(InternalizationKeyStringBuilder.class).setValue(__inject__(SystemActionList.class)).execute().getOutput());
	}
	
	@Test
	public void systemActionSelect(){
		assertionHelper.assertEquals("select", __inject__(InternalizationKeyStringBuilder.class).setValue(SystemActionSelect.class).execute().getOutput());
		assertionHelper.assertEquals("select", __inject__(InternalizationKeyStringBuilder.class).setValue(__inject__(SystemActionSelect.class)).execute().getOutput());
	}
	
	@Test
	public void systemActionProcess(){
		assertionHelper.assertEquals("process", __inject__(InternalizationKeyStringBuilder.class).setValue(SystemActionProcess.class).execute().getOutput());
		assertionHelper.assertEquals("process", __inject__(InternalizationKeyStringBuilder.class).setValue(__inject__(SystemActionProcess.class)).execute().getOutput());
		assertionHelper.assertEquals("validate", __inject__(InternalizationKeyStringBuilder.class).setValue(__inject__(SystemActionProcess.class).setIdentifier("validate")).execute().getOutput());
	}
	
	@Test
	public void myEntity(){
		assertionHelper.assertEquals("my.entity", __inject__(InternalizationKeyStringBuilder.class).setValue(MyEntity.class).execute().getOutput());
		assertionHelper.assertEquals("my.entity", __inject__(InternalizationKeyStringBuilder.class).setValue(MyEntityImpl.class).execute().getOutput());
	}
	
	/**/
	
	public static interface MyEntity {
		
	}
	
	public static class MyEntityImpl implements MyEntity {
		
	}
}
