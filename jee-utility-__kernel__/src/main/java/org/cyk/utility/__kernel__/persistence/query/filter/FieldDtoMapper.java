package org.cyk.utility.__kernel__.persistence.query.filter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import javax.json.bind.JsonbBuilder;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldInstance;
import org.cyk.utility.__kernel__.field.FieldInstancesRuntime;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.Value.Dto.Container;

@Deprecated
public abstract class FieldDtoMapper extends MapperSourceDestination.AbstractImpl<FieldDto, Field> {
	private static final long serialVersionUID = 1L;
	
	public Value.Dto getValueDto(Object value) {
		Value.Dto valueDto = null;
		if(value != null) {
			valueDto = new Value.Dto();
			String valueAsJson = null;
			if(value instanceof String) {
				valueAsJson = (String) value;
				valueDto.setContainer(Value.Dto.Container.NONE);
				valueDto.setType(Value.Dto.Type.STRING);
			}else if(value instanceof Collection) {
				valueAsJson = JsonbBuilder.create().toJson(value);
				valueDto.setContainer(Value.Dto.Container.COLLECTION);
				Object element = CollectionHelper.getFirst((Collection<?>) value);
				if(element != null) {
					if(element instanceof String)
						valueDto.setType(Value.Dto.Type.STRING);
					else if(element instanceof Integer)
						valueDto.setType(Value.Dto.Type.INTEGER);
				}
			}
			valueDto.setValue(valueAsJson);
		}
		return valueDto;
	}
	
	public Object getValue(Value.Dto valueDto) {
		Object value = null;
		if(valueDto != null) {
			Container container = valueDto.getContainer();
			Value.Dto.Type type = valueDto.getType();
			if(type == null)
				type = Value.Dto.Type.STRING;
			if(container == null)
				container = Container.NONE;
			if(Container.NONE.equals(container)) {
				if(Value.Dto.Type.STRING.equals(type))
					value = valueDto.getValue();
				else if(Value.Dto.Type.INTEGER.equals(type))
					value = NumberHelper.getInteger(valueDto.getValue(), null);
				else if(Value.Dto.Type.LONG.equals(type))
					value = NumberHelper.getLong(valueDto.getValue(), null);
			}else if(Container.COLLECTION.equals(container)) {
				Collection<?> collection = null;
				if(valueDto.getValue()!= null && !valueDto.getValue().isBlank()) {
					Type __type__ = null;
					if(Value.Dto.Type.INTEGER.equals(valueDto.getType()))
						__type__ = new ArrayList<Integer>(){private static final long serialVersionUID = 1L;}.getClass().getGenericSuperclass();
					else if(Value.Dto.Type.LONG.equals(valueDto.getType()))
						__type__ = new ArrayList<Long>(){private static final long serialVersionUID = 1L;}.getClass().getGenericSuperclass();
					else if(Value.Dto.Type.STRING.equals(valueDto.getType()))
						__type__ = new ArrayList<String>(){private static final long serialVersionUID = 1L;}.getClass().getGenericSuperclass();
					else
						__type__ = new ArrayList<String>(){private static final long serialVersionUID = 1L;}.getClass().getGenericSuperclass();	
					collection = JsonbBuilder.create().fromJson(valueDto.getValue(), __type__);
				}
				value = collection;
			}else if(Container.MAP.equals(container)) {
				
			}
		}
		return value;
	}
	
	@Override
	protected void __listenGetSourceAfter__(Field field, FieldDto fieldDto) {
		super.__listenGetSourceAfter__(field, fieldDto);
		if(field.getInstance() != null) {
			org.cyk.utility.__kernel__.field.FieldDto instanceDto = new org.cyk.utility.__kernel__.field.FieldDto();
			instanceDto.setKlass(field.getInstance().getClazz().getSimpleName());
			instanceDto.setPath(field.getInstance().getPath());
			if(Boolean.TRUE.equals(ClassHelper.isInstanceOf((Class<?>)field.getInstance().getType(), String.class)))
				instanceDto.setType(org.cyk.utility.__kernel__.field.FieldDto.Type.STRING);
			else if(Boolean.TRUE.equals(ClassHelper.isInstanceOf((Class<?>)field.getInstance().getType(), Collection.class)))
				instanceDto.setType(org.cyk.utility.__kernel__.field.FieldDto.Type.COLLECTION);
			fieldDto.setField(instanceDto);
		}
		if(fieldDto.getValue() != null) {
			fieldDto.getValue().setUsageType(field.getValueUsageType());
		}
	}
	
	@Override
	protected void __listenGetDestinationAfter__(FieldDto fieldDto, Field field) {
		super.__listenGetDestinationAfter__(fieldDto, field);
		if(fieldDto.getField() != null) {
			Class<?> klass = null;
			if(StringHelper.isNotBlank(fieldDto.getField().getKlass()))
				klass = ClassHelper.getByName(fieldDto.getField().getKlass());
			if(klass != null && StringHelper.isNotBlank(fieldDto.getField().getPath())) {
				FieldInstance instance = DependencyInjection.inject(FieldInstancesRuntime.class).get(klass, fieldDto.getField().getPath());
				field.setInstance(instance);
			}
		}
		if(field.getValue() != null) {
			field.setValueUsageType(fieldDto.getValue().getUsageType());
		}
	}
}