package org.cyk.utility.server.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.cyk.utility.test.AbstractArquillianIntegrationTest;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;

public class PersistenceUnitTest extends AbstractArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;
		
	@Inject private Persistence persistence;
	
	@Test
	public void create() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("mc001");
		userTransaction.begin();
		persistence.create(myEntity);
		userTransaction.commit();
		assertThat(myEntity.getIdentifier()).isNotNull();
		assertThat(getLastLogEventMessage()).startsWith("Server Persistence Create MyEntity");
	}
	
	@Test
	public void read() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("mc001");
		userTransaction.begin();
		persistence.create(myEntity);
		userTransaction.commit();
		assertThat(myEntity.getIdentifier()).isNotNull();
		myEntity = persistence.read(MyEntity.class,myEntity.getIdentifier());
		assertThat(getLastLogEventMessage()).startsWith("Server Persistence Read MyEntity");
		assertThat(myEntity).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo("mc001");
	}
	
	@Test
	public void update() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("mc001");
		userTransaction.begin();
		persistence.create(myEntity);
		userTransaction.commit();
		myEntity = persistence.read(MyEntity.class,myEntity.getIdentifier());
		myEntity.setCode("nc001");
		userTransaction.begin();
		persistence.update(myEntity);
		userTransaction.commit();
		assertThat(getLastLogEventMessage()).startsWith("Server Persistence Update MyEntity");
		myEntity = persistence.read(MyEntity.class,myEntity.getIdentifier());
		assertThat(myEntity).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo("nc001");
	}
	
	@Test
	public void delete() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("mc001");
		userTransaction.begin();
		persistence.create(myEntity);
		userTransaction.commit();
		myEntity = persistence.read(MyEntity.class,myEntity.getIdentifier());
		assertThat(myEntity).isNotNull();
		userTransaction.begin();
		persistence.delete(myEntity);
		userTransaction.commit();
		assertThat(getLastLogEventMessage()).startsWith("Server Persistence Delete MyEntity");
		myEntity = persistence.read(MyEntity.class,myEntity.getIdentifier());
		assertThat(myEntity).isNull();
	}
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive01(){
		return ShrinkWrap.create(WebArchive.class)
				.addAsResource("org/cyk/utility/server/persistence/jpa/project-defaults.yml", "project-defaults.yml")
				//.addAsResource("org/cyk/utility/server/persistence/jpa/jboss-deployment-structure.xml", "jboss-deployment-structure.xml")
				.addAsResource("org/cyk/utility/server/persistence/jpa/persistence.xml", "META-INF/persistence.xml")
				.addAsResource("org/cyk/utility/server/persistence/jpa/log4j2.xml", "log4j2.xml")
				.addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
						.importRuntimeDependencies().resolve().withTransitivity().asFile())
				.addAsLibraries(Maven.resolver().resolve("org.cyk.jee.utility.server.persistence:jee-utility-server-persistence:0.0.1").withoutTransitivity().asFile())
				.addClasses(MyEntity.class)
		;
	}
}
