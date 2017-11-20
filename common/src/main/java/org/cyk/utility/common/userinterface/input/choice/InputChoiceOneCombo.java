package org.cyk.utility.common.userinterface.input.choice;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.Control;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputChoiceOneCombo extends InputChoiceOne implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**/
	
	@Override
	public InputChoiceOneCombo setField(Object object, String fieldName, Collection<?> choicesElements) {
		return (InputChoiceOneCombo) super.setField(object, fieldName, choicesElements);
	}
	
	@Override
	public InputChoiceOneCombo __setField__(Object object, String fieldName) {
		return (InputChoiceOneCombo) super.__setField__(object, fieldName);
	}
	
	public static interface BuilderBase<OUTPUT extends InputChoiceOneCombo> extends InputChoiceOne.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends InputChoiceOneCombo> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends InputChoiceOneCombo> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<InputChoiceOneCombo> {

		public static class Adapter extends BuilderBase.Adapter.Default<InputChoiceOneCombo> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<InputChoiceOneCombo>) ClassHelper.getInstance().getByName(InputChoiceOneCombo.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}