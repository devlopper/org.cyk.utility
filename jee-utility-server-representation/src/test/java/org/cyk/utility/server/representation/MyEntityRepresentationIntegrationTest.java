package org.cyk.utility.server.representation;

import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram;
import org.junit.Test;

public class MyEntityRepresentationIntegrationTest extends AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram<MyEntity> {
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
