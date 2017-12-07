package org.cyk.utility.common.userinterface.output;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class OutputLink extends Output implements Serializable {
	private static final long serialVersionUID = 1L;

	/**/

	public static interface BuilderBase<OUTPUT extends OutputLink> extends Output.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends OutputLink> extends Output.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends OutputLink> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<OutputLink> {

		public static class Adapter extends BuilderBase.Adapter.Default<OutputLink> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(OutputLink.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}
