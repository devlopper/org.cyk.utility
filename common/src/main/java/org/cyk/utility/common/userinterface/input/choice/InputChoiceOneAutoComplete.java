package org.cyk.utility.common.userinterface.input.choice;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.Control;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputChoiceOneAutoComplete extends InputChoiceOne implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private InputAutoCompleteCommon common = new InputAutoCompleteCommon();
	
	/**/
	
	@Override
	public InputChoiceOneAutoComplete setField(Object object, String fieldName) {
		return (InputChoiceOneAutoComplete) super.setField(object, fieldName);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public InputChoiceOneAutoComplete setField(Field field) {
		super.setField(field);
		common.setClazz((Class<Object>) field.getType());
		return this;
	}
	
	public static interface BuilderBase<OUTPUT extends InputChoiceOneAutoComplete> extends InputChoiceOne.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends InputChoiceOneAutoComplete> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends InputChoiceOneAutoComplete> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<InputChoiceOneAutoComplete> {

		public static class Adapter extends BuilderBase.Adapter.Default<InputChoiceOneAutoComplete> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<InputChoiceOneAutoComplete>) ClassHelper.getInstance().getByName(InputChoiceOneAutoComplete.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}