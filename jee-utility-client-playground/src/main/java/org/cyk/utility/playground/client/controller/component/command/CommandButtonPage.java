package org.cyk.utility.playground.client.controller.component.command;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.__kernel__.identifier.resource.UniformResourceIdentifierAsFunctionParameter;
import org.cyk.utility.__kernel__.system.action.SystemActionCreate;
import org.cyk.utility.__kernel__.system.action.SystemActionDelete;
import org.cyk.utility.__kernel__.system.action.SystemActionList;
import org.cyk.utility.__kernel__.system.action.SystemActionUpdate;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.CommandButton;
import org.cyk.utility.playground.client.controller.entities.Person;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class CommandButtonPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private CommandButton commandButtonServer,commandButtonServerDoNotNotifySuccess;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Command Button Page";
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		commandButtonServer = new CommandButton();
		commandButtonServer.setValue("Server");
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
