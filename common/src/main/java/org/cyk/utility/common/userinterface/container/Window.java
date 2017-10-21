package org.cyk.utility.common.userinterface.container;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.DeviceType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Window extends Container implements Serializable {
	private static final long serialVersionUID = 1L;

	protected DeviceType deviceType;
	protected Object templateIdentifier;
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends Window> extends Component.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends Window> extends Component.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Window> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Window> {

		public static class Adapter extends BuilderBase.Adapter.Default<Window> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Window.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
	
	/**/
	
}
