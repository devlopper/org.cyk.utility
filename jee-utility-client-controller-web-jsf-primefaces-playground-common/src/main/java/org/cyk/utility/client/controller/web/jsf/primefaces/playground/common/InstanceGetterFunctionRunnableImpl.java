package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

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
				//InstanceGetter function = getFunction();
				if(FieldName.IDENTIFIER.equals(getFunction().getFieldName())) {
					/*Object one = null;
					if(function.getClazz().equals(MyEntity.class))
						one = null;//MyEntityImpl.getByIdentifier(function.getValue().toString());
					else if(function.getClazz().equals(VerySimpleEntity.class))
						one = null;//VerySimpleEntityImpl.getByIdentifier(function.getValue().toString());
					Collection<Object> collection = new ArrayList<>();
					//if(one!=null)
					//	collection.add(one);
					setOutput(collection);*/
				}
			}
		});
	}
	
}