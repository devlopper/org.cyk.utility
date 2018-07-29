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
		Assert.assertEquals(new Long(3), ____inject____(MyEntityPersistence.class).countByIntegerValue(2));
	}
	
	@Test
	public void executeIncrementIntegerValue(){
		__createEntity__(new MyEntity().setCode("e01A").setIntegerValue(10));
		__createEntity__(new MyEntity().setCode("e02B").setIntegerValue(20));
		__createEntity__(new MyEntity().setCode("e03C").setIntegerValue(10));
		__createEntity__(new MyEntity().setCode("e04D").setIntegerValue(20));
		__createEntity__(new MyEntity().setCode("e05E").setIntegerValue(20));
		
		try {
			userTransaction.begin();
			____inject____(MyEntityPersistence.class).executeIncrementIntegerValue(7);
			userTransaction.commit();	
		}catch(Exception exception) {
			throw new RuntimeException(exception);
		}
		
		MyEntity myEntity = ____inject____(MyEntityPersistence.class).readOneByBusinessIdentifier("e02B");
		Assert.assertEquals(new Integer(27), myEntity.getIntegerValue());
		
	}
}
