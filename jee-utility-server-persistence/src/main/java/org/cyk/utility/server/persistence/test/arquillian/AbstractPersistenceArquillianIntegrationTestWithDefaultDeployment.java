package org.cyk.utility.server.persistence.test.arquillian;

import org.cyk.utility.__kernel__.test.arquillian.archive.builder.WebArchiveBuilder;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public abstract class AbstractPersistenceArquillianIntegrationTestWithDefaultDeployment extends AbstractPersistenceArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;

	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive(){
		return new WebArchiveBuilder().execute(); 
	}
	
}
