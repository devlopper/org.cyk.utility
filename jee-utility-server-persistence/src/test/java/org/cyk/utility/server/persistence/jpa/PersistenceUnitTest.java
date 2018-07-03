package org.cyk.utility.server.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

import org.cyk.utility.test.AbstractArquillianIntegrationTest;
import org.cyk.utility.test.AbstractArquillianUnitTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class PersistenceUnitTest extends AbstractArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;
	
	static {
		System.setProperty("log4j.configurationFile", "org/cyk/utility/server/persistence/jpa/log4j2.xml");
	}
	
	@Inject private Persistence persistence;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		persistence.setEntityManager(javax.persistence.Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager());
	}
	
	@Test
	public void create() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("mc001");
		persistence.create(myEntity);
		assertThat(myEntity.getIdentifier()).isNotNull();
	}
	
	@Test
	public void read(){
		MyEntity myEntity = new MyEntity().setCode("mc001");
		persistence.create(myEntity);
		assertThat(myEntity.getIdentifier()).isNotNull();
		myEntity = persistence.read(MyEntity.class,myEntity.getIdentifier());
		assertThat(myEntity).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo("mc001");
	}
	
	@Test
	public void update(){
		MyEntity myEntity = new MyEntity().setCode("mc001");
		persistence.create(myEntity);
		myEntity = persistence.read(MyEntity.class,myEntity.getIdentifier());
		myEntity.setCode("nc001");
		persistence.update(myEntity);
		myEntity = persistence.read(MyEntity.class,myEntity.getIdentifier());
		assertThat(myEntity).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo("nc001");
	}
	
	@Test
	public void delete(){
		MyEntity myEntity = new MyEntity().setCode("mc001");
		persistence.create(myEntity);
		myEntity = persistence.read(MyEntity.class,myEntity.getIdentifier());
		assertThat(myEntity).isNotNull();
		persistence.delete(myEntity);
		myEntity = persistence.read(MyEntity.class,myEntity.getIdentifier());
		assertThat(myEntity).isNull();
	}
	
	@Deployment
	public static JavaArchive createDeployment01() {
		return AbstractArquillianUnitTest.createJavaArchiveDeployment().addPackage(PersistenceUnitTest.class.getPackage())
				.addAsManifestResource("org/cyk/utility/server/persistence/jpa/persistence.xml", "persistence.xml");
	}
}
