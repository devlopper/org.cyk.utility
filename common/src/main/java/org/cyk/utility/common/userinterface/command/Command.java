package org.cyk.utility.common.userinterface.command;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.Control;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Command extends Control implements Serializable {
	private static final long serialVersionUID = 1L;

	private org.cyk.utility.common.helper.CommandHelper.Command action;
	
	/**/

	public static interface BuilderBase<OUTPUT extends Command> extends Control.BuilderBase<OUTPUT> {

		public static class Adapter<OUTPUT extends Command> extends Control.BuilderBase.Adapter.Default<OUTPUT> implements BuilderBase<OUTPUT>, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter(Class<OUTPUT> outputClass) {
				super(outputClass);
			}

			/**/

			public static class Default<OUTPUT extends Command> extends BuilderBase.Adapter<OUTPUT> implements Serializable {
				private static final long serialVersionUID = 1L;

				public Default(Class<OUTPUT> outputClass) {
					super(outputClass);
				}
			}
		}
	}
	
	public static interface Builder extends BuilderBase<Command> {

		public static class Adapter extends BuilderBase.Adapter.Default<Command> implements Builder, Serializable {
			private static final long serialVersionUID = 1L;

			public Adapter() {
				super(Command.class);
			}

			/**/

			public static class Default extends Builder.Adapter implements Serializable {
				private static final long serialVersionUID = 1L;

			}
		}
	}
}
