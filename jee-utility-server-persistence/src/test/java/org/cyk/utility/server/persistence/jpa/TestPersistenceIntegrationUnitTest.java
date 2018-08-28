package org.cyk.utility.server.persistence.jpa;

import org.cyk.utility.server.persistence.test.TestPersistenceCreate;
import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceArquillianIntegrationTestWithDefaultDeploymentAsSwram;
import org.junit.Test;

public class TestPersistenceIntegrationUnitTest extends AbstractPersistenceArquillianIntegrationTestWithDefaultDeploymentAsSwram {
	private static final long serialVersionUID = 1L;

	@Test
	public void create(){
		__inject__(TestPersistenceCreate.class).addObjects(new MyEntity().setCode(__getRandomCode__())).execute();
	}
	
	@Test
	public void isCodeMustBeUnique(){
		String code = __getRandomCode__();
		__inject__(TestPersistenceCreate.class).addObjects(new MyEntity().setCode(code),new MyEntity().setCode(code)).execute()
			.assertThrowableCauseIsInstanceOfSqlException();
	}
	
}
