package org.cyk.utility.client.controller.web.jsf.primefaces.model.menu;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.map.MapHelper;
import org.cyk.utility.__kernel__.object.Builder;
import org.cyk.utility.__kernel__.object.Configurator;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class MenuButton extends AbstractMenu implements Serializable {

	private String value;
	
	/**/
	
	public static final String FIELD_VALUE = "value";
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<MenuButton> implements Serializable {

		@Override
		protected Class<MenuButton> __getClass__() {
			return MenuButton.class;
		}
	}
	
	public static MenuButton build(Map<Object, Object> arguments) {
		return Builder.build(MenuButton.class,arguments);
	}
	
	public static MenuButton build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(MenuButton.class, new ConfiguratorImpl());
	}
}
