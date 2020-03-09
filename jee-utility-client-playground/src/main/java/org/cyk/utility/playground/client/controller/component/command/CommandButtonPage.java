package org.cyk.utility.playground.client.controller.component.command;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.icon.Icon;
import org.cyk.utility.__kernel__.throwable.Message;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog;
import org.primefaces.PrimeFaces;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class CommandButtonPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private CommandButton commandButtonServer,commandButtonServerArgument1,commandButtonServerArgument2,commandButtonServerDoNotNotifySuccess,commandButtonServerDoErrorJava
		,commandButtonServerDoErrorCyk,commandButtonServerDoErrorCykMessageOne,commandButtonServerRenderMessageSuccessGrowl,commandButtonServerRenderMessageErrorGrowl
		,commandButtonServerConfirmDialog,commandButtonServerConfirmDialogUpdated
		,commandButtonIcon,commandButtonIconOnly,commandButtonShowDialog,commandButtonOpenDialog;
	
	private Dialog dialog;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Command Button Page";
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		commandButtonServer = CommandButton.build("value","Server");
		
		commandButtonServerArgument1 = CommandButton.build("value","Server Argument1");
		commandButtonServerArgument1.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				System.out.println("Argument1 : "+argument);
			}
		});
		
		commandButtonServerArgument2 = CommandButton.build("value","Server Argument2");
		commandButtonServerArgument2.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				System.out.println("Argument2 : "+argument);
			}
		});
		
		commandButtonServerDoNotNotifySuccess = CommandButton.build("value","Server Do Not Notify Success");
		commandButtonServerDoNotNotifySuccess.getRunnerArguments().setSuccessMessageArguments(null);
		
		commandButtonServerDoErrorJava = CommandButton.build("value","Server Do Error Java");
		commandButtonServerDoErrorJava.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				throw new RuntimeException("Something goes wrong from controller");
			}
		});
		
		commandButtonServerDoErrorCyk = CommandButton.build("value","Server Do Error Cyk");
		commandButtonServerDoErrorCyk.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				throw new org.cyk.utility.__kernel__.throwable.RuntimeException("Something goes wrong from controller");
			}
		});
		
		commandButtonServerDoErrorCykMessageOne = CommandButton.build("value","Server Do Error Cyk One Message");
		commandButtonServerDoErrorCykMessageOne.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				throw new org.cyk.utility.__kernel__.throwable.RuntimeException().addMessages(new Message().setSummary("Something goes wrong from controller"));
			}
		});
		
		commandButtonServerRenderMessageSuccessGrowl = CommandButton.build("value","Server Render Message Success Growl"
				,CommandButton.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_RENDER_TYPES,List.of(RenderType.GROWL));
		
		commandButtonServerRenderMessageErrorGrowl = CommandButton.build("value","Server Do Error Render Message Error Growl");
		commandButtonServerRenderMessageErrorGrowl.getRunnerArguments().getThrowableMessageArguments().setRenderTypes(List.of(RenderType.GROWL));
		commandButtonServerRenderMessageErrorGrowl.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				throw new RuntimeException("Something goes wrong from controller");
			}
		});
		
		commandButtonServerConfirmDialog = CommandButton.build("value","Server Confirm Dialog",CommandButton.ConfiguratorImpl.FIELD_CONFIRMABLE,Boolean.TRUE);
		
		commandButtonServerConfirmDialogUpdated = CommandButton.build("value","Server Confirm Dialog Updated",CommandButton.ConfiguratorImpl.FIELD_CONFIRMABLE,Boolean.TRUE);
		commandButtonServerConfirmDialogUpdated.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				commandButtonServerConfirmDialogUpdated.getConfirm().setMessage("Time is "+LocalDateTime.now()+". Do you want to continue ?");
				PrimeFaces.current().ajax().update(":form:contractBodyConfirmDialogOutputPanel");
			}
		});
		
		commandButtonIcon = CommandButton.build("value","Yes").setIcon(Icon.EDIT);
		
		commandButtonIconOnly = CommandButton.build().setIcon(Icon.EDIT);
		
		dialog = Dialog.build();
		commandButtonShowDialog = CommandButton.build(CommandButton.FIELD_VALUE,"Show Dialog",CommandButton.ConfiguratorImpl.FIELD_DIALOG,dialog);
		
		commandButtonOpenDialog = CommandButton.build(CommandButton.FIELD_VALUE,"Open Dialog"
				,CommandButton.ConfiguratorImpl.FIELD_OPEN_VIEW_IN_DIALOG_ARGUMENTS_GETTER_OUTCOME,"openedfromcommandbutton");
	}
	
}
