package org.cyk.utility.__kernel__.persistence;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.jboss.weld.exceptions.IllegalArgumentException;

public interface PersistenceHelper {

	static EntityManager getEntityManager(Properties properties, Boolean injectIfNull) {
		EntityManager entityManager = (EntityManager) (properties == null ? null : properties.getEntityManager());
		if(entityManager == null && Boolean.TRUE.equals(injectIfNull))
			entityManager = DependencyInjection.inject(EntityManager.class);
		return entityManager;
	}

	static EntityManager getEntityManager(Properties properties) {
		return getEntityManager(properties, Boolean.TRUE);
	}
	
	static <T> T getEntityWithItsReferenceOnly(Class<T> klass,Object identifier,EntityManager entityManager) {
		if(klass == null)
			throw new IllegalArgumentException("class is required");		
		if(identifier == null)
			return null;
		if(entityManager == null)
			entityManager = EntityManagerGetter.getInstance().get();
		return entityManager.getReference(klass, identifier);
	}
	
	static <T> T getEntityWithItsReferenceOnly(Class<T> klass,Object identifier) {
		return getEntityWithItsReferenceOnly(klass, identifier, EntityManagerGetter.getInstance().get());
	}
	
	/**/
	
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

	static String getTableName(Class<?> klass) {
		if(klass == null)
			return null;
		if(CLASS_TABLE_NAME.containsKey(klass))
			return CLASS_TABLE_NAME.get(klass);
		String name = null;
		Table table = klass.getAnnotation(Table.class);
		if(table != null)
			name = table.name();
		if(StringHelper.isBlank(name))
			name = klass.getSimpleName().toUpperCase();
		CLASS_TABLE_NAME.put(klass, name);
		return name;
	}
	
	static Set<String> getColumnsNames(Class<?> klass) {
		if(klass == null)
			return null;
		if(CLASS_COLUMNS_NAMES.containsKey(klass))
			return CLASS_COLUMNS_NAMES.get(klass);
		Set<String> names = null;
		Collection<Field> fields = FieldHelper.get(klass);
		if(CollectionHelper.isEmpty(fields)) {
			CLASS_COLUMNS_NAMES.put(klass, null);
			return null;
		}
		Map<String,Field> map = COLUMN_NAME_FIELD.get(klass);
		if(map == null)
			COLUMN_NAME_FIELD.put(klass, map = new HashMap<>());
		/*EntityManagerFactory entityManagerFactory = EntityManagerFactoryGetter.getInstance().get();
		if(entityManagerFactory != null) {
			Set attributes = entityManagerFactory.getMetamodel().entity(klass).getAttributes();
			if(CollectionHelper.isEmpty(attributes)) {
				CLASS_COLUMNS_NAMES.put(klass, null);
				return null;
			}
			for(Object object : attributes) {
				Attribute<?,?> attribute = (Attribute<?, ?>) object;
				attribute.
			}
		}
		*/
		for(Field field : fields) {
			String name = null;
			Column column = field.getAnnotation(Column.class);
			if(column == null) {
				JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
				if(joinColumn == null) {
					
				}else {
					name = joinColumn.name();
				}
			}else {
				name = column.name();
			}
				
			if(StringHelper.isBlank(name))
				continue;
			if(names == null)
				names = new LinkedHashSet<>();		
			map.put(name,field);
			Id id = field.getAnnotation(Id.class);
			if(id != null)
				CLASS_PRIMARY_KEY_COLUMN_NAME.put(klass, name);
			names.add(name);
		}	
		CLASS_COLUMNS_NAMES.put(klass, names);
		return names;
	}
	
	static Collection<String> getColumnsValues(Object object) {
		if(object == null)
			return null;
		Collection<String> names = getColumnsNames(object.getClass());
		if(CollectionHelper.isEmpty(names))
			return null;
		Collection<String> values = new ArrayList<>();
		for(String name : names) {
			Field field = COLUMN_NAME_FIELD.get(object.getClass()).get(name);
			String value = stringifyColumnValue(FieldHelper.read(object, field));	
			values.add(value);
		}
		return values;
	}
	
	static String stringifyColumnValue(Object value) {
		if(value == null)
			return null;	
		if(!ClassHelper.isBelongsToJavaPackages(value.getClass()))
			value = FieldHelper.readSystemIdentifier(value);
		if(String.class.equals(value.getClass()))
			value = "'"+value+"'";		
		return value.toString();
	}
	
	static Map<String,String> getColumnsValuesAsMap(Object object) {
		if(object == null)
			return null;
		Collection<String> names = getColumnsNames(object.getClass());
		if(CollectionHelper.isEmpty(names))
			return null;
		Map<String,String> map = new LinkedHashMap<>();
		for(String name : names) {
			Field field = COLUMN_NAME_FIELD.get(object.getClass()).get(name);
			String value =stringifyColumnValue(FieldHelper.read(object, field));
			map.put(name,value);
		}
		return map;
	}
	
	static String getPrimaryKeyColumnName(Class<?> klass) {
		getColumnsNames(klass);
		return CLASS_PRIMARY_KEY_COLUMN_NAME.get(klass);
	}
	
	/**/
	
	Map<Class<?>,String> CLASS_TABLE_NAME = new HashMap<>();
	Map<Class<?>,String> CLASS_PRIMARY_KEY_COLUMN_NAME = new HashMap<>();
	Map<Class<?>,Set<String>> CLASS_COLUMNS_NAMES = new HashMap<>();
	Map<Class<?>,Map<String,Field>> COLUMN_NAME_FIELD = new HashMap<>();
}