package org.cyk.utility.persistence.server.query;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.json.bind.JsonbBuilder;
import javax.xml.bind.annotation.XmlRootElement;

import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.field.FieldInstance;
import org.cyk.utility.__kernel__.field.FieldInstancesRuntime;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.number.NumberHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.Value.Dto.Container;
import org.cyk.utility.__kernel__.value.ValueUsageType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Field extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String name;
	private FieldInstance instance;
	private Object value;
	private ValueUsageType valueUsageType;
	private ArithmeticOperator arithmeticOperator;

	public List<String> getValueLikes(Integer numberOfTokens) {
		if(value instanceof String)
			return QueryArgumentHelper.getLikes((String) value, numberOfTokens);
		return null;
	}
	
	@Override
	public String toString() {
		Collection<String> strings = new ArrayList<>();
		if(StringHelper.isNotBlank(name))
			strings.add("Name="+name);
		if(instance != null)
			strings.add("Field instance="+instance);
		if(value != null)
			strings.add("Value="+value);
		if(arithmeticOperator != null)
			strings.add("AO="+arithmeticOperator);
		if(valueUsageType != null)
			strings.add("VUT="+valueUsageType);
		return StringHelper.concatenate(strings, " ");
	}
		
	/**/
	
	@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public static class Dto extends AbstractObject implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String name;
		private org.cyk.utility.__kernel__.field.Field.Dto field;
		private Value.Dto value;
		private ArithmeticOperator arithmeticOperator;
		
		@Override
		public String toString() {
			Collection<String> strings = new ArrayList<>();
			if(StringHelper.isNotBlank(name))
				strings.add("Name="+name);
			if(field != null)
				strings.add("Field="+field);
			if(value != null)
				strings.add("Value="+value);
			if(arithmeticOperator != null)
				strings.add("AO="+arithmeticOperator);
			return StringHelper.concatenate(strings, " ");
		}
		
		/**/
		
		@org.mapstruct.Mapper
		public static abstract class Mapper extends MapperSourceDestination.AbstractImpl<Dto, Field> {
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
			protected void __listenGetSourceAfter__(Field field, Dto fieldDto) {
				super.__listenGetSourceAfter__(field, fieldDto);
				if(field.getInstance() != null) {
					org.cyk.utility.__kernel__.field.Field.Dto instanceDto = new org.cyk.utility.__kernel__.field.Field.Dto();
					instanceDto.setKlass(field.getInstance().getClazz().getSimpleName());
					instanceDto.setPath(field.getInstance().getPath());
					if(Boolean.TRUE.equals(ClassHelper.isInstanceOf((Class<?>)field.getInstance().getType(), String.class)))
						instanceDto.setType(org.cyk.utility.__kernel__.field.Field.Dto.Type.STRING);
					else if(Boolean.TRUE.equals(ClassHelper.isInstanceOf((Class<?>)field.getInstance().getType(), Collection.class)))
						instanceDto.setType(org.cyk.utility.__kernel__.field.Field.Dto.Type.COLLECTION);
					fieldDto.setField(instanceDto);
				}
				if(fieldDto.getValue() != null) {
					fieldDto.getValue().setUsageType(field.getValueUsageType());
				}
			}
			
			@Override
			protected void __listenGetDestinationAfter__(Dto fieldDto, Field field) {
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
	}
}
