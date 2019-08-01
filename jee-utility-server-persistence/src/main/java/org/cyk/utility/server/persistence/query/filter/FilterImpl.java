package org.cyk.utility.server.persistence.query.filter;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.field.FieldInstancesRuntime;

@Dependent
public class FilterImpl extends AbstractObject implements Filter,Serializable {
	private static final long serialVersionUID = 1L;

	private Class<?> klass;
	private Fields fields;
	
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
	public Filter addField(String fieldName,Object fieldValue,ArithmeticOperator arithmeticOperator) {
		Class<?> klass = getKlass();
		Field field = __inject__(Field.class).setInstance(__inject__(FieldInstancesRuntime.class).get(klass, fieldName)).setValue(fieldValue).setArithmeticOperator(arithmeticOperator);
		return addFields(field);
	}
	
	@Override
	public Filter addField(String fieldName, Object fieldValue) {
		return addField(fieldName, fieldValue, null);
	}
}
