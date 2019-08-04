package org.cyk.utility.server.persistence.query.filter;

import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.annotation.JavaScriptObjectNotation;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.collection.CollectionHelper;
import org.cyk.utility.field.FieldInstance;
import org.cyk.utility.field.FieldInstancesRuntime;
import org.cyk.utility.mapping.AbstractMapperSourceDestinationImpl;
import org.cyk.utility.mapping.Instantiator;
import org.cyk.utility.number.NumberHelper;
import org.cyk.utility.object.ObjectFromStringBuilder;
import org.cyk.utility.object.ObjectToStringBuilder;
import org.cyk.utility.string.StringHelper;
import org.cyk.utility.value.ValueDto;
import org.cyk.utility.value.ValueDto.Container;
import org.mapstruct.Mapper;

@Mapper(uses= {Instantiator.class})
public abstract class FieldDtoMapper extends AbstractMapperSourceDestinationImpl<FieldDto, Field> {
	private static final long serialVersionUID = 1L;
	
	public ValueDto getValueDto(Object value) {
		ValueDto valueDto = null;
		if(value != null) {
			valueDto = new ValueDto();
			String valueAsJson = null;
			if(value instanceof String) {
				valueAsJson = (String) value;
				valueDto.setContainer(ValueDto.Container.NONE);
				valueDto.setType(ValueDto.Type.STRING);
			}else if(value instanceof Collection) {
				ObjectToStringBuilder objectToStringBuilder = DependencyInjection.injectByQualifiersClasses(ObjectToStringBuilder.class,JavaScriptObjectNotation.Class.class);
				objectToStringBuilder.setObject(value);
				valueAsJson = objectToStringBuilder.execute().getOutput();	
				valueDto.setContainer(ValueDto.Container.COLLECTION);
				Object element = DependencyInjection.inject(CollectionHelper.class).getFirst((Collection<?>) value);
				if(element != null) {
					if(element instanceof String)
						valueDto.setType(ValueDto.Type.STRING);
					else if(element instanceof Integer)
						valueDto.setType(ValueDto.Type.INTEGER);
				}
			}
			valueDto.setValue(valueAsJson);
		}
		return valueDto;
	}
	
	public Object getValue(ValueDto valueDto) {
		Object value = null;
		if(valueDto != null) {
			Container container = valueDto.getContainer();
			ValueDto.Type type = valueDto.getType();
			if(type == null)
				type = ValueDto.Type.STRING;
			if(container == null)
				container = Container.NONE;
			if(Container.NONE.equals(container)) {
				if(ValueDto.Type.STRING.equals(type))
					value = valueDto.getValue();
				else if(ValueDto.Type.INTEGER.equals(type))
					value = DependencyInjection.inject(NumberHelper.class).getInteger(valueDto.getValue(), null);
				else if(ValueDto.Type.LONG.equals(type))
					value = DependencyInjection.inject(NumberHelper.class).getLong(valueDto.getValue(), null);
			}else if(Container.COLLECTION.equals(container)) {
				Collection<?> collection = null;
				if(DependencyInjection.inject(StringHelper.class).isNotBlank(valueDto.getValue())) {
					ObjectFromStringBuilder objectFromStringBuilder = DependencyInjection.injectByQualifiersClasses(ObjectFromStringBuilder.class,JavaScriptObjectNotation.Class.class);
					objectFromStringBuilder.setString(valueDto.getValue());
					objectFromStringBuilder.setKlass(List.class);
					collection = (Collection<?>) objectFromStringBuilder.execute().getOutput();		
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
			org.cyk.utility.field.FieldDto instanceDto = new org.cyk.utility.field.FieldDto();
			instanceDto.setKlass(field.getInstance().getClazz().getSimpleName());
			instanceDto.setPath(field.getInstance().getPath());
			if(Boolean.TRUE.equals(DependencyInjection.inject(ClassHelper.class).isInstanceOf(field.getInstance().getType(), String.class)))
				instanceDto.setType(org.cyk.utility.field.FieldDto.Type.STRING);
			else if(Boolean.TRUE.equals(DependencyInjection.inject(ClassHelper.class).isInstanceOf(field.getInstance().getType(), Collection.class)))
				instanceDto.setType(org.cyk.utility.field.FieldDto.Type.COLLECTION);
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
			if(DependencyInjection.inject(StringHelper.class).isNotBlank(fieldDto.getField().getKlass()))
				klass = DependencyInjection.inject(ClassHelper.class).getByName(fieldDto.getField().getKlass());
			if(klass != null && DependencyInjection.inject(StringHelper.class).isNotBlank(fieldDto.getField().getPath())) {
				FieldInstance instance = DependencyInjection.inject(FieldInstancesRuntime.class).get(klass, fieldDto.getField().getPath());
				field.setInstance(instance);
			}
		}
		if(field.getValue() != null) {
			field.setValueUsageType(fieldDto.getValue().getUsageType());
		}
	}
}