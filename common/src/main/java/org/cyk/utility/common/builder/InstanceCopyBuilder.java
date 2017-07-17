package org.cyk.utility.common.builder;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import org.cyk.utility.common.AbstractBuilder;
import org.cyk.utility.common.ListenerUtils;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;

@Getter @Setter @NoArgsConstructor @Accessors(chain=true) @Deprecated
public class InstanceCopyBuilder<T> extends AbstractBuilder<T> implements Serializable {
	private static final long serialVersionUID = -872728112292086623L;
	
	private T source;
	private Collection<String> ignoredFieldNames = new ArrayList<>();
	private Collection<Class<?>> ignoredFieldAnnotationClasses = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public InstanceCopyBuilder(T source){
		super((Class<T>) source.getClass());
		this.source = source;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T build() {
		instance = InstanceHelper.getInstance().instanciateOne(aClass);
		@SuppressWarnings("rawtypes")
		Collection listeners = Listener.COLLECTION;
		for(Field field : FieldHelper.getInstance().get(instance.getClass())){
			final Field finalField = field;
			if(Boolean.TRUE.equals(listenerUtils.getBoolean(listeners, new ListenerUtils.BooleanMethod<Listener<T>>() {
				@Override
				public Boolean execute(Listener<T> listener) {
					return listener.isField(finalField);
				}
				
				@Override
				public Boolean getNullValue() {
					Boolean ignored = ignoredFieldNames!=null && ignoredFieldNames.contains(finalField.getName());
					if(Boolean.FALSE.equals(ignored)){
						if(ignoredFieldAnnotationClasses!=null)
							for(@SuppressWarnings("rawtypes") Class aClass : ignoredFieldAnnotationClasses){
								if(finalField.getAnnotation(aClass)!=null){
									ignored = Boolean.TRUE;
									break;
								}
							}
					}
					return Boolean.FALSE.equals(ignored) ;
				}
			}) )){
				FieldHelper.getInstance().set(instance, FieldHelper.getInstance().read(source, field), field);
			}
		}
		return instance;
	}
	
	public InstanceCopyBuilder<T> addIgnoredFieldNames(String...names){
		return addIgnoredFieldNames(Arrays.asList(names));
	}
	
	public InstanceCopyBuilder<T> addIgnoredFieldNames(Collection<String> names){
		if(names!=null)
			ignoredFieldNames.addAll(names);
		return this;
	}
	
	public InstanceCopyBuilder<T> addIgnoredFieldAnnotationClasses(Class<?>...classes){
		return addIgnoredFieldAnnotationClasses(Arrays.asList(classes));
	}
	
	public InstanceCopyBuilder<T> addIgnoredFieldAnnotationClasses(Collection<Class<?>> classes){
		if(classes!=null)
			ignoredFieldAnnotationClasses.addAll(classes);
		return this;
	}
	
	/**/
	
	
	/**/
	
	public static interface Listener<T> extends AbstractBuilder.Listener<T> {
		
		Collection<Listener<?>> COLLECTION = new ArrayList<>();
		
		Boolean isField(Field field);
		
		public static class Adapter<T> extends AbstractBuilder.Listener.Adapter.Default<T> implements Listener<T>,Serializable {
			private static final long serialVersionUID = 1L;
				
			/**/
			
			@Override
			public Boolean isField(Field field) {
				return null;
			}
			
			public static class Default<T> extends Listener.Adapter<T> implements Serializable {
				private static final long serialVersionUID = 1L;
				
			}

		}
		
	}
	
	/**/
	
}