package org.cyk.utility.server.persistence.jpa;

import java.util.Collection;

import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceEntityIntegrationTestWithDefaultDeploymentAsSwram;
import org.junit.Assert;
import org.junit.Test;

public class MyEntityPersistenceIntegrationTest extends AbstractPersistenceEntityIntegrationTestWithDefaultDeploymentAsSwram<MyEntity> {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void readByIntegerValue(){
		__createEntity__(new MyEntity().setCode("e01").setIntegerValue(1));
		__createEntity__(new MyEntity().setCode("e02").setIntegerValue(2));
		__createEntity__(new MyEntity().setCode("e03").setIntegerValue(1));
		__createEntity__(new MyEntity().setCode("e04").setIntegerValue(2));
		__createEntity__(new MyEntity().setCode("e05").setIntegerValue(2));
		
		Collection<MyEntity> collection = ____inject____(MyEntityPersistence.class).readByIntegerValue(2);
		Assert.assertNotNull(collection);
		Assert.assertEquals(3, collection.size());
	}
}
