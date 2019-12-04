package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputNumber;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class InputNumberPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Input Number";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		Numbers numbers = new Numbers();
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);		
		viewBuilder.addInputBuilderByObjectByFieldNames(numbers, Boolean.TRUE, "bigDecimal");
		return viewBuilder;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Numbers {
		
		@Input @InputNumber
		private BigDecimal bigDecimal;
		
	}
	
}
