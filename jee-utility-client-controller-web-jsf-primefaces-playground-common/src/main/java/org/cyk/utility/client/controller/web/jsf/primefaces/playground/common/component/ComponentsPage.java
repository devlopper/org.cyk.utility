package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.component;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.Components;
import org.cyk.utility.client.controller.component.ComponentsBuilder;
import org.cyk.utility.client.controller.component.input.InputStringLineOneBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutItemBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @RequestScoped @Getter @Setter
public class ComponentsPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Components components01;
	private Components components02;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		
		components01 = __inject__(ComponentsBuilder.class)
				.setLayout( __inject__(LayoutBuilder.class)
						.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(6))
						.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(6))
						
						.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(6))
						.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(6))
						)
				
				.addComponents(__inject__(OutputStringTextBuilder.class).setValue("Label 01"),__inject__(InputStringLineOneBuilder.class).setMessage(null))
				.addComponents(__inject__(OutputStringTextBuilder.class).setValue("Label 02"),__inject__(InputStringLineOneBuilder.class).setMessage(null))
				
				.execute().getOutput()
				;

		components02 = __inject__(ComponentsBuilder.class)
				
				.addComponents(__inject__(InputStringLineOneBuilder.class).setLabelValue("Label 01"))
				.addComponents(__inject__(InputStringLineOneBuilder.class).setLabelValue("Label 02"))
				
				.execute().getOutput()
				;
		
	}
	
}
