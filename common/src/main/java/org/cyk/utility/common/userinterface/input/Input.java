package org.cyk.utility.common.userinterface.input;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;

import org.cyk.utility.common.CardinalPoint;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Control;
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
	
	public static Input<?> get(Object object,java.lang.reflect.Field field){
		Input<?> input = null;
		if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.Input.class)!=null){
			if(field.getAnnotation(org.cyk.utility.common.annotation.user.interfaces.InputText.class)!=null){
				input = new InputText();
			}
			
			if(input==null){
				input = new Input<>();
			}
			
			input.setObject(object).setField(field);
		}
		return input;
	}
	
	public static Input<?> get(Object object,String fieldName){
		return get(object, FieldHelper.getInstance().get(object.getClass(), fieldName));
	}
	
	public static java.util.List<Input<?>> get(Object object){
		java.util.List<Input<?>> list = new ArrayList<Input<?>>();
		for(Field field : FieldHelper.getInstance().get(object.getClass(), ANNOTATIONS)){
			list.add(get(object, field));
		}
		return list;
	}
	
	/**/
	
	public static final java.util.List<Class<? extends Annotation>> ANNOTATIONS = new ArrayList<Class<? extends Annotation>>();
	static {
		ANNOTATIONS.add(org.cyk.utility.common.annotation.user.interfaces.Input.class);
	}
}