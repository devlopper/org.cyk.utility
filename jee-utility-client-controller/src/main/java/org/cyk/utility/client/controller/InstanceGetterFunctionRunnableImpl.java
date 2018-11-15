package org.cyk.utility.client.controller;

import java.io.Serializable;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.field.FieldName;
import org.cyk.utility.instance.InstanceGetter;

public class InstanceGetterFunctionRunnableImpl extends AbstractFunctionRunnableImpl<InstanceGetter> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public InstanceGetterFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				if(FieldName.IDENTIFIER.equals(getFunction().getFieldName())) {
					//USe REST API to get Instances
					/*
					Object one = __inject__(Persistence.class).readOne(getFunction().getClazz(), getFunction().getValue(), new Properties().setValueUsageType(getFunction().getValueUsageType()));
					Collection<Object> collection = new ArrayList<>();
					collection.add(one);
					setOutput(collection);
					*/
				}
			}
		});
	}
	
}