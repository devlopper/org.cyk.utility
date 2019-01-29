package org.cyk.utility.instance;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.field.FieldHelper;

@SuppressWarnings("rawtypes")
public abstract class AbstractInstanceBuilderFunctionRunnableImpl extends AbstractFunctionRunnableImpl<InstanceBuilder> {
	private static final long serialVersionUID = 1L;

	public AbstractInstanceBuilderFunctionRunnableImpl() {
		setRunnable(new Runnable() {
			@Override
			public void run() {
				Object instance = null;
				Object fieldsValuesObject = getFunction().getFieldsValuesObject();
				if(fieldsValuesObject != null) {
					Class<?> aClass = getFunction().getClazz();
					Object[] parameters = getFunction().getConstructorParameters();
					instance = __inject__(ClassHelper.class).instanciate(aClass,parameters);
					__copy__(fieldsValuesObject, instance);
				}
				setOutput(instance);
			}
		});
	}
	
	protected void __copy__(Object source,Object destination) {
		__inject__(FieldHelper.class).copy(source, destination);
	}
	
	
	
}
