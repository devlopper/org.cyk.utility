package org.cyk.utility.client.controller.web.jsf.primefaces.model.command;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.random.RandomHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class RemoteCommand extends AbstractAction implements Serializable{

	private String name;
	private Long delay;
	private Boolean async,autoRun,global;
	
	/**/
	
	public void action() {
		act(null);
	}
	
	/**/

	public static final String FIELD_ASYNC = "async";
	public static final String FIELD_AUTO_RUN = "autoRun";
	public static final String FIELD_DELAY = "delay";
	public static final String FIELD_GLOBAL = "global";
	public static final String FIELD_NAME = "name";
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractAction.AbstractConfiguratorImpl<RemoteCommand> implements Serializable {

		@Override
		public void configure(RemoteCommand remoteCommand, Map<Object, Object> arguments) {
			super.configure(remoteCommand, arguments);
			if(StringHelper.isBlank(remoteCommand.name))
				remoteCommand.name = RandomHelper.getAlphabetic(5);
			if(remoteCommand.async == null)
				remoteCommand.async = Boolean.TRUE;
			if(remoteCommand.autoRun == null)
				remoteCommand.autoRun = Boolean.TRUE;
			if(remoteCommand.global == null)
				remoteCommand.global = Boolean.FALSE;
			if(remoteCommand.userInterfaceAction == null) {
				remoteCommand.userInterfaceAction = UserInterfaceAction.EXECUTE_FUNCTION;
				if(remoteCommand.runnerArguments != null)
					remoteCommand.runnerArguments.setSuccessMessageArguments(null);
			}
		}
		
		@Override
		protected Class<RemoteCommand> __getClass__() {
			return RemoteCommand.class;
		}
		
		@Override
		protected String __getTemplate__(RemoteCommand object, Map<Object, Object> arguments) {
			return "/remotecommand/default.xhtml";
		}
	}
	
	public static RemoteCommand build(Map<Object, Object> arguments) {
		return Builder.build(RemoteCommand.class,arguments);
	}
	
	public static RemoteCommand build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(RemoteCommand.class, new ConfiguratorImpl());
	}	
}