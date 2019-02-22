package org.cyk.utility.client.controller.data;

import static org.assertj.core.api.Assertions.assertThat;

import org.cyk.utility.client.controller.entities.MyEntity;
import org.cyk.utility.system.action.SystemActionRelatedClassesNamesGetter;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionCreateImpl;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class FormClassesNamesGetterUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = -2849775962912387317L;
	
	@Test
	public void myEntity_fromActionInterface() {
		assertThat(__inject__(SystemActionRelatedClassesNamesGetter.class).setEntityClass(MyEntity.class).setSystemActionClass(SystemActionCreate.class)
				.setNameSuffix("Form").setExtendedInterface(FormData.class).execute().getOutput().get())
			.asList().containsExactly("org.cyk.utility.client.controller.entities.MyEntityCreateForm","org.cyk.utility.client.controller.entities.MyEntityEditForm"
					,"org.cyk.utility.client.controller.entities.MyEntityForm","org.cyk.utility.client.controller.data.FormDataCreateDefault"
					,"org.cyk.utility.client.controller.data.FormDataDefault");
	}
	
	@Test
	public void myEntity_fromActionImpl() {
		assertThat(__inject__(SystemActionRelatedClassesNamesGetter.class).setEntityClass(MyEntity.class).setSystemActionClass(SystemActionCreateImpl.class)
				.setNameSuffix("Form").setExtendedInterface(FormData.class).execute().getOutput().get())
			.asList().containsExactly("org.cyk.utility.client.controller.entities.MyEntityCreateForm","org.cyk.utility.client.controller.entities.MyEntityEditForm"
					,"org.cyk.utility.client.controller.entities.MyEntityForm","org.cyk.utility.client.controller.data.FormDataCreateDefault"
					,"org.cyk.utility.client.controller.data.FormDataDefault");
	}
}
