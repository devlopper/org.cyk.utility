package org.cyk.utility.server.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.cyk.utility.test.AbstractArquillianUnitTest;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

public class JpaUnitTest extends AbstractArquillianUnitTest {
	private static final long serialVersionUID = 1L;

	@Test
	public void createEntityManagerFactory(){
		EntityManagerFactory entityManagerFactory = javax.persistence.Persistence.createEntityManagerFactory("persistenceUnit");
		assertThat(entityManagerFactory).isNotNull();
	}
	
	@Test
	public void executeSelectQuery(){
		EntityManagerFactory entityManagerFactory = javax.persistence.Persistence.createEntityManagerFactory("persistenceUnit");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		entityManager.createQuery("SELECT p FROM MyEntity p");
	}
	
	
	@Deployment
	public static JavaArchive createDeployment01() {
		return AbstractArquillianUnitTest.createJavaArchiveDeployment().addPackage(JpaUnitTest.class.getPackage())
				.addAsManifestResource("org/cyk/utility/server/persistence/jpa/persistence.xml", "persistence.xml");
	}
}
