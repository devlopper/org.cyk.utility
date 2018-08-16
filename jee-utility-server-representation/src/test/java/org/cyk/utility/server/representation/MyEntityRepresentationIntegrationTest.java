package org.cyk.utility.server.representation;

import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram;

public class MyEntityRepresentationIntegrationTest extends AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram<MyEntityDto> {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends RepresentationEntity> __getLayerEntityInterfaceClass__() {
		return MyEntityRepresentation.class;
	}

	@Override
	protected Class<? extends AbstractEntityCollection<?>> __getEntityCollectionClass__() {
		return MyEntityDtoCollection.class;
	}	

}
