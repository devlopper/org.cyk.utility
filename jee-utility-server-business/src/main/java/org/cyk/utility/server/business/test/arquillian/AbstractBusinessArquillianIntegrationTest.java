package org.cyk.utility.server.business.test.arquillian;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.business.Business;
import org.cyk.utility.server.business.BusinessEntity;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerBusiness;
import org.cyk.utility.test.arquillian.AbstractSystemServerArquillianIntegrationTestImpl;
import org.cyk.utility.test.arquillian.SystemServerIntegrationTest;
import org.cyk.utility.__kernel__.value.ValueUsageType;

@SuppressWarnings({"rawtypes","unchecked"})
public abstract class AbstractBusinessArquillianIntegrationTest extends AbstractSystemServerArquillianIntegrationTestImpl<BusinessEntity> implements SystemServerIntegrationTest<BusinessEntity>, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		__inject__(Business.class).deleteAll();
	}
	
	@Override
	protected <ENTITY> void ____createEntity____(ENTITY entity, BusinessEntity business) {
		business.create(entity);
	}
	
	@Override
	protected <ENTITY> void ____createEntity____(Collection<ENTITY> entities, BusinessEntity business) {
		business.createMany(entities);
	}

	@Override
	protected <ENTITY> ENTITY ____readEntity____(Class<ENTITY> entityClass, Object identifier,ValueUsageType valueUsageType, Properties expectedFieldValues, BusinessEntity business) {
		return (ENTITY) business.findByIdentifier(identifier, valueUsageType);
	}

	@Override
	protected <ENTITY> void ____updateEntity____(ENTITY entity, BusinessEntity business) {
		business.update(entity);
	}

	@Override
	protected <ENTITY> void ____deleteEntity____(ENTITY entity, BusinessEntity business) {
		business.delete(entity);
	}
	
	@Override
	protected <ENTITY> void ____deleteEntityAll____(Class<ENTITY> entityClass, BusinessEntity business) {
		business.deleteAll();
	}
	
	@Override
	protected <ENTITY> Long ____countEntitiesAll____(Class<ENTITY> entityClass, BusinessEntity business) {
		return business.count();
	}
	
	@Override
	protected BusinessEntity ____getLayerEntityInterfaceFromClass____(Class<?> aClass) {
		return __inject__(SystemLayerBusiness.class).injectInterfaceClassFromPersistenceEntityClassName(aClass,__getLayerEntityInterfaceClass__());
	}

	@Override
	public SystemLayer __getSystemLayer__() {
		return __inject__(SystemLayerBusiness.class);
	}
}
