package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

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
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.view.ViewTypeForm;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.system.action.SystemActionCreate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class ViewPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "View";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		return createViewBuilder();
	}
	
	public static ViewBuilder createViewBuilder() {
		Model model = new Model();
		model.set__title__("Titre");
		model.setLastNames("Yao Christian");
		model.getSubModel().set__title__("Sous-titre");
		model.getSubModel().setPhone1("11223344");
		
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		VisibleComponentBuilder<?> visibleComponentBuilder = (VisibleComponentBuilder<?>) viewBuilder.addComponentBuilderByObjectByFieldNames(model, "__title__");
		visibleComponentBuilder.getLayoutItemStyle(Boolean.TRUE).addClasses("cyk_layout_title");
		viewBuilder.addComponentBuilderByObjectByFieldNames(model, "firstName");
		viewBuilder.addComponentBuilderByObjectByFieldNames(model, "lastNames");
		viewBuilder.addComponentBuilderByObjectByFieldNames(model, "otherDetails");
		
		ViewBuilder subViewBuilder = __inject__(ViewBuilder.class);
		subViewBuilder.setType(__inject__(ViewTypeForm.class));
		visibleComponentBuilder = (VisibleComponentBuilder<?>) subViewBuilder.addComponentBuilderByObjectByFieldNames(model.getSubModel(), "__title__");
		visibleComponentBuilder.getLayoutItemStyle(Boolean.TRUE).addClasses("cyk_layout_title");
		
		subViewBuilder.addComponentBuilderByObjectByFieldNames(model.getSubModel(), "phone1");
		subViewBuilder.addComponentBuilderByObjectByFieldNames(model.getSubModel(), "phone2");
		subViewBuilder.addComponentBuilderByObjectByFieldNames(model.getSubModel(), "otherDetails");
		viewBuilder.addComponentBuilder(subViewBuilder);
		
		viewBuilder.addComponentBuilderByObjectByMethodName(model, "submit");
		viewBuilder.addComponentBuilderByObjectByMethodName(model, "close");
		
		return viewBuilder;
	}
	
	public static View createView() {
		return createViewBuilder().execute().getOutput();
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
		
		private SubModel subModel = new SubModel();
		
		@Commandable(systemActionClass=SystemActionCreate.class) @CommandableButton
		public void submit() {
			System.out.println("ViewPage.Model.submit() : "+this);
		}
		
		@Commandable(systemActionClass=SystemActionCreate.class) @CommandableButton
		public void close() {
			System.out.println("ViewPage.Model.close() : "+this);
		}
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class SubModel {
		
		@Output @OutputString @OutputStringText
		private String __title__;
		
		@Input @InputString @InputStringLineOne
		@NotNull
		private String phone1;
		
		@Input @InputString @InputStringLineOne
		private String phone2;
		
		@Input @InputString @InputStringLineMany
		@NotNull
		private String otherDetails;
		
	}
}
