package org.cyk.utility.common.userinterface.input.choice;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.Control;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputChoiceManyList extends InputChoiceMany implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**/
	
	@Override
	public InputChoiceManyList setField(Object object, String fieldName, Collection<?> choicesElements) {
		return (InputChoiceManyList) super.setField(object, fieldName, choicesElements);
	}
	
	@Override
	public InputChoiceManyList setField(Object object, String fieldName) {
		return (InputChoiceManyList) super.setField(object, fieldName);
	}
	
	public static interface BuilderBase<OUTPUT extends InputChoiceManyList> extends InputChoiceMany.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends InputChoiceManyList> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends InputChoiceManyList> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<InputChoiceManyList> {

		public static class Adapter extends BuilderBase.Adapter.Default<InputChoiceManyList> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<InputChoiceManyList>) ClassHelper.getInstance().getByName(InputChoiceManyList.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}