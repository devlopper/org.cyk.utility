package org.cyk.utility.__kernel__.persistence.query.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.json.bind.JsonbBuilder;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.computation.ArithmeticOperator;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.field.FieldInstance;
import org.cyk.utility.__kernel__.field.FieldInstancesRuntime;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.mapping.MappingHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.__static__.representation.AbstractRepresentationObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.Value;
import org.cyk.utility.__kernel__.value.ValueUsageType;

import lombok.Getter;
import lombok.NoArgsConstructor;
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
	
	@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public static class Dto extends AbstractRepresentationObject implements Serializable {
		private static final long serialVersionUID = 1L;

		private String klass;
		private ArrayList<Field.Dto> fields;

		private String value;
		
		public Dto useKlass(Class<?> klass) {
			if(klass != null)
				this.klass = klass.getName();
			return this;
		}
		
		public ArrayList<Field.Dto> getFields(Boolean injectIfNull) {
			if(fields == null && Boolean.TRUE.equals(injectIfNull))
				fields = new ArrayList<>();
			return fields;
		}
		
		public Dto addField(String path,String value,org.cyk.utility.__kernel__.field.FieldDto.Type type,Value.Dto.Container valueContainer,Value.Dto.Type valueType,ValueUsageType valueUsageType,ArithmeticOperator arithmeticOperator) {
			Field.Dto fieldDto = new Field.Dto();
			fieldDto.setArithmeticOperator(arithmeticOperator);
			if(StringUtils.isBlank(klass))
				fieldDto.setName(path);
			else
				fieldDto.setField(new org.cyk.utility.__kernel__.field.FieldDto().setKlass(klass).setPath(path).setType(type));
			fieldDto.setValue(new Value.Dto().setContainer(valueContainer).setType(valueType).setUsageType(valueUsageType).setValue(value));
			getFields(Boolean.TRUE).add(fieldDto);
			return this;
		}
		
		public Dto addField(String path,String value,org.cyk.utility.__kernel__.field.FieldDto.Type type,Value.Dto.Container valueContainer,Value.Dto.Type valueType,ValueUsageType valueUsageType) {
			addField(path, value, type, valueContainer, valueType, valueUsageType,null);
			return this;
		}
		
		public Dto addField(String path,String value,org.cyk.utility.__kernel__.field.FieldDto.Type type,Value.Dto.Container valueContainer,Value.Dto.Type valueType) {
			addField(path, value, type, valueContainer, valueType,null);
			return this;
		}
		
		public Dto addField(String path,Object value,ValueUsageType valueUsageType) {
			org.cyk.utility.__kernel__.field.FieldDto.Type type = null;
			Value.Dto.Container valueContainer = Value.Dto.Container.NONE;
			Value.Dto.Type valueType = null;
			String valueAsString = null;
			if(value != null) {
				if(value instanceof Collection) {
					type = org.cyk.utility.__kernel__.field.FieldDto.Type.COLLECTION;
					valueContainer = Value.Dto.Container.COLLECTION;
					valueType = Value.Dto.Type.STRING;
				}else if(value instanceof String) {
					type = org.cyk.utility.__kernel__.field.FieldDto.Type.STRING;
					valueType = Value.Dto.Type.STRING;
					valueAsString = (String) value;
				}else if(value instanceof Integer) {
					type = org.cyk.utility.__kernel__.field.FieldDto.Type.INTEGER;
					valueType = Value.Dto.Type.INTEGER;
				}
				if(StringHelper.isBlank(valueAsString))
					try {
						valueAsString = JsonbBuilder.create().toJson(value);
					} catch (Exception exception) {
						throw new RuntimeException(exception);
					}
			}
			return addField(path, valueAsString,type,valueContainer,valueType, valueUsageType);
		}
		
		public Dto addField(String path,Object value) {
			return addField(path,value,null);
		}
		
		/**/
		
		public static final String FIELD_FIELDS = "fields";
		
		/**/
		
		@org.mapstruct.Mapper
		public static abstract class Mapper extends MapperSourceDestination.AbstractImpl<Dto, Filter> {
			private static final long serialVersionUID = 1L;
			
			public Class<?> getKlass(String name) {
				return StringHelper.isBlank(name) ? null : ClassHelper.getByName(name);
			}
			
			public String getClassName(Class<?> klass) {
				return klass == null ? null : klass.getName();
			}
			
			public Collection<Field> getFields(ArrayList<Field.Dto> dtos) {
				if(CollectionHelper.isEmpty(dtos))
					return null;
				Collection<Field> fields = new ArrayList<>();
				for(Field.Dto index : dtos)
					fields.add(MappingHelper.getDestination(index, Field.class));
				return fields;
			}
			
			public ArrayList<Field.Dto> getFieldDtos(Collection<Field> fields) {
				if(CollectionHelper.isEmpty(fields))
					return null;
				ArrayList<Field.Dto> dtos = new ArrayList<>();		
				for(Field index : fields)
					dtos.add(MappingHelper.getDestination(index, Field.Dto.class));
				return dtos;
			}
			
			@Override
			protected void __listenGetDestinationAfter__(Dto source, Filter destination) {
				
			}
			
		}
	}
}
