package org.cyk.utility.common.userinterface.input;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.cyk.utility.common.CardinalPoint;
import org.cyk.utility.common.cdi.AbstractBean;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.InstanceHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.output.OutputText;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true) @NoArgsConstructor
public class Input<T> extends Control implements Serializable {
	private static final long serialVersionUID = 1L;

	private Object object;
	private Field field;
	private T value,initialValue;

	private CardinalPoint labelCardinalPoint;
	
	private CardinalPoint messageCardinalPoint;
	
	public Input<T> setFieldFromName(String name){
		setField(FieldHelper.getInstance().get(object.getClass(), name));
		return this;
	}
	
	public Input<T> setField(Field field){
		this.field = field;
		if(this.field == null){
			value = initialValue = null;
		}else{
			OutputText outputText = new OutputText();
			outputText.getPropertiesMap().setValue(StringHelper.getInstance().getField(field));
			setLabel(outputText);
			read();
		}
		return this;
	}
	
	public Input<T> setField(Object object,String fieldName){
		setObject(object);
		setFieldFromName(fieldName);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public Input<T> setLabelFromIdentifier(String identifier){
		return (Input<T>) super.setLabelFromIdentifier(identifier);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Input<T> setWidth(Number width) {
		return (Input<T>) super.setWidth(width);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Input<T> setLength(Number length) {
		return (Input<T>) super.setLength(length);
	}
	
	@SuppressWarnings("unchecked")
	public Input<T> read(){
		if(object!=null && field!=null)
			value = initialValue = (T) FieldHelper.getInstance().read(object, field);
		return this;
	}
	
	public Input<T> write(){
		if(object!=null && field!=null)
			FieldHelper.getInstance().set(object,getWritableValue(), field);
		return this;
	}
	
	public T getWritableValue(){
		return value;
	}
	
	/**/

	public static interface BuilderBase<OUTPUT extends Input<?>> extends Control.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends Input<?>> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Input<?>> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Input<?>> {

		public static class Adapter extends BuilderBase.Adapter.Default<Input<?>> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<Input<?>>) ClassHelper.getInstance().getByName(Input.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}

	/**/
	
	public static Input<?> get(Form.Detail form,Object object,java.lang.reflect.Field field){
		return ClassHelper.getInstance().instanciateOne(InstanceHelper.getInstance().getIfNotNullElseDefault(Listener.Adapter.Default.DEFAULT_CLASS
				, Listener.Adapter.Default.class)).get(form,object, field);
	}
	
	public static Input<?> get(Form.Detail form,Object object,String fieldName){
		return get(form,object, FieldHelper.getInstance().get(object.getClass(), fieldName));
	}
	
	public static java.util.List<Input<?>> get(Form.Detail form,Object object){
		return ClassHelper.getInstance().instanciateOne(InstanceHelper.getInstance().getIfNotNullElseDefault(Listener.Adapter.Default.DEFAULT_CLASS
				, Listener.Adapter.Default.class)).get(form,object);
	}
	
	/**/
	
	public static final java.util.List<Class<? extends Annotation>> ANNOTATIONS = new ArrayList<Class<? extends Annotation>>();
	static {
		ANNOTATIONS.add(org.cyk.utility.common.annotation.user.interfaces.Input.class);
	}
	
	/**/
	
	public static interface Listener {
		
		Class<? extends Input<?>> getClass(Form.Detail form,Object object,java.lang.reflect.Field field);
		Input<?> get(Form.Detail form,Object object,java.lang.reflect.Field field);
		Boolean isInputable(Form.Detail form,Object object,java.lang.reflect.Field field);
		java.util.List<Input<?>> get(Form.Detail form,Object object);
		java.util.Collection<Field> getFields(Form.Detail form,Class<?> aClass);
		
		public static class Adapter extends AbstractBean implements Listener {
			private static final long serialVersionUID = 1L;

			public static class Default extends Listener.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;
				
				@SuppressWarnings("unchecked")
				public static Class<? extends Listener> DEFAULT_CLASS = (Class<? extends Listener>) ClassHelper.getInstance().getByName(Default.class);
				
				@Override
				public Boolean isInputable(Form.Detail form,Object object, Field field) {
					return field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.Input.class)!=null;
				}
				
				@SuppressWarnings("unchecked")
				@Override
				public Class<? extends Input<?>> getClass(Form.Detail form,Object object, Field field) {
					Class<? extends Input<?>> aClass = null;
					if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.Input.class)==null){
						if(String.class.equals(field.getType())){
							aClass = InputText.class;
						}else if(Date.class.equals(field.getType())){
							aClass = InputTime.class;
						}
					}else{
						if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputText.class)!=null)
							aClass = InputText.class;
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputTextarea.class)!=null)
							aClass = InputTextarea.class;
						else if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputCalendar.class)!=null)
							aClass = InputTime.class;
					}
					if(aClass==null)
						aClass = (Class<? extends Input<?>>) ClassHelper.getInstance().getByName(Input.class);
					return aClass;
				}
				
				@Override
				public Input<?> get(Form.Detail form,Object object, Field field) {
					Input<?> input = null;
					Class<? extends Input<?>> aClass = getClass(form,object, field);
					if(aClass!=null){
						input = ClassHelper.getInstance().instanciateOne(aClass);
					}
					if(input!=null){
						input.setObject(object).setField(field);
						input.getPropertiesMap().setLabel(input.getLabel().getPropertiesMap().getValue());
						input.getPropertiesMap().setRequired(field.getAnnotation(NotNull.class)!=null);
						input.getPropertiesMap().setRequiredMessage(StringHelper.getInstance().get("validation.userinterface.input.value.required"
								, new Object[]{input.getLabel().getPropertiesMap().getValue()}));
					}
					return input;
				}
			
				@Override
				public Collection<Field> getFields(Form.Detail form,Class<?> aClass) {
					return FieldHelper.getInstance().get(aClass, ANNOTATIONS);
				}
				
				@Override
				public java.util.List<Input<?>> get(Form.Detail form,Object object) {
					java.util.List<Input<?>> list = new ArrayList<Input<?>>();
					for(Field field : getFields(form,object.getClass()))
						list.add(get(form,object, field));
					return list;
				}
			
				protected Boolean isField(Class<?> aClass,Object object1,Object object2,Field field,String fieldName){
					return ClassHelper.getInstance().isEqual(aClass,object1.getClass()) && object1 == object2 && field.getName().equals(fieldName);
				}
			}
			
			@Override
			public Class<? extends Input<?>> getClass(Form.Detail form,Object object, Field field) {
				return null;
			}
			
			@Override
			public Input<?> get(Form.Detail form,Object object, Field field) {
				return null;
			}
			
			@Override
			public Boolean isInputable(Form.Detail form,Object object, Field field) {
				return null;
			}
			
			@Override
			public java.util.List<Input<?>> get(Form.Detail form,Object object) {
				return null;
			}
			
			@Override
			public Collection<Field> getFields(Form.Detail form,Class<?> aClass) {
				return null;
			}
		}
		
	}
}