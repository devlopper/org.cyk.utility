package org.cyk.utility.service.server;

import java.io.Serializable;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.cyk.utility.persistence.server.view.MaterializedViewActualizer;
import org.cyk.utility.persistence.server.view.MaterializedViewCreator;
import org.cyk.utility.persistence.server.view.MaterializedViewDeletor;
import org.cyk.utility.persistence.server.view.MaterializedViewUpdator;
import org.cyk.utility.service.MaterializedViewSpecificService;

public abstract class AbstractMaterializedViewSpecificServiceImpl<SERVICE_ENTITY,SERVICE_ENTITY_IMPL,PERSISTENCE_ENTITY,PERSISTENCE_ENTITY_IMPL> extends AbstractSpecificServiceImpl<SERVICE_ENTITY, SERVICE_ENTITY_IMPL, PERSISTENCE_ENTITY, PERSISTENCE_ENTITY_IMPL> implements MaterializedViewSpecificService<SERVICE_ENTITY>,Serializable {

	@Inject MaterializedViewCreator materializedViewCreator;
	@Inject MaterializedViewUpdator materializedViewUpdator;
	@Inject MaterializedViewActualizer materializedViewActualizer;
	@Inject MaterializedViewDeletor materializedViewDeletor;
	
	@Override
	public Response create() {
		materializedViewCreator.execute(null, persistenceEntityImplClass);
		return Response.ok(String.format("Materialized view for <<%s>> has been created.", persistenceEntityImplClass.getName())).build();
	}
	
	@Override
	public Response update() {
		materializedViewUpdator.execute(null, persistenceEntityImplClass);
		return Response.ok(String.format("Materialized view for <<%s>> has been updated.", persistenceEntityImplClass.getName())).build();
	}
	
	@Override
	public Response actualize() {
		materializedViewActualizer.execute(null, persistenceEntityImplClass);
		return Response.ok(String.format("Materialized view for <<%s>> has been actualized.", persistenceEntityImplClass.getName())).build();
	}
	
	@Override
	public Response delete() {
		materializedViewDeletor.execute(null, persistenceEntityImplClass);
		return Response.ok(String.format("Materialized view for <<%s>> has been deleted.", persistenceEntityImplClass.getName())).build();
	}
}