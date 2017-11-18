package org.cyk.utility.common.userinterface.output;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class OutputImage extends OutputFile implements Serializable {
	private static final long serialVersionUID = 1L;

	/**/
	
	/**/
	
	public static interface BuilderBase<OUTPUT extends OutputImage> extends OutputFile.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends OutputImage> extends Output.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends OutputImage> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<OutputImage> {

		public static class Adapter extends BuilderBase.Adapter.Default<OutputImage> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(OutputImage.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}
