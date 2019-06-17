package org.cyk.utility.client.controller.component.window;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionCreateImpl;
import org.cyk.utility.system.action.SystemActionRelatedClassesNamesGetter;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.Test;

public class WindowClassesNamesGetterUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = -2849775962912387317L;
	
	@Test
	public void myEntity_fromActionInterface() {
		assertThat(__inject__(SystemActionRelatedClassesNamesGetter.class).setEntityClass(VerySimpleEntity.class).setSystemActionClass(SystemActionCreate.class)
				.setNameSuffix("WindowBuilder").setExtendedInterface(WindowContainerManagedWindowBuilder.class).execute().getOutput().get())
			.asList().containsExactly("org.cyk.utility.client.controller.component.window.VerySimpleEntityCreateWindowBuilder","org.cyk.utility.client.controller.component.window.VerySimpleEntityEditWindowBuilder"
					,"org.cyk.utility.client.controller.component.window.VerySimpleEntityWindowBuilder"
					,"org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilderCreateDefault"
					,"org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilderDefault");
	}
	
	@Test
	public void myEntity_fromActionImpl() {
		assertThat(__inject__(SystemActionRelatedClassesNamesGetter.class).setEntityClass(VerySimpleEntity.class).setSystemActionClass(SystemActionCreateImpl.class)
				.setNameSuffix("WindowBuilder").setExtendedInterface(WindowContainerManagedWindowBuilder.class).execute().getOutput().get())
			.asList().containsExactly("org.cyk.utility.client.controller.component.window.VerySimpleEntityCreateWindowBuilder","org.cyk.utility.client.controller.component.window.VerySimpleEntityEditWindowBuilder"
					,"org.cyk.utility.client.controller.component.window.VerySimpleEntityWindowBuilder"
					,"org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilderCreateDefault"
					,"org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilderDefault");
	}
}
