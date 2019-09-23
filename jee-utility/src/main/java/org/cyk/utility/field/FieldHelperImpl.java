package org.cyk.utility.field;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.array.ArrayHelperImpl;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.helper.AbstractHelper;

@ApplicationScoped
public class FieldHelperImpl extends AbstractHelper implements FieldHelper,Serializable {
	private static final long serialVersionUID = -5367150176793830358L;

	@Override
	public FieldHelper copy(Object source, Object destination,Properties properties) {
		FieldValueCopy fieldValueCopy = __inject__(FieldValueCopy.class);
		fieldValueCopy.copyProperty(Properties.CONTEXT, properties);
		fieldValueCopy.copyProperty(Properties.REQUEST, properties);
		Boolean isAutomaticallyDetectFields = null;
		Object fields = properties.getFields();
		if(fields instanceof Collection && CollectionHelper.isNotEmpty((Collection<?>) fields)) {
			for(String index : ((Collection<String>)fields))
				fieldValueCopy.setFieldName(index);
			isAutomaticallyDetectFields = Boolean.FALSE;
		}else {
			isAutomaticallyDetectFields = Boolean.TRUE;
		}
		fieldValueCopy.setSource(source).setDestination(destination).setIsAutomaticallyDetectFields(isAutomaticallyDetectFields).execute();
		return this;
	}
	
	@Override
	public FieldHelper copy(Object source, Object destination) {
		return copy(source, destination, null);
	}
	
	/* get field type*/
	
	@Deprecated
	public static FieldType __getType__(Class<?> klass,Field field) {
		FieldType fieldType = null;
		if(field!=null){
			if(klass == null)
				klass = field.getDeclaringClass();
			fieldType = __inject__(FieldType.class).setField(field);
			if(field.getType().equals(field.getGenericType())) {
				fieldType.setType(field.getType());
			}else {
				if(field.getGenericType() instanceof ParameterizedType) {
					ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
					if(ArrayHelperImpl.__isEmpty__(parameterizedType.getActualTypeArguments())) {
							
					}else {
						fieldType.setType(field.getType());
						for(Integer index = 0 ; index < parameterizedType.getActualTypeArguments().length ; index = index + 1) {
							Type type = parameterizedType.getActualTypeArguments()[index];
							Class<?> argumentClass = null;
							if(type instanceof TypeVariable) {
								argumentClass = org.cyk.utility.__kernel__.klass.ClassHelper.getParameterAt(klass, index);
							}else
								argumentClass = (Class<?>) type;
							fieldType.getParameterizedClasses(Boolean.TRUE).set(index,argumentClass);
						}
					}
				}else {
					fieldType.setType(org.cyk.utility.__kernel__.klass.ClassHelper.getParameterAt(klass, 0));
				}
			}
			if(fieldType.getType() == null)
				fieldType.setType(Object.class);
		}
		return fieldType;
	}
	
}
