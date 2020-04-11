package org.cyk.utility.__kernel__.persistence.query.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.Dependent;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldInstance;
import org.cyk.utility.__kernel__.field.FieldInstancesRuntime;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueUsageType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Dependent @Getter @Setter @Accessors(chain=true) @ToString(callSuper = false)
public class Filter extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private Class<?> klass;
	private Collection<Field> fields;
	private String value;
	
	public Collection<Field> getFields(Boolean injectIfNull) {
		if(fields == null && Boolean.TRUE.equals(injectIfNull))
			fields = new ArrayList<>();
		return fields;
	}
	
	public Filter normalize(Class<?> klass) {
		if(this.klass == null || CollectionHelper.isEmpty(fields))
			this.klass = klass;
		if(CollectionHelper.isNotEmpty(fields))
			for(Field index : fields) {
				if(index.getInstance() == null) {
					if(StringHelper.isNotBlank(index.getName())) {
						java.lang.reflect.Field javaField = FieldHelper.getByName(this.klass, index.getName());
						if(javaField == null)
							continue;
						index.setInstance(__inject__(FieldInstancesRuntime.class).get(this.klass, index.getName()));
					}
				}
			}
		return this;
	}
	
	public Boolean hasFieldWithPath(String... paths) {
		return fields == null ? Boolean.FALSE : getFieldByPath(paths) != null;
	}
	
	public Field getFieldByPath(String... paths) {
		return fields == null ? null : getField(paths);
	}
	
	public Field getField(Collection<String> paths) {
		if(CollectionHelper.isEmpty(fields) || CollectionHelper.isEmpty(paths))
			return null;
		String path = FieldHelper.join(paths);
		if(StringHelper.isBlank(path))
			return null;		
		for(Field index : fields) {
			FieldInstance fieldInstance = index.getInstance();
			if(fieldInstance != null && path.equals(fieldInstance.getPath()))
				return index;
		}		
		return null;
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
	
	public Filter addFields(Collection<Field> fields) {
		if(CollectionHelper.isEmpty(fields))
			return this;
		getFields(Boolean.TRUE).addAll(fields);
		return this;
	}

	public Filter addFields(Field... fields) {
		if(ArrayHelper.isEmpty(fields))
			return this;
		addFields(CollectionHelper.listOf(fields));
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
	
	/**/
	
	public Map<Object,Object> generateMap() {
		if(CollectionHelper.isEmpty(fields))
			return null;
		Map<Object,Object> map = new HashMap<>();
		for(Field field : fields) {
			map.put(field.getName(), field.getValue());
		}
		return map;
	}
	
	/**/
	
}
