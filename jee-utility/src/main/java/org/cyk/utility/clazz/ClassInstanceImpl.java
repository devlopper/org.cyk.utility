package org.cyk.utility.clazz;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.Fields;

@Dependent
public class ClassInstanceImpl extends AbstractObject implements ClassInstance,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<?> clazz;
	private Field systemIdentifierField,businessIdentifierField;
	private Boolean isPersistable;
	private Boolean isTransferable;
	private Fields fields;
	
	@Override
	public Class<?> getClazz() {
		return clazz;
	}
	
	@Override
	public ClassInstance setClazz(Class<?> clazz) {
		this.clazz = clazz;
		return this;
	}
	
	@Override
	public Fields getFields() {
		return fields;
	}
	
	@Override
	public Fields getFields(Boolean injectIfNull) {
		if(fields == null && Boolean.TRUE.equals(injectIfNull)) {
			fields = __inject__(Fields.class);
			fields.add(__getFields__(getClazz()));
		}
		return fields;
	}
	
	@Override
	public ClassInstance setFields(Fields fields) {
		this.fields = fields;
		return this;
	}
	
	@Override
	public Field getSystemIdentifierField() {
		return systemIdentifierField;
	}
	
	@Override
	public ClassInstance setSystemIdentifierField(Field systemIdentifierField) {
		this.systemIdentifierField = systemIdentifierField;
		return this;
	}
	
	@Override
	public Field getBusinessIdentifierField() {
		return businessIdentifierField;
	}
	
	@Override
	public ClassInstance setBusinessIdentifierField(Field businessIdentifierField) {
		this.businessIdentifierField = businessIdentifierField;
		return this;
	}
	
	@Override
	public Boolean getIsPersistable() {
		return isPersistable;
	}
	
	@Override
	public ClassInstance setIsPersistable(Boolean isPersistable) {
		this.isPersistable = isPersistable;
		return this;
	}
	
	@Override
	public Boolean getIsTransferable() {
		return isTransferable;
	}
	
	@Override
	public ClassInstance setIsTransferable(Boolean isTransferable) {
		this.isTransferable = isTransferable;
		return this;
	}
	
	/**/
	
	protected static List<Field> __getFields__(Class<?> klass) {
		List<Field> fields = null;
		if(klass != null) {
			if(fields == null) {
				fields = new ArrayList<>();
				//Process extends
			    Class<?> indexClass = klass;
			    while (indexClass != null && indexClass != Object.class) {
			    	__addFields__(fields, indexClass.getDeclaredFields());
			        indexClass = indexClass.getSuperclass();
			    }
				//Process implements
			    __addFieldsFromInterface__(fields, klass);	
			}
		}
	    return fields;
	}
	
	private static void __addFieldsFromInterface__(List<Field> fields,Class<?> aClass) {
		if(aClass!=null && !aClass.equals(Object.class) && Boolean.TRUE.equals(aClass.isInterface())) {
			__addFields__(fields, aClass.getDeclaredFields());
			Class<?>[] interfaces = aClass.getInterfaces();
		    for(Class<?> index : interfaces)
		    	__addFieldsFromInterface__(fields, index);
		}
	}
	
	private static void __addFields__(List<Field> fields,Field...values) {
		fields.addAll(0, __inject__(CollectionHelper.class).instanciate(values));
	}
	
}
