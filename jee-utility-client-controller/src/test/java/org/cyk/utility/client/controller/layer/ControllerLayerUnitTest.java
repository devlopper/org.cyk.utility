package org.cyk.utility.client.controller.layer;

import org.cyk.utility.client.controller.ControllerLayer;
import org.cyk.utility.client.controller.api.MyEntityController;
import org.cyk.utility.client.controller.entities.MyEntity;
import org.cyk.utility.client.controller.entities.MyEntityEditForm;
import org.cyk.utility.client.controller.entities.MyEntityEditWindowBuilder;
import org.cyk.utility.client.controller.entities.MyEntityReadRow;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionList;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class ControllerLayerUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = -2849775962912387317L;
	
	@Test
	public void getInterfaceClassFromEntityClass() {
		assertionHelper.assertEquals(MyEntityController.class, __inject__(ControllerLayer.class).getInterfaceClassFromEntityClass(MyEntity.class));
	}
	
	@Test
	public void getWindowBuilderClassFromEntityClass() {
		assertionHelper.assertEquals(MyEntityEditWindowBuilder.class, __inject__(ControllerLayer.class).getWindowBuilderClassFromEntityClass(MyEntity.class,SystemActionCreate.class));
	}
	
	@Test
	public void getFormClass() {
		assertionHelper.assertEquals(MyEntityEditForm.class, __inject__(ControllerLayer.class).getFormClass(MyEntity.class,SystemActionCreate.class));
	}
	
	@Test
	public void getRowClass() {
		assertionHelper.assertEquals(MyEntityReadRow.class, __inject__(ControllerLayer.class).getRowClass(MyEntity.class,SystemActionList.class));
	}
	
}
