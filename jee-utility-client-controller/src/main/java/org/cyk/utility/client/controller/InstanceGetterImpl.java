package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.instance.AbstractInstanceGetterImpl;

@org.cyk.utility.__kernel__.annotation.Controller
public class InstanceGetterImpl extends AbstractInstanceGetterImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected Collection<Object> __execute__() throws Exception {
		Collection<Object> collection = null;
		if(FieldName.IDENTIFIER.equals(getFieldName())) {
			Properties properties = new Properties().setValueUsageType(getValueUsageType());
			properties.copyFrom(getProperties(),Properties.CONTEXT,Properties.REQUEST);
			Object one = __inject__(Controller.class).readOne(getClazz(), getValue(), properties);
			collection = new ArrayList<>();
			collection.add(one);
		}		
		return collection;
	}
	
}