package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.output;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputString;
import org.cyk.utility.client.controller.component.annotation.InputStringLineMany;
import org.cyk.utility.client.controller.component.annotation.InputStringLineOne;
import org.cyk.utility.client.controller.component.output.OutputStringLinkBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.data.AbstractDataImpl;
import org.cyk.utility.client.controller.data.AbstractFormDataImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class OutputLinkPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Output Link";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		Form form = __inject__(Form.class);
		form.setData(__inject__(Data.class));
		form.getData().setName("Komenan Yao Christian");
		form.getData().setDetails("Détails 1\nDétails 2\n\tUn autre détails");
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		viewBuilder.addComponentBuilderByObjectByFieldNames(form.getData(),OutputStringLinkBuilder.class, "name");
		viewBuilder.addComponentBuilderByObjectByFieldNames(form.getData(), OutputStringLinkBuilder.class,"details");
		
		return viewBuilder;
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Data extends AbstractDataImpl implements Serializable {
		private static final long serialVersionUID = 1L;

		@Input @InputString @InputStringLineOne
		private String name;
		
		@Input @InputString @InputStringLineMany
		private String details;
		
	}
	
	@Getter @Setter @Accessors(chain=true) @ToString
	public static class Form extends AbstractFormDataImpl<Data> implements Serializable {
		private static final long serialVersionUID = 1L;
		
	}
	
}
