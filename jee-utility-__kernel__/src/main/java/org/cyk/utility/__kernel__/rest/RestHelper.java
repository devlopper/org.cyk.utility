package org.cyk.utility.__kernel__.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.json.bind.JsonbBuilder;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.instance.InstanceHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.protocol.http.HttpHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.jboss.weld.exceptions.IllegalArgumentException;

/**
 * Representational State Transfer Helper
 * @author CYK
 *
 */
public interface RestHelper {
	
	static <RESOURCE> Collection<RESOURCE> getMany(Class<RESOURCE> resourceClass,Object classifier,URI uniformResourceIdentifier,Map<String,String> fieldsNamesMap) {
		if(resourceClass == null)
			throw new IllegalArgumentException("Resource class is required");
		if(uniformResourceIdentifier == null) {
			String uniformResourceIdentifierAsString = ConfigurationHelper.getClassUniformResourceIdentifier(resourceClass,classifier);
			if(StringHelper.isBlank(uniformResourceIdentifierAsString))
				throw new IllegalArgumentException("Resource uniform identifier is required");
			try {
				uniformResourceIdentifier = new URI(uniformResourceIdentifierAsString);
			} catch (URISyntaxException exception) {
				throw new RuntimeException(exception);
			}
		}
		//1 - get json string
		HttpResponse<String> response = HttpHelper.get(uniformResourceIdentifier);
		String responseBody = response.body();
		if(StringHelper.isBlank(responseBody))
			return null;
		//2 - build collection of map from json
		Collection<Map<String,String>> maps = JsonbBuilder.create().fromJson(responseBody, new ArrayList<Map<String,String>>(){
			private static final long serialVersionUID = 1L;}.getClass().getGenericSuperclass());
		if(CollectionHelper.isEmpty(maps))
			return null;
		if(fieldsNamesMap == null) {
			VariableHelper.load(resourceClass,classifier);
			//deduce fields names from json
			for(Map.Entry<String, String> entry : CollectionHelper.getFirst(maps).entrySet()) {
				if(StringHelper.isBlank(entry.getKey()))
					continue;
				String fieldName = VariableHelper.readFieldNameByValue(resourceClass, classifier, entry.getKey());
				if(StringHelper.isBlank(fieldName)) {
					if(FieldHelper.getNames(resourceClass).contains(entry.getKey()))
						fieldName = entry.getKey();
				}
				if(StringHelper.isBlank(fieldName)) 
					continue;
				if(fieldsNamesMap == null)
					fieldsNamesMap = new HashMap<>();
				fieldsNamesMap.put(entry.getKey(), fieldName);
			}
		}
		if(MapHelper.isEmpty(fieldsNamesMap))
			return null;
		//3 - copy map to resource instance
		Collection<RESOURCE> resources = new ArrayList<>();
		for(Map<String, String> map : maps) {
			RESOURCE resource = ClassHelper.instanciate(resourceClass);
			InstanceHelper.copy(map, resource, fieldsNamesMap);
			resources.add(resource);
		}
		return resources;
	}
	
	static <RESOURCE> Collection<RESOURCE> getMany(Class<RESOURCE> resourceClass,Object classifier) {
		return getMany(resourceClass, classifier, null, null);
	}
	
	static <RESOURCE> Collection<RESOURCE> getMany(Class<RESOURCE> resourceClass) {
		return getMany(resourceClass, null, null, null);
	}
	
}
