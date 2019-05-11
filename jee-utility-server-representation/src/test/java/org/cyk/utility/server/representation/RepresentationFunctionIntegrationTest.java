package org.cyk.utility.server.representation;

import javax.ws.rs.core.Response;

import org.cyk.utility.server.representation.test.TestRepresentationCreate;
import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationArquillianIntegrationTestWithDefaultDeployment;
import org.junit.Test;

public class RepresentationFunctionIntegrationTest extends AbstractRepresentationArquillianIntegrationTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	/* Create */
	
	@Test
	public void createOneMyEntity() throws Exception{
		__inject__(TestRepresentationCreate.class).addObjects(new MyEntityDto().setCode("a")).execute();
	}
	
	@Test
	public void paginate() throws Exception{
		__inject__(MyEntityRepresentation.class).createOne(new MyEntityDto().setCode("a"));
		__inject__(MyEntityRepresentation.class).createOne(new MyEntityDto().setCode("b"));
		__inject__(MyEntityRepresentation.class).createOne(new MyEntityDto().setCode("c"));
		__inject__(MyEntityRepresentation.class).createOne(new MyEntityDto().setCode("d"));
		__inject__(MyEntityRepresentation.class).createOne(new MyEntityDto().setCode("e"));
		Response response = __inject__(MyEntityRepresentation.class).getMany(null, null, null,null);
		//System.out.println(response.getHeaders());
		__inject__(MyEntityRepresentation.class).deleteAll();
	}

	@Override
	protected <ENTITY> Class<? extends AbstractEntityCollection<ENTITY>> __getEntityCollectionClass__(Class<ENTITY> aClass) {
		return null;
	}

}
