package org.cyk.utility.client.controller.component.window;

import org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilderEditDataDefault;
import org.cyk.utility.client.controller.component.window.WindowHelper;
import org.cyk.utility.client.controller.entities.EntityUsingDefaultForm;
import org.cyk.utility.client.controller.entities.VerySimpleEntity;
import org.cyk.utility.client.controller.entities.VerySimpleEntityForm;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionCustom;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class WindowHelperUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = -2849775962912387317L;
	
	@Test
	public void isNull() {
		assertionHelper.assertEquals(null, __inject__(WindowHelper.class).getWindowContainerManagedWindowBuilderClass(null));
	}
		
	@Test
	public void isVerySimpleEntityForm_when_Create_VerySimpleEntity() {
		assertionHelper.assertEquals(VerySimpleEntityEditWindowBuilder.class, __inject__(WindowHelper.class).getWindowContainerManagedWindowBuilderClass(__inject__(SystemActionCreate.class)
				.setEntityClass(VerySimpleEntity.class)));
	}
	
	@Test
	public void isVerySimpleEntityForm_when_Read_VerySimpleEntity() {
		assertionHelper.assertEquals(VerySimpleEntityEditWindowBuilder.class, __inject__(WindowHelper.class).getWindowContainerManagedWindowBuilderClass(__inject__(SystemActionRead.class)
				.setEntityClass(VerySimpleEntity.class)));
	}
	
	@Test
	public void isVerySimpleEntityForm_when_Update_VerySimpleEntity() {
		assertionHelper.assertEquals(VerySimpleEntityEditWindowBuilder.class, __inject__(WindowHelper.class).getWindowContainerManagedWindowBuilderClass(__inject__(SystemActionUpdate.class)
				.setEntityClass(VerySimpleEntity.class)));
	}
	
	@Test
	public void isVerySimpleEntityForm_when_Delete_VerySimpleEntity() {
		assertionHelper.assertEquals(VerySimpleEntityEditWindowBuilder.class, __inject__(WindowHelper.class).getWindowContainerManagedWindowBuilderClass(__inject__(SystemActionDelete.class)
				.setEntityClass(VerySimpleEntity.class)));
	}
	
	@Test
	public void isVerySimpleEntityForm_when_Custom_VerySimpleEntity() {
		assertionHelper.assertEquals(VerySimpleEntityForm.class, __inject__(WindowHelper.class).getWindowContainerManagedWindowBuilderClass(__inject__(SystemActionCustom.class)
				.setEntityClass(VerySimpleEntity.class)));
	}
	
	@Test
	public void isEntityUsingDefaultFormForm_when_Create_EntityUsingDefaultForm() {
		assertionHelper.assertEquals(WindowContainerManagedWindowBuilderEditDataDefault.class, __inject__(WindowHelper.class).getWindowContainerManagedWindowBuilderClass(__inject__(SystemActionCreate.class)
				.setEntityClass(EntityUsingDefaultForm.class)));
	}
	
	@Test
	public void isEntityUsingDefaultFormForm_when_Read_EntityUsingDefaultForm() {
		assertionHelper.assertEquals(WindowContainerManagedWindowBuilderEditDataDefault.class, __inject__(WindowHelper.class).getWindowContainerManagedWindowBuilderClass(__inject__(SystemActionRead.class)
				.setEntityClass(EntityUsingDefaultForm.class)));
	}
	
	@Test
	public void isEntityUsingDefaultFormForm_when_Update_EntityUsingDefaultForm() {
		assertionHelper.assertEquals(WindowContainerManagedWindowBuilderEditDataDefault.class, __inject__(WindowHelper.class).getWindowContainerManagedWindowBuilderClass(__inject__(SystemActionUpdate.class)
				.setEntityClass(EntityUsingDefaultForm.class)));
	}
	
	@Test
	public void isEntityUsingDefaultFormForm_when_Delete_EntityUsingDefaultForm() {
		assertionHelper.assertEquals(WindowContainerManagedWindowBuilderEditDataDefault.class, __inject__(WindowHelper.class).getWindowContainerManagedWindowBuilderClass(__inject__(SystemActionDelete.class)
				.setEntityClass(EntityUsingDefaultForm.class)));
	}
	
	@Test
	public void isEntityUsingDefaultFormForm_when_Custom_EntityUsingDefaultForm() {
		assertionHelper.assertEquals(WindowContainerManagedWindowBuilderEditDataDefault.class, __inject__(WindowHelper.class).getWindowContainerManagedWindowBuilderClass(__inject__(SystemActionCustom.class)
				.setEntityClass(EntityUsingDefaultForm.class)));
	}
}
