package org.cyk.utility.system;

import java.util.Collection;

import org.cyk.utility.system.layer.SystemSubLayer;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public abstract class AbstractSystemSubLayerUnitTestWithDefaultDeployment extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	protected abstract SystemSubLayer __injectSystemSubLayer__();
	
	protected abstract String __getExpectedPackageNameRegularExpression__();
	protected abstract String __getExpectedClassNameRegularExpression__();
	protected abstract String __getExpectedInterfaceNameRegularExpression__();
	protected abstract Collection<String> __getTruePackages__();
	protected abstract Collection<String> __getFalsePackages__();
	
	@Test
	public void getPackageNameRegularExpression() {
		assertionHelper.assertEquals(__getExpectedPackageNameRegularExpression__(), __injectSystemSubLayer__().getPackageNameRegularExpression(Boolean.TRUE).getExpression());
	}
	
	@Test
	public void getClassNameRegularExpression() {
		assertionHelper.assertEquals(__getExpectedClassNameRegularExpression__(), __injectSystemSubLayer__().getClassNameRegularExpression(Boolean.TRUE).getExpression());
	}
	
	@Test
	public void getInterfaceNameRegularExpression() {
		assertionHelper.assertEquals(__getExpectedInterfaceNameRegularExpression__(), __injectSystemSubLayer__().getInterfaceNameRegularExpression(Boolean.TRUE).getExpression());
	}
	
	@Test
	public void isTruePackages() {
		SystemSubLayer subLayer = __injectSystemSubLayer__();
		for(String index : __getTruePackages__())
			assertionHelper.assertTrue(index+" is not a valid package for "+subLayer,subLayer.isPackage(index));
	}
	
	@Test
	public void isFalsePackages() {
		SystemSubLayer subLayer = __injectSystemSubLayer__();
		for(String index : __getFalsePackages__())
			assertionHelper.assertFalse(index+" is a valid package for "+subLayer,subLayer.isPackage(index));
	}
	
	/**/
	
}
