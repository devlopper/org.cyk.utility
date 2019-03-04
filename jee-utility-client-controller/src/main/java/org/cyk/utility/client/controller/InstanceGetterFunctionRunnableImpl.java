package org.cyk.utility.client.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.instance.InstanceGetter;

public class InstanceGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<InstanceGetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public InstanceGetterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				if(FieldName.IDENTIFIER.equals(getFunction().getFieldName())) {
					Properties properties = new Properties().setValueUsageType(getFunction().getValueUsageType());
					properties.copyFrom(getFunction().getProperties(),Properties.CONTEXT,Properties.REQUEST);
					Object one = __inject__(Controller.class).readOne(getFunction().getClazz(), getFunction().getValue(), properties);
					Collection<Object> collection = new ArrayList<>();
					collection.add(one);
					setOutput(collection);
					
				}
			}
		});
	}
	
}