package org.cyk.utility.common.userinterface.input;

import java.io.Serializable;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.Control;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputTextarea extends InputText implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**/

	public static interface BuilderBase<OUTPUT extends InputTextarea> extends InputText.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends InputTextarea> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends InputTextarea> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<InputTextarea> {

		public static class Adapter extends BuilderBase.Adapter.Default<InputTextarea> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<InputTextarea>) ClassHelper.getInstance().getByName(InputTextarea.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}