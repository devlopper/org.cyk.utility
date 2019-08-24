package org.cyk.utility.server.persistence.query.filter;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldInstancesRuntime;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.value.ValueUsageType;

@Dependent
public class FilterImpl extends AbstractObject implements Filter,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<?> klass;
	private Fields fields;
	private String value;
	
	@Override
	public Filter normalize(Class<?> klass) {
		if(this.klass == null)
			this.klass = klass;
		Fields fields = getFields();
		if(__inject__(CollectionHelper.class).isNotEmpty(fields))
			for(Field index : fields.get()) {
				if(index.getInstance() == null) {
					if(__inject__(StringHelper.class).isNotBlank(index.getName()))
						index.setInstance(__inject__(FieldInstancesRuntime.class).get(this.klass, index.getName()));
				}
			}
		return this;
	}
	
	@Override
	public Class<?> getKlass() {
		return klass;
	}
	
	@Override
	public Filter setKlass(Class<?> klass) {
		this.klass = klass;
		return this;
	}
	
	@Override
	public Boolean hasFieldWithPath(String... paths) {
		return fields == null ? Boolean.FALSE : fields.hasPath(paths);
	}
	
	@Override
	public Field getFieldByPath(String... paths) {
		return fields == null ? null : fields.getByPath(paths);
	}
	
	@Override
	public Object getFieldValueByPath(String... paths) {
		Field field = getFieldByPath(paths);
		return field == null ? null : field.getValue();
	}
	
	@Override
	public Fields getFields() {
		return fields;
	}

	@Override
	public Fields getFields(Boolean injectIfNull) {
		return (Fields) __getInjectIfNull__(PROPERTY_FIELDS, injectIfNull);
	}

	@Override
	public Filter setFields(Fields fields) {
		this.fields = fields;
		return this;
	}

	@Override
	public Filter addFields(Collection<Field> fields) {
		getFields(Boolean.TRUE).add(fields);
		return this;
	}

	@Override
	public Filter addFields(Field... fields) {
		getFields(Boolean.TRUE).add(fields);
		return this;
	}

	@Override
	public Filter addField(String fieldName,Object fieldValue,ValueUsageType valueUsageType,ArithmeticOperator arithmeticOperator) {
		Field field = __inject__(Field.class);
		Class<?> klass = getKlass();
		if(klass == null)
			field.setName(fieldName);
		else
			field.setInstance(__inject__(FieldInstancesRuntime.class).get(klass, fieldName));
		field.setValue(fieldValue).setValueUsageType(valueUsageType).setArithmeticOperator(arithmeticOperator);
		return addFields(field);
	}
	
	@Override
	public Filter addField(String fieldName,Object fieldValue,ValueUsageType valueUsageType) {
		return addField(fieldName, fieldValue, valueUsageType, null);
	}
	
	@Override
	public Filter addField(String fieldName, Object fieldValue) {
		return addField(fieldName, fieldValue, null);
	}
	
	@Override
	public String getValue() {
		return value;
	}
	
	@Override
	public Filter setValue(String value) {
		this.value = value;
		return this;
	}
}
