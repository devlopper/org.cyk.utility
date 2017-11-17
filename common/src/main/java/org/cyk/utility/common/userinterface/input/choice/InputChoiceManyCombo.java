package org.cyk.utility.common.userinterface.input.choice;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.Control;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputChoiceManyCombo extends InputChoiceMany implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**/
	
	@Override
	public InputChoiceManyCombo setField(Object object, String fieldName, Collection<?> choicesElements) {
		return (InputChoiceManyCombo) super.setField(object, fieldName, choicesElements);
	}
	
	@Override
	public InputChoiceManyCombo _setField(Object object, String fieldName) {
		return (InputChoiceManyCombo) super._setField(object, fieldName);
	}
	
	public static interface BuilderBase<OUTPUT extends InputChoiceManyCombo> extends InputChoiceMany.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends InputChoiceManyCombo> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends InputChoiceManyCombo> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<InputChoiceManyCombo> {

		public static class Adapter extends BuilderBase.Adapter.Default<InputChoiceManyCombo> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<InputChoiceManyCombo>) ClassHelper.getInstance().getByName(InputChoiceManyCombo.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}