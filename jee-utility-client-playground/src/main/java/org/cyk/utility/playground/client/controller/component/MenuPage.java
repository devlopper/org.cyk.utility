package org.cyk.utility.playground.client.controller.component;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.MenuButton;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class MenuPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private MenuButton menuButton;
	
	@Override
	protected String __getWindowTitleValue__() {
		return "Menu Page";
	}
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		menuButton = MenuButton.build(MenuButton.FIELD_VALUE,"MyButtonMenu");
		
	}
	
}
