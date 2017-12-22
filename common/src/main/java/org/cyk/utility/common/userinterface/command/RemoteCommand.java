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
	
	public static RemoteCommand instanciateOne(Component.Visible command,CommandHelper.Command action,CommandHelper.Command listener){
		RemoteCommand remoteCommand = new RemoteCommand();
		if(action!=null)
			remoteCommand.setAction(action);
		if(listener!=null)
			remoteCommand.setActionListener(listener);
		
		remoteCommand.getPropertiesMap().copyFrom(command.getPropertiesMap(), Properties.INPUT_VALUE_IS_NOT_REQUIRED,Properties.PROCESS,Properties.UPDATE,Properties.IMMEDIATE);
		
		if(isJavaServerFacesLibraryPrimefaces()){
			command._setPropertyOnClick(remoteCommand.getPropertiesMap().getName()+"();", Boolean.FALSE);
		}
		
		command.getPropertiesMap().setRemoteCommand(remoteCommand);
		
		return remoteCommand;
	}
	
	public static RemoteCommand instanciateOne(Command command){
		return instanciateOne(command, command.getAction(), command.getActionListener());
	}
	
	public static RemoteCommand instanciateOne(MenuNode node){
		return instanciateOne(node, null, null);
	}
	
	/**/
	
	public static class CommandActionAdapter extends CommandHelper.Command.Adapter.Default implements Serializable{
		private static final long serialVersionUID = 1L;
		
		protected Object __execute__() {
			return null;
		}
		
	}
}