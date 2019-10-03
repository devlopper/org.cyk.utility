package org.cyk.utility.__kernel__.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.annotation.Test;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.value.ValueUsageType;

@Test
public class InstanceGetterImpl implements InstanceGetter {

	private static final Map<Class<?>,List<Object>> OBJECTS = new HashMap<>();
	
	@SuppressWarnings("unchecked")
	@Override
	public <INSTANCE> INSTANCE getByIdentifier(Class<INSTANCE> klass, Object identifier,ValueUsageType valueUsageType) {
		if(klass == null || identifier == null)
			return null;
		List<Object> objects = OBJECTS.get(klass);
		if(CollectionHelper.isEmpty(objects))
			return null;
		if(valueUsageType == null)
			valueUsageType = ValueUsageType.SYSTEM;
		for(Object index : objects) {
			if(ValueUsageType.SYSTEM.equals(valueUsageType) && identifier.equals(FieldHelper.readSystemIdentifier(index)))
				return (INSTANCE) index;
			if(ValueUsageType.BUSINESS.equals(valueUsageType) && identifier.equals(FieldHelper.readBusinessIdentifier(index)))
				return (INSTANCE) index;			
		}
		return null;
	}

	public static <INSTANCE> void add(Class<INSTANCE> klass,Collection<INSTANCE> instances) {
		if(klass == null || CollectionHelper.isEmpty(instances))
			return;
		List<Object> objects = OBJECTS.get(klass);
		if(objects == null)
			OBJECTS.put(klass, objects = new ArrayList<>());
		objects.addAll(instances);
	}
	
	public static <INSTANCE> void add(Class<INSTANCE> klass,@SuppressWarnings("unchecked") INSTANCE...instances) {
		if(klass == null || ArrayHelper.isEmpty(instances))
			return;
		add(klass,CollectionHelper.listOf(instances));
	}
	
	public static void clear() {
		OBJECTS.clear();
	}
}
