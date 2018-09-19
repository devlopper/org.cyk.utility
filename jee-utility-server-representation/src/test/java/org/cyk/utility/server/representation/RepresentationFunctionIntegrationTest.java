package org.cyk.utility.server.representation;

import org.cyk.utility.server.representation.test.TestRepresentationCreate;
import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationArquillianIntegrationTestWithDefaultDeploymentAsSwram;
import org.junit.Test;

public class RepresentationFunctionIntegrationTest extends AbstractRepresentationArquillianIntegrationTestWithDefaultDeploymentAsSwram {
	private static final long serialVersionUID = 1L;
	
	/* Create */
	
	@Test
	public void createOneMyEntity() throws Exception{
		__inject__(TestRepresentationCreate.class)
			.setUniformResourceIdentifier(contextPath.toString())
			.setActionableSingleton(MyEntityDto.class, MyEntityRepresentation.class)
		.addObjects(new MyEntityDto().setCode("a")).setIsCatchThrowable(Boolean.FALSE).execute();
	}

	@Override
	protected <ENTITY> Class<? extends AbstractEntityCollection<ENTITY>> __getEntityCollectionClass__(Class<ENTITY> aClass) {
		return null;
	}

}
