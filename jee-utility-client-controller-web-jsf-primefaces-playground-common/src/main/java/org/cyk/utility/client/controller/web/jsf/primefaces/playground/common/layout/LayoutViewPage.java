package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.layout;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutItemBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @RequestScoped @Getter @Setter
public class LayoutViewPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Layout layout;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		
		layout = __inject__(LayoutBuilder.class)
				//Title
				.addItems(__inject__(LayoutItemBuilder.class).setOutputPropertyValue("Title").addStyleClasses("cyk_view_title"))
				//Inputs
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(2).setOutputPropertyValue("Label01"))
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(10).setOutputPropertyValue("Input01"))
				
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(2).setOutputPropertyValue("Label02"))
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(10).setOutputPropertyValue("Input02"))
				
				.addItems(__inject__(LayoutItemBuilder.class).setLayout(__inject__(LayoutBuilder.class)
						//SubTitle
						.addItems(__inject__(LayoutItemBuilder.class).setOutputPropertyValue("SubTitle").addStyleClasses("cyk_view_title"))
						//SubInputs
						.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(2).setOutputPropertyValue("Label11"))
						.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(10).setOutputPropertyValue("Input11"))
						
						.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(2).setOutputPropertyValue("Label12"))
						.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(10).setOutputPropertyValue("Input12"))
						
						.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(2).setOutputPropertyValue("Label13"))
						.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(10).setOutputPropertyValue("Input13"))
						
						.addItems(__inject__(LayoutItemBuilder.class).setOutputPropertyValue("Infos"))
						)
					)
				
				.addItems(__inject__(LayoutItemBuilder.class).setOutputPropertyValue("Commands").addStyleClasses("cyk_view_commands"))
				
				.execute().getOutput()
				;
		
	}
	
}
