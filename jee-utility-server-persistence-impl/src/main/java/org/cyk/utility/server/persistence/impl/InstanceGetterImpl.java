package org.cyk.utility.server.persistence.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.instance.AbstractInstanceGetterImpl;
import org.cyk.utility.server.persistence.Persistence;

@Dependent @org.cyk.utility.__kernel__.annotation.Persistence
public class InstanceGetterImpl extends AbstractInstanceGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Collection<Object> __execute__() throws Exception {
		Collection<Object> collection = null;
		if(FieldName.IDENTIFIER.equals(getFieldName())) {
			Object one = __inject__(Persistence.class).readByIdentifier(getClazz(), getValue(), getValueUsageType());
			collection = new ArrayList<>();
			collection.add(one);
		}
		return collection;
	}
	
}