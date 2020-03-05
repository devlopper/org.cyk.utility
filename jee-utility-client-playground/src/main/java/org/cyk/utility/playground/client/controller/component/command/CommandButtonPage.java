package org.cyk.utility.playground.client.controller.component.command;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.icon.Icon;
import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierAsFunctionParameter;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.__kernel__.system.action.SystemActionDelete;
import org.cyk.utility.__kernel__.system.action.SystemActionList;
import org.cyk.utility.__kernel__.system.action.SystemActionUpdate;
import org.cyk.utility.__kernel__.throwable.Message;
import org.cyk.utility.__kernel__.user.interface_.message.RenderType;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.playground.client.controller.entities.Person;
import org.primefaces.PrimeFaces;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class CommandButtonPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private CommandButton commandButtonServer,commandButtonServerArgument1,commandButtonServerArgument2,commandButtonServerDoNotNotifySuccess,commandButtonServerDoErrorJava
		,commandButtonServerDoErrorCyk,commandButtonServerDoErrorCykMessageOne,commandButtonServerRenderMessageSuccessGrowl,commandButtonServerRenderMessageErrorGrowl
		,commandButtonServerConfirmDialog,commandButtonServerConfirmDialogUpdated
		,commandButtonIcon,commandButtonIconOnly;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Command Button Page";
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		commandButtonServer = Builder.build(CommandButton.class,Map.of("value","Server"));
		
		commandButtonServerArgument1 = Builder.build(CommandButton.class,Map.of("value","Server Argument1"));
		commandButtonServerArgument1.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				System.out.println("Argument1 : "+argument);
			}
		});
		
		commandButtonServerArgument2 = Builder.build(CommandButton.class,Map.of("value","Server Argument2"));
		commandButtonServerArgument2.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				System.out.println("Argument2 : "+argument);
			}
		});
		
		commandButtonServerDoNotNotifySuccess = Builder.build(CommandButton.class,Map.of("value","Server Do Not Notify Success"));
		commandButtonServerDoNotNotifySuccess.getRunnerArguments().setSuccessMessageArguments(null);
		
		commandButtonServerDoErrorJava = Builder.build(CommandButton.class,Map.of("value","Server Do Error Java"));
		commandButtonServerDoErrorJava.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				throw new RuntimeException("Something goes wrong from controller");
			}
		});
		
		commandButtonServerDoErrorCyk = Builder.build(CommandButton.class,Map.of("value","Server Do Error Cyk"));
		commandButtonServerDoErrorCyk.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				throw new org.cyk.utility.__kernel__.throwable.RuntimeException("Something goes wrong from controller");
			}
		});
		
		commandButtonServerDoErrorCykMessageOne = Builder.build(CommandButton.class,Map.of("value","Server Do Error Cyk One Message"));
		commandButtonServerDoErrorCykMessageOne.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				throw new org.cyk.utility.__kernel__.throwable.RuntimeException().addMessages(new Message().setSummary("Something goes wrong from controller"));
			}
		});
		
		commandButtonServerRenderMessageSuccessGrowl = Builder.build(CommandButton.class,Map.of("value","Server Render Message Success Growl"));
		commandButtonServerRenderMessageSuccessGrowl.getRunnerArguments().getSuccessMessageArguments().setRenderTypes(List.of(RenderType.GROWL));
		
		commandButtonServerRenderMessageErrorGrowl = Builder.build(CommandButton.class,Map.of("value","Server Do Error Render Message Error Growl"));
		commandButtonServerRenderMessageErrorGrowl.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				throw new RuntimeException("Something goes wrong from controller");
			}
		});
		commandButtonServerRenderMessageErrorGrowl.getRunnerArguments().getThrowableMessageArguments().setRenderTypes(List.of(RenderType.GROWL));
		
		commandButtonServerConfirmDialog = Builder.build(CommandButton.class,Map.of("value","Server Confirm Dialog"));
		commandButtonServerConfirmDialog.getConfirm().setDisabled(Boolean.FALSE);
		
		commandButtonServerConfirmDialogUpdated = Builder.build(CommandButton.class,Map.of("value","Server Confirm Dialog Updated"));
		commandButtonServerConfirmDialogUpdated.getConfirm().setDisabled(Boolean.FALSE);
		commandButtonServerConfirmDialogUpdated.setListener(new CommandButton.Listener.AbstractImpl() {
			@Override
			public void listenAction(Object argument) {
				commandButtonServerConfirmDialogUpdated.getConfirm().setMessage("Time is "+LocalDateTime.now()+". Do you want to continue ?");
				PrimeFaces.current().ajax().update(":form:contractBodyConfirmDialogOutputPanel");
			}
		});
		
		commandButtonIcon = Builder.build(CommandButton.class,Map.of("value","Yes")).setIcon(Icon.EDIT);
		
		commandButtonIconOnly = Builder.build(CommandButton.class).setIcon(Icon.EDIT);
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		CommandableBuilder commandableBuilder = __inject__(CommandableBuilder.class);
		commandableBuilder.setName("Fixed uri : http://www.google.com");
		UniformResourceIdentifierAsFunctionParameter uniformResourceIdentifierAsFunctionParameter = commandableBuilder.getUniformResourceIdentifier(Boolean.TRUE);
		uniformResourceIdentifierAsFunctionParameter.setValue("http://www.google.com");
		viewBuilder.addComponentBuilder(commandableBuilder);
		
		commandableBuilder = __inject__(CommandableBuilder.class);
		commandableBuilder.setName("Derived from outcome(complete) : google");
		uniformResourceIdentifierAsFunctionParameter = commandableBuilder.getUniformResourceIdentifier(Boolean.TRUE);
		uniformResourceIdentifierAsFunctionParameter.getPath(Boolean.TRUE).setIdentifier("google");
		viewBuilder.addComponentBuilder(commandableBuilder);
		
		commandableBuilder = __inject__(CommandableBuilder.class);
		commandableBuilder.setName("Derived from outcome(relative) : outcome");
		uniformResourceIdentifierAsFunctionParameter = commandableBuilder.getUniformResourceIdentifier(Boolean.TRUE);
		uniformResourceIdentifierAsFunctionParameter.getPath(Boolean.TRUE).setIdentifier("outcome");
		viewBuilder.addComponentBuilder(commandableBuilder);
		
		commandableBuilder = __inject__(CommandableBuilder.class);
		commandableBuilder.setName("Derived from system action : create");
		uniformResourceIdentifierAsFunctionParameter = commandableBuilder.getUniformResourceIdentifier(Boolean.TRUE);
		uniformResourceIdentifierAsFunctionParameter.getPath(Boolean.TRUE).getSystemAction(Boolean.TRUE).setKlass(SystemActionCreate.class);
		uniformResourceIdentifierAsFunctionParameter.getPath(Boolean.TRUE).getSystemAction(Boolean.TRUE).getEntities(Boolean.TRUE).setElementClass(Person.class);
		uniformResourceIdentifierAsFunctionParameter.getQuery(Boolean.TRUE).getSystemAction(Boolean.TRUE).setKlass(SystemActionCreate.class);
		uniformResourceIdentifierAsFunctionParameter.getQuery(Boolean.TRUE).getSystemAction(Boolean.TRUE).getEntities(Boolean.TRUE).setElementClass(Person.class);
		viewBuilder.addComponentBuilder(commandableBuilder);
		
		commandableBuilder = __inject__(CommandableBuilder.class);
		commandableBuilder.setName("Derived from system action : list");
		uniformResourceIdentifierAsFunctionParameter = commandableBuilder.getUniformResourceIdentifier(Boolean.TRUE);
		uniformResourceIdentifierAsFunctionParameter.getPath(Boolean.TRUE).getSystemAction(Boolean.TRUE).setKlass(SystemActionList.class);
		uniformResourceIdentifierAsFunctionParameter.getPath(Boolean.TRUE).getSystemAction(Boolean.TRUE).getEntities(Boolean.TRUE).setElementClass(Person.class);
		uniformResourceIdentifierAsFunctionParameter.getQuery(Boolean.TRUE).getSystemAction(Boolean.TRUE).setKlass(SystemActionCreate.class);
		uniformResourceIdentifierAsFunctionParameter.getQuery(Boolean.TRUE).getSystemAction(Boolean.TRUE).getEntities(Boolean.TRUE).setElementClass(Person.class);
		viewBuilder.addComponentBuilder(commandableBuilder);
		
		commandableBuilder = __inject__(CommandableBuilder.class);
		commandableBuilder.setName("Derived from system action : update");
		uniformResourceIdentifierAsFunctionParameter = commandableBuilder.getUniformResourceIdentifier(Boolean.TRUE);
		uniformResourceIdentifierAsFunctionParameter.getPath(Boolean.TRUE).getSystemAction(Boolean.TRUE).setKlass(SystemActionUpdate.class);
		uniformResourceIdentifierAsFunctionParameter.getPath(Boolean.TRUE).getSystemAction(Boolean.TRUE).getEntities(Boolean.TRUE).setElementClass(Person.class);
		uniformResourceIdentifierAsFunctionParameter.getPath(Boolean.TRUE).getSystemAction(Boolean.TRUE).getEntitiesIdentifiers(Boolean.TRUE).setElements(List.of(159));
		uniformResourceIdentifierAsFunctionParameter.getQuery(Boolean.TRUE).getSystemAction(Boolean.TRUE).setKlass(SystemActionCreate.class);
		uniformResourceIdentifierAsFunctionParameter.getQuery(Boolean.TRUE).getSystemAction(Boolean.TRUE).getEntities(Boolean.TRUE).setElementClass(Person.class);
		uniformResourceIdentifierAsFunctionParameter.getQuery(Boolean.TRUE).getSystemAction(Boolean.TRUE).getEntitiesIdentifiers(Boolean.TRUE).setElements(List.of(159));
		viewBuilder.addComponentBuilder(commandableBuilder);
		
		commandableBuilder = __inject__(CommandableBuilder.class);
		commandableBuilder.setName("Derived from system action : delete");
		uniformResourceIdentifierAsFunctionParameter = commandableBuilder.getUniformResourceIdentifier(Boolean.TRUE);
		uniformResourceIdentifierAsFunctionParameter.getPath(Boolean.TRUE).getSystemAction(Boolean.TRUE).setKlass(SystemActionDelete.class);
		uniformResourceIdentifierAsFunctionParameter.getPath(Boolean.TRUE).getSystemAction(Boolean.TRUE).getEntities(Boolean.TRUE).setElementClass(Person.class);
		uniformResourceIdentifierAsFunctionParameter.getPath(Boolean.TRUE).getSystemAction(Boolean.TRUE).getEntitiesIdentifiers(Boolean.TRUE).setElements(List.of(159));
		uniformResourceIdentifierAsFunctionParameter.getQuery(Boolean.TRUE).getSystemAction(Boolean.TRUE).setKlass(SystemActionCreate.class);
		uniformResourceIdentifierAsFunctionParameter.getQuery(Boolean.TRUE).getSystemAction(Boolean.TRUE).getEntities(Boolean.TRUE).setElementClass(Person.class);
		uniformResourceIdentifierAsFunctionParameter.getQuery(Boolean.TRUE).getSystemAction(Boolean.TRUE).getEntitiesIdentifiers(Boolean.TRUE).setElements(List.of(159));
		viewBuilder.addComponentBuilder(commandableBuilder);
		
		return viewBuilder;
	}
	
}
