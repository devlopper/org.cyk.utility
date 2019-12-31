package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;
import java.util.Collection;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.annotation.Input;
import org.cyk.utility.client.controller.component.annotation.InputChoice;
import org.cyk.utility.client.controller.component.annotation.InputChoiceMany;
import org.cyk.utility.client.controller.component.annotation.InputChoiceManyCheckBox;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.playground.client.controller.entities.PersonType;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Named @ViewScoped @Getter @Setter
public class InputChoiceManyCheckBoxPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Input Choice Many Check Box";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		Inputs inputs = new Inputs();
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);		
		viewBuilder.addInputBuilderByObjectByFieldNames(inputs, Boolean.TRUE, "myEnum");
		viewBuilder.addInputBuilderByObjectByFieldNames(inputs, Boolean.TRUE, "personType");
		return viewBuilder;
	}
	
	@Getter @Setter @Accessors(chain=true)
	public static class Inputs {
		
		@Input @InputChoice @InputChoiceMany @InputChoiceManyCheckBox
		private Collection<MyEnum> myEnum;
	
		@Input @InputChoice @InputChoiceMany @InputChoiceManyCheckBox
		private Collection<PersonType> personType;
		
		public static enum MyEnum {
			V01,V02,V03
			;
		}
	}
	
}
