package org.cyk.utility.server.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.cyk.utility.architecture.system.SystemActionCreate;
import org.cyk.utility.architecture.system.SystemActionRead;
import org.cyk.utility.server.persistence.PersistenceFunctionCreate;
import org.cyk.utility.server.persistence.PersistenceFunctionRead;
import org.cyk.utility.test.AbstractArquillianIntegrationTest;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;

public class PersistenceFunctionReadIntegrationTest extends AbstractArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;
		
	@Inject private PersistenceFunctionCreate persistenceFunctionCreate;
	@Inject private PersistenceFunctionRead persistenceFunctionRead;
	
	@Test
	public void readOneExisting() throws Exception{
		MyEntity myEntity = new MyEntity().setCode("mc001");
		userTransaction.begin();
		persistenceFunctionCreate.setAction(__inject__(SystemActionCreate.class)).setEntity(myEntity).execute();
		userTransaction.commit();
		
		myEntity = (MyEntity) persistenceFunctionRead.setAction(__inject__(SystemActionRead.class)).setEntityClass(MyEntity.class)
				.setEntityIdentifier(myEntity.getIdentifier()).execute().getProperties().getEntity();
		
		assertThat(myEntity).isNotNull();
		assertThat(myEntity.getIdentifier()).isNotNull();
		assertThat(myEntity.getCode()).isEqualTo("mc001");
		assertThat(getLastLogEventMessage()).startsWith("Server Persistence Read MyEntity");
		assertThat(getLastLogEventMessage()).contains("identifier="+myEntity.getIdentifier());
		assertThat(getLastLogEventMessage()).contains("code=mc001");
	}
	
	@Test
	public void readOneNotExisting() throws Exception{
		MyEntity myEntity = (MyEntity) persistenceFunctionRead.setAction(__inject__(SystemActionRead.class)).setEntityClass(MyEntity.class).setEntityIdentifier(-1l).execute()
				.getProperties().getEntity();
		
		assertThat(myEntity).isNull();
		assertThat(getLastLogEventMessage()).startsWith("Server Persistence Read MyEntity");
		assertThat(getLastLogEventMessage()).contains("not found");
		assertThat(getLastLogEventMessage()).contains("identifier=-1");
	}
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive(){
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
