package org.cyk.utility.server.business;

import org.cyk.utility.assertion.AssertionsProviderClassMap;
import org.cyk.utility.server.business.test.arquillian.AbstractBusinessEntityIntegrationTestWithDefaultDeployment;
import org.cyk.utility.server.persistence.entities.MyEntity;
import org.junit.Test;

public class MyEntityBusinessIntegrationTestToBeMoved extends AbstractBusinessEntityIntegrationTestWithDefaultDeployment<MyEntity> {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected <T> T __instanciate__(Class<T> aClass, Object action) throws Exception {
		T t = super.__instanciate__(aClass, action);
		((MyEntity)t)/*.setTimestamp(System.currentTimeMillis())*/.setLong1(2l);
		return t;
	}
	
	@Test
	public void createWithLong1Null(){
		MyEntity myEntity = new MyEntity().setCode("c01").setLong1(1l)/*.setTimestamp(1l)*/;
		__createEntity__(myEntity);
		__deleteEntitiesAll__(MyEntity.class);
	}
	
	@Test
	public void createWithAssertionFail(){
		__inject__(AssertionsProviderClassMap.class).set(MyEntity.class, MyEntityAssertionsProvider.class);
		MyEntity myEntity = new MyEntity().setCode("c01").setLong1(-11l);
		__createEntity__(myEntity);
	}
}
