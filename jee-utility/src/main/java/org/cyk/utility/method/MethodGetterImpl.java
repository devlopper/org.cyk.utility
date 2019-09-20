package org.cyk.utility.method;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;
import org.cyk.utility.string.StringLocation;

@Dependent
public class MethodGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Collection<Method>> implements MethodGetter, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Collection<Method> __execute__() {
		Collection<Method> collection = null;
		Class<?> aClass = getClazz();
		Collection<Class<?>> annotationsClasses = getAnnotationClasses();
		Collection<Method> methods = new ArrayList<Method>();
		String token = getToken();
		//Get all
		Class<?> indexClass = aClass;
		while (indexClass != null) {
			for (Method index : aClass.getDeclaredMethods()) {
				Boolean overridden = Boolean.FALSE;
				for (Method indexFound : methods) {
					if (indexFound.getName().equals(index.getName()) && Arrays.deepEquals(index.getParameterTypes(), indexFound.getParameterTypes())) {
						overridden = Boolean.TRUE;
						break;
					}
				}
				if(!overridden) {
					//index.geta
					if(__injectStringHelper__().isBlank(token) || index.getName().equals(token))
						methods.add(index);
				}
			}
			indexClass = indexClass.getSuperclass();
		}
		
		if(__injectCollectionHelper__().isEmpty(annotationsClasses)) {
			
		}else {
			methods = MethodUtils.getMethodsListWithAnnotation(aClass, (Class<? extends Annotation>) __inject__(CollectionHelper.class)
					.getFirst(getAnnotationClasses()));	
		}
		
		
		for(Method index : methods){
			Boolean add = Boolean.TRUE;
			/*if(__inject__(StringHelper.class).isNotBlank(token)){
				add = __inject__(StringHelper.class).isAtLocation(index.getName(), token, location);				
			}*/
			if(Boolean.TRUE.equals(add)){
				if(collection == null)
					collection = new ArrayList<>();
				collection.add(index);
			}
		}
		return collection;
	}
	
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
	public MethodGetter setInput(Properties input) {
		return (MethodGetter) super.setInput(input);
	}

	@Override
	public MethodGetter setProperties(Properties properties) {
		return (MethodGetter) super.setProperties(properties);
	}
	
	@Override
	public Boolean getIsRecursive() {
		return (Boolean) getProperties().getIsRecursive();
	}
	
	@Override
	public MethodGetter setIsRecursive(Boolean value) {
		getProperties().setIsRecursive(value);
		return this;
	}

	@Override
	public String getToken() {
		return (String) getProperties().getToken();
	}

	@Override
	public MethodGetter setToken(String token) {
		getProperties().setToken(token);
		return this;
	}

	@Override
	public StringLocation getTokenLocation() {
		getProperties().getTokenLocation();
		return null;
	}

	@Override
	public MethodGetter setTokenLocation(StringLocation stringLocation) {
		getProperties().setTokenLocation(stringLocation);
		return this;
	}

	@Override
	public Set<Integer> getModifiers() {
		return (Set<Integer>) getProperties().getModifiers();
	}

	@Override
	public MethodGetter setModifiers(Set<Integer> modifiers) {
		getProperties().setModifiers(modifiers);
		return this;
	}

	@Override
	public MethodGetter addModifiers(Collection<Integer> modifiers) {
		if(modifiers != null){
			Set<Integer> set = getModifiers();
			if(set == null)
				setModifiers(set = new LinkedHashSet<>());
			set.addAll(modifiers);
		}
		return this;
	}

	@Override
	public MethodGetter addModifiers(Integer... modifiers) {
		addModifiers(__inject__(CollectionHelper.class).instanciate(modifiers));
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Class<?>> getAnnotationClasses() {
		return (Set<Class<?>>) getProperties().getAnnotationClasses();
	}

	@Override
	public MethodGetter setAnnotationClasses(Set<Class<?>> annotationClasses) {
		getProperties().setAnnotationClasses(annotationClasses);
		return this;
	}

	@Override
	public MethodGetter addAnnotationClasses(Collection<Class<?>> annotationClasses) {
		if(annotationClasses != null){
			Set<Class<?>> set = getAnnotationClasses();
			if(set == null)
				setAnnotationClasses(set = new LinkedHashSet<>());
			set.addAll(annotationClasses);
		}
		return this;
	}

	@Override
	public MethodGetter addAnnotationClasses(Class<?>...annotationClasses) {
		addAnnotationClasses(__inject__(CollectionHelper.class).instanciate(annotationClasses));
		return this;
	}

	@Override
	public Class<?> getClazz() {
		return (Class<?>) getProperties().getClazz();
	}

	@Override
	public MethodGetter setClazz(Class<?> aClass) {
		getProperties().setClass(aClass);
		return this;
	}

}
