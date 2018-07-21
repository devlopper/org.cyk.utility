package org.cyk.utility.server.persistence.jpa;

import javax.inject.Inject;

import org.cyk.utility.value.ValueUsageType;
import org.junit.Test;

public class MyEntityPersistenceIntegrationTest extends AbstractArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
		
	@Inject private MyEntityPersistence persistence;
	
	/*@Test
	public void create() throws Exception{
		create(persistence, new MyEntity().setCode(getRandomCode()));
	}*/
	
	@Test
	public void read() throws Exception{
		MyEntity myEntity = new MyEntity().setCode(getRandomCode());
		create(persistence, myEntity);
		read(persistence, myEntity.getIdentifier(),ValueUsageType.SYSTEM);
	}
	
	@Test
	public void update() throws Exception{
		MyEntity myEntity = new MyEntity().setCode(getRandomCode());
		create(persistence, myEntity);
		myEntity = read(persistence, myEntity.getIdentifier(),ValueUsageType.SYSTEM);
		myEntity.setCode(getRandomCode());
		update(persistence, myEntity);
		read(persistence, myEntity.getIdentifier(),ValueUsageType.SYSTEM);
	}
	
	@Test
	public void delete() throws Exception{
		MyEntity myEntity = new MyEntity().setCode(getRandomCode());
		create(persistence, myEntity);
		myEntity = read(persistence, myEntity.getIdentifier(),ValueUsageType.SYSTEM);
		delete(persistence, myEntity);
	}
	
	/**/
	
	/*@Override
	protected Class<?> __getEntityClass__(Object action) {
		return MyEntity.class;
	}*/
	
}
