package org.cyk.utility.common.userinterface.input.choice;

import java.io.Serializable;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.Control;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputChoiceOneRadio extends InputChoiceOne implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**/
	
	@Override
	public InputChoiceOneRadio _setField(Object object, String fieldName) {
		return (InputChoiceOneRadio) super._setField(object, fieldName);
	}
	
	public static interface BuilderBase<OUTPUT extends InputChoiceOneRadio> extends InputChoiceOne.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends InputChoiceOneRadio> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends InputChoiceOneRadio> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<InputChoiceOneRadio> {

		public static class Adapter extends BuilderBase.Adapter.Default<InputChoiceOneRadio> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<InputChoiceOneRadio>) ClassHelper.getInstance().getByName(InputChoiceOneRadio.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}