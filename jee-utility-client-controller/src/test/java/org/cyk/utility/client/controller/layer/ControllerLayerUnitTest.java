package org.cyk.utility.client.controller.layer;

import org.cyk.utility.client.controller.ControllerLayer;
import org.cyk.utility.client.controller.api.MyEntityController;
import org.cyk.utility.client.controller.entities.MyEntity;
import org.cyk.utility.client.controller.entities.MyEntityImpl;
import org.cyk.utility.server.representation.entities.MyEntityDto;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.Test;

public class ControllerLayerUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = -2849775962912387317L;
	
	@Test
	public void getDataTransferClassFromEntityClass() {
		assertionHelper.assertEquals(MyEntityDto.class, __inject__(ControllerLayer.class).getDataTransferClassFromEntityClass(MyEntity.class));
	}
	
	@Test
	public void getDataTransferClassFromEntityImplClass() {
		assertionHelper.assertEquals(MyEntityDto.class, __inject__(ControllerLayer.class).getDataTransferClassFromEntityClass(MyEntityImpl.class));
	}
	
	@Test
	public void getInterfaceClassFromEntityClass() {
		assertionHelper.assertEquals(MyEntityController.class, __inject__(ControllerLayer.class).getInterfaceClassFromEntityClass(MyEntity.class));
	}
	
}
