package org.cyk.utility.client.controller.web.jsf.primefaces.model.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.WebController;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.menu.TabMenu.Tab.MenuItemBuilder;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class TabMenu extends AbstractMenu implements Serializable {

	private Integer activeIndex;
	private Tab selected;
	
	/**/

	/**/
	
	public static final String FIELD_ACTIVE_INDEX = "activeIndex";
	public static final String FIELD_MODEL = "model";
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<TabMenu> implements Serializable {

		@SuppressWarnings("unchecked")
		public void configure(TabMenu tabMenu, java.util.Map<Object,Object> arguments) {
			super.configure(tabMenu, arguments);
			List<Tab> tabs = (List<Tab>) MapHelper.readByKey(arguments, FIELD_TABS);
			if(CollectionHelper.isNotEmpty(tabs)) {
				tabMenu.selected = Tab.getSelectedByRequestParameter(tabs);
				tabMenu.activeIndex = Tab.getIndexOf(tabs, tabMenu.selected);			
			}
			Collection<MenuItem> items = (Collection<MenuItem>) MapHelper.readByKey(arguments, FIELD_ITEMS);
			if(CollectionHelper.isEmpty(items) && CollectionHelper.isNotEmpty(tabs)) {
				Tab.MenuItemBuilder menuItemBuilder = (MenuItemBuilder) MapHelper.readByKey(arguments, FIELD_TAB_MENU_ITEM_BUILDER);
				if(menuItemBuilder == null)
					menuItemBuilder = new MenuItemBuilder.AbstractImpl.DefaultImpl();	
				for(Tab tab : tabs) {
					MenuItem item = menuItemBuilder.build(tabMenu,tab);
					if(item == null)
						continue;
					if(items == null)
						items = new ArrayList<>();
					items.add(item);
				}
			}
			if(CollectionHelper.isEmpty(items))
				return;
			if(tabMenu.model == null)
				tabMenu.model = new DefaultMenuModel();
			for(MenuItem index : items) {
				DefaultMenuItem item = new DefaultMenuItem();
				if(StringHelper.isNotBlank(index.getValue()))
					item.setValue(index.getValue());
				if(StringHelper.isNotBlank(index.getIcon()))
					item.setIcon(index.getIcon());
				String outcome = index.getOutcome();
				if(StringHelper.isBlank(outcome))
					outcome = (String) MapHelper.readByKey(arguments, FIELD_ITEMS_OUTCOME);
				if(StringHelper.isNotBlank(outcome))
					item.setOutcome(outcome);
				if(MapHelper.isNotEmpty(index.getParameters()))
					index.getParameters().forEach( (name,value) -> {
						if(StringHelper.isNotBlank(name) && CollectionHelper.isNotEmpty(value))
							value.stream().filter(x -> StringHelper.isNotBlank(x)).forEach(x -> item.setParam(name, x));
							//item.setParam(name, value);
					});
				tabMenu.model.addElement(item);
			}
		}
		
		@Override
		protected String __getTemplate__(TabMenu object, Map<Object, Object> arguments) {
			return "/menu/tab/default.xhtml";
		}
		
		@Override
		protected Class<TabMenu> __getClass__() {
			return TabMenu.class;
		}
		
		public static final String FIELD_ITEMS = "c.items";
		public static final String FIELD_ITEMS_OUTCOME = "c.items.outcome";
		public static final String FIELD_TABS = "c.tabs";
		public static final String FIELD_TAB_MENU_ITEM_BUILDER = "c.tab.menu.item.builder";
	}
	
	public static TabMenu build(Map<Object, Object> arguments) {
		return Builder.build(TabMenu.class,arguments);
	}
	
	public static TabMenu build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(TabMenu.class, new ConfiguratorImpl());
	}
	
	/**/
	
	@Getter @Setter @Accessors(chain=true) @NoArgsConstructor @AllArgsConstructor
	public static class Tab implements Serializable {
		private String name;
		private String parameterValue;
		
		/**/
		public static String PARAMETER_NAME = "tab";
		/**/
		
		public static Tab getByParameterValue(Collection<Tab> tabs,String value) {
			if(CollectionHelper.isEmpty(tabs) || StringHelper.isBlank(value))
				return null;
			for(Tab tab : tabs)
				if(value.equals(tab.parameterValue))
					return tab;
			return null;
		}
		
		public static Tab getSelectedByRequestParameter(Collection<Tab> tabs,String name,Integer indexIfNull) {
			if(CollectionHelper.isEmpty(tabs) || StringHelper.isBlank(name))
				return null;
			String value = WebController.getInstance().getRequestParameter(name);
			Tab tab = getByParameterValue(tabs,value);
			if(tab == null)
				tab = CollectionHelper.getElementAt(tabs, indexIfNull);
			return tab;
		}
		
		public static Tab getSelectedByRequestParameter(Collection<Tab> tabs) {
			return getSelectedByRequestParameter(tabs, PARAMETER_NAME, 0);
		}
		
		public static Integer getIndexOf(List<Tab> tabs,Tab tab) {
			if(CollectionHelper.isEmpty(tabs) || tab == null)
				return null;
			return tabs.indexOf(tab);
		}
		
		/**/
		
		public static interface MenuItemBuilder {
			MenuItem build(TabMenu tabMenu,Tab tab);
			
			public static abstract class AbstractImpl extends AbstractObject implements MenuItemBuilder,Serializable {
				
				@Override
				public MenuItem build(TabMenu tabMenu,Tab tab) {
					if(tab == null)
						return null;
					MenuItem item = new MenuItem().setValue(tab.getName()).addParameter(TabMenu.Tab.PARAMETER_NAME, tab.getParameterValue());
					process(tabMenu,tab,item);
					return item;
				}
				
				protected void process(TabMenu tabMenu,Tab tab,MenuItem item) {};
				
				public static class DefaultImpl extends AbstractImpl implements Serializable {
					
				}
			}
		}
	}
}