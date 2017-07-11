package org.cyk.utility.common.helper;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Singleton;

import org.apache.commons.lang3.ClassUtils;
import org.cyk.utility.common.annotation.FieldOverride;
import org.reflections.Reflections;

import lombok.Getter;
import lombok.Setter;

@Singleton
public class ClassHelper extends AbstractReflectionHelper<Class<?>> implements Serializable {

	private static final long serialVersionUID = 1L;

	public Boolean isNumber(Class<?> aClass){
		return Number.class.isAssignableFrom(ClassUtils.primitiveToWrapper(aClass));
	}	
	
	public Class<?> get(Class<?> aClass, String fieldName,Class<?> fieldType) {
		FieldOverride fieldOverride = inject(FieldHelper.class).getOverride(aClass,fieldName);
		Class<?> clazz;
		if(fieldOverride==null)
			clazz = fieldType;
		else
			clazz = fieldOverride.type();
		return clazz;
	}
	
	public Class<?> get(Class<?> aClass, String fieldName) {
		Field field = inject(FieldHelper.class).get(aClass, fieldName);
		return get(aClass, field);
	}
	
	public Class<?> get(Class<?> aClass, Field field) {
		return get(aClass, field.getName(), field.getType());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Class<?>> get(String packageName,Class<?> baseClass){
		Reflections reflections = new Reflections(packageName);
		@SuppressWarnings("rawtypes")
		Collection classes = reflections.getSubTypesOf(baseClass);
		logTrace("sub types of {} in package {} are : {}", baseClass,packageName,classes);
	    return classes;
	}
	
	@SuppressWarnings("unchecked")
	public <TYPE> Class<TYPE> getParameterAt(Class<?> aClass,Integer index,Class<TYPE> typeClass){
		return (Class<TYPE>) ((ParameterizedType) aClass.getGenericSuperclass()).getActualTypeArguments()[index];
	}
	
	public Class<?> getByName(String name){
		try {
			return Class.forName(name);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public <T> T instanciate(Class<T> aClass,Object[] constructorParameters){
		Class<?>[] classes = new Class[constructorParameters.length / 2];
		Object[] arguments = new Object[constructorParameters.length / 2];
		int j = 0;
		for(int i = 0 ; i < constructorParameters.length ; i = i + 2){
			classes[j] = (Class<?>) constructorParameters[i];
			arguments[j++] = constructorParameters[i+1];
		}
		try {
			return aClass.getConstructor(classes).newInstance(arguments);
		} catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	public <T> T instanciateOne(Class<T> aClass){
		try {
			return aClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**/
	
	public static interface Get extends AbstractReflectionHelper.Get<Package, Class<?>> {
		
		Class<?> getBaseClass();
		Get setBaseClass(Class<?> aClass);
		
		@Getter @Setter 
		public static class Adapter extends AbstractReflectionHelper.Get.Adapter.Default<Package, Class<?>> implements Get,Serializable {
			private static final long serialVersionUID = 1L;

			protected Class<?> baseClass;
			
			@SuppressWarnings("unchecked")
			public Adapter(Package input) {
				super(input);
				setInputClass((Class<Package>) new ClassHelper().getByName(Class.class.getName())); 
				setOutputClass((Class<Collection<Class<?>>>) new ClassHelper().getByName(Class.class.getName())); 
			}
			
			public Get setBaseClass(Class<?> baseClass){
				this.baseClass = baseClass;
				return this;
			}
			
			/**/
			
			public static class Default extends Get.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Package input) {
					super(input);
				}
				
				@Override
				public Integer getModifiers(Class<?> clazz) {
					return clazz.getModifiers();
				}
				
				@Override
				public String getName(Class<?> clazz) {
					return clazz.getName();
				}
				
				@Override
				protected Package getParent(Package aPackage) {
					return null;
				}
				
				@Override
				protected Collection<Class<?>> getTypes(Package aPackage) {
					return new ClassHelper().get(aPackage.getName(), getBaseClass());
				}
				
				@Override
				public Set<Class<?>> getAnnotationClasses(Class<?> aClass) {
					Set<Class<?>> classes = new HashSet<>();
					for(Annotation annotation : aClass.getAnnotations()){
						classes.add(annotation.annotationType());
					}
					return classes;
				}
				
			}
		}
		
	}
}
