package org.cyk.utility.playground.client.controller.component.command;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.enumeration.Action;
import org.cyk.utility.__kernel__.icon.Icon;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.throwable.Message;
import org.cyk.utility.__kernel__.user.interface_.UserInterfaceAction;
import org.cyk.utility.__kernel__.user.interface_.message.MessageRenderer;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.AbstractAction;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Cell;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.layout.Layout;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.panel.Dialog;
import org.cyk.utility.playground.client.controller.entities.Namable;
import org.primefaces.PrimeFaces;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class CommandButtonPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Layout layout;
	private CommandButton commandButtonServer,commandButtonServerArgument1,commandButtonServerArgument2,commandButtonServerDoNotNotifySuccess,commandButtonServerDoErrorJava
		,commandButtonServerDoErrorCyk,commandButtonServerDoErrorCykMessageOne,commandButtonServerRenderMessageSuccessGrowl,commandButtonServerRenderMessageErrorGrowl
		,commandButtonServerConfirmDialog,commandButtonServerConfirmDialogUpdated
		,commandButtonIcon,commandButtonIconOnly,commandButtonShowDialog,commandButtonOpenDialog,commandButtonCreateNamableInOpenedDialog,commandButtonListNamableInOpenedDialog
		,commandButtonDynamic;
	//private DataTable dataTable01;
	
	private Dialog dialog;
	private UserInterfaceAction userInterfaceAction;
	private Collection<UserInterfaceAction> actions = CollectionHelper.listOf(UserInterfaceAction.values());
	private String actionParameter,dialogMessage;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Command Button Page";
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		commandButtonServer = CommandButton.build(CommandButton.FIELD_VALUE,"Server no run");
		
		commandButtonServerArgument1 = CommandButton.build(CommandButton.FIELD_VALUE,"Server Argument1");
		commandButtonServerArgument1.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void run(AbstractAction action) {
				MessageRenderer.getInstance().render(action.get__argument__().toString(),RenderType.GROWL);
			}
		});
		
		commandButtonServerArgument2 = CommandButton.build(CommandButton.FIELD_VALUE,"Server Argument2");
		commandButtonServerArgument2.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void run(AbstractAction action) {
				MessageRenderer.getInstance().render(action.get__argument__().toString(),RenderType.GROWL);
			}
		});
		
		commandButtonServerDoNotNotifySuccess = CommandButton.build(CommandButton.FIELD_VALUE,"Server Do Not Notify Success");
		commandButtonServerDoNotNotifySuccess.getRunnerArguments().setSuccessMessageArguments(null);
		commandButtonServerDoNotNotifySuccess.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void run(AbstractAction action) {
				
			}
		});
		
		commandButtonServerDoErrorJava = CommandButton.build(CommandButton.FIELD_VALUE,"Server Do Error Java");
		commandButtonServerDoErrorJava.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void run(AbstractAction action) {
				throw new RuntimeException("Something goes wrong from controller");
			}
		});
		
		commandButtonServerDoErrorCyk = CommandButton.build(CommandButton.FIELD_VALUE,"Server Do Error Cyk");
		commandButtonServerDoErrorCyk.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void run(AbstractAction action) {
				throw new org.cyk.utility.__kernel__.throwable.RuntimeException("Something goes wrong from controller");
			}
		});
		
		commandButtonServerDoErrorCykMessageOne = CommandButton.build(CommandButton.FIELD_VALUE,"Server Do Error Cyk One Message");
		commandButtonServerDoErrorCykMessageOne.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void run(AbstractAction action) {
				throw new org.cyk.utility.__kernel__.throwable.RuntimeException().addMessages(new Message().setSummary("Something goes wrong from controller"));
			}
		});
		
		commandButtonServerRenderMessageSuccessGrowl = CommandButton.build(CommandButton.FIELD_VALUE,"Server Render Message Success Growl",CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.EXECUTE_FUNCTION
				,CommandButton.ConfiguratorImpl.FIELD_RUNNER_ARGUMENTS_SUCCESS_MESSAGE_ARGUMENTS_RENDER_TYPES,List.of(RenderType.GROWL));
		commandButtonServerRenderMessageSuccessGrowl.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void run(AbstractAction action) {
				
			}
		});
		
		commandButtonServerRenderMessageErrorGrowl = CommandButton.build(CommandButton.FIELD_VALUE,"Server Do Error Render Message Error Growl");
		commandButtonServerRenderMessageErrorGrowl.getRunnerArguments().getThrowableMessageArguments().setRenderTypes(List.of(RenderType.GROWL));
		commandButtonServerRenderMessageErrorGrowl.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void run(AbstractAction action) {
				throw new RuntimeException("Something goes wrong from controller");
			}
		});
		
		commandButtonServerConfirmDialog = CommandButton.build(CommandButton.FIELD_VALUE,"Server Confirm Dialog",CommandButton.ConfiguratorImpl.FIELD_CONFIRMABLE,Boolean.TRUE);
		
		commandButtonServerConfirmDialogUpdated = CommandButton.build(CommandButton.FIELD_VALUE,"Server Confirm Dialog Updated",CommandButton.ConfiguratorImpl.FIELD_CONFIRMABLE,Boolean.TRUE);
		commandButtonServerConfirmDialogUpdated.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void run(AbstractAction action) {
				commandButtonServerConfirmDialogUpdated.getConfirm().setMessage("Time is "+LocalDateTime.now()+". Do you want to continue ?");
				PrimeFaces.current().ajax().update(":form:contractBodyConfirmDialogOutputPanel");
			}
		});
		
		commandButtonIcon = CommandButton.build(CommandButton.FIELD_VALUE,"Yes").setIcon(Icon.EDIT);
		commandButtonIcon.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void run(AbstractAction action) {
				
			}
		});
		
		commandButtonIconOnly = CommandButton.build().setIcon(Icon.EDIT);
		commandButtonIconOnly.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void run(AbstractAction action) {
				
			}
		});
		
		dialog = Dialog.build();
		commandButtonShowDialog = CommandButton.build(CommandButton.FIELD_VALUE,"Show Dialog",CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.SHOW_DIALOG
				,CommandButton.FIELD___DIALOG__,dialog);
		
		commandButtonOpenDialog = CommandButton.build(CommandButton.FIELD_VALUE,"Open Dialog",CommandButton.FIELD_USER_INTERFACE_ACTION,UserInterfaceAction.OPEN_VIEW_IN_DIALOG
				,CommandButton.FIELD___OUTCOME__,"openedfromcommandbutton");
		
		commandButtonCreateNamableInOpenedDialog = CommandButton.build(CommandButton.FIELD___ACTION__,Action.CREATE
				,CommandButton.FIELD___ACTION_ON_CLASS__,Namable.class,CommandButton.FIELD_USER_INTERFACE_ACTION
				,UserInterfaceAction.OPEN_VIEW_IN_DIALOG);
		
		commandButtonListNamableInOpenedDialog = CommandButton.build(CommandButton.FIELD___ACTION__,Action.LIST
				,CommandButton.FIELD___ACTION_ON_CLASS__,Namable.class,CommandButton.FIELD_USER_INTERFACE_ACTION
				,UserInterfaceAction.OPEN_VIEW_IN_DIALOG);
		
		commandButtonDynamic = CommandButton.build(CommandButton.FIELD_VALUE,"Dynamic");
		commandButtonDynamic.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void run(AbstractAction action) {
				MessageRenderer.getInstance().render("Action : "+userInterfaceAction+" , "+actionParameter);
				
			}
		});
		
		//dataTable01 = DataTable.build(DataTable.FIELD_ELEMENT_CLASS,PersonType.class);
		
		layout = Layout.build(Layout.FIELD_CELL_WIDTH_UNIT,Cell.WidthUnit.UI_G,Layout.FIELD_NUMBER_OF_COLUMNS,6
					,Layout.FIELD_ROW_CELL_MODEL,Map.of(0,new Cell().setWidth(2),1,new Cell().setWidth(2),2,new Cell().setWidth(2),3,new Cell().setWidth(2),4,new Cell().setWidth(2),5,new Cell().setWidth(2))
					,Layout.ConfiguratorImpl.FIELD_CELLS_MAPS,CollectionHelper.listOf(
						MapHelper.instantiate(Cell.FIELD_CONTROL,commandButtonServer),MapHelper.instantiate(Cell.FIELD_CONTROL,commandButtonServerArgument1)
						,MapHelper.instantiate(Cell.FIELD_CONTROL,commandButtonServerArgument2),MapHelper.instantiate(Cell.FIELD_CONTROL,commandButtonServerDoNotNotifySuccess)
						,MapHelper.instantiate(Cell.FIELD_CONTROL,commandButtonServerDoErrorJava),MapHelper.instantiate(Cell.FIELD_CONTROL,commandButtonServerDoErrorCyk)
						,MapHelper.instantiate(Cell.FIELD_CONTROL,commandButtonServerDoErrorCykMessageOne),MapHelper.instantiate(Cell.FIELD_CONTROL,commandButtonServerRenderMessageSuccessGrowl)
						,MapHelper.instantiate(Cell.FIELD_CONTROL,commandButtonServerConfirmDialog),MapHelper.instantiate(Cell.FIELD_CONTROL,commandButtonServerConfirmDialogUpdated)
						,MapHelper.instantiate(Cell.FIELD_CONTROL,commandButtonIcon),MapHelper.instantiate(Cell.FIELD_CONTROL,commandButtonIconOnly)
						,MapHelper.instantiate(Cell.FIELD_CONTROL,commandButtonShowDialog),MapHelper.instantiate(Cell.FIELD_CONTROL,commandButtonOpenDialog)
						,MapHelper.instantiate(Cell.FIELD_CONTROL,commandButtonCreateNamableInOpenedDialog),MapHelper.instantiate(Cell.FIELD_CONTROL,commandButtonListNamableInOpenedDialog)
					));
	}
	
}
