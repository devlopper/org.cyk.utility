package org.cyk.utility.common.userinterface.input;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.common.CardinalPoint;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.FieldHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.output.OutputText;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Input<T> extends Control implements Serializable {
	private static final long serialVersionUID = 1L;

	private OutputText label;
	private CardinalPoint labelCardinalPoint;
	
	private T value,initialValue;
	private Object object;
	private Field field;
	private CardinalPoint messageLocation;
	
	public Input<T> setLabelString(String labelIdentifier){
		label = new OutputText();
		label.getPropertiesMap().setValue(StringHelper.getInstance().get(labelIdentifier, new Object[]{}));
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public Input<T> read(){
		value = (T) FieldHelper.getInstance().read(object, field);
		return this;
	}
	
	public Input<T> write(){
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
}