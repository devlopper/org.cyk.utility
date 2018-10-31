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
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

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
		//menuPanel.getProperties().setModel(get());
		
		menuBar = __getMenuBuilder__(__inject__(MenuRenderTypeRowBar.class)).execute().getOutput();
		//menuBar.getProperties().setModel(get());
	}
	
	private MenuBuilder __getMenuBuilder__(MenuRenderType renderType) {
		MenuBuilder menuBuilder = __inject__(MenuBuilder.class).setRenderType(renderType);
		menuBuilder.addItems(
				__inject__(MenuItemBuilder.class).setName("L1")
					.addChild(__inject__(MenuItemBuilder.class).setName("L1.1"))
				,__inject__(MenuItemBuilder.class).setName("L2")
				,__inject__(MenuItemBuilder.class).setName("L3"));
		return menuBuilder;
	}
	
	private MenuModel get() {
		MenuModel model = new DefaultMenuModel();
        
        //First submenu
        DefaultSubMenu firstSubmenu = new DefaultSubMenu("Dynamic Submenu");
         
        DefaultMenuItem item = new DefaultMenuItem("External");
        item.setUrl("http://www.primefaces.org");
        item.setIcon("ui-icon-home");
        firstSubmenu.addElement(item);
         
        model.addElement(firstSubmenu);
         
        //Second submenu
        DefaultSubMenu secondSubmenu = new DefaultSubMenu("Dynamic Actions");
 
        item = new DefaultMenuItem("Save");
        item.setIcon("ui-icon-disk");
        secondSubmenu.addElement(item);
         
        item = new DefaultMenuItem("Delete");
        item.setIcon("ui-icon-close");
        item.setAjax(false);
        secondSubmenu.addElement(item);
         
        item = new DefaultMenuItem("Redirect");
        item.setIcon("ui-icon-search");
        secondSubmenu.addElement(item);
 
        model.addElement(secondSubmenu);
        
        return model;
	}
}
