package org.cyk.utility.client.controller.web.jsf.primefaces.model.menu;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;
import org.cyk.utility.__kernel__.string.StringHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class MenuBar extends AbstractMenu implements Serializable {

	private String toggleEvent;
	private Boolean autoDisplay;
	
	/**/
	
	public static final String FIELD_VALUE = "value";
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<MenuBar> implements Serializable {

		@Override
		public void configure(MenuBar menuBar, Map<Object, Object> arguments) {
			super.configure(menuBar, arguments);
			if(StringHelper.isBlank(menuBar.toggleEvent))
				menuBar.toggleEvent = "hover";
			if(menuBar.autoDisplay == null)
				menuBar.autoDisplay = Boolean.TRUE;
		}
		
		@Override
		protected String __getTemplate__(MenuBar menuBar, Map<Object, Object> arguments) {
			return "/menu/bar/default.xhtml";
		}
		
		@Override
		protected Class<MenuBar> __getClass__() {
			return MenuBar.class;
		}
	}
	
	public static MenuBar build(Map<Object, Object> arguments) {
		return Builder.build(MenuBar.class,arguments);
	}
	
	public static MenuBar build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(MenuBar.class, new ConfiguratorImpl());
	}
}