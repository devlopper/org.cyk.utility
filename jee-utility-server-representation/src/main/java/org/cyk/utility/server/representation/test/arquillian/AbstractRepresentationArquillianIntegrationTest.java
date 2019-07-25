package org.cyk.utility.server.representation.test.arquillian;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.util.Collection;

import javax.ws.rs.core.Response;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.server.representation.AbstractEntityCollection;
import org.cyk.utility.server.representation.AbstractEntityFromPersistenceEntity;
import org.cyk.utility.server.representation.Representation;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerRepresentation;
import org.cyk.utility.test.arquillian.AbstractSystemServerArquillianIntegrationTestImpl;
import org.cyk.utility.test.arquillian.SystemServerIntegrationTest;
import org.cyk.utility.value.ValueUsageType;
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
		Object businessIdentifier = __inject__(FieldHelper.class).getFieldValueBusinessIdentifier(entity);
		if(businessIdentifier!=null) {
			response = representation.getOne(businessIdentifier.toString(), ValueUsageType.BUSINESS.name(),null);
			entity = (ENTITY) response.getEntity();
			Assert.assertNotNull("Get entity with business identifier <"+businessIdentifier+"> not found", entity);
			Assert.assertNotNull("Entity <"+entity+"> found under business identifier <"+businessIdentifier+"> has null system identifier"
					,__inject__(FieldHelper.class).getFieldValueSystemIdentifier(entity));
			response.close();
		}
	}
	
	@Override
	protected <ENTITY> void ____createEntity____(Collection<ENTITY> entities,RepresentationEntity representation) {
		AbstractEntityCollection<ENTITY> collection = (AbstractEntityCollection<ENTITY>) instanciateOne(__getEntityCollectionClass__(entities.iterator().next().getClass()));
		collection.add(entities);		
		Response response = representation.createMany(collection,null);
		assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
		response.close();
		for(ENTITY index : entities) {
			Object businessIdentifier = __inject__(FieldHelper.class).getFieldValueBusinessIdentifier(index);
			if(businessIdentifier != null) {
				Object entity = index;
				response = representation.getOne(businessIdentifier.toString(), ValueUsageType.BUSINESS.name(),null);
				entity = (ENTITY) response.getEntity();
				String systemIdentifier = (String) __inject__(FieldHelper.class).getFieldValueSystemIdentifier(entity);
				assertThat(systemIdentifier).isNotBlank();	
				response.close();
			}
		}
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
		representation.deleteOne(((AbstractEntityFromPersistenceEntity)entity));
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

	protected abstract <ENTITY> Class<? extends AbstractEntityCollection<ENTITY>> __getEntityCollectionClass__(Class<ENTITY> aClass);
}
