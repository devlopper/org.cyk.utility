package org.cyk.utility.__kernel__.instance;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface InstanceCopier {

	void copy(Object source,Object destination,Map<String,String> fieldsNames);
	
	default void copy(Object source,Object destination) {
		if(source == null || destination == null)
			return;	
		Collection<String> fieldsNames = FieldHelper.getNames(source.getClass());
		if(CollectionHelper.isEmpty(fieldsNames))
			return;
		copy(source, destination,fieldsNames);
	}
	
	default void copy(Object source,Object destination,Collection<String> fieldsNames) {
		if(source == null || destination == null || CollectionHelper.isEmpty(fieldsNames))
			return;
		Map<String,String> map = new LinkedHashMap<>();
		for(String index : fieldsNames)
			map.put(index, index);
		copy(source, destination, map);
	}
	
	/* JSON */
	
	default void copyFromJson(String json,Object destination,Map<String,String> fieldsNames) {
		if(StringHelper.isBlank(json) || destination == null)
			return;	
		if(destination instanceof Collection<?>) {
			
			return;
		}
		
		Map<String,?> map = MapHelper.instantiateFromJson(json);
		if(map == null)
			return;	
		if(MapHelper.isEmpty(fieldsNames))
			copy(map, destination, map.keySet());
		else
			copy(map, destination, fieldsNames);
	}
	
	default void copyFromJson(String json,Object destination) {
		copyFromJson(json, destination, null);
	}
	
	/**/
	
	static InstanceCopier getInstance() {
		InstanceCopier instance = (InstanceCopier) INSTANCE.get();
		if(instance != null)
			return instance;
		INSTANCE.set(instance = DependencyInjection.inject(InstanceCopier.class));
		LogHelper.logInfo("instance has been set. <<"+instance.getClass()+">>", InstanceCopier.class);
		return instance;
	}
	
	Value INSTANCE = new Value();
}
