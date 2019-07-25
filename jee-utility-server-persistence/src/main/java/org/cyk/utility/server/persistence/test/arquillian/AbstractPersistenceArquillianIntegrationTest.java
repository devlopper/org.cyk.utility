package org.cyk.utility.server.persistence.test.arquillian;

import java.io.Serializable;
import java.util.Collection;

import javax.inject.Inject;
import javax.transaction.UserTransaction;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.server.persistence.PersistenceEntity;
import org.cyk.utility.system.layer.SystemLayer;
import org.cyk.utility.system.layer.SystemLayerPersistence;
import org.cyk.utility.test.arquillian.AbstractSystemServerArquillianIntegrationTestImpl;
import org.cyk.utility.test.arquillian.SystemServerIntegrationTest;
import org.cyk.utility.value.ValueUsageType;

@SuppressWarnings({"rawtypes","unchecked"})
public abstract class AbstractPersistenceArquillianIntegrationTest extends AbstractSystemServerArquillianIntegrationTestImpl<PersistenceEntity> implements SystemServerIntegrationTest<PersistenceEntity>, Serializable {
	private static final long serialVersionUID = 1L;

	@Inject protected UserTransaction userTransaction;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		try {
			userTransaction.begin();
			__inject__(Persistence.class).deleteAll();
			userTransaction.commit();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	
	public <ENTITY> void ____createEntity____(ENTITY entity,PersistenceEntity persistence){
		try {
			userTransaction.begin();
			persistence.create(entity);
			userTransaction.commit();	
		}catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Override
	protected <ENTITY> void ____createEntity____(Collection<ENTITY> entities, PersistenceEntity persistence) {
		try {
			userTransaction.begin();
			persistence.createMany(entities);
			userTransaction.commit();	
		}catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public <ENTITY> ENTITY ____readEntity____(Class<ENTITY> entityClass,Object identifier,ValueUsageType valueUsageType,Properties expectedFieldValues,PersistenceEntity persistence){
		return (ENTITY) persistence.readByIdentifier(identifier,valueUsageType);
	}
	
	public <ENTITY> void ____updateEntity____(ENTITY entity,PersistenceEntity persistence) {
		try {
			userTransaction.begin();
			persistence.update(entity);
			userTransaction.commit();	
		}catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	public <ENTITY> void ____deleteEntity____(ENTITY entity,PersistenceEntity persistence){
		try {
			userTransaction.begin();
			persistence.delete(entity);
			userTransaction.commit();
		}catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Override
	protected <ENTITY> void ____deleteEntityAll____(Class<ENTITY> entityClass, PersistenceEntity persistence) {
		try {
			userTransaction.begin();
			persistence.deleteAll();
			userTransaction.commit();
		}catch(Exception exception) {
			throw new RuntimeException(exception);
		}
	}
	
	@Override
	protected <ENTITY> Long ____countEntitiesAll____(Class<ENTITY> entityClass,PersistenceEntity persistence) {
		return persistence.count();
	}
	
	@Override
	protected PersistenceEntity ____getLayerEntityInterfaceFromClass____(Class<?> aClass) {
		return __inject__(SystemLayerPersistence.class).injectInterfaceClassFromEntityClassName(aClass,__getLayerEntityInterfaceClass__());
	}

	@Override
	public SystemLayer __getSystemLayer__() {
		return __inject__(SystemLayerPersistence.class);
	}
	
	/**/
	
	protected <T> T __readByBusinessIdentifier__(Class<T> aClass,Object identifier){
		return identifier == null ? null : __inject__(Persistence.class).readByIdentifier(aClass,identifier,new Properties().setValueUsageType(ValueUsageType.BUSINESS));
	}
	
	protected <T> T __readBySystemIdentifier__(Class<T> aClass,Object identifier){
		return identifier == null ? null : __inject__(Persistence.class).readByIdentifier(aClass,identifier,new Properties().setValueUsageType(ValueUsageType.SYSTEM));
	}
}
