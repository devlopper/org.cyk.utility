package org.cyk.utility.test.arquillian;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.ClassHelperImpl;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.system.layer.SystemLayerBusiness;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractSystemLayerIntegrationTestImpl<LAYER_ENTITY_INTERFACE> extends org.cyk.utility.test.arquillian.AbstractArquillianIntegrationTest implements SystemLayerIntegrationTest<LAYER_ENTITY_INTERFACE>, Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<LAYER_ENTITY_INTERFACE> layerEntityInterfaceClass;
	
	{
		layerEntityInterfaceClass = (Class<LAYER_ENTITY_INTERFACE>) ClassHelperImpl.__getParameterAt__(getClass(), 0, Object.class);
	}
	
	protected abstract <ENTITY> void ____createEntity____(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	public <ENTITY> void __createEntity__(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface){
		____createEntity____(entity, layerEntityInterface);
		assertThat(__inject__(FieldValueGetter.class).execute(entity,FieldName.IDENTIFIER,ValueUsageType.SYSTEM).getOutput()).isNotNull();
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Create "+entity.getClass().getSimpleName())
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
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Read "+entity.getClass().getSimpleName())
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
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Update "+entity.getClass().getSimpleName())
			.assertContainsLastLogEventMessage("code="+__inject__(FieldValueGetter.class).execute(entity,FieldName.IDENTIFIER,ValueUsageType.BUSINESS).getOutput());
	}
	
	public <ENTITY> void __updateEntity__(ENTITY entity) {
		__updateEntity__(entity, __getLayerEntityInterfaceFromObject__(entity));
	}
	
	protected abstract <ENTITY> void ____deleteEntity____(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	public <ENTITY> void __deleteEntity__(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface){
		____deleteEntity____(entity, layerEntityInterface);
		assertionHelper.assertStartsWithLastLogEventMessage("Server Persistence Delete "+entity.getClass().getSimpleName()).assertContainsLastLogEventMessage(
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
	
	@Override
	public LAYER_ENTITY_INTERFACE __getLayerEntityInterfaceFromClass__(Class<?> aClass) {
		return __inject__(SystemLayerBusiness.class).injectInterfaceClassFromEntityClassName(aClass,__getLayerEntityInterfaceClass__());
	}
	
	@Override
	public LAYER_ENTITY_INTERFACE __getLayerEntityInterfaceFromObject__(Object object) {
		return __getLayerEntityInterfaceFromClass__(object.getClass());
	}
}
