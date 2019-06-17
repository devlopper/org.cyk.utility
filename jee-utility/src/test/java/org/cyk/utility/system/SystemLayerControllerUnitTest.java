package org.cyk.utility.system;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.cyk.utility.map.MapHelper;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerController;
import org.cyk.utility.system.layer.SystemLayerPersistence;
import org.cyk.utility.system.layer.SystemSubLayer;
import org.junit.jupiter.api.Test;

public class SystemLayerControllerUnitTest extends AbstractSystemLayerUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	/* Entity */
	
	@Override
	protected SystemLayer __injectSystemLayer__() {
		return __inject__(SystemLayerController.class);
	}

	@Override
	protected String __getExpectedEntityPackageNameRegularExpression__() {
		return "[.]{0,1}controller.entities[.]{0,1}";
	}

	@Override
	protected Collection<String> __getEntityTruePackages__() {
		return Arrays.asList("a.controller.entities.b","a.controller.entities","controller.entities.b","controller.entities");
	}

	@Override
	protected Collection<String> __getEntityFalsePackages__() {
		return Arrays.asList("a.controller.c.entities.b","a.controller.c.entities","controller.c.entities.b","controller.c.entities",
				"entities","controller");
	}

	@Override
	protected String __getExpectedEntityInterfaceNameRegularExpression__() {
		return null;
	}
	
	@Override
	protected Collection<String> __getEntityTrueInterfaces__() {
		return null;
	}

	@Override
	protected Collection<String> __getEntityFalseInterfaces__() {
		return null;
	}

	@Override
	protected String __getExpectedEntityClassNameRegularExpression__() {
		return "controller.entities..+Impl$";
	}
	
	@Override
	protected Collection<String> __getEntityTrueClasses__() {
		return Arrays.asList("a.controller.entities.b.MyClassImpl","a.controller.entities.MyClassImpl","controller.entities.b.MyClassImpl","controller.entities.MyClassImpl");
	}

	@Override
	protected Collection<String> __getEntityFalseClasses__() {
		return Arrays.asList("a.controller.entitie.MyClass","a.controller.entities.Impl","controller.entities.b.MyClass","controller.entities.MyClass");
	}

	/* Interface */
	
	@Override
	protected String __getExpectedInterfacePackageNameRegularExpression__() {
		return "[.]{0,1}controller.api[.]{0,1}";
	}

	@Override
	protected String __getExpectedInterfaceClassNameRegularExpression__() {
		return null;
	}

	@Override
	protected String __getExpectedInterfaceInterfaceNameRegularExpression__() {
		return "Controller$";
	}

	@Override
	protected Collection<String> __getInterfaceTruePackages__() {
		return Arrays.asList("a.controller.api.b","a.controller.api","controller.api.b","controller.api");
	}

	@Override
	protected Collection<String> __getInterfaceFalsePackages__() {
		return Arrays.asList("a.controller.c.api.b","a.controller.c.api","controller.c.api.b","controller.c.api",
				"api","controller");
	}

	@Override
	protected Collection<String> __getInterfaceTrueInterfaces__() {
		return null;
	}

	@Override
	protected Collection<String> __getInterfaceFalseInterfaces__() {
		return null;
	}

	@Override
	protected Collection<String> __getInterfaceTrueClasses__() {
		return null;
	}

	@Override
	protected Collection<String> __getInterfaceFalseClasses__() {
		return null;
	}
	
	@Override
	protected Map<String, String> __getExpectedInterfaceNameFromEntityClassName__() {
		return __inject__(MapHelper.class).instanciateKeyAsStringValueAsString("controller.entities.MyClass","controller.api.MyClassController"
				,"p.controller.entities.MyClass","p.controller.api.MyClassController"
				,"p.controller.entities.p1.MyClass","p.controller.api.p1.MyClassController"
				,"p.controller.entities.p1.MyClassImpl","p.controller.api.p1.MyClassController"
				);
	}
	
	@Override
	protected Map<SystemSubLayer,Map<String, String>> __getExpectedInterfaceNameFrom__() {
		Map<SystemSubLayer,Map<String, String>> map = new LinkedHashMap<>();
		map.put(__inject__(SystemLayerPersistence.class).getEntityLayer(), __inject__(MapHelper.class).instanciateKeyAsStringValueAsString(
				"persistence.entities.MyClass","controller.api.MyClassController"
				,"p.persistence.entities.MyClass","p.controller.api.MyClassController"
				,"p.persistence.entities.p1.MyClass","p.controller.api.p1.MyClassController"
				,"MyClass","MyClassController"));
		return map;
	}
	
	/* Implementation */

	@Override
	protected String __getExpectedImplementationPackageNameRegularExpression__() {
		return "[.]{0,1}controller.impl[.]{0,1}";
	}

	@Override
	protected String __getExpectedImplementationClassNameRegularExpression__() {
		return "ControllerImpl$";
	}

	@Override
	protected String __getExpectedImplementationInterfaceNameRegularExpression__() {
		return null;
	}

	@Override
	protected Collection<String> __getImplementationTruePackages__() {
		return Arrays.asList("a.controller.impl.b","a.controller.impl","controller.impl.b","controller.impl");
	}

	@Override
	protected Collection<String> __getImplementationFalsePackages__() {
		return Arrays.asList("a.controller.c.impl.b","a.controller.c.impl","controller.c.impl.b","controller.c.impl",
				"impl","controller");
	}
	
	@Test
	public void getDataTransferClassNameFromEntityClassName() {
		assertionHelper.assertEquals("org.cyk.system.actor.server.representation.entities.MyDataDto"
				, __inject__(SystemLayerController.class).getDataTransferClassNameFromEntityClassName("org.cyk.system.actor.client.controller.entities.MyData"));
		assertionHelper.assertEquals("org.cyk.system.actor.server.representation.entities.p01.MyDataDto"
				, __inject__(SystemLayerController.class).getDataTransferClassNameFromEntityClassName("org.cyk.system.actor.client.controller.entities.p01.MyData"));
	}
	
	@Test
	public void getRepresentationClassNameFromEntityClassName() {
		assertionHelper.assertEquals("org.cyk.system.actor.server.representation.api.MyDataRepresentation"
				, __inject__(SystemLayerController.class).getRepresentationClassNameFromEntityClassName("org.cyk.system.actor.client.controller.entities.MyData"));
		assertionHelper.assertEquals("org.cyk.system.actor.server.representation.api.MyDataRepresentation"
				, __inject__(SystemLayerController.class).getRepresentationClassNameFromEntityClassName("org.cyk.system.actor.client.controller.entities.MyDataImpl"));
		assertionHelper.assertEquals("org.cyk.system.actor.server.representation.api.p01.MyDataRepresentation"
				, __inject__(SystemLayerController.class).getRepresentationClassNameFromEntityClassName("org.cyk.system.actor.client.controller.entities.p01.MyData"));
	}

	@Override
	protected Collection<String> __getImplementationTrueInterfaces__() {
		return null;
	}

	@Override
	protected Collection<String> __getImplementationFalseInterfaces__() {
		return null;
	}

	@Override
	protected Collection<String> __getImplementationTrueClasses__() {
		return null;
	}

	@Override
	protected Collection<String> __getImplementationFalseClasses__() {
		return null;
	}
}
