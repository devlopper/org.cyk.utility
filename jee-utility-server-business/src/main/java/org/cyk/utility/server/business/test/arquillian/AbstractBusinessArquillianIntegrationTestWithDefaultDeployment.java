package org.cyk.utility.server.business.test.arquillian;

import org.cyk.utility.__kernel__.test.arquillian.archive.builder.WebArchiveBuilder;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public abstract class AbstractBusinessArquillianIntegrationTestWithDefaultDeployment extends AbstractBusinessArquillianIntegrationTest {
	private static final long serialVersionUID = 1L;

	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive(){
		return new WebArchiveBuilder().execute(); 
	}
	
}
