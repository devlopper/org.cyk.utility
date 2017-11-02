package org.cyk.utility.common.userinterface.input.choice;

import java.io.Serializable;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.Control;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputChoiceOneButton extends InputChoiceOne implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**/
	
	@Override
	public InputChoiceOneButton setField(Object object, String fieldName) {
		return (InputChoiceOneButton) super.setField(object, fieldName);
	}
	
	public static interface BuilderBase<OUTPUT extends InputChoiceOneButton> extends InputChoiceOne.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends InputChoiceOneButton> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends InputChoiceOneButton> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<InputChoiceOneButton> {

		public static class Adapter extends BuilderBase.Adapter.Default<InputChoiceOneButton> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<InputChoiceOneButton>) ClassHelper.getInstance().getByName(InputChoiceOneButton.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}