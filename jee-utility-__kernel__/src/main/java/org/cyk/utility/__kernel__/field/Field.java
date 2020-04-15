package org.cyk.utility.__kernel__.field;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.mapping.MapperSourceDestination;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Field extends AbstractObject implements Serializable {

	private Class<?> klass;
	private String path;
	private java.lang.reflect.Field javaField;
	private Type type;

	/**/
	
	public static Field get(Class<?> klass,List<String> paths) {
		if(klass == null || CollectionHelper.isEmpty(paths))
			return null;
		String path = org.cyk.utility.__kernel__.field.FieldHelper.join(paths);
		if(StringHelper.isBlank(path))
			return null;
		Map<String,Field> map = MAP.get(klass);
		if(map != null && map.containsKey(path))
			return map.get(path);
		if(map == null)
			MAP.put(klass, map = new HashMap<>());
		Field field = new Field();
		field.setKlass(klass);
		field.setPath(path);
		if(paths.size() > 1) {
			for(Integer index = 0 ; index < paths.size() - 1; index = index + 1)
				klass = FieldUtils.getDeclaredField(klass, paths.get(index), Boolean.TRUE).getType();
		}	
		field.setJavaField(FieldHelper.getByName(klass, paths.get(paths.size()-1)));
		field.setType(FieldHelper.getType(field.getJavaField(),klass));
		//instance.setIsGeneratable(instance.getJavaField().isAnnotationPresent(GeneratedValue.class) || instance.getJavaField().isAnnotationPresent(Generatable.class));
		map.put(path, field);
		return field;
	}
	
	public static Field get(Class<?> aClass, String... paths) {
		return get(aClass,List.of(paths));
	}
	
	/**/
	
	private static final Map<Class<?>,Map<String,Field>> MAP = new HashMap<>();
	
	@XmlRootElement @Getter @Setter @Accessors(chain=true) @NoArgsConstructor
	public static class Dto extends AbstractObject implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String klass;
		private String path;
		private Type type;

		@Override
		public String toString() {
			Collection<String> strings = new ArrayList<>();
			if(StringHelper.isNotBlank(klass))
				strings.add("Class="+klass);
			if(StringHelper.isNotBlank(path))
				strings.add("Path="+path);
			if(type != null)
				strings.add("TYP="+type);
			return StringHelper.concatenate(strings, " ");
		}
		
		/**/
		
		public static enum Type {
			STRING
			,INTEGER
			,COLLECTION
		}
		
		//@org.mapstruct.Mapper
		public static abstract class Mapper extends MapperSourceDestination.AbstractImpl<Dto, Field> {
			
			public String getSourceClass(Class<?> klass) {
				if(klass == null)
					return null;
				return klass.toString();
			}
			
			public Class<?> getDestinationClass(String name) {
				if(StringHelper.isBlank(name))
					return null;
				return ClassHelper.getByName(name);
			}
			/*
			public Type getSourceType(java.lang.reflect.Type type) {
				if(type == null)
					return null;
				if(ClassHelper.isInstanceOf((Class<?>)type, String.class))
					return Type.STRING;
				else if(ClassHelper.isInstanceOf((Class<?>)type, Collection.class))
					return Type.COLLECTION;	
			}
			
			public java.lang.reflect.Type getDestinationType(Type type) {
				if(type == null)
					return null;
					
			}
			*/
			@Override
			protected void __listenGetSourceAfter__(Field field, Dto fieldDto) {
				super.__listenGetSourceAfter__(field, fieldDto);				
				fieldDto.setPath(field.getPath());
				if(Boolean.TRUE.equals(ClassHelper.isInstanceOf((Class<?>)field.getType(), String.class)))
					fieldDto.setType(Type.STRING);
				else if(Boolean.TRUE.equals(ClassHelper.isInstanceOf((Class<?>)field.getType(), Collection.class)))
					fieldDto.setType(Type.COLLECTION);			
			}
			
			@Override
			protected void __listenGetDestinationAfter__(Dto fieldDto, Field field) {
				super.__listenGetDestinationAfter__(fieldDto, field);			
				if(field.klass != null && StringHelper.isNotBlank(fieldDto.getPath())) {
					Field __field__ = Field.get(field.klass, fieldDto.getPath());
					if(__field__ != null)
						field.setJavaField(__field__.getJavaField());
				}				
			}		
		}
	}
}