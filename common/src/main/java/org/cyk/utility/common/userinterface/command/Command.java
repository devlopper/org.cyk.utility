package org.cyk.utility.common.userinterface.command;

import java.io.Serializable;

import org.cyk.utility.common.Properties;
import org.cyk.utility.common.helper.ClassHelper;
import org.cyk.utility.common.helper.CommandHelper;
import org.cyk.utility.common.helper.JavaScriptHelper;
import org.cyk.utility.common.helper.StringHelper;
import org.cyk.utility.common.helper.UniformResourceLocatorHelper;
import org.cyk.utility.common.userinterface.Control;
import org.cyk.utility.common.userinterface.RequestHelper;
import org.cyk.utility.common.userinterface.event.Confirm;
import org.cyk.utility.common.userinterface.panel.ConfirmationDialog;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Command extends Control implements Serializable {
	private static final long serialVersionUID = 1L;

	private org.cyk.utility.common.helper.CommandHelper.Command action = new CommandHelper.Command.Adapter.Default.NoExecution();
	private org.cyk.utility.common.helper.CommandHelper.Command actionListener = new CommandHelper.Command.Adapter.Default.NoExecution();
	private Confirm confirm;
	private ConfirmationDialog confirmationDialog;
	
	/**/
	
	public Command setActionFromClass(Class<? extends ActionAdapter> actionAdapterClass){
		setAction(ClassHelper.getInstance().instanciateOne(actionAdapterClass));
		setIsConfirmable(getAction().getIsConfirmable());
		return this;
	}
	
	public Command setIsConfirmable(Boolean isConfirmable){
		org.cyk.utility.common.helper.CommandHelper.Command action = getAction();
		if(action!=null){
			action.setIsConfirmable(isConfirmable);
			if(Boolean.TRUE.equals(action.getIsConfirmable()))
				setConfirm(new Confirm());
			else
				setConfirm(null);	
		}
		
		return this;
	}
	
	public Command addJavaScriptGoToUniformResourceLocatorOnEvent(String event,String url,Boolean useUrlIfUrlRequestParameterIsBlank){
		if(useUrlIfUrlRequestParameterIsBlank!=null){
			String urlRequestParameterValue = RequestHelper.getInstance().getParameterAsString(UniformResourceLocatorHelper.QueryParameter.Name.URL_PREVIOUS);
			if(StringHelper.getInstance().isNotBlank(urlRequestParameterValue) && Boolean.TRUE.equals(useUrlIfUrlRequestParameterIsBlank))
				url = urlRequestParameterValue;
		}
		
		String script = JavaScriptHelper.getInstance().getScriptWindowGoTo(url, Boolean.TRUE);
		if(StringHelper.getInstance().isNotBlank(script))
			getPropertiesMap().addString(event, JavaScriptHelper.INSTRUCTION_SEPARATOR, script);
		return this;
	}
	
	public Command addJavaScriptGoToUniformResourceLocatorOnEvent(String event,String url){
		return addJavaScriptGoToUniformResourceLocatorOnEvent(event, url,null);
	}
	
	public Command addJavaScriptGoToUniformResourceLocatorOnEvent(String event){
		return addJavaScriptGoToUniformResourceLocatorOnEvent(event, RequestHelper.getInstance().getParameterAsString(UniformResourceLocatorHelper.QueryParameter.Name.URL_PREVIOUS));
	}
	
	public Command setPropertyOnClick(String onClick,Boolean actionable){
		getPropertiesMap().setOnClick(onClick);
		if(!Boolean.TRUE.equals(actionable)){
			if(isJavaServerFacesLibraryPrimefaces())
				getPropertiesMap().setType("button");
		}
		return this;
	}
	
	public Command usePropertyRemoteCommand(){
		RemoteCommand remoteCommand = new RemoteCommand();
		remoteCommand.setAction(getAction());
		remoteCommand.setActionListener(getActionListener());
		
		remoteCommand.getPropertiesMap().copyFrom(getPropertiesMap(), Properties.INPUT_VALUE_IS_NOT_REQUIRED,Properties.PROCESS,Properties.UPDATE,Properties.IMMEDIATE);
		
		//remoteCommand.getPropertiesMap().setInputValueIsNotRequired(getPropertiesMap().getInputValueIsNotRequired());
		//if(/*remoteCommand.getPropertiesMap().getProcess()==null && */getPropertiesMap().getProcess()!=null)
		//	remoteCommand.getPropertiesMap().setProcess(getPropertiesMap().getProcess());
		//remoteCommand.getPropertiesMap().setUpdate(getPropertiesMap().getUpdate());
		
		if(isJavaServerFacesLibraryPrimefaces()){
			setPropertyOnClick(remoteCommand.getPropertiesMap().getName()+"();", Boolean.FALSE);
			//remoteCommand.getPropertiesMap().setImmediate(getPropertiesMap().getInputValueIsNotRequired());
		}
		
		getPropertiesMap().setRemoteCommand(remoteCommand);
		
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
