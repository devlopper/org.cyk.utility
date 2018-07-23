package org.cyk.utility.system;

import org.cyk.utility.system.layer.SystemLayerPersistence;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class SystemLayerPersistenceUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void isIdentifierEqualsPersistence() {
		assertionHelper.assertEquals("Persistence", __inject__(SystemLayerPersistence.class).getIdentifier());
	}
	
	@Test
	public void isEntityPackageNameTokenEqualsEntities() {
		assertionHelper.assertEquals(".entities.", __inject__(SystemLayerPersistence.class).getEntityPackageNameToken());
	}
	
	@Test
	public void isInterfacePackageNameTokenEqualsApi() {
		assertionHelper.assertEquals(".api.", __inject__(SystemLayerPersistence.class).getInterfacePackageNameToken());
	}
	
	@Test
	public void isInterfaceNameSuffixEqualsPersistence() {
		assertionHelper.assertEquals("Persistence", __inject__(SystemLayerPersistence.class).getInterfaceNameSuffix());
	}
	
	@Test
	public void getInterfaceNameFromEntityClassName() {
		assertionHelper.assertEquals("package01.api.MyEntity01Persistence", __inject__(SystemLayerPersistence.class).getInterfaceNameFromEntityClassName("package01.entities.MyEntity01"));
	}
	
	@Test
	public void getInterfaceNameFromEntityClassName02() {
		assertionHelper.assertEquals("org.cyk.system.datastructure.server.persistence.api.collection.set.nested.NestedSetPersistence"
				, __inject__(SystemLayerPersistence.class).getInterfaceNameFromEntityClassName("org.cyk.system.datastructure.server.persistence.entities.collection.set.nested.NestedSet"));
	}
}
