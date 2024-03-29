package org.cyk.utility.instance;

import org.cyk.utility.__kernel__.function.AbstractFunctionRunnableImpl;
import org.cyk.utility.__kernel__.properties.Properties;

@Deprecated
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
					instance = org.cyk.utility.__kernel__.klass.ClassHelper.instanciate(aClass,parameters);
					__copy__(fieldsValuesObject, instance);
				}
				setOutput(instance);
			}
		});
	}
	
	protected void __copy__(Object source,Object destination) {
		Properties properties = new Properties();
		properties.copyFrom(getFunction().getProperties(), Properties.CONTEXT,Properties.REQUEST,Properties.FIELDS);
		__inject__(org.cyk.utility.field.FieldHelper.class).copy(source, destination,properties);
	}
	
	
	
}
