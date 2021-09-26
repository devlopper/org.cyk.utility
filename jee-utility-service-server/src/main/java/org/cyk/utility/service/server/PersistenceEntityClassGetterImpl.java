package org.cyk.utility.service.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersistenceEntityClassGetterImpl extends PersistenceEntityClassGetter.AbstractImpl implements Serializable {

	public static final Map<Class<?>,Class<?>> MAP = new HashMap<>();
	
	@Override
	public Class<?> __get__(Class<?> klass) {
		if(MAP.containsKey(klass))
			return MAP.get(klass);
		throw new RuntimeException(String.format("Persistence entity class of <<%s>> cannot be got", klass));
	}
	
}