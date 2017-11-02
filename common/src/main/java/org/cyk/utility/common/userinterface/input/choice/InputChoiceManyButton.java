package org.cyk.utility.common.userinterface.input.choice;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.Control;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputChoiceManyButton extends InputChoiceMany implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**/
	
	@Override
	public InputChoiceManyButton setField(Object object, String fieldName, Collection<?> choicesElements) {
		return (InputChoiceManyButton) super.setField(object, fieldName, choicesElements);
	}
	
	@Override
	public InputChoiceManyButton setField(Object object, String fieldName) {
		return (InputChoiceManyButton) super.setField(object, fieldName);
	}
	
	public static interface BuilderBase<OUTPUT extends InputChoiceManyButton> extends InputChoiceMany.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends InputChoiceManyButton> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends InputChoiceManyButton> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<InputChoiceManyButton> {

		public static class Adapter extends BuilderBase.Adapter.Default<InputChoiceManyButton> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<InputChoiceManyButton>) ClassHelper.getInstance().getByName(InputChoiceManyButton.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}