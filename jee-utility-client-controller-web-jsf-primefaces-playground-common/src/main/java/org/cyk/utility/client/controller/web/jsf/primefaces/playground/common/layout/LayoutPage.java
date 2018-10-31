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
public class LayoutPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Layout layout01;
	private Layout layout02;
	private Layout layout03;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		
		layout01 = createSimple().execute().getOutput();
		
		layout02 = createComplex().execute().getOutput();
		
		layout03 = createNested().execute().getOutput();
	}
	
	private LayoutBuilder createSimple() {
		return __inject__(LayoutBuilder.class)
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(2))
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(10))
				
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(2))
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(10))
				
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(2))
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(10))
				
				;
	}
	
	private LayoutBuilder createComplex() {
		return __inject__(LayoutBuilder.class)
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(2))
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(5))
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(5))
				
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(3))
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(3))
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(3))
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(3))
				
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsAll(6))
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsAll(6))
				
				;
	}
	
	private LayoutBuilder createNested() {
		return __inject__(LayoutBuilder.class)
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(4).setAreaWidthProportionsTablet(6).setLayout(createSimple()))
				.addItems(__inject__(LayoutItemBuilder.class).setAreaWidthProportionsNotPhone(8).setAreaWidthProportionsTablet(6).setLayout(createComplex()));
	}
	
}
