package org.cyk.utility.system;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.map.MapHelper;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerBusiness;
import org.junit.Test;

public class SystemLayerBusinessUnitTest extends AbstractSystemLayerUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void getInterfaceNameFromPersistenceEntityClassName() {
		assertionHelper.assertEquals("com.business.api.MyClassBusiness", __inject__(SystemLayerBusiness.class).getInterfaceNameFromPersistenceEntityClassName("com.persistence.entities.MyClass"));
	}
	
	/* Entity */
	
	@Override
	protected SystemLayer __injectSystemLayer__() {
		return __inject__(SystemLayerBusiness.class);
	}

	@Override
	protected String __getExpectedEntityPackageNameRegularExpression__() {
		return "[.]{0,1}business.entities[.]{0,1}";
	}

	@Override
	protected Collection<String> __getEntityTruePackages__() {
		return Arrays.asList("a.business.entities.b","a.business.entities","business.entities.b","business.entities");
	}

	@Override
	protected Collection<String> __getEntityFalsePackages__() {
		return Arrays.asList("a.business.c.entities.b","a.business.c.entities","business.c.entities.b","business.c.entities",
				"entities","business");
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
		return "[.]{0,1}business.api[.]{0,1}";
	}

	@Override
	protected String __getExpectedInterfaceClassNameRegularExpression__() {
		return null;
	}

	@Override
	protected String __getExpectedInterfaceInterfaceNameRegularExpression__() {
		return "Business$";
	}

	@Override
	protected Collection<String> __getInterfaceTruePackages__() {
		return Arrays.asList("a.business.api.b","a.business.api","business.api.b","business.api");
	}

	@Override
	protected Collection<String> __getInterfaceFalsePackages__() {
		return Arrays.asList("a.business.c.api.b","a.business.c.api","business.c.api.b","business.c.api",
				"api","business");
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
		return __inject__(MapHelper.class).instanciateKeyAsStringValueAsString("business.entities.MyClass","business.api.MyClassBusiness"
				,"p.business.entities.MyClass","p.business.api.MyClassBusiness"
				,"p.business.entities.p1.MyClass","p.business.api.p1.MyClassBusiness");
	}
	
	/* Implementation */

	@Override
	protected String __getExpectedImplementationPackageNameRegularExpression__() {
		return "[.]{0,1}business.impl[.]{0,1}";
	}

	@Override
	protected String __getExpectedImplementationClassNameRegularExpression__() {
		return "BusinessImpl$";
	}

	@Override
	protected String __getExpectedImplementationInterfaceNameRegularExpression__() {
		return null;
	}

	@Override
	protected Collection<String> __getImplementationTruePackages__() {
		return Arrays.asList("a.business.impl.b","a.business.impl","business.impl.b","business.impl");
	}

	@Override
	protected Collection<String> __getImplementationFalsePackages__() {
		return Arrays.asList("a.business.c.impl.b","a.business.c.impl","business.c.impl.b","business.c.impl",
				"impl","business");
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
