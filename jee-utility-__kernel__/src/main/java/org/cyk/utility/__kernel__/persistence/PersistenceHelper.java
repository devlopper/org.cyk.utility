package org.cyk.utility.__kernel__.persistence;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ManyToOne;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface PersistenceHelper {

	static Boolean areRelated(Class<?> class1,Class<?> class2,Collection<Class<? extends Annotation>> relationsAnnotationsClasses) {
		if(class1 == null || class2 == null || CollectionHelper.isEmpty(relationsAnnotationsClasses))
			return Boolean.FALSE;
		Collection<Field> relationsAnnotationsFields = FieldHelper.getByAnnotationsClasses(class1, relationsAnnotationsClasses);
		if(CollectionHelper.isEmpty(relationsAnnotationsFields))
			return Boolean.FALSE;
		for(Field field : relationsAnnotationsFields) {
			Type type = FieldHelper.getType(field, class1);
			if(type.equals(class2))
				return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	@SafeVarargs
	static Boolean areRelated(Class<?> class1,Class<?> class2,Class<? extends Annotation>...relationsAnnotationsClasses) {
		if(class1 == null || class2 == null || ArrayHelper.isEmpty(relationsAnnotationsClasses))
			return Boolean.FALSE;
		return areRelated(class1, class2, CollectionHelper.listOf(relationsAnnotationsClasses));
	}
	
	static void sort(List<Class<?>> classes,Boolean ascending) {
		if(CollectionHelper.isEmpty(classes))
			return;
		if(ascending == null)
			ascending = Boolean.TRUE;
		for(Integer i=0; i<classes.size() - 1;) {
			Boolean swapped = null;
			for(Integer j=i+1; j<classes.size(); j = j+1) {
				if(PersistenceHelper.areRelated(classes.get(i), classes.get(j), ManyToOne.class)) {
					if(!ascending)
						continue;
					CollectionHelper.swap(classes, i, j);
					swapped = Boolean.TRUE;
				}				
			}
			if(Boolean.TRUE.equals(swapped))
				i = 0;
			else
				i = i+1;
			swapped = null;
		}
	}
	
	static void sort(List<Class<?>> classes) {
		if(CollectionHelper.isEmpty(classes))
			return;
		sort(classes, null);
	}
	
	static List<Class<?>> sort(Boolean ascending,Class<?>...classes) {
		if(ArrayHelper.isEmpty(classes))
			return null;
		List<Class<?>> list = CollectionHelper.listOf(classes);
		sort(list,ascending);
		return list;
	}
	
	static List<Class<?>> sort(Class<?>...classes) {
		if(ArrayHelper.isEmpty(classes))
			return null;
		return sort(Boolean.TRUE,classes);
	}

	static String getQueryIdentifier(Class<?> klass,String name) {
		if(klass == null || StringHelper.isBlank(name))
			return null;
		Map<String,String> map = QUERY_IDENTIFIERS.get(klass);
		if(map == null)
			QUERY_IDENTIFIERS.put(klass, map = new HashMap<>());
		String identifier = map.get(name);
		if(StringHelper.isBlank(identifier))
			map.put(name, identifier = QueryIdentifierBuilder.getInstance().build(klass,name));
		return identifier;
	}
	
	static String getQueryIdentifierReadByFilters(Class<?> klass) {
		if(klass == null)
			return null;
		return getQueryIdentifier(klass, "readByFilters");
	}
	
	static String getQueryIdentifierCountByFilters(Class<?> klass) {
		if(klass == null)
			return null;
		return getQueryIdentifier(klass, "countByFilters");
	}
	
	static String getQueryIdentifierReadWhereCodeNotInByFilters(Class<?> klass) {
		if(klass == null)
			return null;
		return getQueryIdentifier(klass, "readWhereCodeNotInByFilters");
	}
	
	static String getQueryIdentifierCountWhereCodeNotInByFilters(Class<?> klass) {
		if(klass == null)
			return null;
		return getQueryIdentifier(klass, "countWhereCodeNotInByFilters");
	}
	
	static String getQueryIdentifierReadWhereWhereBusinessIdentifierOrNameContains(Class<?> klass) {
		if(klass == null)
			return null;
		return getQueryIdentifier(klass, "readWhereBusinessIdentifierOrNameContains");
	}
	
	static String getQueryIdentifierCountWhereWhereBusinessIdentifierOrNameContains(Class<?> klass) {
		if(klass == null)
			return null;
		return getQueryIdentifier(klass, "countWhereBusinessIdentifierOrNameContains");
	}
	
	/**/
	
	static void clear() {
		QUERY_IDENTIFIERS.clear();
	}
	
	Map<Class<?>,Map<String,String>> QUERY_IDENTIFIERS = new HashMap<>();
	/*
	String READ_BY_FILTERS = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"readByFilters");
	String COUNT_BY_FILTERS = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"countByFilters");
	
	String READ_WHERE_CODE_NOT_IN_BY_FILTERS = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"readWhereCodeNotInByFilters");
	String COUNT_WHERE_CODE_NOT_IN_BY_FILTERS = QueryIdentifierBuilder.getInstance().build(AdministrativeUnit.class,"countWhereCodeNotInByFilters");
	*/
}
