package org.cyk.utility.common.utility.userinterface;

import java.io.Serializable;

import org.cyk.utility.common.userinterface.Component;
import org.cyk.utility.common.userinterface.command.Menu;
import org.cyk.utility.common.userinterface.command.MenuNode;
import org.cyk.utility.common.userinterface.container.Form;
import org.cyk.utility.common.userinterface.container.window.Window;
import org.cyk.utility.test.unit.AbstractUnitTest;
import org.junit.Test;

public class UserInterfaceMenuUnitTest extends AbstractUnitTest {
	private static final long serialVersionUID = -6691092648665798471L;
	
	@Test
	public void build(){
		Window window = new Window();
		Menu menu = Menu.build(window, Menu.Type.MAIN);
		assertNotNull(menu);
		assertNotNull(menu.getParent());
	}
	
	@Test
	public void buildTarget(){
		
		Menu menu = new Menu();
		
		MenuNode menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier("L1");
		menu.addOneChild(menuNode);
		
		menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier("L2");
		menu.addOneChild(menuNode);
		
		MenuNode menuNode3 = new MenuNode();
		menuNode3.setLabelFromIdentifier("L3");
		menu.addOneChild(menuNode3);
		menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier("L31");
		menuNode3.addOneChild(menuNode);
		menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier("L32");
		menuNode3.addOneChild(menuNode);
		menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier("L33");
		menuNode3.addOneChild(menuNode);
		
		menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier("L4");
		menu.addOneChild(menuNode);
		
		menuNode = new MenuNode();
		menuNode.setLabelFromIdentifier("L5");
		menu.addOneChild(menuNode);
		
		menu.build();
		
	}
	
	/**/
	
	public static class PrimefacesMenu {
		
	}
	
	public static class PrimefacesMenuItem {
		
	}

	public static class PrimefacesSubMenu {
		
	}
	
	public static class MenuBuilder extends Menu.Builder.Target.Adapter.Default<PrimefacesMenu> implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Override
		protected Object createNotLeaf(PrimefacesMenu menu, MenuNode menuNode) {
			PrimefacesSubMenu subMenu = new PrimefacesSubMenu();
			//subMenu.setLabel((String)menuNode.getLabel().getPropertiesMap().getValue());
			return subMenu;
		}
		
		@Override
		protected Object createLeaf(PrimefacesMenu menu, MenuNode menuNode) {
			PrimefacesMenuItem	menuItem = new PrimefacesMenuItem();
			//menuItem.setValue(menuNode.getLabel().getPropertiesMap().getValue());
			return menuItem;
		}
				
	}
	
	static {
		Component.Listener.COLLECTION.add(new Component.Listener.Adapter(){
			private static final long serialVersionUID = 1L;
			
			@Override
			public Object build(Component component) {
				if(component instanceof Form.Detail)
					return Form.Detail.buildTarget((Form.Detail) component);
				if(component instanceof Menu)
					return new MenuBuilder().setInput((Menu) component).execute();
				return super.build(component);
			}
			
		});
	}
}
