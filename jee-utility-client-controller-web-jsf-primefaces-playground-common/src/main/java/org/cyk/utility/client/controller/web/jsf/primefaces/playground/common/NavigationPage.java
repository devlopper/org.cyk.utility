package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.component.view.View;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.navigation.NavigationIdentifierToUrlStringMapper;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class NavigationPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Navigation";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		viewBuilder.getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE)
		.addComponents(createViewBuilder())
		
		;
		return viewBuilder;
	}
	
	public static ViewBuilder createViewBuilder() {
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		OutputStringTextBuilder outputStringTextBuilder = __inject__(OutputStringTextBuilder.class);
		outputStringTextBuilder.setValue("Id : navigation01");
		outputStringTextBuilder.addStyleValues("font-size: 22px !important; color: red !important; font-weight: bold !important;text-transform: uppercase;");
		
		viewBuilder.getComponentsBuilder(Boolean.TRUE).setIsCreateLayoutItemOnAddComponent(Boolean.TRUE)
		.addComponents(outputStringTextBuilder)
		.addComponents(__inject__(OutputStringTextBuilder.class).setValue("Value : "+__inject__(NavigationIdentifierToUrlStringMapper.class).setIdentifier("navigation01").execute().getOutput()))
		
		.addComponents(__inject__(OutputStringTextBuilder.class).setValue("Id : navigation02"))
		.addComponents(__inject__(OutputStringTextBuilder.class).setValue("Value : "+__inject__(NavigationIdentifierToUrlStringMapper.class).setIdentifier("navigation02").execute().getOutput()))
		
		;
		return viewBuilder;
	}
	
	public static View createView() {
		return createViewBuilder().execute().getOutput();
	}
	
}
