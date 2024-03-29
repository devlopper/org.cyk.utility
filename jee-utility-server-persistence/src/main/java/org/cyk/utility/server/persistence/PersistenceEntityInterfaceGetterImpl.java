package org.cyk.utility.server.persistence;

import java.io.Serializable;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputImpl;

@Dependent @SuppressWarnings("rawtypes")
public class PersistenceEntityInterfaceGetterImpl extends AbstractFunctionWithPropertiesAsInputImpl<Class> implements PersistenceEntityInterfaceGetter,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<?> __execute__() {
		String name = StringUtils.replace(getEntityClass().getName(), ".entities.", ".api.");
		name = name+"Persistence";
		return org.cyk.utility.__kernel__.klass.ClassHelper.getByName(name);
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
