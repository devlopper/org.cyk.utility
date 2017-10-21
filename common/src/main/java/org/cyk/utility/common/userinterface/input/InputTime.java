package org.cyk.utility.common.userinterface.input;

import java.io.Serializable;
import java.util.Date;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.userinterface.Control;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class InputTime extends Input<Date> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**/

	public static interface BuilderBase<OUTPUT extends InputTime> extends Input.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends InputTime> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends InputTime> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<InputTime> {

		public static class Adapter extends BuilderBase.Adapter.Default<InputTime> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			public Adapter() {
				super((Class<InputTime>) ClassHelper.getInstance().getByName(InputTime.class));
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}