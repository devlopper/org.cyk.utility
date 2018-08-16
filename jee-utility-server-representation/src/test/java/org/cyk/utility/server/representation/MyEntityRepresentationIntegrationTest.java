package org.cyk.utility.server.representation;

import org.cyk.utility.server.representation.test.arquillian.AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram;

public class MyEntityRepresentationIntegrationTest extends AbstractRepresentationEntityIntegrationTestWithDefaultDeploymentAsSwram<MyEntityDto> {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class<? extends RepresentationEntity> __getLayerEntityInterfaceClass__() {
		return MyEntityRepresentation.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <ENTITY> Class<? extends AbstractEntityCollection<ENTITY>> __getEntityCollectionClass__(Class<ENTITY> aClass) {
		try {
			return (Class<? extends AbstractEntityCollection<ENTITY>>) Class.forName(MyEntityDtoCollection.class.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}	

}
