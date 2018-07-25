package org.cyk.utility.server.persistence;

import org.cyk.utility.server.persistence.test.arquillian.AbstractPersistenceArquillianIntegrationTestWithDefaultDeploymentAsSwram;
import org.junit.Assert;
import org.junit.Test;

public class PersistenceQueryIdentifierStringBuilderUnitTest extends AbstractPersistenceArquillianIntegrationTestWithDefaultDeploymentAsSwram {
	private static final long serialVersionUID = 1L;

	@Test
	public void buildFromName(){
		Assert.assertEquals("MyEntity.readByValue", __inject__(PersistenceQueryIdentifierStringBuilder.class)
				.setClassSimpleName("MyEntity").setName("readByValue").execute().getOutput());
	}
	
	@Test
	public void buildFromDerivedFromQueryIdentifier(){
		Assert.assertEquals("MyEntity.countByValue", __inject__(PersistenceQueryIdentifierStringBuilder.class)
				.setIsDerivedFromQueryIdentifier(Boolean.TRUE).setDerivedFromQueryIdentifier("MyEntity.readByValue")
				.setIsCountInstances(Boolean.TRUE).execute().getOutput());
	}
}
