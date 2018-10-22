package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineMany;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.component.annotation.Output;
import org.cyk.utility.client.controller.component.annotation.OutputString;
import org.cyk.utility.client.controller.component.annotation.OutputStringText;
import org.cyk.utility.client.controller.view.View;
import org.cyk.utility.client.controller.view.ViewBuilder;
import org.cyk.utility.client.controller.view.ViewTypeForm;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class ViewPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private View view01;
	private View viewForm01;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		view01 = createView01();
	}
	
	private View createView01() {
		Model model = new Model();
		model.setFirstName("Yao");
		
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		viewBuilder.setType(__inject__(ViewTypeForm.class));
		viewBuilder.setNameOutputPropertyValue("Mon titre de formulaire");
		
		viewBuilder.addComponentBuilderByFieldName(model, "firstName");
		viewBuilder.addComponentBuilderByFieldName(model, "lastNames");
		viewBuilder.addComponentBuilderByFieldName(model, "otherDetails");
		
		return viewBuilder.execute().getOutput();
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
	}
}
