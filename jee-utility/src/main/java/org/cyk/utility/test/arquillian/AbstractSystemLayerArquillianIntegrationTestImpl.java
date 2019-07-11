package org.cyk.utility.test.arquillian;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.clazz.ClassHelperImpl;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldsGetter;
import org.cyk.utility.field.FieldHelper;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.field.FieldValueGetter;
import org.cyk.utility.field.Fields;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionCreate;
import org.cyk.utility.system.action.SystemActionDelete;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.system.action.SystemActionUpdate;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractSystemLayerArquillianIntegrationTestImpl<LAYER_ENTITY_INTERFACE> extends org.cyk.utility.test.arquillian.AbstractArquillianIntegrationTest implements SystemLayerIntegrationTest<LAYER_ENTITY_INTERFACE>, Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<? extends LAYER_ENTITY_INTERFACE> layerEntityInterfaceClass;
	
	{
		layerEntityInterfaceClass = (Class<LAYER_ENTITY_INTERFACE>) ClassHelperImpl.__getParameterAt__(getClass(), 0, Object.class);
	}
	
	/* Create one entity */
	
	protected abstract <ENTITY> void ____createEntity____(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	public <ENTITY> void __createEntity__(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface){
		____createEntity____(entity, layerEntityInterface);
		____assertThatEntityHasBeenPersisted____(entity, layerEntityInterface);
		____assertThatLogSaysEntityHasBeen____(SystemActionCreate.class,entity, layerEntityInterface);
	}
	
	protected <ENTITY> void ____assertThatEntityHasBeenPersisted____(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface) {
		assertThat(__getFieldValueSystemIdentifier__(entity)).isNotNull();
	}
	
	protected <ENTITY> void ____assertThatLogSaysEntityHasBeen____(Class<? extends SystemAction> systemActionClass,ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface) {
		assertionHelper.assertStartsWithLastLogEventMessage(__getLogMessageStart__(__inject__(systemActionClass),entity.getClass()))
		.assertContainsLastLogEventMessage("code="+__inject__(FieldValueGetter.class).execute(entity,FieldName.IDENTIFIER,ValueUsageType.BUSINESS).getOutput());
	}
	
	public <ENTITY> void __createEntity__(ENTITY entity){
		__createEntity__(entity,  __getLayerEntityInterfaceFromObject__(entity));
	}
	
	/* Create many entities */
	
	protected abstract <ENTITY> void ____createEntity____(Collection<ENTITY> entities,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	public <ENTITY> void __createEntity__(Collection<ENTITY> entities,LAYER_ENTITY_INTERFACE layerEntityInterface){
		____createEntity____(entities, layerEntityInterface);
		____assertThatEntityHasBeenPersisted____(entities, layerEntityInterface);
		____assertThatLogSaysEntityHasBeen____(SystemActionCreate.class,entities, layerEntityInterface);
	}
	
	protected <ENTITY> void ____assertThatEntityHasBeenPersisted____(Collection<ENTITY> entities,LAYER_ENTITY_INTERFACE layerEntityInterface) {
		for(ENTITY index : entities)
			assertThat(__getFieldValueSystemIdentifier__(index)).isNotNull();
	}
	
	protected <ENTITY> void ____assertThatLogSaysEntityHasBeen____(Class<? extends SystemAction> systemActionClass,Collection<ENTITY> entities,LAYER_ENTITY_INTERFACE layerEntityInterface) {}
	
	public <ENTITY> void __createEntity__(Collection<ENTITY> entities){
		if(entities!=null && !entities.isEmpty())
			__createEntity__(entities,  __getLayerEntityInterfaceFromObject__(entities.iterator().next()));
	}
	
	/* Read one entity */
	
	protected abstract <ENTITY> ENTITY ____readEntity____(Class<ENTITY> entityClass,Object identifier,ValueUsageType valueUsageType,Properties expectedFieldValues,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	public <ENTITY> ENTITY __readEntity__(Class<ENTITY> entityClass,Object identifier,ValueUsageType valueUsageType,Properties expectedFieldValues,LAYER_ENTITY_INTERFACE layerEntityInterface){
		ENTITY entity = ____readEntity____(entityClass, identifier, valueUsageType, expectedFieldValues, layerEntityInterface);
		____assertThatEntityHasBeenPersisted____(entity, layerEntityInterface);
		//assertionHelper.assertEqualsByFieldValue(expectedFieldValues, entity);
		____assertThatLogSaysEntityHasBeen____(SystemActionRead.class,entity, layerEntityInterface);
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
	
	/* Update one entity */
	
	protected abstract <ENTITY> void ____updateEntity____(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	public <ENTITY> void __updateEntity__(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface) {
		____updateEntity____(entity, layerEntityInterface);
		____assertThatEntityHasBeenPersisted____(entity, layerEntityInterface);
		//assertThat(__inject__(FieldValueGetter.class).execute(entity,FieldName.IDENTIFIER,ValueUsageType.SYSTEM).getOutput()).isNotNull();
		____assertThatLogSaysEntityHasBeen____(SystemActionUpdate.class, entity, layerEntityInterface);
		//assertionHelper.assertStartsWithLastLogEventMessage(__getLogMessageStart__(__inject__(SystemActionUpdate.class),entity.getClass()))
		//	.assertContainsLastLogEventMessage("code="+__inject__(FieldValueGetter.class).execute(entity,FieldName.IDENTIFIER,ValueUsageType.BUSINESS).getOutput());
	}
	
	public <ENTITY> void __updateEntity__(ENTITY entity) {
		__updateEntity__(entity, __getLayerEntityInterfaceFromObject__(entity));
	}
	
	/* Delete one entity */
	
	protected abstract <ENTITY> void ____deleteEntity____(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	public <ENTITY> void __deleteEntity__(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface){
		____deleteEntity____(entity, layerEntityInterface);
		____assertThatLogSaysEntityHasBeen____(SystemActionDelete.class, entity, layerEntityInterface);
		//assertionHelper.assertStartsWithLastLogEventMessage(__getLogMessageStart__(__inject__(SystemActionDelete.class),entity.getClass())).assertContainsLastLogEventMessage(
		//		"code="+__inject__(FieldValueGetter.class).execute(entity,FieldName.IDENTIFIER,ValueUsageType.BUSINESS).getOutput());
		
		//Object systemIdentifier = __inject__(FieldValueGetter.class).execute(entity,FieldName.IDENTIFIER,ValueUsageType.SYSTEM).getOutput();
		//entity = (ENTITY) layerEntityInterface.readOne(systemIdentifier);
		//assertThat(entity).isNull();
	}
	
	public <ENTITY> void __deleteEntity__(ENTITY entity) {
		__deleteEntity__(entity, __getLayerEntityInterfaceFromObject__(entity));
	}
	
	@Override
	public <ENTITY> void __deleteEntity__(Class<ENTITY> entityClass, Object identifier,ValueUsageType valueUsageType) {
		__deleteEntity__(__readEntity__(entityClass, identifier, valueUsageType));
	}
	
	@Override
	public <ENTITY> void __deleteEntityByBusinessIdentifier__(Class<ENTITY> entityClass, Object identifier) {
		__deleteEntity__(entityClass, identifier, ValueUsageType.BUSINESS);
	}
	
	@Override
	public <ENTITY> void __deleteEntityBySystemIdentifier__(Class<ENTITY> entityClass, Object identifier) {
		__deleteEntity__(entityClass, identifier, ValueUsageType.SYSTEM);
	}
	
	/* Delete all entities */
	
	protected abstract <ENTITY> void ____deleteEntityAll____(Class<ENTITY> entityClass,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	public <ENTITY> void __deleteEntitiesAll__(Class<ENTITY> entityClass,LAYER_ENTITY_INTERFACE layerEntityInterface){
		____deleteEntityAll____(entityClass, layerEntityInterface);
		//assertionHelper.assertEqualsNumber("number of "+entityClass.getSimpleName()+" is not zero", 0, __countEntitiesAll__(entityClass, layerEntityInterface));
		
		//Object systemIdentifier = __inject__(FieldValueGetter.class).execute(entity,FieldName.IDENTIFIER,ValueUsageType.SYSTEM).getOutput();
		//entity = (ENTITY) layerEntityInterface.readOne(systemIdentifier);
		//assertThat(entity).isNull();
	}
	
	public <ENTITY> void __deleteEntitiesAll__(Class<ENTITY> entityClass){
		__deleteEntitiesAll__(entityClass, __getLayerEntityInterfaceFromClass__(entityClass));
	}
	
	/* Count all entities */
	
	protected abstract <ENTITY> Long ____countEntitiesAll____(Class<ENTITY> entityClass,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	public <ENTITY> Long __countEntitiesAll__(Class<ENTITY> entityClass,LAYER_ENTITY_INTERFACE layerEntityInterface){
		return ____countEntitiesAll____(entityClass, layerEntityInterface);
	}
	
	public <ENTITY> Long __countEntitiesAll__(Class<ENTITY> entityClass){
		return __countEntitiesAll__(entityClass, __getLayerEntityInterfaceFromClass__(entityClass));
	}
	
	/**/
	
	@Override
	public Class<? extends LAYER_ENTITY_INTERFACE> __getLayerEntityInterfaceClass__() {
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
	
	/**/
	
	@Override
	protected void __setFieldValues__(Object object) {
		super.__setFieldValues__(object);
		if(object != null) {
			__inject__(FieldHelper.class).setFieldValueSystemIdentifier(object, __getRandomIdentifier__());
			
			Fields fields = __inject__(FieldsGetter.class).setFieldName(FieldName.IDENTIFIER).setValueUsageType(ValueUsageType.BUSINESS).setClazz(object.getClass()).execute().getOutput();
			if(__inject__(CollectionHelper.class).isNotEmpty(fields))
				__inject__(FieldHelper.class).setFieldValueBusinessIdentifier(object, __getRandomCode__());
		}
	}
	
	protected <T> T __instanciate__(Class<T> aClass,Object action) throws Exception{
		T object = instanciateOne(aClass);
		__setFieldValues__(object);
		return object;
	}
	
	protected <T> Collection<T> __instanciate__(Class<T> aClass,Object action,Integer count) throws Exception{
		Collection<T> collection = new ArrayList<>();
		for(Integer index = 0 ; index < count ; index = index + 1)
			collection.add(__instanciate__(aClass,action));
		return collection;
	}
}
