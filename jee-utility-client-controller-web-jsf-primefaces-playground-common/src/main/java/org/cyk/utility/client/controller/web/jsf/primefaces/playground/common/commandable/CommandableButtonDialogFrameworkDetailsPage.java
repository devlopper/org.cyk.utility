package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.commandable;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.annotation.Commandable;
import org.cyk.utility.client.controller.component.annotation.CommandableButton;
import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineMany;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.component.annotation.Output;
import org.cyk.utility.client.controller.component.annotation.OutputString;
import org.cyk.utility.client.controller.component.annotation.OutputStringText;
import org.cyk.utility.client.controller.component.command.CommandableBuilder;
import org.cyk.utility.client.controller.component.menu.MenuBuilderMap;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.system.action.SystemActionCreate;
import org.primefaces.PrimeFaces;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class CommandableButtonDialogFrameworkDetailsPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Commandable Button Dialog Framework Details";
	}
	
	@Override
	protected MenuBuilderMap __getMenuBuilderMap__() {
		return null;
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		Model model = new Model();
		
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		VisibleComponentBuilder<?> visibleComponentBuilder = (VisibleComponentBuilder<?>) viewBuilder.addComponentBuilderByObjectByFieldNames(model, "__title__");
		visibleComponentBuilder.getLayoutItemStyle(Boolean.TRUE).addClasses("cyk_layout_title");
		viewBuilder.addComponentBuilderByObjectByFieldNames(model, "firstName");
		viewBuilder.addComponentBuilderByObjectByFieldNames(model, "lastNames");
		viewBuilder.addComponentBuilderByObjectByFieldNames(model, "otherDetails");
		CommandableBuilder commandable =  (CommandableBuilder) viewBuilder.addComponentBuilderByObjectByMethodName(model, "submit");
		
		/*
		CommandableBuilder commandable = __inject__(CommandableBuilder.class).setName("OK").setCommandFunctionActionClass(SystemActionCreate.class);
		commandable.addCommandFunctionTryRunRunnable(new Runnable() {
			@Override
			public void run() {
				PrimeFaces.current().dialog().closeDynamic(model);
			}
		});
		*/
		commandable.getCommand(Boolean.TRUE).getFunction(Boolean.TRUE).setIsNotifyOnSuccess(Boolean.FALSE);
		
		//viewBuilder.getComponentsBuilder(Boolean.TRUE).addComponents(commandable);
		return viewBuilder;
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Model {
		
		@Output @OutputString @OutputStringText
		private String __title__;
		
		@Input @InputString @InputStringLineOne
		@NotNull
		private String firstName;
		
		@Input @InputString @InputStringLineOne
		private String lastNames;
		
		@Input @InputString @InputStringLineMany
		@NotNull
		private String otherDetails;
		
		@Commandable(systemActionClass=SystemActionCreate.class) @CommandableButton
		public void submit() {
			System.out.println("CommandableButtonDialogFrameworkDetailsPage.Model.submit() ::: "+this);
			PrimeFaces.current().dialog().closeDynamic(this);
		}

	}

}
