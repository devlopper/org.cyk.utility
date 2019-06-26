package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.utility.client.controller.component.VisibleComponentBuilder;
import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.component.annotation.Output;
import org.cyk.utility.client.controller.component.annotation.OutputString;
import org.cyk.utility.client.controller.component.annotation.OutputStringText;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.component.view.ViewTypeForm;
import org.cyk.utility.client.controller.component.window.WindowContainerManagedWindowBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class ViewMixHandProgramPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private View view01,view02,view03;
	
	@Override
	protected WindowContainerManagedWindowBuilder __getWindowContainerManagedWindowBuilder__() {
		return null;
	}
	
	@Override
	protected String __getWindowTitleValue__() {
		return "View Mix Hand Program";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		return createViewBuilder();
	}
	
	public ViewBuilder createViewBuilder() {
		Model model = new Model();
		
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		ViewBuilder viewBuilder01 = __inject__(ViewBuilder.class);
		viewBuilder01.setType(__inject__(ViewTypeForm.class));
		viewBuilder01.setType(__inject__(ViewTypeForm.class));
		VisibleComponentBuilder<?> visibleComponentBuilder = (VisibleComponentBuilder<?>) viewBuilder01.addComponentBuilderByObjectByFieldNames(model.getModel01(), "__title__");
		visibleComponentBuilder.getLayoutItemStyle(Boolean.TRUE).addClasses("cyk_layout_title");
		viewBuilder01.addComponentBuilderByObjectByFieldNames(model.getModel01(), "f01");
		viewBuilder01.addComponentBuilderByObjectByFieldNames(model.getModel01(), "f02");
		view01 = viewBuilder01.execute().getOutput();
		
		ViewBuilder viewBuilder02 = __inject__(ViewBuilder.class);
		viewBuilder02.setType(__inject__(ViewTypeForm.class));
		viewBuilder02.setType(__inject__(ViewTypeForm.class));
		visibleComponentBuilder = (VisibleComponentBuilder<?>) viewBuilder02.addComponentBuilderByObjectByFieldNames(model.getModel02(), "__title__");
		visibleComponentBuilder.getLayoutItemStyle(Boolean.TRUE).addClasses("cyk_layout_title");
		viewBuilder02.addComponentBuilderByObjectByFieldNames(model.getModel02(), "f03");
		view02 = viewBuilder02.execute().getOutput();
		
		ViewBuilder viewBuilder03 = __inject__(ViewBuilder.class);
		viewBuilder03.setType(__inject__(ViewTypeForm.class));
		viewBuilder03.setType(__inject__(ViewTypeForm.class));
		visibleComponentBuilder = (VisibleComponentBuilder<?>) viewBuilder03.addComponentBuilderByObjectByFieldNames(model.getModel03(), "__title__");
		visibleComponentBuilder.getLayoutItemStyle(Boolean.TRUE).addClasses("cyk_layout_title");
		viewBuilder03.addComponentBuilderByObjectByFieldNames(model.getModel03(), "f01");
		viewBuilder03.addComponentBuilderByObjectByFieldNames(model.getModel03(), "f05");
		view03 = viewBuilder03.execute().getOutput();
		
		return viewBuilder;
	}
	
	public View createView() {
		return createViewBuilder().execute().getOutput();
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Model {
		
		private Model01 model01 = new Model01();
		
		private Model02 model02 = new Model02();
		
		private Model03 model03 = new Model03();
		
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Model01 {
		
		@Output @OutputString @OutputStringText
		private String __title__ = "Model 01";
		
		@Input @InputString @InputStringLineOne @NotNull
		private String f01;
		
		@Input @InputString @InputStringLineOne @NotNull
		private String f02;
		
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Model02 {
		
		@Output @OutputString @OutputStringText
		private String __title__ = "Model 02";
		
		@Input @InputString @InputStringLineOne @NotNull
		private String f03;
		
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Model03 {
		
		@Output @OutputString @OutputStringText
		private String __title__ = "Model 03";
		
		@Input @InputString @InputStringLineOne @NotNull
		private String f01;
		
		@Input @InputString @InputStringLineOne @NotNull
		private String f05;
		
	}
	
}
