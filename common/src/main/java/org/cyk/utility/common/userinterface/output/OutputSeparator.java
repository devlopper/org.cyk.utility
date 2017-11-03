package org.cyk.utility.common.userinterface.output;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class OutputSeparator extends Output implements Serializable {
	private static final long serialVersionUID = 1L;

	/**/

	public static interface BuilderBase<OUTPUT extends OutputSeparator> extends Output.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends OutputSeparator> extends Output.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends OutputSeparator> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<OutputSeparator> {

		public static class Adapter extends BuilderBase.Adapter.Default<OutputSeparator> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(OutputSeparator.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}
