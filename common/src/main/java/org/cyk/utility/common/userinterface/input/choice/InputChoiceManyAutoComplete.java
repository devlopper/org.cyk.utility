package org.cyk.utility.common.userinterface.input.choice;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.Control;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputChoiceManyAutoComplete extends InputChoiceMany implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private InputAutoCompleteCommon common = new InputAutoCompleteCommon();
	
	/**/
	
	@Override
	public InputChoiceManyAutoComplete setField(Object object, String fieldName, Collection<?> choicesElements) {
		return (InputChoiceManyAutoComplete) super.setField(object, fieldName, choicesElements);
	}
	
	@Override
	public InputChoiceManyAutoComplete _setField(Object object, String fieldName) {
		return (InputChoiceManyAutoComplete) super._setField(object, fieldName);
	}
	
	@Override
	public InputChoiceManyAutoComplete setField(Field field) {
		super.setField(field);
		common.setClazz(ClassHelper.getInstance().getParameterAt(field,0,Object.class));
		return this;
	}
	
	public static interface BuilderBase<OUTPUT extends InputChoiceManyAutoComplete> extends InputChoiceMany.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends InputChoiceManyAutoComplete> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends InputChoiceManyAutoComplete> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<InputChoiceManyAutoComplete> {

		public static class Adapter extends BuilderBase.Adapter.Default<InputChoiceManyAutoComplete> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<InputChoiceManyAutoComplete>) ClassHelper.getInstance().getByName(InputChoiceManyAutoComplete.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}