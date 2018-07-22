package org.cyk.utility.test.arquillian;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.value.ValueUsageType;

public interface SystemLayerIntegrationTest<LAYER_ENTITY_INTERFACE> {

	<ENTITY> void __createEntity__(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	<ENTITY> void __createEntity__(ENTITY entity);
	
	<ENTITY> ENTITY __readEntity__(Class<ENTITY> entityClass,Object identifier,ValueUsageType valueUsageType,Properties expectedFieldValues,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	<ENTITY> ENTITY __readEntity__(Class<ENTITY> entityClass,Object identifier,ValueUsageType valueUsageType,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	<ENTITY> ENTITY __readEntity__(Class<ENTITY> entityClass,Object identifier,ValueUsageType valueUsageType,Properties expectedFieldValues);
	
	<ENTITY> ENTITY __readEntity__(Class<ENTITY> entityClass,Object identifier,ValueUsageType valueUsageType);
	
	<ENTITY> void __updateEntity__(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	<ENTITY> void __updateEntity__(ENTITY entity);
	
	<ENTITY> void __deleteEntity__(ENTITY entity,LAYER_ENTITY_INTERFACE layerEntityInterface);
	
	<ENTITY> void __deleteEntity__(ENTITY entity);
	
	/**/
	
	Class<LAYER_ENTITY_INTERFACE> __getLayerEntityInterfaceClass__();
	
	LAYER_ENTITY_INTERFACE __getLayerEntityInterfaceFromObject__(Object object);
	
	LAYER_ENTITY_INTERFACE __getLayerEntityInterfaceFromClass__(Class<?> aClass);
	
}
