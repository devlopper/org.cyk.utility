package org.cyk.utility.server.business;

import org.cyk.utility.server.persistence.test.arquillian.AbstractArquillianIntegrationTest;
import org.cyk.utility.test.arquillian.ArchiveBuilder;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public abstract class AbstractArquillianIntegrationTestWithDefaultDeployment extends AbstractArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;
		
	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive(){
		return new ArchiveBuilder<WebArchive>(WebArchive.class)
				.setProjectDefaultsYml("org/cyk/utility/server/business/project-defaults.yml")
				.setPersistenceXml("org/cyk/utility/server/business/persistence.xml")
				.execute().addClasses(MyEntity.class,MyEntityPersistence.class,MyEntityPersistenceImpl.class); 
	}
}
