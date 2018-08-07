package org.cyk.utility.server.business;

import org.cyk.utility.server.business.test.arquillian.AbstractBusinessEntityIntegrationTestWithDefaultDeploymentAsSwram;
import org.junit.Test;

public class MyEntityBusinessIntegrationTest extends AbstractBusinessEntityIntegrationTestWithDefaultDeploymentAsSwram<MyEntity> {
	private static final long serialVersionUID = 1L;
		
	@Override
	protected MyEntity __instanciateEntity__(Object action) throws Exception {
		return super.__instanciateEntity__(action).setLong1(2l);
	}
	
	@Test
	public void createWithLong1Null(){
		MyEntity myEntity = new MyEntity().setCode("c01").setLong1(1l);
		__createEntity__(myEntity);
	}
}
