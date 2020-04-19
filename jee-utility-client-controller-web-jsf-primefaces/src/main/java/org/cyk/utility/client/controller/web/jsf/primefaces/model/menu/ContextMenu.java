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
public class ContextMenu extends AbstractMenu implements Serializable {

	private String for_,beforeShow,event,nodeType,selectionMode,targetFilter;
	
	/**/
	
	public static final String FIELD_VALUE = "value";
	
	/**/
	
	public static class ConfiguratorImpl extends AbstractConfiguratorImpl<ContextMenu> implements Serializable {

		@Override
		protected String __getTemplate__(ContextMenu object, Map<Object, Object> arguments) {
			return "/menu/context/default.xhtml";
		}
		
		@Override
		protected Class<ContextMenu> __getClass__() {
			return ContextMenu.class;
		}
	}
	
	public static ContextMenu build(Map<Object, Object> arguments) {
		return Builder.build(ContextMenu.class,arguments);
	}
	
	public static ContextMenu build(Object...objects) {
		return build(MapHelper.instantiate(objects));
	}
	
	static {
		Configurator.set(ContextMenu.class, new ConfiguratorImpl());
	}
}
