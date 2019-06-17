package org.cyk.utility.system;

import java.util.Collection;
import java.util.Map;

import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemSubLayer;
import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.junit.jupiter.api.Test;

public abstract class AbstractSystemLayerUnitTestWithDefaultDeployment extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;

	protected abstract SystemLayer __injectSystemLayer__();
	
	/* Entity */
	
	protected abstract String __getExpectedEntityPackageNameRegularExpression__();
	protected abstract String __getExpectedEntityClassNameRegularExpression__();
	protected abstract String __getExpectedEntityInterfaceNameRegularExpression__();
	protected abstract Collection<String> __getEntityTruePackages__();
	protected abstract Collection<String> __getEntityFalsePackages__();
	protected abstract Collection<String> __getEntityTrueInterfaces__();
	protected abstract Collection<String> __getEntityFalseInterfaces__();
	protected abstract Collection<String> __getEntityTrueClasses__();
	protected abstract Collection<String> __getEntityFalseClasses__();
	
	/* Interface */
	
	protected abstract String __getExpectedInterfacePackageNameRegularExpression__();
	protected abstract String __getExpectedInterfaceClassNameRegularExpression__();
	protected abstract String __getExpectedInterfaceInterfaceNameRegularExpression__();
	protected abstract Collection<String> __getInterfaceTruePackages__();
	protected abstract Collection<String> __getInterfaceFalsePackages__();
	protected abstract Collection<String> __getInterfaceTrueInterfaces__();
	protected abstract Collection<String> __getInterfaceFalseInterfaces__();
	protected abstract Collection<String> __getInterfaceTrueClasses__();
	protected abstract Collection<String> __getInterfaceFalseClasses__();
	protected abstract Map<String,String> __getExpectedInterfaceNameFromEntityClassName__();
	protected Map<SystemSubLayer,Map<String,String>> __getExpectedInterfaceNameFrom__(){
		return null;
	}
	//protected abstract Map<String,String> __getExpectedInterfaceNameFromPersistenceEntityClassName__();
	
	/* Implementation */
	
	protected abstract String __getExpectedImplementationPackageNameRegularExpression__();
	protected abstract String __getExpectedImplementationClassNameRegularExpression__();
	protected abstract String __getExpectedImplementationInterfaceNameRegularExpression__();
	protected abstract Collection<String> __getImplementationTruePackages__();
	protected abstract Collection<String> __getImplementationFalsePackages__();
	protected abstract Collection<String> __getImplementationTrueInterfaces__();
	protected abstract Collection<String> __getImplementationFalseInterfaces__();
	protected abstract Collection<String> __getImplementationTrueClasses__();
	protected abstract Collection<String> __getImplementationFalseClasses__();
	
	@Test
	public void getEntityPackageNameRegularExpression() {
		assertionHelper.assertEquals(__getExpectedEntityPackageNameRegularExpression__(), __injectSystemLayer__().getEntityLayer().getPackageNameRegularExpression(Boolean.TRUE).getExpression());
	}
	
	@Test
	public void getEntityClassNameRegularExpression() {
		assertionHelper.assertEquals(__getExpectedEntityClassNameRegularExpression__(), __injectSystemLayer__().getEntityLayer().getClassNameRegularExpression(Boolean.TRUE).getExpression());
	}
	
	@Test
	public void getEntityInterfaceNameRegularExpression() {
		assertionHelper.assertEquals(__getExpectedEntityInterfaceNameRegularExpression__(), __injectSystemLayer__().getEntityLayer().getInterfaceNameRegularExpression(Boolean.TRUE).getExpression());
	}
	
	@Test
	public void isEntityTruePackages() {
		__assertPackages__(__injectSystemLayer__().getEntityLayer(), __getEntityTruePackages__(), Boolean.TRUE,"entity");
	}
	
	@Test
	public void isEntityFalsePackages() {
		__assertPackages__(__injectSystemLayer__().getEntityLayer(), __getEntityFalsePackages__(), Boolean.FALSE,"entity");
	}
	
	@Test
	public void isEntityTrueInterfaces() {
		__assertInterfaces__(__injectSystemLayer__().getEntityLayer(), __getEntityTrueInterfaces__(), Boolean.TRUE,"entity");
	}
	
	@Test
	public void isEntityFalseInterfaces() {
		__assertInterfaces__(__injectSystemLayer__().getEntityLayer(), __getEntityFalseInterfaces__(), Boolean.FALSE,"entity");
	}
	
	@Test
	public void isEntityTrueClasses() {
		__assertClasses__(__injectSystemLayer__().getEntityLayer(), __getEntityTrueClasses__(), Boolean.TRUE,"entity");
	}
	
	@Test
	public void isEntityFalseClasses() {
		__assertClasses__(__injectSystemLayer__().getEntityLayer(), __getEntityFalseClasses__(), Boolean.FALSE,"entity");
	}
	
	/* Interface */
	
	@Test
	public void getInterfacePackageNameRegularExpression() {
		assertionHelper.assertEquals(__getExpectedInterfacePackageNameRegularExpression__(), __injectSystemLayer__().getInterfaceLayer().getPackageNameRegularExpression(Boolean.TRUE).getExpression());
	}
	
	@Test
	public void getInterfaceClassNameRegularExpression() {
		assertionHelper.assertEquals(__getExpectedInterfaceClassNameRegularExpression__(), __injectSystemLayer__().getInterfaceLayer().getClassNameRegularExpression(Boolean.TRUE).getExpression());
	}
	
	@Test
	public void getInterfaceInterfaceNameRegularExpression() {
		assertionHelper.assertEquals(__getExpectedInterfaceInterfaceNameRegularExpression__(), __injectSystemLayer__().getInterfaceLayer().getInterfaceNameRegularExpression(Boolean.TRUE).getExpression());
	}
	
	@Test
	public void isInterfaceTruePackages() {
		__assertPackages__(__injectSystemLayer__().getInterfaceLayer(), __getInterfaceTruePackages__(), Boolean.TRUE,"interface");
	}
	
	@Test
	public void isInterfaceFalsePackages() {
		__assertPackages__(__injectSystemLayer__().getInterfaceLayer(), __getInterfaceFalsePackages__(), Boolean.FALSE,"interface");
	}
	
	@Test
	public void isInterfaceTrueInterfaces() {
		__assertInterfaces__(__injectSystemLayer__().getInterfaceLayer(), __getInterfaceTrueInterfaces__(), Boolean.TRUE,"interface");
	}
	
	@Test
	public void isInterfaceFalseInterfaces() {
		__assertInterfaces__(__injectSystemLayer__().getInterfaceLayer(), __getInterfaceFalseInterfaces__(), Boolean.FALSE,"interface");
	}
	
	@Test
	public void isInterfaceTrueClasses() {
		__assertClasses__(__injectSystemLayer__().getInterfaceLayer(), __getInterfaceTrueClasses__(), Boolean.TRUE,"interface");
	}
	
	@Test
	public void isInterfaceFalseClasses() {
		__assertClasses__(__injectSystemLayer__().getInterfaceLayer(), __getInterfaceFalseClasses__(), Boolean.FALSE,"interface");
	}
	
	@Test
	public void getInterfaceNameFromEntityClassName() {
		Map<String,String> map = __getExpectedInterfaceNameFromEntityClassName__();
		if(map!=null)
			for(Map.Entry<String, String> index : map.entrySet()) {
				assertionHelper.assertEquals(index.getValue(), __injectSystemLayer__().getInterfaceNameFromEntityClassName(index.getKey()));
			}
	}
	
	@Test
	public void getInterfaceNameFromClassName() {
		Map<SystemSubLayer,Map<String,String>> map = __getExpectedInterfaceNameFrom__();
		if(map!=null) {
			for(Map.Entry<SystemSubLayer,Map<String,String>> indexEntry : map.entrySet()) {
				if(indexEntry.getKey()!=null && indexEntry.getValue()!=null) {
					for(Map.Entry<String, String> indexEntrySystemSubLayer : indexEntry.getValue().entrySet()) {
						assertionHelper.assertEquals(indexEntrySystemSubLayer.getValue(), __injectSystemLayer__().getInterfaceNameFrom(indexEntrySystemSubLayer.getKey()
								,indexEntry.getKey()));
					}
				}
			}
		}
	}
	
	/* Implementation */
	
	@Test
	public void getImplementationPackageNameRegularExpression() {
		assertionHelper.assertEquals(__getExpectedImplementationPackageNameRegularExpression__(), __injectSystemLayer__().getImplementationLayer().getPackageNameRegularExpression(Boolean.TRUE).getExpression());
	}
	
	@Test
	public void getImplementationClassNameRegularExpression() {
		assertionHelper.assertEquals(__getExpectedImplementationClassNameRegularExpression__(), __injectSystemLayer__().getImplementationLayer().getClassNameRegularExpression(Boolean.TRUE).getExpression());
	}
	
	@Test
	public void getImplementationInterfaceNameRegularExpression() {
		assertionHelper.assertEquals(__getExpectedImplementationInterfaceNameRegularExpression__(), __injectSystemLayer__().getImplementationLayer().getInterfaceNameRegularExpression(Boolean.TRUE).getExpression());
	}
	
	@Test
	public void isImplementationTruePackages() {
		__assertPackages__(__injectSystemLayer__().getImplementationLayer(), __getImplementationTruePackages__(), Boolean.TRUE,"implementation");
	}
	
	@Test
	public void isImplementationFalsePackages() {
		__assertPackages__(__injectSystemLayer__().getImplementationLayer(), __getImplementationFalsePackages__(), Boolean.FALSE,"implementation");
	}
	
	@Test
	public void isImplementationTrueImplementations() {
		__assertClasses__(__injectSystemLayer__().getImplementationLayer(), __getImplementationTrueClasses__(), Boolean.TRUE,"implementation");
	}
	
	@Test
	public void isImplementationFalseImplementations() {
		__assertClasses__(__injectSystemLayer__().getImplementationLayer(), __getImplementationTrueClasses__(), Boolean.FALSE,"implementation");
	}
	
	@Test
	public void isImplementationTrueClasses() {
		__assertClasses__(__injectSystemLayer__().getImplementationLayer(), __getImplementationTrueClasses__(), Boolean.TRUE,"implementation");
	}
	
	@Test
	public void isImplementationFalseClasses() {
		__assertClasses__(__injectSystemLayer__().getImplementationLayer(), __getImplementationFalseClasses__(), Boolean.FALSE,"implementation");
	}
	
	/**/
	
	private void __assertPackages__(SystemSubLayer subLayer,Collection<String> collection,Boolean isTrue,String description) {
		if(__inject__(CollectionHelper.class).isNotEmpty(collection))
			for(String index : collection)
				assertionHelper.assertEquals(index+" is "+(isTrue ? "not" : "")+" a valid "+description+" package for "+subLayer,isTrue,subLayer.isPackage(index));
	}
	
	private void __assertInterfaces__(SystemSubLayer subLayer,Collection<String> collection,Boolean isTrue,String description) {
		if(__inject__(CollectionHelper.class).isNotEmpty(collection))
			for(String index : collection)
				assertionHelper.assertEquals(index+" is "+(isTrue ? "not" : "")+" a valid "+description+" interface for "+subLayer,isTrue,subLayer.isInterface(index));
	}
	
	private void __assertClasses__(SystemSubLayer subLayer,Collection<String> collection,Boolean isTrue,String description) {
		if(__inject__(CollectionHelper.class).isNotEmpty(collection))
			for(String index : collection)
				assertionHelper.assertEquals(index+" is "+(isTrue ? "not" : "")+" a valid "+description+" class for "+subLayer,isTrue,subLayer.isClass(index));
	}
}
