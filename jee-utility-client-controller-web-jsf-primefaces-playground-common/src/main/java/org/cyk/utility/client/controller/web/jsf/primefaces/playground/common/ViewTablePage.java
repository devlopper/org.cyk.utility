package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.ComponentsBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineOneBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutItemBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class ViewTablePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private View view;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		view =  createView();
	}
	
	public static ViewBuilder createViewBuilder() {
		ComponentsBuilder components = __inject__(ComponentsBuilder.class)
				.setLayout( __inject__(LayoutBuilder.class)
						.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(1))
						.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(3))
						.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(3))
						.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(3))
						.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(2))
						
						)
				
				.addComponents(__inject__(OutputStringTextBuilder.class).setValue("Label 01"),__inject__(InputStringLineOneBuilder.class).setMessage(null))
				.addComponents(__inject__(OutputStringTextBuilder.class).setValue("Label 02"),__inject__(InputStringLineOneBuilder.class).setMessage(null))
				.addComponents(__inject__(OutputStringTextBuilder.class).setValue("Label 03").execute().getOutput()
						,__inject__(InputStringLineOneBuilder.class).setMessage(null).execute().getOutput())
				;
		
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class).setComponentsBuilder(components);
		
		return viewBuilder;
	}
	
	public static View createView() {
		return createViewBuilder().execute().getOutput();
	}
	
	/**/
}
