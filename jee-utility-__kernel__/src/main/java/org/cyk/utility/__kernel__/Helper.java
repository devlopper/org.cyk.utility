package org.cyk.utility.__kernel__;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;

public interface Helper {

	static Boolean isHaveModifiers(Integer encodedModifiers,Collection<Integer> modifiers,Integer numberOfMatch) {
		if(encodedModifiers == null)
			return Boolean.FALSE;
		if( modifiers == null || modifiers.isEmpty())
			return Boolean.TRUE;
		if(numberOfMatch == null || numberOfMatch <= 0)
			return Boolean.TRUE;
		Integer countNumberOfMatch = 0;
		for(Integer modifier : modifiers) {
			if(	(
					(Modifier.isAbstract(encodedModifiers) && Modifier.isAbstract(modifier))
					||(Modifier.isFinal(encodedModifiers) && Modifier.isFinal(modifier))
					||(Modifier.isNative(encodedModifiers) && Modifier.isNative(modifier))
					||(Modifier.isPrivate(encodedModifiers) && Modifier.isPrivate(modifier))
					||(Modifier.isProtected(encodedModifiers) && Modifier.isProtected(modifier))
					||(Modifier.isPublic(encodedModifiers) && Modifier.isPublic(modifier))
					||(Modifier.isStatic(encodedModifiers) && Modifier.isStatic(modifier))
					||(Modifier.isStrict(encodedModifiers) && Modifier.isStrict(modifier))
					||(Modifier.isTransient(encodedModifiers) && Modifier.isTransient(modifier))
					||(Modifier.isVolatile(encodedModifiers) && !Modifier.isVolatile(modifier))
					)
				) {
				if(++countNumberOfMatch == numberOfMatch)
					return Boolean.TRUE;	
			}				
		}
		return Boolean.FALSE;
	}
	
	static Boolean isHaveModifiers(Integer encodedModifiers,Collection<Integer> modifiers) {
		if(encodedModifiers == null)
			return Boolean.FALSE;
		if( modifiers == null || modifiers.isEmpty())
			return Boolean.TRUE;
		return isHaveModifiers(encodedModifiers, modifiers, 1);
	}
	
	static Boolean isHaveModifiers(Integer encodedModifiers,Integer...modifiers) {
		if(encodedModifiers == null)
			return Boolean.FALSE;
		if( modifiers == null || modifiers.length == 0)
			return Boolean.TRUE;
		return isHaveModifiers(encodedModifiers, List.of(modifiers),1);
	}
	
	static Boolean isHaveAllModifiers(Integer encodedModifiers,Collection<Integer> modifiers) {
		if(encodedModifiers == null)
			return Boolean.FALSE;
		if( modifiers == null || modifiers.isEmpty())
			return Boolean.TRUE;
		return isHaveModifiers(encodedModifiers, modifiers, modifiers.size());
	}
	
	static Boolean isHaveAllModifiers(Integer encodedModifiers,Integer...modifiers) {
		if(encodedModifiers == null)
			return Boolean.FALSE;
		if( modifiers == null || modifiers.length == 0)
			return Boolean.TRUE;
		return isHaveModifiers(encodedModifiers, List.of(modifiers),modifiers.length);
	}

	@SuppressWarnings("unchecked")
	static <T extends Enum<?>> T getEnumByName(Class<T> klass,String name,Boolean isCaseSensitive) {
		if(klass == null || !klass.isEnum())
			return null;
		for(Object index : klass.getEnumConstants())
			if(isCaseSensitive && ((Enum<?>)index).name().equals(name) || !isCaseSensitive && ((Enum<?>)index).name().equalsIgnoreCase(name))
				return (T) index;
		return null;		
	}
	
	static <T extends Enum<?>> T getEnumByName(Class<T> klass,String name) {
		return getEnumByName(klass, name,Boolean.FALSE);
	}
}
