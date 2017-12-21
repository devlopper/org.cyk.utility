package org.cyk.utility.common.userinterface.command;

import java.io.Serializable;

import org.cyk.utility.common.Properties;
import org.cyk.utility.common.helper.CommandHelper;
import org.cyk.utility.common.helper.RandomHelper;
import org.cyk.utility.common.userinterface.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class RemoteCommand extends Component.Invisible implements Serializable {
	private static final long serialVersionUID = 1L;

	private org.cyk.utility.common.helper.CommandHelper.Command action = new CommandHelper.Command.Adapter.Default.NoExecution();
	private org.cyk.utility.common.helper.CommandHelper.Command actionListener = new CommandHelper.Command.Adapter.Default.NoExecution();
	
	/**/

	@Override
	protected void constructorJavaServerFacesLibraryPrimefaces() {
		super.constructorJavaServerFacesLibraryPrimefaces();
		
	}
	
	@Override
	protected void listenPropertiesInstanciated(Properties propertiesMap) {
		super.listenPropertiesInstanciated(propertiesMap);
		if(Boolean.TRUE.equals(isJavaServerFacesLibraryPrimefaces())){
			propertiesMap.setName(RandomHelper.getInstance().getAlphabetic(5)+System.currentTimeMillis());
			propertiesMap.setGlobal(Boolean.TRUE);
			propertiesMap.setTimeOut(0);
		}
	}
	
	/**/
	
	public static class CommandActionAdapter extends CommandHelper.Command.Adapter.Default implements Serializable{
		private static final long serialVersionUID = 1L;
		
		protected Object __execute__() {
			return null;
		}
		
	}
}