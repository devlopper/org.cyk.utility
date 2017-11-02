package org.cyk.utility.common.userinterface.command;

import java.io.Serializable;

import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CommandHelper;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.event.Confirm;
import org.cyk.utility.common.userinterface.panel.ConfirmationDialog;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Command extends Control implements Serializable {
	private static final long serialVersionUID = 1L;

	private Confirm confirm;
	private ConfirmationDialog confirmationDialog;
	private org.cyk.utility.common.helper.CommandHelper.Command action;
	
	/**/
	
	public Command setActionFromClass(Class<? extends ActionAdapter> actionAdapterClass){
		setAction(ClassHelper.getInstance().instanciateOne(actionAdapterClass));
		if(Boolean.TRUE.equals(getAction().getIsConfirmable()))
			setConfirm(new Confirm());
		return this;
	}
	
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

	/**/
	
	@Getter @Setter @Accessors(chain=true)
	public static class ActionAdapter extends CommandHelper.Command.Adapter.Default implements Serializable{
		private static final long serialVersionUID = 1L;
		
		
		
	}
}
