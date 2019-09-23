package org.cyk.utility.system;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.map.MapHelper;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerPersistence;
import org.cyk.utility.system.layer.SystemSubLayer;

public class SystemLayerPersistenceUnitTest extends AbstractSystemLayerUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	/* Entity */
	
	@Override
	protected SystemLayer __injectSystemLayer__() {
		return __inject__(SystemLayerPersistence.class);
	}

	@Override
	protected String __getExpectedEntityPackageNameRegularExpression__() {
		return "[.]{0,1}persistence.entities[.]{0,1}";
	}

	@Override
	protected Collection<String> __getEntityTruePackages__() {
		return List.of("a.persistence.entities.b","a.persistence.entities","persistence.entities.b","persistence.entities");
	}

	@Override
	protected Collection<String> __getEntityFalsePackages__() {
		return List.of("a.persistence.c.entities.b","a.persistence.c.entities","persistence.c.entities.b","persistence.c.entities",
				"entities","persistence");
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
		return null;
	}
	
	@Override
	protected Collection<String> __getEntityTrueClasses__() {
		return null;
	}

	@Override
	protected Collection<String> __getEntityFalseClasses__() {
		return null;
	}

	/* Interface */
	
	@Override
	protected String __getExpectedInterfacePackageNameRegularExpression__() {
		return "[.]{0,1}persistence.api[.]{0,1}";
	}

	@Override
	protected String __getExpectedInterfaceClassNameRegularExpression__() {
		return null;
	}

	@Override
	protected String __getExpectedInterfaceInterfaceNameRegularExpression__() {
		return "Persistence$";
	}

	@Override
	protected Collection<String> __getInterfaceTruePackages__() {
		return List.of("a.persistence.api.b","a.persistence.api","persistence.api.b","persistence.api");
	}

	@Override
	protected Collection<String> __getInterfaceFalsePackages__() {
		return List.of("a.persistence.c.api.b","a.persistence.c.api","persistence.c.api.b","persistence.c.api",
				"api","persistence");
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
		return __inject__(MapHelper.class).instanciateKeyAsStringValueAsString("persistence.entities.MyClass","persistence.api.MyClassPersistence"
				,"p.persistence.entities.MyClass","p.persistence.api.MyClassPersistence"
				,"p.persistence.entities.p1.MyClass","p.persistence.api.p1.MyClassPersistence"
				,"MyClass","MyClassPersistence");
	}
	
	@Override
	protected Map<SystemSubLayer,Map<String, String>> __getExpectedInterfaceNameFrom__() {
		Map<SystemSubLayer,Map<String, String>> map = new LinkedHashMap<>();
		map.put(__inject__(SystemLayerPersistence.class).getEntityLayer(), __inject__(MapHelper.class).instanciateKeyAsStringValueAsString(
				"persistence.entities.MyClass","persistence.api.MyClassPersistence"
				,"p.persistence.entities.MyClass","p.persistence.api.MyClassPersistence"
				,"p.persistence.entities.p1.MyClass","p.persistence.api.p1.MyClassPersistence"
				,"MyClass","MyClassPersistence"));
		return map;
	}
	
	/* Implementation */

	@Override
	protected String __getExpectedImplementationPackageNameRegularExpression__() {
		return "[.]{0,1}persistence.impl[.]{0,1}";
	}

	@Override
	protected String __getExpectedImplementationClassNameRegularExpression__() {
		return "PersistenceImpl$";
	}

	@Override
	protected String __getExpectedImplementationInterfaceNameRegularExpression__() {
		return null;
	}

	@Override
	protected Collection<String> __getImplementationTruePackages__() {
		return List.of("a.persistence.impl.b","a.persistence.impl","persistence.impl.b","persistence.impl");
	}

	@Override
	protected Collection<String> __getImplementationFalsePackages__() {
		return List.of("a.persistence.c.impl.b","a.persistence.c.impl","persistence.c.impl.b","persistence.c.impl",
				"impl","persistence");
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
