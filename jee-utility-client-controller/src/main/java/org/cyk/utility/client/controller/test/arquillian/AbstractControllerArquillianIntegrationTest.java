package org.cyk.utility.client.controller.test.arquillian;

import java.io.Serializable;
import java.net.URI;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.client.controller.Controller;
import org.cyk.utility.client.controller.ControllerEntity;
import org.cyk.utility.client.controller.proxy.ProxyClassUniformResourceIdentifierGetterImpl;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerController;
import org.cyk.utility.test.arquillian.AbstractSystemClientArquillianIntegrationTestImpl;
import org.cyk.utility.test.arquillian.SystemClientIntegrationTest;
import org.cyk.utility.value.ValueUsageType;

@SuppressWarnings({"rawtypes"})
public abstract class AbstractControllerArquillianIntegrationTest extends AbstractSystemClientArquillianIntegrationTestImpl<ControllerEntity> implements SystemClientIntegrationTest<ControllerEntity>, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		String systemIdentifier = __getSystemIdentifier__();
		if(StringUtils.isNotBlank(systemIdentifier))
			ProxyClassUniformResourceIdentifierGetterImpl.UNIFORM_RESOURCE_IDENTIFIER = URI.create(String.format("http://localhost:8080/%s/server/",systemIdentifier));
		super.__listenBefore__();
		__inject__(Controller.class).deleteAll();
	}
	
	@Override
	protected <ENTITY> void ____createEntity____(ENTITY entity, ControllerEntity controller) {
		controller.create(entity);
	}
	
	@Override
	protected <ENTITY> void ____createEntity____(Collection<ENTITY> entities, ControllerEntity controller) {
		//controller.createMany(entities);
	}

	@Override
	protected <ENTITY> ENTITY ____readEntity____(Class<ENTITY> entityClass, Object identifier,ValueUsageType valueUsageType, Properties expectedFieldValues, ControllerEntity controller) {
		return null;//(ENTITY) controller.findOne(identifier, valueUsageType);
	}

	@Override
	protected <ENTITY> void ____updateEntity____(ENTITY entity, ControllerEntity controller) {
		controller.update(entity);
	}

	@Override
	protected <ENTITY> void ____deleteEntity____(ENTITY entity, ControllerEntity controller) {
		controller.delete(entity);
	}
	
	@Override
	protected <ENTITY> void ____deleteEntityAll____(Class<ENTITY> entityClass, ControllerEntity controller) {
		controller.deleteAll();
	}
	
	@Override
	protected <ENTITY> Long ____countEntitiesAll____(Class<ENTITY> entityClass, ControllerEntity controller) {
		return null;//controller.count();
	}
	
	@Override
	protected ControllerEntity ____getLayerEntityInterfaceFromClass____(Class<?> aClass) {
		return null;//__inject__(SystemLayerController.class).injectInterfaceClassFromPersistenceEntityClassName(aClass,__getLayerEntityInterfaceClass__());
	}

	@Override
	public SystemLayer __getSystemLayer__() {
		return __inject__(SystemLayerController.class);
	}
	
	protected String __getSystemIdentifier__() {
		String identifier = getClass().getPackage().getName();
		identifier = StringUtils.substringBefore(identifier, ".client.");
		identifier = StringUtils.substringAfterLast(identifier, ".");
		return identifier;
	}
}
