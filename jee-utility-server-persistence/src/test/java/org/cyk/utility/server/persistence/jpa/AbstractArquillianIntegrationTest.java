package org.cyk.utility.server.persistence.jpa;

import org.cyk.utility.test.arquillian.ArchiveBuilder;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public abstract class AbstractArquillianIntegrationTest extends org.cyk.utility.test.arquillian.AbstractArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;
		
	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive(){
		return new ArchiveBuilder<WebArchive>(WebArchive.class)
				.setProjectDefaultsYml("org/cyk/utility/server/persistence/jpa/project-defaults.yml")
				.setPersistenceXml("org/cyk/utility/server/persistence/jpa/persistence.xml")
				.setLog4j2Xml("org/cyk/utility/server/persistence/jpa/log4j2.xml")
				.execute().addClasses(MyEntity.class); 
	}
}
