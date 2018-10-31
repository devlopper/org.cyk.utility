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
public class LayoutPageTemplatePage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Layout layout;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		
		layout = __inject__(LayoutBuilder.class)
				//North
				.addItems(__inject__(LayoutItemBuilder.class).setOutputPropertyValue("North").addStyleClasses("cyk_view_page_template_north"))
				//Center
				.addItems(__inject__(LayoutItemBuilder.class).setOutputPropertyValue("Center").addStyleClasses("cyk_view_page_template_center"))
				//South
				.addItems(__inject__(LayoutItemBuilder.class).setOutputPropertyValue("South").addStyleClasses("cyk_view_page_template_south"))
				
				.execute().getOutput()
				;
		
	}
	
}
