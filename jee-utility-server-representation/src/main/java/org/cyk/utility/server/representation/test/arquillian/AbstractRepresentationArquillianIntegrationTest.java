package org.cyk.utility.server.representation.test.arquillian;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.representation.RepresentationEntity;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerRepresentation;
import org.cyk.utility.test.arquillian.AbstractSystemServerArquillianIntegrationTestImpl;
import org.cyk.utility.test.arquillian.SystemServerIntegrationTest;
import org.cyk.utility.throwable.ThrowableHelper;
import org.cyk.utility.value.ValueUsageType;

@SuppressWarnings({"rawtypes","unchecked"})
public abstract class AbstractRepresentationArquillianIntegrationTest extends AbstractSystemServerArquillianIntegrationTestImpl<RepresentationEntity> implements SystemServerIntegrationTest<RepresentationEntity>, Serializable {
	private static final long serialVersionUID = 1L;
	
	/* Global variable */
	/*protected static ResteasyClient CLIENT;
	protected static ResteasyWebTarget TARGET;
	
	
	*/
	@Override
	protected void __listenBeforeFirstCall__() {
		super.__listenBeforeFirstCall__();
		//CLIENT = new ResteasyClientBuilder().build();
		//TARGET = CLIENT.target(UriBuilder.fromPath(contextPath.toExternalForm()));
	}
	
	@Override
	protected <ENTITY> void ____createEntity____(ENTITY entity, RepresentationEntity business) {
		//business.create(entity);
		__inject__(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented();
	}

	@Override
	protected <ENTITY> ENTITY ____readEntity____(Class<ENTITY> entityClass, Object identifier,ValueUsageType valueUsageType, Properties expectedFieldValues, RepresentationEntity business) {
		return (ENTITY) (ValueUsageType.SYSTEM.equals(valueUsageType) ? business.getOne(identifier.toString(),ValueUsageType.SYSTEM.name()) 
				: business.getOne(identifier.toString(),ValueUsageType.SYSTEM.name()));
	}

	@Override
	protected <ENTITY> void ____updateEntity____(ENTITY entity, RepresentationEntity business) {
		//business.update(entity);
		__inject__(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented();
	}

	@Override
	protected <ENTITY> void ____deleteEntity____(ENTITY entity, RepresentationEntity business) {
		//business.delete(entity);
		__inject__(ThrowableHelper.class).throwRuntimeExceptionNotYetImplemented();
	}
	
	@Override
	protected RepresentationEntity ____getLayerEntityInterfaceFromClass____(Class<?> aClass) {
		return __inject__(SystemLayerRepresentation.class).injectInterfaceClassFromEntityClassName(aClass,__getLayerEntityInterfaceClass__());
	}

	@Override
	public SystemLayer __getSystemLayer__() {
		return __inject__(SystemLayerRepresentation.class);
	}
}
