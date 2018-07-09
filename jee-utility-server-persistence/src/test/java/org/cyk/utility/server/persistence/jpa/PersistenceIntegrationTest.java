package org.cyk.utility.server.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.cyk.utility.server.persistence.Persistence;
import org.junit.Test;

public class PersistenceIntegrationTest extends AbstractArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
		
	@Inject private Persistence persistence;
	
	@Test
	public void create() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("mc001");
		userTransaction.begin();
		persistence.create(myEntity);
		userTransaction.commit();
		assertThat(myEntity.getIdentifier()).isNotNull();
		assertThat(logEventEntityRepository.getLastMessage()).startsWith("Server Persistence Create MyEntity");
	}
	
	@Test
	public void read() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("mc001");
		userTransaction.begin();
		persistence.create(myEntity);
		userTransaction.commit();
		assertThat(myEntity.getIdentifier()).isNotNull();
		myEntity = persistence.readOne(MyEntity.class,myEntity.getIdentifier());
		assertThat(logEventEntityRepository.getLastMessage()).startsWith("Server Persistence Read MyEntity");
		assertThat(myEntity).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo("mc001");
	}
	
	@Test
	public void update() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("mc001");
		userTransaction.begin();
		persistence.create(myEntity);
		userTransaction.commit();
		myEntity = persistence.readOne(MyEntity.class,myEntity.getIdentifier());
		myEntity.setCode("nc001");
		userTransaction.begin();
		persistence.update(myEntity);
		userTransaction.commit();
		assertThat(logEventEntityRepository.getLastMessage()).startsWith("Server Persistence Update MyEntity");
		myEntity = persistence.readOne(MyEntity.class,myEntity.getIdentifier());
		assertThat(myEntity).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo("nc001");
	}
	
	@Test
	public void delete() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("mc001");
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
	
}
