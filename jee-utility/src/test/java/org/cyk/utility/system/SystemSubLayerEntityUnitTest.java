package org.cyk.utility.system;

import java.util.Arrays;
import java.util.Collection;

import org.cyk.utility.system.layer.SystemSubLayer;
import org.cyk.utility.system.layer.SystemSubLayerEntity;

public class SystemSubLayerEntityUnitTest extends AbstractSystemSubLayerUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Override
	protected SystemSubLayer __injectSystemSubLayer__() {
		return __inject__(SystemSubLayerEntity.class);
	}

	@Override
	protected String __getExpectedPackageNameRegularExpression__() {
		return "[.]{0,1}entities[.]{0,1}";
	}

	@Override
	protected String __getExpectedClassNameRegularExpression__() {
		return null;
	}

	@Override
	protected String __getExpectedInterfaceNameRegularExpression__() {
		return null;
	}
	
	@Override
	protected Collection<String> __getTruePackages__() {
		return Arrays.asList("a.entities.b","a.entities","entities.b","entities");
	}
	
	@Override
	protected Collection<String> __getFalsePackages__() {
		return Arrays.asList("a.entitie.b","a.entitie","entitie.b","entitie");
	}
	
}
