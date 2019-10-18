package org.cyk.utility.__kernel__.instance;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.map.MapHelper;

public abstract class AbstractInstanceCopierImpl implements InstanceCopier,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void copy(Object source, Object destination, Map<String, String> fieldsNames) {
		if(source == null || destination == null || MapHelper.isEmpty(fieldsNames))
			return;
		//process only simple name first.
		//collect nested fields names and process them last.
		Collection<String> processedSourceSimpleNames = new ArrayList<>();
		Map<String, Map<String,String>> nested = null;
		for(Map.Entry<String, String> entry : fieldsNames.entrySet()) {
			String sourceFieldName = entry.getKey();
			String destinationFieldName = entry.getValue();
			String sourceSimpleName = FieldHelper.getSimpleName(sourceFieldName, Boolean.TRUE);
			//if(processedSourceSimpleNames.contains(sourceSimpleName))
			//	continue;
			if(FieldHelper.isNestedName(sourceFieldName)) {
				if(nested == null)
					nested = new HashMap<>();
				//String sourceSimpleName = FieldHelper.getSimpleName(sourceFieldName, Boolean.TRUE);
				Map<String,String> map = nested.get(sourceSimpleName);
				if(map == null)
					nested.put(sourceSimpleName, map = new HashMap<>());
				map.put(StringUtils.substringAfter(sourceFieldName, FieldHelper.DOT), StringUtils.substringAfter(destinationFieldName, FieldHelper.DOT));
				
				if(processedSourceSimpleNames.contains(sourceSimpleName))
					continue;
				
				sourceFieldName = sourceSimpleName;
				destinationFieldName = FieldHelper.getSimpleName(destinationFieldName, Boolean.TRUE);
				//processedSourceSimpleNames.add(e);
			}
			Field sourceField = FieldHelper.getByName(source.getClass(), sourceFieldName);
			if(sourceField == null)
				throw new RuntimeException("field "+source.getClass()+"."+sourceFieldName+" not found");
			Field destinationField = FieldHelper.getByName(destination.getClass(), destinationFieldName);
			if(destinationField == null)
				throw new RuntimeException("field "+destination.getClass()+"."+destinationFieldName+" not found");			
			Object sourceFieldValue = FieldHelper.read(source, sourceField);
			FieldHelper.copy(source,sourceField,sourceFieldValue,destination,destinationField);
			processedSourceSimpleNames.add(sourceFieldName);
		}
		//process nested fields names
		if(nested != null) {		
			for(Map.Entry<String, Map<String,String>> entry : nested.entrySet()) {
				Object nestedSource = FieldHelper.read(source, entry.getKey());
				Object nestedDestination = FieldHelper.read(destination, entry.getKey());
				copy(nestedSource, nestedDestination, entry.getValue());
			}
		}
	}
	
}
