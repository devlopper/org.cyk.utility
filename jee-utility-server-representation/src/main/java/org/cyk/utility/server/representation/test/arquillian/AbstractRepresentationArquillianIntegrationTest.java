package org.cyk.utility.server.representation.test.arquillian;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.util.Collection;

import javax.json.bind.JsonbBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.cyk.utility.__kernel__.object.__static__.representation.AbstractCollectionOfIdentifiedImpl;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.system.action.SystemAction;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.server.representation.Representation;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.server.representation.ResponseHelper;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerRepresentation;
import org.cyk.utility.test.arquillian.AbstractSystemServerArquillianIntegrationTestImpl;
import org.cyk.utility.test.arquillian.SystemServerIntegrationTest;
import org.junit.Assert;

@SuppressWarnings({"rawtypes","unchecked"})
public abstract class AbstractRepresentationArquillianIntegrationTest extends AbstractSystemServerArquillianIntegrationTestImpl<RepresentationEntity> implements SystemServerIntegrationTest<RepresentationEntity>, Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		__inject__(Representation.class).deleteAll();
	}
	
	@Override
	protected <ENTITY> void ____createEntity____(ENTITY entity, RepresentationEntity representation) {
		Response response = representation.createOne(entity);
		assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
		response.close();
		Object businessIdentifier = org.cyk.utility.__kernel__.field.FieldHelper.readBusinessIdentifier(entity);
		if(businessIdentifier!=null) {
			response = representation.getOne(businessIdentifier.toString(), ValueUsageType.BUSINESS.name(),null);
			entity = (ENTITY) response.getEntity();
			Assert.assertNotNull("Get entity with business identifier <"+businessIdentifier+"> not found", entity);
			Assert.assertNotNull("Entity <"+entity+"> found under business identifier <"+businessIdentifier+"> has null system identifier"
					,org.cyk.utility.__kernel__.field.FieldHelper.readSystemIdentifier(entity));
			response.close();
		}
	}
	
	@Override
	protected <ENTITY> void ____createEntity____(Collection<ENTITY> entities,RepresentationEntity layerEntityInterface) {
		throw new RuntimeException("not yet implemented");
	}
	
	@Override
	protected <ENTITY> void ____assertThatLogSaysEntityHasBeen____(Class<? extends SystemAction> systemActionClass,ENTITY entity,RepresentationEntity layerEntityInterface) {}

	@Override
	protected <ENTITY> ENTITY ____readEntity____(Class<ENTITY> entityClass, Object identifier,ValueUsageType valueUsageType, Properties expectedFieldValues, RepresentationEntity representation) {
		return (ENTITY) (ValueUsageType.SYSTEM.equals(valueUsageType) ? representation.getOne(identifier.toString(),ValueUsageType.SYSTEM.name(),null).getEntity() 
				: representation.getOne(identifier.toString(),ValueUsageType.BUSINESS.name(),null).getEntity());
	}

	@Override
	protected <ENTITY> void ____updateEntity____(ENTITY entity, RepresentationEntity representation) {
		representation.updateOne(entity,null);
	}

	@Override
	protected <ENTITY> void ____deleteEntity____(ENTITY entity, RepresentationEntity representation) {
		representation.deleteOne(entity);
	}
	
	@Override
	protected <ENTITY> void ____deleteEntityAll____(Class<ENTITY> entityClass,RepresentationEntity representation) {
		representation.deleteAll();
	}
	
	@Override
	protected <ENTITY> Long ____countEntitiesAll____(Class<ENTITY> entityClass,RepresentationEntity representation) {
		return (Long) representation.count(null).getEntity();
	}
	
	@Override
	protected RepresentationEntity ____getLayerEntityInterfaceFromClass____(Class<?> aClass) {
		//if(aClass == null)
			aClass = __getLayerEntityInterfaceClass__();
		return (RepresentationEntity) __inject__(aClass);
	}

	@Override
	public SystemLayer __getSystemLayer__() {
		return __inject__(SystemLayerRepresentation.class);
	}
	
	protected String getJavaScriptObjectNotation(Object object) {
		 return object == null ? null : JsonbBuilder.create().toJson(object);
	}

	@Deprecated
	protected <ENTITY> Class<? extends AbstractCollectionOfIdentifiedImpl<ENTITY,?>> __getEntityCollectionClass__(Class<ENTITY> aClass) {
		return null;
	}

	/**/
	
	protected static void __assertResponseTransaction__(Response response,Status expectedStatus,Long expectedNumberOfProcessedElement
			,Collection<String> expectedSystemIdentifiers,Collection<String> expectedBusinessIdentifiers) {
		assertThat(response.getStatusInfo()).isEqualTo(expectedStatus);
		if(Status.CREATED.equals(expectedStatus)) {
			assertThat(ResponseHelper.getHeaderXTotalCount(response)).isEqualTo(expectedNumberOfProcessedElement);
			Collection<String> identifiers = null;
			identifiers = ResponseHelper.getHeaderEntitiesSystemIdentifiers(response);
			assertThat(identifiers).hasSize(expectedNumberOfProcessedElement.intValue());
			if(expectedSystemIdentifiers!=null)
				assertThat(identifiers).containsExactlyInAnyOrder(expectedSystemIdentifiers.toArray(new String[] {}));	
			identifiers = ResponseHelper.getHeaderEntitiesBusinessIdentifiers(response);
			assertThat(identifiers).hasSize(expectedNumberOfProcessedElement.intValue());
			if(expectedBusinessIdentifiers!=null)
				assertThat(identifiers).containsExactlyInAnyOrder(expectedBusinessIdentifiers.toArray(new String[] {}));	
		}
	}

}
