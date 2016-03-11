package org.cyk.utility.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.cyk.utility.common.cdi.BeanAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClassRepository implements Serializable {

	private static final long serialVersionUID = 2331517379189246417L;

	private static final ClassRepository INSTANCE = new ClassRepository();
	private static final Logger LOGGER = LoggerFactory.getLogger(ClassRepository.class);
	public static Boolean ENABLED = Boolean.TRUE;
	
	static{
		Listener.COLLECTION.add(new Listener.Adapter.Default());
	}
	
	private Map<Class<?>, Clazz> map = new HashMap<>();
	private Boolean addOnGet = Boolean.TRUE;
	
	/**/
	
	public Clazz add(Class<?> value){
		Clazz clazz = map.get(value);
		if(clazz==null){
			map.put(value, clazz = new Clazz(value));
			populateFields(value, clazz.getFields());
			//populate(value,null, clazz.get__fields__());
		}
		//populate(value,null, clazz.get__fields__());
		return clazz;
	}
	
	public Clazz get(Class<?> value){
		Clazz clazz = map.get(value);
		if(clazz==null && Boolean.TRUE.equals(addOnGet))
			return add(value);
		return clazz;
	}
	
	public Collection<Field> getFields(Class<?> value){
		Collection<Field> fields = new ArrayList<>();
		if(Boolean.TRUE.equals(ENABLED))
			fields.addAll(get(value).getFields());
		else
			populateFields(value, fields);
		return fields;
	}
	
	public Field getField(Class<?> aClass,String name){
		return get(aClass).getField(name).getField();
	}
	
	/**/
	
	private void populateFields(Class<?> type,Collection<Field> fields) {
		//super class fields first
		if (type.getSuperclass() != null)
			populateFields(type.getSuperclass(),fields);
		//declared class fields second
		for (Field field : type.getDeclaredFields())
			fields.add(field);
	}
	
	private void populateOwnedAndDependentFields(Class<?> aClass,ClassField parentClassField,Collection<ClassField> classFields) {
		Class<?> theClass = aClass==null ? parentClassField.getField().getType() : aClass;
		LOGGER.trace("Looking for fields in class {} from field {}",theClass,parentClassField==null?Constant.EMPTY_STRING:parentClassField);
		Collection<Field> fields = new HashSet<>(getFields(theClass));
		//Collection<Field> fields = new HashSet<>(get(aClass==null ? parentClassField.getField().getType() : aClass).getFields());
		//Collection<Field> fields = parentClassField==null ? parentClassField.get new HashSet<>(getFields(aClass));
		for(Field field : fields){
			ClassField childClassField = new ClassField();
			classFields.add(childClassField);
			childClassField.setParent(parentClassField);
			childClassField.setField(field);
			if(aClass==null)
				childClassField.setName(parentClassField.getName()+Constant.CHARACTER_DOT+field.getName());
			else
				childClassField.setName(field.getName());
			
			Boolean populatable = null;
			for(Listener listener : Listener.COLLECTION){
				Boolean v = listener.canGetOwnedAndDependentFields(parentClassField, field);
				if(v!=null)
					populatable = v;
			}
			
			if(Boolean.TRUE.equals(populatable))
				populateOwnedAndDependentFields(null,childClassField, classFields);
			else
				LOGGER.trace("cannot populate for field {} in class {}",childClassField.field,childClassField.field.getDeclaringClass());
		}
		
		//LOGGER.trace("Looking for fields in class {} from field {} FOUND : {}",theClass,parentClassField==null?Constant.EMPTY_STRING:parentClassField,fields);
	}
		
	/**/
	
	public static ClassRepository getInstance() {
		return INSTANCE;
	}
	
	@Getter @Setter
	public static class Clazz implements Serializable {

		private static final long serialVersionUID = -4384997700934391685L;

		private final Class<?> value;
		private final Collection<Field> fields = new ArrayList<>();
		
		private Collection<ClassField> ownedAndDependentFields;
		
		public Clazz(Class<?> value) {
			super();
			this.value = value;
		}
		
		public Collection<ClassField> getOwnedAndDependentFields(){
			if(ownedAndDependentFields==null){
				ownedAndDependentFields = new HashSet<>();
				ClassRepository.getInstance().populateOwnedAndDependentFields(value, null, ownedAndDependentFields);
			}
			return ownedAndDependentFields;
		}
		
		public ClassField getField(String name){
			for(ClassField field : getOwnedAndDependentFields())
				if(field.getName().equals(name))
					return field;
			return null;
		}
		
	}
	 
	@Getter @Setter @EqualsAndHashCode(of={"field"})
	public static class ClassField implements Serializable {
		
		private static final long serialVersionUID = 6076797357920215507L;
		
		public final String TO_STRING_FORMAT = "%s(%s)";
		
		private Field field;
		
		private ClassField parent;
		private String name;
		
		@Override
		public String toString() {
			return String.format(TO_STRING_FORMAT,name,field.getType().getSimpleName());
		}
	}
	
	/**/
	
	public interface Listener {

		Collection<Listener> COLLECTION = new ArrayList<>();
		
		Boolean canGetOwnedAndDependentFields(ClassField parent,Field field);
		
		public static class Adapter extends BeanAdapter implements Serializable,Listener {
			private static final long serialVersionUID = -4122987497921232243L;
			
			@Override
			public Boolean canGetOwnedAndDependentFields(ClassField parent,Field field) {return null;}
			
			/**/
			
			public static class Default extends Adapter implements Serializable {
				private static final long serialVersionUID = -5446765876489940336L;
				@Override
				public Boolean canGetOwnedAndDependentFields(ClassField parent,Field field) {
					ClassField previous = parent;
					while(previous!=null && previous.getParent()!=null)
						previous = previous.getParent();
					//System.out
					//		.println((previous!=null)+" - "+field.getType().isPrimitive()+" - "+field.getType().getName().startsWith("java.")+" - "
					//				+(parent!=null && parent.getParent()!=null && field.equals(parent.getParent().getField())));
					/*return !(previous!=null
							|| field.getType().isPrimitive()
							|| field.getType().getName().startsWith("java.") 
							|| (parent!=null && parent.getParent()!=null && field.equals(parent.getParent().getField()))
							);*/
					return !(previous!=null
							|| field.getType().isPrimitive()
							|| field.getType().getName().startsWith("java.") 
							//|| (parent!=null && parent.getParent()!=null && field.equals(parent.getParent().getField()))
							|| (parent!=null && parent.getParent()!=null && parent.getParent().getParent()!=null 
								&& field.equals(parent.getParent().getParent().getField()))
							);
				}
			}
			
		}
		
	}
	
}
