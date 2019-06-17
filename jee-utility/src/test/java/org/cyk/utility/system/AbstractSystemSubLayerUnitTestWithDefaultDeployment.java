package org.cyk.utility.system;

import java.util.Collection;
import java.util.Map;

import org.cyk.utility.system.layer.SystemSubLayer;
import org.cyk.utility.system.layer.SystemSubLayerInterface;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public abstract class AbstractSystemSubLayerUnitTestWithDefaultDeployment extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	protected abstract SystemSubLayer __injectSystemSubLayer__();
	
	protected abstract String __getExpectedPackageNameRegularExpression__();
	protected abstract String __getExpectedClassNameRegularExpression__();
	protected abstract String __getExpectedInterfaceNameRegularExpression__();
	protected abstract Collection<String> __getTruePackages__();
	protected abstract Collection<String> __getFalsePackages__();
	
	protected Map<SystemSubLayer,Map<String,String>> __getExpectedInterfaceNameFromClassName__(){
		return null;
	}
	
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
	
	@Test
	public void getInterfaceNameFromClassName() {
		Map<SystemSubLayer,Map<String,String>> map = __getExpectedInterfaceNameFromClassName__();
		if(map!=null) {
			for(Map.Entry<SystemSubLayer,Map<String,String>> indexEntry : map.entrySet()) {
				if(indexEntry.getKey()!=null && indexEntry.getValue()!=null) {
					for(Map.Entry<String,String> indexEntrySub : indexEntry.getValue().entrySet()) {
						assertionHelper.assertEquals(indexEntrySub.getValue(), __inject__(SystemSubLayerInterface.class).getInterfaceNameFromClassName(indexEntrySub.getKey(), indexEntry.getKey()));
					}
				}
			}
		}
	}
	
	/**/
	
}
