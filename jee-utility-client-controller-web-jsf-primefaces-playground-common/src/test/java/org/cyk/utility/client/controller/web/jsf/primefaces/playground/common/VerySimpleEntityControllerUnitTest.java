package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import org.cyk.utility.client.controller.api.verysimpleentity.VerySimpleEntityController;
import org.cyk.utility.client.controller.entities.verysimpleentity.VerySimpleEntity;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.Test;

public class VerySimpleEntityControllerUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void create() {
		VerySimpleEntity verySimpleEntity = __inject__(VerySimpleEntity.class);
		__inject__(VerySimpleEntityController.class).create(verySimpleEntity);
	}
	
	
}
