package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.link;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.link.LinkBuilder;
import org.cyk.utility.client.controller.component.view.ViewBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.identifier.resource.UniformResourceIdentifierStringBuilder;
import org.cyk.utility.resource.locator.UniformResourceLocatorStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class LinkPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected String __getWindowTitleValue__() {
		return "Link";
	}
	
	@Override
	protected ViewBuilder __getViewBuilder__() {
		ViewBuilder viewBuilder = __inject__(ViewBuilder.class);
		
		viewBuilder.addComponentBuilder(__inject__(LinkBuilder.class).setTextCharacters("Hello world.")
				.setUniformResourceLocator(__inject__(UniformResourceLocatorStringBuilder.class)
						.setUniformResourceIdentifierString(__inject__(UniformResourceIdentifierStringBuilder.class).setString("https://www.google.com/")))
				);
		
		return viewBuilder;
	}
	
}
