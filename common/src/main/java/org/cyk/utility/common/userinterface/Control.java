package org.cyk.utility.common.userinterface;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Control extends Component.Visible implements Serializable {
	private static final long serialVersionUID = 1L;

	/**/

	public static interface BuilderBase<OUTPUT extends Control> extends org.cyk.utility.common.Builder.NullableInput<OUTPUT> {

		public static class Adapter<OUTPUT extends Control> extends org.cyk.utility.common.Builder.NullableInput.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Control> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Control> {

		public static class Adapter extends BuilderBase.Adapter.Default<Control> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Control.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
	
	/**/
	
}