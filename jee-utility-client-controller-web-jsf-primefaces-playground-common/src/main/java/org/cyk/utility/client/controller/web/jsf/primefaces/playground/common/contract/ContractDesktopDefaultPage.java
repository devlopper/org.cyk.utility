package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.contract;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.Components;
import org.cyk.utility.client.controller.component.ComponentsBuilder;
import org.cyk.utility.client.controller.component.layout.InsertBuilder;
import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.client.controller.component.layout.LayoutBuilder;
import org.cyk.utility.client.controller.component.layout.LayoutItemBuilder;
import org.cyk.utility.client.controller.component.output.OutputStringText;
import org.cyk.utility.client.controller.component.output.OutputStringTextBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;

import lombok.Getter;
import lombok.Setter;

@Named @RequestScoped @Getter @Setter
public class ContractDesktopDefaultPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private OutputStringText __title__;
	private Layout __layout__;
	private Components __components__;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		__title__ = __inject__(OutputStringTextBuilder.class).setValue("MyPage").execute().getOutput();
		__layout__ = __inject__(LayoutBuilder.class)
				.addItems(__inject__(LayoutItemBuilder.class).setLayout(__getNorth__()))
				.addItems(__inject__(LayoutItemBuilder.class).setLayout(__getCenter__()))
				.addItems(__inject__(LayoutItemBuilder.class).setLayout(__getSouth__()))
				
				.execute().getOutput()
				;
		
		__components__ = __inject__(ComponentsBuilder.class)
				.setLayout(__inject__(LayoutBuilder.class)
						.addItems(__inject__(LayoutItemBuilder.class).addStyleClasses("cyk_layout_page_north").setOutputPropertyValue("North"))
						.addItems(__inject__(LayoutItemBuilder.class).addStyleClasses("cyk_layout_page_center").setOutputPropertyValue("Center"))
						.addItems(__inject__(LayoutItemBuilder.class).addStyleClasses("cyk_layout_page_south").setOutputPropertyValue("South"))
						)
				
				//.addComponents(__inject__(InsertBuilder.class).setName("north"),__inject__(InsertBuilder.class).setName("center"),__inject__(InsertBuilder.class).setName("south"))
				
				.execute().getOutput()
				;
		
		getProperties().setContracts("org.cyk.utility.client.controller.web.jsf.primefaces.desktop.default");
		getProperties().setTemplate("/template/default.xhtml");
	}
	
	private LayoutBuilder __getNorth__() {
		return __inject__(LayoutBuilder.class).addItems(__inject__(LayoutItemBuilder.class).addStyleClasses("cyk_layout_page_north").setOutputPropertyValue("North"))
		;
	}
	
	private LayoutBuilder __getCenter__() {
		return __inject__(LayoutBuilder.class).addItems(__inject__(LayoutItemBuilder.class).addStyleClasses("cyk_layout_page_center").setOutputPropertyValue("Center"))
		;
	}
	
	private LayoutBuilder __getSouth__() {
		return __inject__(LayoutBuilder.class).addItems(__inject__(LayoutItemBuilder.class).addStyleClasses("cyk_layout_page_south").setOutputPropertyValue("South"))
		;
	}
	
}
