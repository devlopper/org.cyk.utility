package org.cyk.utility.persistence.server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DefaultProjectionsGetterImpl extends DefaultProjectionsGetter.AbstractImpl implements Serializable {

	public static final Map<Class<?>,List<String>> MAP = new HashMap<>();
	
	@Override
	protected List<String> __get__(Class<?> klass) {
		return MAP.get(klass);
	}
}