package org.cyk.utility.__kernel__.persistence.query.filter;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.Dependent;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.field.FieldInstancesRuntime;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueUsageType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Dependent @Getter @Setter @Accessors(chain=true)
public class Filter extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private Class<?> klass;
	private Fields fields;
	private String value;
	
	public Filter normalize(Class<?> klass) {
		if(this.klass == null)
			this.klass = klass;
		Fields fields = getFields();
		if(CollectionHelper.isNotEmpty(fields))
			for(Field index : fields.get()) {
				if(index.getInstance() == null) {
					if(StringHelper.isNotBlank(index.getName()))
						index.setInstance(__inject__(FieldInstancesRuntime.class).get(this.klass, index.getName()));
				}
			}
		return this;
	}
	
	public Boolean hasFieldWithPath(String... paths) {
		return fields == null ? Boolean.FALSE : fields.hasPath(paths);
	}
	
	public Field getFieldByPath(String... paths) {
		return fields == null ? null : fields.get(paths);
	}
	
	public Field getField(Collection<String> paths) {
		if(CollectionHelper.isEmpty(fields) || CollectionHelper.isEmpty(paths))
			return null;
		return fields.get(paths);
	}
	
	public Field getField(String... paths) {
		if(ArrayHelper.isEmpty(paths))
			return null;
		return getField(CollectionHelper.listOf(paths));
	}
	
	public Object getFieldValueByPath(String... paths) {
		Field field = getFieldByPath(paths);
		return field == null ? null : field.getValue();
	}
	
	public Fields getFields(Boolean injectIfNull) {
		if(fields == null && Boolean.TRUE.equals(injectIfNull))
			fields = new Fields();
		return fields;
	}

	public Filter addFields(Collection<Field> fields) {
		getFields(Boolean.TRUE).add(fields);
		return this;
	}

	public Filter addFields(Field... fields) {
		getFields(Boolean.TRUE).add(fields);
		return this;
	}

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
	
	public Filter addField(String fieldName,Object fieldValue,ValueUsageType valueUsageType) {
		return addField(fieldName, fieldValue, valueUsageType, null);
	}
	
	public Filter addField(String fieldName, Object fieldValue) {
		return addField(fieldName, fieldValue, null);
	}

	/**/
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
