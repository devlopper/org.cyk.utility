package org.cyk.utility.__kernel__.configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.string.StringHelper;

@ApplicationScoped
public class ClassIdentifierGetterImpl extends ClassIdentifierGetter.AbstractImpl implements Serializable {

	public static final Map<Class<?>,String> MAP = new HashMap<>();
	
	@Override
	public String get(Class<?> klass) {
		if(klass == null)
			return null;
		if(MAP.containsKey(klass))
			return MAP.get(klass);
		String identifier = StringHelper.getVariableNameFrom(klass.getSimpleName());
		MAP.put(klass, identifier);
		return identifier;
	}

}