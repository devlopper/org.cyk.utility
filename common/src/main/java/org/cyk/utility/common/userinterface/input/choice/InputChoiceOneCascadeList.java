package org.cyk.utility.common.userinterface.input.choice;

import java.io.Serializable;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.Control;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputChoiceOneCascadeList extends InputChoiceOne implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**/
	
	@Override
	public InputChoiceOneCascadeList __setField__(Object object, String fieldName) {
		return (InputChoiceOneCascadeList) super.__setField__(object, fieldName);
	}
	
	public static interface BuilderBase<OUTPUT extends InputChoiceOneCascadeList> extends InputChoiceOne.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends InputChoiceOneCascadeList> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends InputChoiceOneCascadeList> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<InputChoiceOneCascadeList> {

		public static class Adapter extends BuilderBase.Adapter.Default<InputChoiceOneCascadeList> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<InputChoiceOneCascadeList>) ClassHelper.getInstance().getByName(InputChoiceOneCascadeList.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}