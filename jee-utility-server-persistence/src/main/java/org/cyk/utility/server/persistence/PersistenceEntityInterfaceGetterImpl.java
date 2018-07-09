package org.cyk.utility.server.persistence;

import java.io.Serializable;

import org.codehaus.plexus.util.StringUtils;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

public class PersistenceEntityInterfaceGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Class<?>> implements PersistenceEntityInterfaceGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<?> __execute__() {
		String name = StringUtils.replace(getEntityClass().getName(), ".entities.", ".api.");
		name = name+"Persistence";
		return __inject__(ClassHelper.class).getByName(name);
	}

	@Override
	public PersistenceEntityInterfaceGetter setEntityClass(Class<?> aClass) {
		getProperties().setEntityClass(aClass);
		return this;
	}

	@Override
	public Class<?> getEntityClass() {
		return (Class<?>) getProperties().getEntityClass();
	}

	
}
