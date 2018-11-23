package org.cyk.utility.client.controller.data;

import org.cyk.utility.client.controller.entities.MyEntity;
import org.cyk.utility.client.controller.entities.MyEntityEditForm;
import org.cyk.utility.client.controller.entities.MyEntityReadForm;
import org.cyk.utility.client.controller.entities.VerySimpleEntity;
import org.cyk.utility.client.controller.entities.VerySimpleEntityEditForm;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class FormClassGetterUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = -2849775962912387317L;
	
	@Test
	public void isNull() {
		assertionHelper.assertEquals(null, __inject__(FormClassGetter.class).execute().getOutput());
	}
	
	@Test
	public void isMyEntityEditForm_when_Create_MyEntity() {
		assertionHelper.assertEquals(MyEntityEditForm.class, __inject__(FormClassGetter.class).setSystemAction(__inject__(SystemActionCreate.class)
				.setEntityClass(MyEntity.class)).execute().getOutput());
	}
	
	@Test
	public void isMyEntityReadForm_when_Read_MyEntity() {
		assertionHelper.assertEquals(MyEntityReadForm.class, __inject__(FormClassGetter.class).setSystemAction(__inject__(SystemActionRead.class)
				.setEntityClass(MyEntity.class)).execute().getOutput());
	}
	
	@Test
	public void isMyEntityEditForm_when_Update_MyEntity() {
		assertionHelper.assertEquals(MyEntityEditForm.class, __inject__(FormClassGetter.class).setSystemAction(__inject__(SystemActionUpdate.class)
				.setEntityClass(MyEntity.class)).execute().getOutput());
	}
	
	@Test
	public void isMyEntityEditForm_when_Delete_MyEntity() {
		assertionHelper.assertEquals(MyEntityEditForm.class, __inject__(FormClassGetter.class).setSystemAction(__inject__(SystemActionDelete.class)
				.setEntityClass(MyEntity.class)).execute().getOutput());
	}
	
	@Test
	public void isMyEntityEditForm_when_Create_VerySimpleEntity() {
		assertionHelper.assertEquals(VerySimpleEntityEditForm.class, __inject__(FormClassGetter.class).setSystemAction(__inject__(SystemActionCreate.class)
				.setEntityClass(VerySimpleEntity.class)).execute().getOutput());
	}
	
	@Test
	public void isMyEntityReadForm_when_Read_VerySimpleEntity() {
		assertionHelper.assertEquals(VerySimpleEntityEditForm.class, __inject__(FormClassGetter.class).setSystemAction(__inject__(SystemActionRead.class)
				.setEntityClass(VerySimpleEntity.class)).execute().getOutput());
	}
	
	@Test
	public void isMyEntityEditForm_when_Update_VerySimpleEntity() {
		assertionHelper.assertEquals(VerySimpleEntityEditForm.class, __inject__(FormClassGetter.class).setSystemAction(__inject__(SystemActionUpdate.class)
				.setEntityClass(VerySimpleEntity.class)).execute().getOutput());
	}
	
	@Test
	public void isMyEntityEditForm_when_Delete_VerySimpleEntity() {
		assertionHelper.assertEquals(VerySimpleEntityEditForm.class, __inject__(FormClassGetter.class).setSystemAction(__inject__(SystemActionDelete.class)
				.setEntityClass(VerySimpleEntity.class)).execute().getOutput());
	}
}
