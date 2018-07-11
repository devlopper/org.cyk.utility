package org.cyk.utility.server.persistence.jpa;

import org.cyk.utility.server.persistence.test.TestPersistenceCreate;
import org.junit.Test;

public class TestPersistenceIntegrationUnitTest extends AbstractArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void create(){
		__inject__(TestPersistenceCreate.class).setObject(new MyEntity().setCode(getRandomCode())).execute();
	}
	
	@Test
	public void isCodeMustBeUnique(){
		String code = getRandomCode();
		__inject__(TestPersistenceCreate.class).addObjects(new MyEntity().setCode(code),new MyEntity().setCode(code)).execute()
			.assertThrowableCauseIsInstanceOfSqlException();
	}
	
}
