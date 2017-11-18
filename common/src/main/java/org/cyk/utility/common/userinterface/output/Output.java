package org.cyk.utility.common.userinterface.output;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.Control;

public class Output extends Control implements Serializable {
	private static final long serialVersionUID = 1L;

	/**/

	public static interface BuilderBase<OUTPUT extends Output> extends Control.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends Output> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Output> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Output> {

		public static class Adapter extends BuilderBase.Adapter.Default<Output> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Output.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}

}
