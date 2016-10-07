package org.cyk.utility.common;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

import org.cyk.utility.common.annotation.user.interfaces.Sequence;
import org.cyk.utility.common.annotation.user.interfaces.Sequence.Direction;
import org.cyk.utility.common.annotation.user.interfaces.SequenceOverride;

public abstract class AbstractFieldSorter<OBJECT> {
		
	private List<OBJECT> objects;
	private Class<?> clazz;
	private List<String> expectedFieldNames;
	
	public AbstractFieldSorter(List<OBJECT> objects,Class<?> clazz) {
		super();
		this.objects = objects;
		this.clazz = clazz;
	}
	
	public List<String> getExpectedFieldNames(){
		if(expectedFieldNames == null)
			expectedFieldNames = new ArrayList<>();
		return expectedFieldNames;
	}
	
	public void setExpectedFieldNames(String...values){
		getExpectedFieldNames().clear();
		getExpectedFieldNames().addAll(Arrays.asList(values));
	}
	
	public void sort(){
		Collection<OBJECT> reorders = new ArrayList<>();
		if(expectedFieldNames == null){
			for(int i=0;i<objects.size();){
				if(sequence(getField(objects.get(i)))==null)
					i++;
				else
					reorders.add(objects.remove(i));
			}
			
			for(OBJECT object : reorders){	
				Sequence sequence = sequence(getField(object));
				for(int position=0;position<objects.size();){
					if(sequence.field().equals(getField(objects.get(position)).getName())){
						if(Direction.BEFORE.equals(sequence.direction()))
							objects.add(position, object);
						else{
							objects.add(position+1, object);
						}
						break;
					}else
						position++;
				}
			}
		}else{
			for(String value : expectedFieldNames){
				Integer index = getIndexByFieldName(value);
				if(index==null)
					continue;
				OBJECT object = objects.remove(index.intValue());
				reorders.add(object);
			}
			objects.clear();
			objects.addAll(reorders);
		}
	}
	
	private Sequence sequence(Field field){
		Sequence sequence = CommonUtils.getInstance().getFieldAnnotation(field,clazz, Sequence.class, Boolean.TRUE);
		if(sequence==null){
			if(clazz!=null)
				for(Annotation annotation : clazz.getAnnotations()){
					if(annotation instanceof SequenceOverride && ((SequenceOverride)annotation).field().equals(field.getName()) ){
						return ((SequenceOverride)annotation).value();
					}
				}
		}
		return sequence;
	}
	
	protected abstract Field getField(OBJECT object);
	
	public Integer getIndexByFieldName(String fieldName){
		Integer i = 0;
		for(OBJECT object : objects){
			Field field = getField(object);
			if(field.getName().equals(fieldName))
				return i;
			else
				i++;
		}
		return null;
	}
	
	/**/
	
	@AllArgsConstructor @Getter
	public static class ObjectField implements Serializable {
		
		private static final long serialVersionUID = -4424267370983658359L;
		private Object object;
		private Field field;
		
	}
	
	public static class FieldSorter extends AbstractFieldSorter<Field> {

		public FieldSorter(List<Field> objects,Class<?> clazz) {
			super(objects,clazz);
		}

		@Override
		protected Field getField(Field object) {
			return object;
		}
		
	}
	
	public static class ObjectFieldSorter extends AbstractFieldSorter<ObjectField> {

		public ObjectFieldSorter(List<ObjectField> objects,Class<?> clazz) {
			super(objects,clazz);
		}
		
		@Override
		protected Field getField(ObjectField objectField) {
			return objectField.field;
		}
		
	}
		
}