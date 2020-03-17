package org.cyk.utility.client.controller.web.jsf.primefaces.model.menu;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.client.controller.web.jsf.primefaces.model.command.AbstractCommand;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MenuItem extends AbstractCommand implements Serializable {

	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<MenuItem> implements Serializable {

		@Override
		public void configure(MenuItem menuItem, Map<Object, Object> arguments) {
			super.configure(menuItem, arguments);
			if(StringHelper.isBlank(menuItem.__actionArgumentIdentifierParameterName__))
				menuItem.__actionArgumentIdentifierParameterName__ = "entityidentifier";
		}
		
		@Override
		protected String __getTemplate__() {
			return "/menu/item/default.xhtml";
		}
		
		@Override
		protected Class<MenuItem> __getClass__() {
			return MenuItem.class;
		}
		
	}
	
	public static MenuItem build(Map<Object, Object> arguments) {
		return Builder.build(MenuItem.class,arguments);
	}
	
	public static MenuItem build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(MenuItem.class, new ConfiguratorImpl());
	}
	
}
