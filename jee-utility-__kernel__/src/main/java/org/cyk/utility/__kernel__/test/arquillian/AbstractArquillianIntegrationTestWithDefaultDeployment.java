package org.cyk.utility.__kernel__.test.arquillian;

import java.io.Serializable;

import org.cyk.utility.__kernel__.test.arquillian.archive.builder.WebArchiveBuilder;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public abstract class AbstractArquillianIntegrationTestWithDefaultDeployment extends AbstractArquillianIntegrationTest implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive(){
		return new WebArchiveBuilder().execute(); 
	}
	
}
