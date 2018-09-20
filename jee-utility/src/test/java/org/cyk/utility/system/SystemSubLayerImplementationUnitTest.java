package org.cyk.utility.system;

import java.util.Arrays;
import java.util.Collection;

import org.cyk.utility.system.layer.SystemSubLayer;
import org.cyk.utility.system.layer.SystemSubLayerImplementation;

public class SystemSubLayerImplementationUnitTest extends AbstractSystemSubLayerUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Override
	protected SystemSubLayer __injectSystemSubLayer__() {
		return __inject__(SystemSubLayerImplementation.class);
	}

	@Override
	protected String __getExpectedPackageNameRegularExpression__() {
		return "[.]{0,1}impl[.]{0,1}";
	}

	@Override
	protected String __getExpectedClassNameRegularExpression__() {
		return "Impl";
	}

	@Override
	protected String __getExpectedInterfaceNameRegularExpression__() {
		return null;
	}
	
	@Override
	protected Collection<String> __getTruePackages__() {
		return Arrays.asList("a.impl.b","a.impl","impl.b","impl");
	}
	
	@Override
	protected Collection<String> __getFalsePackages__() {
		return Arrays.asList("a.imp.b","a.imp","imp.b","imp");
	}
}
