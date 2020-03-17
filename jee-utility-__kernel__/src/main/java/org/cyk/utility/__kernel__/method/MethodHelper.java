package org.cyk.utility.__kernel__.method;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface MethodHelper {

	static Collection<Method> filter(Class<?> aClass,Collection<Class<?>> annotationsClasses,String token) {
		Collection<Method> collection = null;
		Collection<Method> methods = new ArrayList<Method>();
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
					if(StringHelper.isBlank(token) || index.getName().equals(token))
						methods.add(index);
				}
			}
			indexClass = indexClass.getSuperclass();
		}
		
		if(CollectionHelper.isEmpty(annotationsClasses)) {
			
		}else {
			methods = MethodUtils.getMethodsListWithAnnotation(aClass, (Class<? extends Annotation>) CollectionHelper.getFirst(annotationsClasses));	
		}
		
		
		for(Method index : methods){
			Boolean add = Boolean.TRUE;
			/*if(StringHelper.isNotBlank(token)){
				add =StringHelper.isAtLocation(index.getName(), token, location);				
			}*/
			if(Boolean.TRUE.equals(add)){
				if(collection == null)
					collection = new ArrayList<>();
				collection.add(index);
			}
		}
		return collection;
	}
	
}
