package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.layout.Layout;
import org.cyk.utility.client.controller.component.layout.LayoutBuiler;
import org.cyk.utility.client.controller.component.layout.LayoutBuilerItem;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class LayoutPage extends AbstractPageImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Layout layout01;
	private Layout layout02;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		
		layout01 = __inject__(LayoutBuiler.class)
			.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
			.addItems(__inject__(LayoutBuilerItem.class).setWidth(10).setWidthForDesktop(10).setWidthForTablet(10).setWidthForPhone(12))
			
			.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
			.addItems(__inject__(LayoutBuilerItem.class).setWidth(10).setWidthForDesktop(10).setWidthForTablet(10).setWidthForPhone(12))
			
			.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
			.addItems(__inject__(LayoutBuilerItem.class).setWidth(10).setWidthForDesktop(10).setWidthForTablet(10).setWidthForPhone(12))
			
			.execute().getOutput()
			;
		
		layout02 = __inject__(LayoutBuiler.class)
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(2).setWidthForDesktop(2).setWidthForTablet(2).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(5).setWidthForDesktop(5).setWidthForTablet(5).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(5).setWidthForDesktop(5).setWidthForTablet(5).setWidthForPhone(12))
				
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(3).setWidthForDesktop(3).setWidthForTablet(3).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(3).setWidthForDesktop(3).setWidthForTablet(3).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(3).setWidthForDesktop(3).setWidthForTablet(3).setWidthForPhone(12))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(3).setWidthForDesktop(3).setWidthForTablet(3).setWidthForPhone(12))
				
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(6).setWidthForDesktop(6).setWidthForTablet(6).setWidthForPhone(6))
				.addItems(__inject__(LayoutBuilerItem.class).setWidth(6).setWidthForDesktop(6).setWidthForTablet(6).setWidthForPhone(6))
				
				.execute().getOutput()
				;
	}
	
}
