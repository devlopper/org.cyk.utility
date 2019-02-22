package org.cyk.utility.client.controller.data;

import org.cyk.utility.client.controller.entities.EntityUsingDefaultForm;
import org.cyk.utility.client.controller.entities.MyEntity;
import org.cyk.utility.client.controller.entities.MyEntityCreateForm;
import org.cyk.utility.client.controller.entities.MyEntityCustomForm;
import org.cyk.utility.client.controller.entities.MyEntityDeleteForm;
import org.cyk.utility.client.controller.entities.MyEntityReadForm;
import org.cyk.utility.client.controller.entities.MyEntityUpdateForm;
import org.cyk.utility.client.controller.entities.VerySimpleEntity;
import org.cyk.utility.client.controller.entities.VerySimpleEntityForm;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionCustom;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class DataHelperUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = -2849775962912387317L;
	
	@Test
	public void isNull() {
		assertionHelper.assertEquals(null, __inject__(DataHelper.class).getFormClass(null));
	}
	
	@Test
	public void isMyEntityCreateForm_when_Create_MyEntity() {
		assertionHelper.assertEquals(MyEntityCreateForm.class, __inject__(DataHelper.class).getFormClass(__inject__(SystemActionCreate.class)
				.setEntityClass(MyEntity.class)));
	}
	
	@Test
	public void isMyEntityReadForm_when_Read_MyEntity() {
		assertionHelper.assertEquals(MyEntityReadForm.class, __inject__(DataHelper.class).getFormClass(__inject__(SystemActionRead.class)
				.setEntityClass(MyEntity.class)));
	}
	
	@Test
	public void isMyEntityUpdateForm_when_Update_MyEntity() {
		assertionHelper.assertEquals(MyEntityUpdateForm.class, __inject__(DataHelper.class).getFormClass(__inject__(SystemActionUpdate.class)
				.setEntityClass(MyEntity.class)));
	}
	
	@Test
	public void isMyEntityDeleteForm_when_Delete_MyEntity() {
		assertionHelper.assertEquals(MyEntityDeleteForm.class, __inject__(DataHelper.class).getFormClass(__inject__(SystemActionDelete.class)
				.setEntityClass(MyEntity.class)));
	}
	
	@Test
	public void isMyEntityCustomForm_when_Custom_MyEntity() {
		assertionHelper.assertEquals(MyEntityCustomForm.class, __inject__(DataHelper.class).getFormClass(__inject__(SystemActionCustom.class)
				.setEntityClass(MyEntity.class)));
	}
	
	@Test
	public void isVerySimpleEntityForm_when_Create_VerySimpleEntity() {
		assertionHelper.assertEquals(VerySimpleEntityForm.class, __inject__(DataHelper.class).getFormClass(__inject__(SystemActionCreate.class)
				.setEntityClass(VerySimpleEntity.class)));
	}
	
	@Test
	public void isVerySimpleEntityForm_when_Read_VerySimpleEntity() {
		assertionHelper.assertEquals(VerySimpleEntityForm.class, __inject__(DataHelper.class).getFormClass(__inject__(SystemActionRead.class)
				.setEntityClass(VerySimpleEntity.class)));
	}
	
	@Test
	public void isVerySimpleEntityForm_when_Update_VerySimpleEntity() {
		assertionHelper.assertEquals(VerySimpleEntityForm.class, __inject__(DataHelper.class).getFormClass(__inject__(SystemActionUpdate.class)
				.setEntityClass(VerySimpleEntity.class)));
	}
	
	@Test
	public void isVerySimpleEntityForm_when_Delete_VerySimpleEntity() {
		assertionHelper.assertEquals(VerySimpleEntityForm.class, __inject__(DataHelper.class).getFormClass(__inject__(SystemActionDelete.class)
				.setEntityClass(VerySimpleEntity.class)));
	}
	
	@Test
	public void isVerySimpleEntityForm_when_Custom_VerySimpleEntity() {
		assertionHelper.assertEquals(VerySimpleEntityForm.class, __inject__(DataHelper.class).getFormClass(__inject__(SystemActionCustom.class)
				.setEntityClass(VerySimpleEntity.class)));
	}
	
	@Test
	public void isEntityUsingDefaultFormForm_when_Create_EntityUsingDefaultForm() {
		assertionHelper.assertEquals(FormDataDefault.class, __inject__(DataHelper.class).getFormClass(__inject__(SystemActionCreate.class)
				.setEntityClass(EntityUsingDefaultForm.class)));
	}
	
	@Test
	public void isEntityUsingDefaultFormForm_when_Read_EntityUsingDefaultForm() {
		assertionHelper.assertEquals(FormDataDefault.class, __inject__(DataHelper.class).getFormClass(__inject__(SystemActionRead.class)
				.setEntityClass(EntityUsingDefaultForm.class)));
	}
	
	@Test
	public void isEntityUsingDefaultFormForm_when_Update_EntityUsingDefaultForm() {
		assertionHelper.assertEquals(FormDataDefault.class, __inject__(DataHelper.class).getFormClass(__inject__(SystemActionUpdate.class)
				.setEntityClass(EntityUsingDefaultForm.class)));
	}
	
	@Test
	public void isEntityUsingDefaultFormForm_when_Delete_EntityUsingDefaultForm() {
		assertionHelper.assertEquals(FormDataDefault.class, __inject__(DataHelper.class).getFormClass(__inject__(SystemActionDelete.class)
				.setEntityClass(EntityUsingDefaultForm.class)));
	}
	
	@Test
	public void isEntityUsingDefaultFormForm_when_Custom_EntityUsingDefaultForm() {
		assertionHelper.assertEquals(FormDataDefault.class, __inject__(DataHelper.class).getFormClass(__inject__(SystemActionCustom.class)
				.setEntityClass(EntityUsingDefaultForm.class)));
	}
}
