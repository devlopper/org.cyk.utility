package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.string.StringLocation;
import org.cyk.utility.value.ValueUsageType;

public class FieldGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Collection<Field>> implements FieldGetter, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Collection<Field> __execute__() {
		Collection<Field> collection = null;
		Class<?> aClass = getClazz();
		Collection<Field> fields = FieldUtils.getAllFieldsList(aClass);
		String token = getToken();
		if(__inject__(StringHelper.class).isBlank(token)){
			FieldName fieldName = getFieldName();
			if(fieldName!=null){
				ValueUsageType valueUsageType = getValueUsageType();
				token = __inject__(FieldNameGetter.class).setClazz(aClass).setFieldName(fieldName).setValueUsageType(valueUsageType).execute().getOutput();
			}
		}
		StringLocation location = getTokenLocation();
		if(location == null)
			location = StringLocation.EXAT;
		for(Field index : fields){
			Boolean add = Boolean.TRUE;
			if(__inject__(StringHelper.class).isNotBlank(token)){
				add = __inject__(StringHelper.class).isAtLocation(index.getName(), token, location);				
			}
			if(Boolean.TRUE.equals(add)){
				if(collection == null)
					collection = new ArrayList<>();
				collection.add(index);
			}
		}
		return collection;
	}
	
	@Override
	public FieldGetter execute(Class<?> aClass) {
		return (FieldGetter) setClazz(aClass).execute();
	}
	
	@Override
	public FieldGetter execute(Class<?> aClass, String name) {
		return (FieldGetter) setClazz(aClass).setToken(name).setTokenLocation(StringLocation.EXAT).execute();
	}
	
	/*protected Collection<Class<?>> get(Class<?> source) {
		//super class fields first
		
		if(!Boolean.FALSE.equals(getIsRecursive())){
			Class<?> parent = source.getSuperclass();
			if (parent != null) {
				get(parent);
			}
		}
		//declared class fields second
		int searchMods = 0x0;
		for (Integer modifier : getModifiers())
			searchMods |= 0x0 | modifier;
		
		Collection<Class<?>> annotationClasses = getAnnotationClasses();
		for (Field type : source.getDeclaredFields()) {
			if(!CollectionHelper.getInstance().isEmpty(annotationClasses) && !CollectionHelper.getInstance().contains(getAnnotationClasses(type), annotationClasses)){
				continue;
			}
					
			//if(((getModifiers(type) & searchMods) == searchMods) &&  StringHelper.getInstance().isAtLocation(getName(type), getToken(), getTokenLocation())){
			//	getOutput().add(type);
			//}
		}
		
		return null;
	}*/
	
	public Integer getModifiers(java.lang.reflect.Field field) {
		return field.getModifiers();
	}
	
	public String getName(java.lang.reflect.Field field) {
		return field.getName();
	}
	
	protected Class<?> getParent(Class<?> clazz) {
		return clazz.getSuperclass();
	}
	
	protected java.util.Collection<java.lang.reflect.Field> getTypes(Class<?> clazz) {
		return Arrays.asList(clazz.getDeclaredFields());
	}
	
	@Override
	public FieldGetter setInput(Properties input) {
		return (FieldGetter) super.setInput(input);
	}

	@Override
	public FieldGetter setProperties(Properties properties) {
		return (FieldGetter) super.setProperties(properties);
	}
	
	@Override
	public Boolean getIsRecursive() {
		return (Boolean) getProperties().getIsRecursive();
	}
	
	@Override
	public FieldGetter setIsRecursive(Boolean value) {
		getProperties().setIsRecursive(value);
		return this;
	}

	@Override
	public String getToken() {
		return (String) getProperties().getToken();
	}

	@Override
	public FieldGetter setToken(String token) {
		getProperties().setToken(token);
		return this;
	}

	@Override
	public StringLocation getTokenLocation() {
		getProperties().getTokenLocation();
		return null;
	}

	@Override
	public FieldGetter setTokenLocation(StringLocation stringLocation) {
		getProperties().setTokenLocation(stringLocation);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Integer> getModifiers() {
		return (Set<Integer>) getProperties().getModifiers();
	}

	@Override
	public FieldGetter setModifiers(Set<Integer> modifiers) {
		getProperties().setModifiers(modifiers);
		return this;
	}

	@Override
	public FieldGetter addModifiers(Collection<Integer> modifiers) {
		if(modifiers != null){
			Set<Integer> set = getModifiers();
			if(set == null)
				setModifiers(set = new LinkedHashSet<>());
			set.addAll(modifiers);
		}
		return this;
	}

	@Override
	public FieldGetter addModifiers(Integer... modifiers) {
		addModifiers(__inject__(CollectionHelper.class).instanciate(modifiers));
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Class<?>> getAnnotationClasses() {
		return (Set<Class<?>>) getProperties().getAnnotationClasses();
	}

	@Override
	public FieldGetter setAnnotationClasses(Set<Class<?>> annotationClasses) {
		getProperties().setAnnotationClasses(annotationClasses);
		return this;
	}

	@Override
	public FieldGetter addAnnotationClasses(Collection<Class<?>> annotationClasses) {
		if(annotationClasses != null){
			Set<Class<?>> set = getAnnotationClasses();
			if(set == null)
				setAnnotationClasses(set = new LinkedHashSet<>());
			set.addAll(annotationClasses);
		}
		return this;
	}

	@Override
	public FieldGetter addAnnotationClasses(Class<?>...annotationClasses) {
		addAnnotationClasses(__inject__(CollectionHelper.class).instanciate(annotationClasses));
		return this;
	}

	@Override
	public Class<?> getClazz() {
		return (Class<?>) getProperties().getClazz();
	}

	@Override
	public FieldGetter setClazz(Class<?> aClass) {
		getProperties().setClass(aClass);
		return this;
	}

	@Override
	public FieldName getFieldName() {
		return (FieldName) getProperties().getFieldName();
	}

	@Override
	public FieldGetter setFieldName(FieldName fieldName) {
		getProperties().setFieldName(fieldName);
		return this;
	}

	@Override
	public ValueUsageType getValueUsageType() {
		return (ValueUsageType) getProperties().getValueUsageType();
	}

	@Override
	public FieldGetter setValueUsageType(ValueUsageType valueUsageType) {
		getProperties().setValueUsageType(valueUsageType);
		return this;
	}

}
