package org.cyk.utility.server.business;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.server.persistence.test.TestPersistenceCreate;
import org.junit.Test;

public class BusinessIntegrationTest extends AbstractArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
		
	@Inject private Business business;
	
	@Test
	public void create() throws Exception{
		business.create(new MyEntity().setCode(getRandomCode()));
	}
	/*
	@Test
	public void read() throws Exception{
		String code = getRandomCode();
		MyEntity myEntity = new MyEntity().setCode(code);
		userTransaction.begin();
		persistence.create(myEntity);
		userTransaction.commit();
		assertThat(myEntity.getIdentifier()).isNotNull();
		myEntity = persistence.readOne(MyEntity.class,myEntity.getIdentifier());
		assertThat(logEventEntityRepository.getLastMessage()).startsWith("Server Persistence Read MyEntity");
		assertThat(myEntity).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo(code);
	}
	
	@Test
	public void update() throws Exception{
		MyEntity myEntity = new MyEntity().setCode(getRandomCode());
		userTransaction.begin();
		persistence.create(myEntity);
		userTransaction.commit();
		myEntity = persistence.readOne(MyEntity.class,myEntity.getIdentifier());
		String newCode = getRandomCode();
		myEntity.setCode(newCode);
		userTransaction.begin();
		persistence.update(myEntity);
		userTransaction.commit();
		assertThat(logEventEntityRepository.getLastMessage()).startsWith("Server Persistence Update MyEntity");
		myEntity = persistence.readOne(MyEntity.class,myEntity.getIdentifier());
		assertThat(myEntity).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo(newCode);
	}
	
	@Test
	public void delete() throws Exception{
		MyEntity myEntity = new MyEntity().setCode(getRandomCode());
		userTransaction.begin();
		persistence.create(myEntity);
		userTransaction.commit();
		myEntity = persistence.readOne(MyEntity.class,myEntity.getIdentifier());
		assertThat(myEntity).isNotNull();
		userTransaction.begin();
		persistence.delete(myEntity);
		userTransaction.commit();
		assertThat(logEventEntityRepository.getLastMessage()).startsWith("Server Persistence Delete MyEntity");
		myEntity = persistence.readOne(MyEntity.class,myEntity.getIdentifier());
		assertThat(myEntity).isNull();
	}
	
	@Test(expected=javax.transaction.RollbackException.class)
	public void isCodeMustBeUnique() throws Exception{
		String code = getRandomCode();
		userTransaction.begin();
		persistence.create(new MyEntity().setCode(code));
		assertThat(logEventEntityRepository.getLastMessage()).startsWith("Server Persistence Create MyEntity").contains("code="+code);
		persistence.create(new MyEntity().setCode(code));
		userTransaction.commit();
	}
	
	@Test(expected=javax.transaction.RollbackException.class)
	public void isCodeMustBeNotNull() throws Exception{
		userTransaction.begin();
		persistence.create(new MyEntity());
		userTransaction.commit();
	}
	*/
}
