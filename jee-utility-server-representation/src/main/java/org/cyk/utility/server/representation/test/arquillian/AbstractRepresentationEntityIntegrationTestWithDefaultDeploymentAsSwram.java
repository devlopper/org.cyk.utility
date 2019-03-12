package org.cyk.utility.server.representation.test.arquillian;

import org.cyk.utility.__kernel__.test.arquillian.archive.builder.ArchiveBuilder;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public abstract class AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram<ENTITY> extends AbstractRepresentationEntityIntegrationTest<ENTITY> {
	private static final long serialVersionUID = 1L;

	@org.jboss.arquillian.container.test.api.Deployment
	public static WebArchive createArchive() {
		return new ArchiveBuilder<WebArchive>(WebArchive.class).execute(); 
	}
	
}
