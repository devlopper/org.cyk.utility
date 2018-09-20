package org.cyk.utility.system;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.cyk.utility.map.MapHelper;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerRepresentation;

public class SystemLayerRepresentationUnitTest extends AbstractSystemLayerUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	/* Entity */
	
	@Override
	protected SystemLayer __injectSystemLayer__() {
		return __inject__(SystemLayerRepresentation.class);
	}

	@Override
	protected String __getExpectedEntityPackageNameRegularExpression__() {
		return "[.]{0,1}representation.entities[.]{0,1}";
	}

	@Override
	protected Collection<String> __getEntityTruePackages__() {
		return Arrays.asList("a.representation.entities.b","a.representation.entities","representation.entities.b","representation.entities");
	}

	@Override
	protected Collection<String> __getEntityFalsePackages__() {
		return Arrays.asList("a.representation.c.entities.b","a.representation.c.entities","representation.c.entities.b","representation.c.entities",
				"entities","representation");
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
		return "representation.entities..+Dto$";
	}
	
	@Override
	protected Collection<String> __getEntityTrueClasses__() {
		return Arrays.asList("a.representation.entities.b.MyClassDto","a.representation.entities.MyClassDto","representation.entities.b.MyClassDto","representation.entities.MyClassDto");
	}

	@Override
	protected Collection<String> __getEntityFalseClasses__() {
		return Arrays.asList("a.representation.entitie.MyClassDto","a.representation.entities.Dto","representation.entities.b.MyClassDo","representation.entities.MyClassDtos");
	}

	/* Interface */
	
	@Override
	protected String __getExpectedInterfacePackageNameRegularExpression__() {
		return "[.]{0,1}representation.api[.]{0,1}";
	}

	@Override
	protected String __getExpectedInterfaceClassNameRegularExpression__() {
		return null;
	}

	@Override
	protected String __getExpectedInterfaceInterfaceNameRegularExpression__() {
		return "Representation$";
	}

	@Override
	protected Collection<String> __getInterfaceTruePackages__() {
		return Arrays.asList("a.representation.api.b","a.representation.api","representation.api.b","representation.api");
	}

	@Override
	protected Collection<String> __getInterfaceFalsePackages__() {
		return Arrays.asList("a.representation.c.api.b","a.representation.c.api","representation.c.api.b","representation.c.api",
				"api","representation");
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
		return __inject__(MapHelper.class).instanciateKeyAsStringValueAsString("representation.entities.MyClassDto","representation.api.MyClassRepresentation"
				,"p.representation.entities.MyClassDto","p.representation.api.MyClassRepresentation"
				,"p.representation.entities.p1.MyClassDto","p.representation.api.p1.MyClassRepresentation");
	}
	
	/* Implementation */

	@Override
	protected String __getExpectedImplementationPackageNameRegularExpression__() {
		return "[.]{0,1}representation.impl[.]{0,1}";
	}

	@Override
	protected String __getExpectedImplementationClassNameRegularExpression__() {
		return "RepresentationImpl$";
	}

	@Override
	protected String __getExpectedImplementationInterfaceNameRegularExpression__() {
		return null;
	}

	@Override
	protected Collection<String> __getImplementationTruePackages__() {
		return Arrays.asList("a.representation.impl.b","a.representation.impl","representation.impl.b","representation.impl");
	}

	@Override
	protected Collection<String> __getImplementationFalsePackages__() {
		return Arrays.asList("a.representation.c.impl.b","a.representation.c.impl","representation.c.impl.b","representation.c.impl",
				"impl","representation");
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
