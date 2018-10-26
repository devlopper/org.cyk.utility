package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common.layout;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.layout.LayoutItem;
import org.cyk.utility.client.controller.component.layout.LayoutItemBuilder;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;

import lombok.Getter;
import lombok.Setter;

@Named @RequestScoped @Getter @Setter
public class LayoutItemPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private LayoutItem layoutItem01,layoutItem02,layoutItem03,layoutItem04;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		
		layoutItem01 = __inject__(LayoutItemBuilder.class).execute().getOutput();
	}
	
	
	
}
