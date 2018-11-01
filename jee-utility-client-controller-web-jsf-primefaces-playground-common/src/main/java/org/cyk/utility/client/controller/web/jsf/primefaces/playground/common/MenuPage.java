package org.cyk.utility.client.controller.web.jsf.primefaces.playground.common;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.cyk.utility.client.controller.component.menu.Menu;
import org.cyk.utility.client.controller.component.menu.MenuBuilder;
import org.cyk.utility.client.controller.component.menu.MenuItemBuilder;
import org.cyk.utility.client.controller.component.menu.MenuRenderType;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeColumnPanel;
import org.cyk.utility.client.controller.component.menu.MenuRenderTypeRowBar;
import org.cyk.utility.client.controller.web.jsf.primefaces.AbstractPageContainerManagedImpl;

import lombok.Getter;
import lombok.Setter;

@Named @ViewScoped @Getter @Setter
public class MenuPage extends AbstractPageContainerManagedImpl implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Menu menuPanel;
	private Menu menuBar;
	
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		menuPanel = __getMenuBuilder__(__inject__(MenuRenderTypeColumnPanel.class)).execute().getOutput();
		menuBar = __getMenuBuilder__(__inject__(MenuRenderTypeRowBar.class)).execute().getOutput();
	}
	
	private MenuBuilder __getMenuBuilder__(MenuRenderType renderType) {
		MenuBuilder menuBuilder = __inject__(MenuBuilder.class).setRenderType(renderType);
		menuBuilder.addItems(
				__inject__(MenuItemBuilder.class).setName("L1")
					.addChild(__inject__(MenuItemBuilder.class).setName("L1.1"),__inject__(MenuItemBuilder.class).setName("L1.2"))
				,__inject__(MenuItemBuilder.class).setName("L2")
				,__inject__(MenuItemBuilder.class).setName("L3")
					.addChild(
							__inject__(MenuItemBuilder.class).setName("L3.1")
							.addChild(__inject__(MenuItemBuilder.class).setName("L3.1.1"),__inject__(MenuItemBuilder.class).setName("L3.1.2"))
							,__inject__(MenuItemBuilder.class).setName("L3.2")));
		return menuBuilder;
	}
	
}
