package org.cyk.utility.test.arquillian;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.ClassHelperImpl;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractSystemLayerArquillianIntegrationTestImpl<LAYER_ENTITY_INTERFACE> extends org.cyk.utility.test.arquillian.AbstractArquillianIntegrationTest implements SystemLayerIntegrationTest<LAYER_ENTITY_INTERFACE>, Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<LAYER_ENTITY_INTERFACE> layerEntityInterfaceClass;
	
	{
		layerEntityInterfaceClass = (Class<LAYER_ENTITY_INTERFACE>) ClassHelperImpl.__getParameterAt__(getClass(), 0, Object.class);
	}
	
	protected abstract <ENTITY> void ____createEntity____(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	public <ENTITY> void __createEntity__(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface){
		____createEntity____(entity, layerEntityInterface);
		assertThat(__inject__(FieldValueGetter.class).execute(entity,FieldName.IDENTIFIER,ValueUsageType.SYSTEM).getOutput()).isNotNull();
		assertionHelper.assertStartsWithLastLogEventMessage(__getLogMessageStart__(__inject__(SystemActionCreate.class),entity.getClass()))
			.assertContainsLastLogEventMessage("code="+__inject__(FieldValueGetter.class).execute(entity,FieldName.IDENTIFIER,ValueUsageType.BUSINESS).getOutput());
	}
	
	public <ENTITY> void __createEntity__(ENTITY entity){
		__createEntity__(entity,  __getLayerEntityInterfaceFromObject__(entity));
	}
	
	protected abstract <ENTITY> ENTITY ____readEntity____(Class<ENTITY> entityClass,Object identifier,ValueUsageType valueUsageType,Properties expectedFieldValues,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	public <ENTITY> ENTITY __readEntity__(Class<ENTITY> entityClass,Object identifier,ValueUsageType valueUsageType,Properties expectedFieldValues,LAYER_ENTITY_INTERFACE layerEntityInterface){
		ENTITY entity = ____readEntity____(entityClass, identifier, valueUsageType, expectedFieldValues, layerEntityInterface);
		assertThat(entity).isNotNull();
		assertionHelper.assertEqualsByFieldValue(expectedFieldValues, entity);
		Object businessIdentifier = ValueUsageType.BUSINESS.equals(valueUsageType) ? identifier : __inject__(FieldValueGetter.class).execute(entity,FieldName.IDENTIFIER,ValueUsageType.BUSINESS).getOutput();
		assertionHelper.assertStartsWithLastLogEventMessage(__getLogMessageStart__(__inject__(SystemActionRead.class),entity.getClass()))
		.assertContainsLastLogEventMessage("code="+businessIdentifier);
		return entity;
	}
	
	public <ENTITY> ENTITY __readEntity__(Class<ENTITY> entityClass,Object identifier,ValueUsageType valueUsageType,LAYER_ENTITY_INTERFACE layerEntityInterface){
		return __readEntity__(entityClass,identifier, valueUsageType,null, layerEntityInterface);
	}
	
	public <ENTITY> ENTITY __readEntity__(Class<ENTITY> entityClass,Object identifier,ValueUsageType valueUsageType,Properties expectedFieldValues){
		return __readEntity__(entityClass, identifier, valueUsageType, expectedFieldValues, __getLayerEntityInterfaceFromClass__(entityClass));
	}
	
	public <ENTITY> ENTITY __readEntity__(Class<ENTITY> entityClass,Object identifier,ValueUsageType valueUsageType){
		return __readEntity__(entityClass, identifier, valueUsageType, __getLayerEntityInterfaceFromClass__(entityClass));
	}
	
	protected abstract <ENTITY> void ____updateEntity____(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	public <ENTITY> void __updateEntity__(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface) {
		____updateEntity____(entity, layerEntityInterface);
		assertThat(__inject__(FieldValueGetter.class).execute(entity,FieldName.IDENTIFIER,ValueUsageType.SYSTEM).getOutput()).isNotNull();
		assertionHelper.assertStartsWithLastLogEventMessage(__getLogMessageStart__(__inject__(SystemActionUpdate.class),entity.getClass()))
			.assertContainsLastLogEventMessage("code="+__inject__(FieldValueGetter.class).execute(entity,FieldName.IDENTIFIER,ValueUsageType.BUSINESS).getOutput());
	}
	
	public <ENTITY> void __updateEntity__(ENTITY entity) {
		__updateEntity__(entity, __getLayerEntityInterfaceFromObject__(entity));
	}
	
	protected abstract <ENTITY> void ____deleteEntity____(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	public <ENTITY> void __deleteEntity__(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface){
		____deleteEntity____(entity, layerEntityInterface);
		assertionHelper.assertStartsWithLastLogEventMessage(__getLogMessageStart__(__inject__(SystemActionDelete.class),entity.getClass())).assertContainsLastLogEventMessage(
				"code="+__inject__(FieldValueGetter.class).execute(entity,FieldName.IDENTIFIER,ValueUsageType.BUSINESS).getOutput());
		
		//Object systemIdentifier = __inject__(FieldValueGetter.class).execute(entity,FieldName.IDENTIFIER,ValueUsageType.SYSTEM).getOutput();
		//entity = (ENTITY) layerEntityInterface.readOne(systemIdentifier);
		//assertThat(entity).isNull();
	}
	
	public <ENTITY> void __deleteEntity__(ENTITY entity) {
		__deleteEntity__(entity, __getLayerEntityInterfaceFromObject__(entity));
	}
	
	@Override
	public Class<LAYER_ENTITY_INTERFACE> __getLayerEntityInterfaceClass__() {
		return layerEntityInterfaceClass;
	}
	
	protected abstract LAYER_ENTITY_INTERFACE ____getLayerEntityInterfaceFromClass____(Class<?> aClass);
	
	@Override
	public LAYER_ENTITY_INTERFACE __getLayerEntityInterfaceFromClass__(Class<?> aClass) {
		LAYER_ENTITY_INTERFACE instance = ____getLayerEntityInterfaceFromClass____(aClass);
		if(instance == null)
			throw new RuntimeException("We cannot find entity interface from class "+aClass);
		return instance;
	}
	
	@Override
	public LAYER_ENTITY_INTERFACE __getLayerEntityInterfaceFromObject__(Object object) {
		return __getLayerEntityInterfaceFromClass__(object.getClass());
	}
	
	protected String __getLogMessageStart__(SystemAction systemAction,Class<?> aClass){
		return __getSystemActor__().getIdentifier().toString()+" "+__getSystemLayer__().getIdentifier().toString()+" "+systemAction.getIdentifier().toString()
				+" "+aClass.getSimpleName();
	}
}
