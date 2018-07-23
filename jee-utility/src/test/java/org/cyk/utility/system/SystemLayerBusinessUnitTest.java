package org.cyk.utility.system;

import org.cyk.utility.system.layer.SystemLayerBusiness;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class SystemLayerBusinessUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void isIdentifierEqualsPersistence() {
		assertionHelper.assertEquals("Business", __inject__(SystemLayerBusiness.class).getIdentifier());
	}
	
	@Test
	public void isEntityPackageNameTokenEqualsEntities() {
		assertionHelper.assertEquals(".entities.", __inject__(SystemLayerBusiness.class).getEntityPackageNameToken());
	}
	
	@Test
	public void isInterfacePackageNameTokenEqualsApi() {
		assertionHelper.assertEquals(".api.", __inject__(SystemLayerBusiness.class).getInterfacePackageNameToken());
	}
	
	@Test
	public void isInterfaceNameSuffixEqualsBusiness() {
		assertionHelper.assertEquals("Business", __inject__(SystemLayerBusiness.class).getInterfaceNameSuffix());
	}
	
	@Test
	public void getInterfaceNameFromEntityClassName() {
		assertionHelper.assertEquals("package01.api.MyEntity01Business", __inject__(SystemLayerBusiness.class).getInterfaceNameFromEntityClassName("package01.entities.MyEntity01"));
	}
	
	@Test
	public void getInterfaceNameFromEntityClassName02() {
		assertionHelper.assertEquals("org.cyk.system.datastructure.server.business.api.collection.set.nested.NestedSetBusiness"
				, __inject__(SystemLayerBusiness.class).getInterfaceNameFromEntityClassName("org.cyk.system.datastructure.server.persistence.entities.collection.set.nested.NestedSet"));
	}
}
