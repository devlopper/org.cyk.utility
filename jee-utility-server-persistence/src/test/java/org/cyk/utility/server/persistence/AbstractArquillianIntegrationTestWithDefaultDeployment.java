package org.cyk.utility.server.persistence;

import org.cyk.utility.test.arquillian.ArchiveBuilder;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public abstract class AbstractArquillianIntegrationTestWithDefaultDeployment extends org.cyk.utility.test.arquillian.AbstractArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;
		
	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive(){
		return new ArchiveBuilder<WebArchive>(WebArchive.class)
				.setProjectDefaultsYml("org/cyk/utility/server/persistence/project-defaults.yml")
				.execute(); 
	}
}
