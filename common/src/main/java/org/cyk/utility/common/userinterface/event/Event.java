package org.cyk.utility.common.userinterface.event;

import java.io.Serializable;

import org.cyk.utility.common.helper.CommandHelper;
import org.cyk.utility.common.userinterface.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Event extends Component.Invisible implements Serializable {
	private static final long serialVersionUID = 1L;

	private org.cyk.utility.common.helper.CommandHelper.Command listener = new CommandHelper.Command.Adapter.Default.NoExecution();
	
	/**/

	public static class CommandAdapter extends CommandHelper.Command.Adapter.Default implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected Object __execute__() {
			____execute____();
			return null;
		}

		protected void ____execute____() {}
		
	}
}